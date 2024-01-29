package com.ssafy.seas.category.mapper;

import com.ssafy.seas.category.dto.CategoryDto;
import com.ssafy.seas.category.entity.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-29T10:45:19+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDto.Response CategoryToResponseDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto.Response.ResponseBuilder response = CategoryDto.Response.builder();

        if ( category.getId() != null ) {
            response.id( category.getId().longValue() );
        }
        response.name( category.getName() );
        response.backgroundColor( category.getBackgroundColor() );
        response.lineColor( category.getLineColor() );
        response.quizCount( category.getQuizCount() );

        return response.build();
    }
}
