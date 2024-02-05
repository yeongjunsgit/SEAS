package com.ssafy.seas.common.logging;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.seas.common.dto.ApiResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingFilter extends OncePerRequestFilter {
	protected static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		MDC.put("traceId", UUID.randomUUID().toString());
		try {
			if (isAsyncDispatch(request)) {
				filterChain.doFilter(request, response);
			} else {
				doFilterWrapped(new RequestWrapper(request), new ResponseWrapper(response), filterChain);
			}
		} catch (Exception ex) {
			handleException(ex, response);
		} finally {
			MDC.clear();
		}
	}

	protected void doFilterWrapped(RequestWrapper request, ContentCachingResponseWrapper response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			logRequest(request);
			filterChain.doFilter(request, response);
		} catch (Exception ex) {
			handleException(ex, response);
			throw ex; // Re-throw the exception to propagate it to the outer catch block
		} finally {
			logResponse(response);
			response.copyBodyToResponse();
		}
	}

	private static void logRequest(RequestWrapper request) throws IOException {
		String queryString = request.getQueryString();
		log.info("Request : {} uri=[{}] content-type=[{}]", request.getMethod(),
			queryString == null ? request.getRequestURI() : request.getRequestURI() + queryString,
			request.getContentType());
		logPayload("Request", request.getContentType(), request.getInputStream());
	}

	private static void logResponse(ContentCachingResponseWrapper response) throws IOException {
		logPayload("Response", response.getContentType(), response.getContentInputStream());
	}

	private static void logPayload(String prefix, String contentType, InputStream inputStream) throws IOException {
		boolean visible = isVisible(MediaType.valueOf(contentType == null ? "application/json" : contentType));
		if (visible) {
			byte[] content = StreamUtils.copyToByteArray(inputStream);
			if (content.length > 0) {
				String contentString = new String(content);
				log.info("{} Payload: {}", prefix, contentString);
			}
		} else {
			log.info("{} Payload: Binary Content", prefix);
		}
	}

	private static boolean isVisible(MediaType mediaType) {
		final List<MediaType> VISIBLE_TYPES = Arrays.asList(MediaType.valueOf("text/*"),
			MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.valueOf("application/*+json"), MediaType.valueOf("application/*+xml"),
			MediaType.MULTIPART_FORM_DATA);
		return VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
	}

	private void handleException(Exception ex, HttpServletResponse response) throws IOException {
		// Log the exception if needed
		log.error("Exception during request processing", ex);
		ex.printStackTrace();
		ApiResponse<?> errorResponse = ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage());
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonResponse = objectMapper.writeValueAsString(errorResponse);
		// Customize the response based on the exception
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		response.getWriter().write(jsonResponse);
	}
}
