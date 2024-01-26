package com.ssafy.seas.ranking.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.ssafy.seas.ranking.dto.QRankerDto_Response is a Querydsl Projection type for Response
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QRankerDto_Response extends ConstructorExpression<RankerDto.Response> {

    private static final long serialVersionUID = 1169614835L;

    public QRankerDto_Response(com.querydsl.core.types.Expression<String> memberId, com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<String> tier) {
        super(RankerDto.Response.class, new Class<?>[]{String.class, String.class, String.class}, memberId, nickname, tier);
    }

}

