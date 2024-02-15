package com.ssafy.seas.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.seas.quiz.entity.IncorrectNote;

@Repository
public interface IncorrectNoteRepository extends JpaRepository<IncorrectNote, Integer> {
	@org.springframework.transaction.annotation.Transactional
	long  deleteByMemberIdAndQuizId(Integer memberId, Integer quizId);
}
