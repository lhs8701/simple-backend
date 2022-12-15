package team7.simple.domain.answer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.simple.domain.answer.entity.Answer;

public interface AnswerJpaRepository extends JpaRepository<Answer, Long> {
}
