package com.ssafy.seas.quiz.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seas.member.entity.Member;
import com.ssafy.seas.quiz.dto.QuizDto;
import com.ssafy.seas.quiz.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.seas.quiz.entity.QFactor.factor;
import static com.ssafy.seas.quiz.entity.QIncorrectNote.incorrectNote;
import static com.ssafy.seas.quiz.entity.QSolvedQuiz.solvedQuiz;

@Repository
@Slf4j
@RequiredArgsConstructor
public class WrongAnswerRepostory {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    // 틀릴 때 factor가 안 들어감
    @Transactional
    public void saveOrUpdateIncorrectNoteAndSolvedQuiz(Integer memberId, Integer quizId) {
        // IncorrectNote와 SolvedQuiz 엔티티를 조인하여 값을 조회

//        String jpql = "SELECT innote, sq FROM IncorrectNote innote " +
//                "LEFT JOIN SolvedQuiz sq ON innote.member.id = sq.member.id AND innote.quiz.id = sq.quiz.id " +
//                "WHERE innote.member.id = :memberId AND innote.quiz.id = :quizId";

        // innote, solvedQuiz와

        //fetchOne() 대신에 fetch() 메서드를 사용하여
        log.info("HELLO");
        List<Tuple> result = jpaQueryFactory
                .select(solvedQuiz, incorrectNote)
                .from(solvedQuiz)
                .leftJoin(incorrectNote)
                .on(solvedQuiz.member.id.eq(incorrectNote.member.id)
                        .and(solvedQuiz.quiz.id.eq(incorrectNote.quiz.id)))
                .where(solvedQuiz.member.id.eq(memberId)
                        .and(solvedQuiz.quiz.id.eq(quizId)))
                .fetch();
                //.fetchOne();


        log.info("INCORRECTNOTE WITH SOLVEDQUIZ JOIN RESULT SIZE : {}", result.size());
        result.forEach((tp) ->{
            System.out.println("SOLVEQUIZ id: " + tp.get(0, SolvedQuiz.class).getId());
            System.out.println("IncorrectNote is exist : " + tp.get(1, IncorrectNote.class));
        });


//        Member member = entityManager.getReference(Member.class, memberId);
//        Quiz quiz = entityManager.getReference(Quiz.class, quizId);
//
//        Query query = entityManager.createQuery(jpql, Tuple.class);
//        query.setParameter("memberId", memberId);
//        query.setParameter("quizId", quizId);

        if(result.isEmpty()){
            Member member = entityManager.getReference(Member.class, memberId);
            Quiz quiz = entityManager.getReference(Quiz.class, quizId);

            IncorrectNote incorrectNote = new IncorrectNote(member, quiz);
            entityManager.persist(incorrectNote);

            // 틀린 횟수 + 1
            SolvedQuiz solvedQuiz = new SolvedQuiz(member, quiz, 0, 1);
            entityManager.persist(solvedQuiz);
        }

        else {
            Tuple tuple = result.get(0);
            SolvedQuiz solvedQuiz = tuple.get(0, SolvedQuiz.class);
            IncorrectNote innote = tuple.get(1, IncorrectNote.class);
            // 결과가 있으면 값 업데이트
            if (innote != null) {
                entityManager.merge(innote);
            }
            else{
                // innote 새로 생성
            }
            if (solvedQuiz != null) {

                // 업데이트 로직 추가
                log.info("현재 SolvedQuiz correct :{}, fail :{}", solvedQuiz.getCorrectCount(), solvedQuiz.getFailedCount());
                Integer todayFailedSolvedQuiz = solvedQuiz.getFailedCount();
                solvedQuiz.setWrongCount(todayFailedSolvedQuiz + 1);
                log.info("현재 SolvedQuiz correct :{}, fail :{}", solvedQuiz.getCorrectCount(), solvedQuiz.getFailedCount());

                // 저장 또는 업데이트
                entityManager.merge(solvedQuiz);
            }
        }

        entityManager.flush();
    }

    @Transactional
    public void saveOrUpdateFactor(Integer memberId, QuizDto.QuizFactorDto factors){
        String jpql = "SELECT COUNT(f) " +
                "FROM Factor f " +
                "WHERE f.cardQuiz.quiz.id = :quizId AND f.member.id = :memberId";


        Query query = entityManager.createQuery(jpql, Integer.class);
        query.setParameter("quizId", factors.getQuizId());
        query.setParameter("memberId", memberId);

        Member member = entityManager.getReference(Member.class, memberId);
        Quiz quiz = entityManager.getReference(Quiz.class, factors.getQuizId());

        if((Long) query.getSingleResult() == 0) {

            String cardQuizJpql =
                    "SELECT cq " +
                    "FROM CardQuiz cq "+
                    "WHERE cq.quiz.id = :quizId";

            Query cardQuizQuery = entityManager.createQuery(cardQuizJpql, Object[].class);
            cardQuizQuery.setParameter("quizId", factors.getQuizId());

            Object[] result = (Object[]) cardQuizQuery.getSingleResult();

            CardQuiz cardQuiz = (CardQuiz) result[0];

            Factor newFactor = new Factor(member, cardQuiz, factors.getQuizInterval(), factors.getEf());

            entityManager.persist(newFactor);
        }
        else { // Factor update

            log.info("현재 퀴즈 아이디, ef, interval : {}, {}, {} ", factors.getQuizId(), factors.getEf(), factors.getQuizInterval());
            jpaQueryFactory
                    .update(factor)
                    .set(factor.ef, factors.getEf())
                    .set(factor.quizInterval, factors.getQuizInterval())
                    .where(factor.cardQuiz.quiz.id.eq(factors.getQuizId())
                                    .and(factor.member.id.eq(factors.getMemberId())))
                    .execute();

        }

        entityManager.flush();
    }
}
