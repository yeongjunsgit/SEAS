package com.ssafy.seas.quiz.entity;


import com.ssafy.seas.common.entity.BaseEntity;
import com.ssafy.seas.flashcard.entity.Flashcard;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class CardQuiz extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "quiz_id", referencedColumnName="id")
    private Quiz quiz;

    @OneToOne
    @JoinColumn(name = "flashcard_id", referencedColumnName="id")
    private Flashcard flashcard;

    public void setQuiz(Quiz quiz){
        this.quiz = quiz;
    }

}
