package com.ssafy.seas.ranking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.seas.ranking.entity.Badge;

public interface BadgeRepository extends JpaRepository<Badge, Integer> {

	Optional<Badge> findByName(String name);
}
