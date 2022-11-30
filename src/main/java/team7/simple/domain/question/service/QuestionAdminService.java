package team7.simple.domain.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.question.dto.QuestionUpdateParam;
import team7.simple.domain.question.entity.Question;
import team7.simple.domain.question.repository.QuestionJpaRepository;

@Service
@RequiredArgsConstructor
public class QuestionAdminService {

    private final QuestionFindService questionFindService;
    private final QuestionJpaRepository questionJpaRepository;

    @Transactional
    public Long updateQuestion(Long questionId, QuestionUpdateParam questionUpdateParam) {
        Question question = questionFindService.getQuestionById(questionId);
        question.update(questionUpdateParam.getTitle(), questionUpdateParam.getContent());

        return question.getId();
    }

    @Transactional
    public void deleteQuestion(Long questionId) {
        Question question = questionFindService.getQuestionById(questionId);
        questionJpaRepository.delete(question);
    }
}
