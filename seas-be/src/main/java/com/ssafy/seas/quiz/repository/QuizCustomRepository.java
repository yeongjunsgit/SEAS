package com.ssafy.seas.quiz.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seas.category.entity.Category;
import com.ssafy.seas.member.entity.Member;
import com.ssafy.seas.quiz.dto.QQuizDto_QuizInfoDto;
import com.ssafy.seas.quiz.dto.QuizDto;
import com.ssafy.seas.quiz.entity.ScoreHistory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.seas.quiz.entity.QFactor.factor;
import static com.ssafy.seas.quiz.entity.QQuiz.quiz;
import static com.ssafy.seas.quiz.entity.QQuizAnswer.quizAnswer;


@Repository
@Slf4j
@RequiredArgsConstructor
public class QuizCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    // 멤버별 factor 테이블의 퀴즈 정보를 가져옴
    public List<QuizDto.QuizFactorDto> findAllQuizInnerJoin(Integer memberId, Integer categoryId) {

        log.info(memberId + "||" + categoryId);

        // log 찍어보면.. member.id가 다 null로 나온다 왜지? ef랑 interval은 잘 들어오는 것 같아..
        List<Tuple> tupleList =
                jpaQueryFactory
                        .select(
                                quiz.id,
                                quiz.problem,
                                quiz.hint,
                                factor.quizInterval.coalesce(1.0),
                                factor.ef.coalesce(1.3),
                                factor.member.id.coalesce(-1),
                                quiz.category.id
                        )
                        .from(quiz)
                        .leftJoin(factor).on(factor.cardQuiz.quiz.id.eq(quiz.id).and(factor.member.id.eq(memberId.intValue())))
                        .where(quiz.category.id.eq(categoryId.intValue()))
                        .fetch();

        //tupleList.stream().forEach(System.out::println);

        List<QuizDto.QuizFactorDto> QuizInfoList =
                tupleList.stream().map(dto -> new QuizDto.QuizFactorDto(
                        dto.get(factor.member.id.coalesce(-1)),
                        dto.get(quiz.id),
                        categoryId,
                        dto.get(quiz.problem),
                        dto.get(quiz.hint),
                        dto.get(factor.quizInterval.coalesce(1.0)),
                        dto.get(factor.ef.coalesce(1.3))
                )).collect(Collectors.toList());

        QuizInfoList.stream().forEach(System.out::println);
        return QuizInfoList;    }

    public List<QuizDto.QuizInfoDto> findQuizzesLimitedBy(Integer requiredCount, Integer categoryId){
        return
                jpaQueryFactory
                .select(new QQuizDto_QuizInfoDto(quiz.id, quiz.problem, quiz.hint))
                .from(quiz)
                .where(quiz.category.id.eq(categoryId.intValue()))
                .fetch();
    }


    public List<String> findAllQuizAnswerByQuizId(Integer quizId){

        return jpaQueryFactory
                .select(quizAnswer.answer)
                .from(quiz)
                .leftJoin(quizAnswer)
                .on(quiz.id.eq(quizAnswer.quiz.id))
                .where(quiz.id.eq(quizId))
                .stream()
                .map(ans -> ans.replaceAll("\s+", "").replaceAll("\t+", "").replaceAll(" ", "").toLowerCase().trim())
                .collect(Collectors.toList());

    }

    @Transactional
    public void saveScoreHistory(Integer memberId, Integer categoryId, Integer score){

        Member member = entityManager.getReference(Member.class, memberId);
        Category category = entityManager.getReference(Category.class, categoryId);

        ScoreHistory scoreHistory = new ScoreHistory(member, category, score);
        entityManager.persist(scoreHistory);

    }


}
