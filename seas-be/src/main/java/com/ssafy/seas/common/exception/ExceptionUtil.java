package com.ssafy.seas.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
	public static String exceptionToString(Exception ex) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
}
