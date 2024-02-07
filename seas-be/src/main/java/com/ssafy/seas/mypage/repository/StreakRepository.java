package com.ssafy.seas.mypage.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.seas.mypage.entity.Streak;

public interface StreakRepository extends JpaRepository<Streak, Integer> {
	List<Streak> findByMemberIdAndCreatedAtAfterOrderByCreatedAt(Integer memberId, LocalDateTime dateTime);
}
