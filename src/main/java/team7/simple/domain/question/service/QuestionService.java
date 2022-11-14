package team7.simple.domain.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.question.dto.QuestionRequestDto;
import team7.simple.domain.question.dto.QuestionDetailResponseDto;
import team7.simple.domain.question.dto.QuestionThumbnailResponseDto;
import team7.simple.domain.question.dto.QuestionUpdateParam;
import team7.simple.domain.question.entity.Question;
import team7.simple.domain.question.repository.QuestionJpaRepository;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.repository.UnitJpaRepository;
import team7.simple.global.error.advice.exception.CQuestionNotFoundException;
import team7.simple.global.error.advice.exception.CUnitNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionJpaRepository questionJpaRepository;
    private final UnitJpaRepository unitJpaRepository;

    @Transactional
    public Long createQuestion(Long unitId, QuestionRequestDto questionRequestDto) {
        Unit unit = unitJpaRepository.findById(unitId)
                .orElseThrow(CUnitNotFoundException::new);

        Question question = questionRequestDto.toEntity(unit);

        return questionJpaRepository.save(question).getQuestionId();
    }

    @Transactional
    public Long updateQuestion(QuestionUpdateParam questionUpdateParam) {
        Long questionId = questionUpdateParam.getQuestionId();
        Question question = questionJpaRepository.findById(questionId)
                .orElseThrow(CQuestionNotFoundException::new);

        question.setTitle(questionUpdateParam.getTitle());
        question.setContent(questionUpdateParam.getContent());
        question.setTimeline(questionUpdateParam.getTimeline());
        question.setCreatedTime(questionUpdateParam.getCreatedTime());

        return questionId;
    }

    @Transactional
    public void deleteQuestion(Long questionId) {
        Question question = questionJpaRepository.findById(questionId).orElseThrow(CQuestionNotFoundException::new);
        questionJpaRepository.delete(question);
    }

    @Transactional
    public QuestionDetailResponseDto getQuestionInfo(Long questionId) {
        Question question = questionJpaRepository.findById(questionId).orElseThrow(CQuestionNotFoundException::new);
        return new QuestionDetailResponseDto(question);
    }
    @Transactional
    public List<QuestionThumbnailResponseDto> getQuestionList(Long unitId) {
        Unit unit = unitJpaRepository.findById(unitId).orElseThrow(CUnitNotFoundException::new);
        return unit.getQuestionList().stream().map(QuestionThumbnailResponseDto::new).collect(Collectors.toList());
    }
}
