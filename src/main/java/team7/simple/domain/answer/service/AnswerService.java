package team7.simple.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.answer.dto.AnswerRequestDto;
import team7.simple.domain.answer.dto.AnswerResponseDto;
import team7.simple.domain.answer.dto.AnswerUpdateParam;
import team7.simple.domain.answer.entity.Answer;
import team7.simple.domain.answer.repository.AnswerJpaRepository;
import team7.simple.domain.question.entity.Question;
import team7.simple.domain.question.service.QuestionService;
import team7.simple.domain.answer.error.exception.CAnswerNotFoundException;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerJpaRepository answerJpaRepository;
    private final QuestionService questionService;

    @Transactional
    public Long createAnswer(Long questionId, AnswerRequestDto answerRequestDto) {
        Question question = questionService.getQuestionById(questionId);
        Answer answer = answerRequestDto.toEntity(question);

        return answerJpaRepository.save(answer).getId();
    }

    @Transactional
    public Long updateAnswer(AnswerUpdateParam answerUpdateParam) {
        Long answerId = answerUpdateParam.getAnswerId();
        Answer answer = getAnswerById(answerId);
        answer.setTitle(answerUpdateParam.getTitle());
        answer.setContent(answerUpdateParam.getContent());

        return answerId;
    }

    @Transactional
    public void deleteAnswer(Long answerId) {
        Answer answer = getAnswerById(answerId);
        answerJpaRepository.delete(answer);
    }

    @Transactional
    public AnswerResponseDto getAnswerInfo(Long answerId) {
        Answer answer = getAnswerById(answerId);
        return new AnswerResponseDto(answer);
    }

    public Answer getAnswerById(Long id){
        return answerJpaRepository.findById(id).orElseThrow(CAnswerNotFoundException::new);
    }
}
