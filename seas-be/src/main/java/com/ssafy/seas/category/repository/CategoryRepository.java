package com.ssafy.seas.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.seas.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}