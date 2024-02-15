package com.ssafy.seas.ranking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.seas.ranking.entity.MemberBadge;

public interface MemberBadgeRepository extends JpaRepository<MemberBadge, Integer> {

	Optional<MemberBadge> findByMemberIdAndBadgeId(Integer memberId, Integer BadgeId);
}
