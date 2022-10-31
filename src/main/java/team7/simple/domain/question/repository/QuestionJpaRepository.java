package team7.simple.domain.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.simple.domain.question.entity.Question;

public interface QuestionJpaRepository extends JpaRepository<Question, Long> {
}
