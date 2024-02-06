package com.ssafy.seas.quiz.entity;


import com.ssafy.seas.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class CardQuiz extends BaseEntity {

//    @OneToMany(mappedBy = "flashcard")
//    private List<Flashcard> flashcards;

    @OneToOne
    @JoinColumn(name = "quiz_id", referencedColumnName="id")
    private Quiz quiz;

}
