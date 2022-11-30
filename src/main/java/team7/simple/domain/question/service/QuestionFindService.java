package team7.simple.domain.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.question.dto.QuestionDetailResponseDto;
import team7.simple.domain.question.dto.QuestionThumbnailResponseDto;
import team7.simple.domain.question.entity.Question;
import team7.simple.domain.question.error.exception.CQuestionNotFoundException;
import team7.simple.domain.question.repository.QuestionJpaRepository;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.service.UnitFindService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionFindService {

    private final UnitFindService unitFindService;
    private final QuestionJpaRepository questionJpaRepository;

    /**
     * Unit에 대한 List<Question>을 반환합니다.
     * @param unitId 강의 아이디
     * @return QuestionDetailResponseDto (질문 아이디, 질문 제목, 답변 수, 질문 타임라인)
     */
    @Transactional
    public List<QuestionThumbnailResponseDto> getQuestionList(Long unitId) {
        Unit unit = unitFindService.getUnitById(unitId);
        return unit.getQuestionList().stream().map(QuestionThumbnailResponseDto::new).collect(Collectors.toList());
    }

    /**
     * Question의 세부 정보를 반환합니다.
     * @param questionId 질문 아이디
     * @return QuestionDetailResponseDto (강의 아이디, 질문 제목, 질문 내용, 답변 수, 질문 타임라인, 질문 등록 일자, 질문 수정 일자)
     */
    public QuestionDetailResponseDto getQuestionDetail(Long questionId) {
        Question question = getQuestionById(questionId);
        return new QuestionDetailResponseDto(question);
    }


    public Question getQuestionById(Long id){
        return questionJpaRepository.findById(id).orElseThrow(CQuestionNotFoundException::new);
    }
}
