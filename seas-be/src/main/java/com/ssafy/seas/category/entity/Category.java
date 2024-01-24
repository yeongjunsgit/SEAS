package com.ssafy.seas.category.entity;

import com.ssafy.seas.common.entity.BaseEntity;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {
	private String name;
	private String backgroundColor;
	private String lineColor;
	private Integer quizCount = 0;
}
