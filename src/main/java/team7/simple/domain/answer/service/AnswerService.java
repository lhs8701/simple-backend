package team7.simple.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.answer.dto.AnswerRequestDto;
import team7.simple.domain.answer.dto.AnswerResponseDto;
import team7.simple.domain.answer.dto.AnswerUpdateParam;
import team7.simple.domain.answer.entity.Answer;
import team7.simple.domain.answer.repository.AnswerJpaRepository;
import team7.simple.domain.question.entity.Question;
import team7.simple.domain.question.repository.QuestionJpaRepository;
import team7.simple.global.error.advice.exception.CAnswerNotFoundException;
import team7.simple.global.error.advice.exception.CQuestionNotFoundException;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerJpaRepository answerJpaRepository;
    private final QuestionJpaRepository questionJpaRepository;

    @Transactional
    public Long createAnswer(Long questionId, AnswerRequestDto answerRequestDto) {
        Question question = questionJpaRepository.findById(questionId)
                .orElseThrow(CQuestionNotFoundException::new);

        Answer answer = answerRequestDto.toEntity(question);

        return answerJpaRepository.save(answer).getAnswerId();
    }

    @Transactional
    public Long updateAnswer(AnswerUpdateParam answerUpdateParam) {
        Long answerId = answerUpdateParam.getAnswerId();
        Answer answer = answerJpaRepository.findById(answerId)
                .orElseThrow(CAnswerNotFoundException::new);

        answer.setTitle(answerUpdateParam.getTitle());
        answer.setContent(answerUpdateParam.getContent());

        return answerId;
    }

    @Transactional
    public void deleteAnswer(Long answerId) {
        Answer answer = answerJpaRepository.findById(answerId)
                .orElseThrow(CAnswerNotFoundException::new);
        answerJpaRepository.delete(answer);
    }

    @Transactional
    public AnswerResponseDto getAnswerInfo(Long answerId) {
        Answer answer = answerJpaRepository.findById(answerId)
                .orElseThrow(CAnswerNotFoundException::new);
        return new AnswerResponseDto(answer);
    }
}
