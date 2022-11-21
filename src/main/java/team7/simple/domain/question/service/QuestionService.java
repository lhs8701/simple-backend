package team7.simple.domain.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.question.dto.QuestionDetailResponseDto;
import team7.simple.domain.question.dto.QuestionRequestDto;
import team7.simple.domain.question.dto.QuestionUpdateParam;
import team7.simple.domain.question.entity.Question;
import team7.simple.domain.question.repository.QuestionJpaRepository;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.service.UnitService;
import team7.simple.global.error.advice.exception.CQuestionNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionJpaRepository questionJpaRepository;
    private final UnitService unitService;

    @Transactional
    public Long createQuestion(Long unitId, QuestionRequestDto questionRequestDto) {
        Unit unit = unitService.findUnitById(unitId);
        Question question = questionRequestDto.toEntity(unit);

        return questionJpaRepository.save(question).getQuestionId();
    }

    @Transactional
    public Long updateQuestion(QuestionUpdateParam questionUpdateParam) {
        Question question = getQuestionById(questionUpdateParam.getQuestionId());

        question.setTitle(questionUpdateParam.getTitle());
        question.setContent(questionUpdateParam.getContent());
        question.setTimeline(questionUpdateParam.getTimeline());
        question.setCreatedTime(questionUpdateParam.getCreatedTime());

        return question.getQuestionId();
    }

    @Transactional
    public List<QuestionDetailResponseDto> getQuestionList(Long unitId) {
        Unit unit = unitService.findUnitById(unitId);
        return unit.getQuestionList().stream().map(QuestionDetailResponseDto::new).collect(Collectors.toList());
    }

    public Question getQuestionById(Long id){
        return questionJpaRepository.findById(id).orElseThrow(CQuestionNotFoundException::new);
    }

    @Transactional
    public void deleteQuestionById(Long questionId) {
        Question question = getQuestionById(questionId);
        questionJpaRepository.delete(question);
    }

}
