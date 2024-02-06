package com.ssafy.seas.quiz.entity;


import com.ssafy.seas.common.entity.BaseEntity;
import com.ssafy.seas.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Factor extends BaseEntity {

    @Column(name="quiz_interval")
    private Double quizInterval;
    private Double ef;

    @ManyToOne(targetEntity = Member.class)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "card_quiz_id")
    private CardQuiz cardQuiz;

}
