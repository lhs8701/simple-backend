package team7.simple.domain.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.global.error.advice.exception.CAccessDeniedException;
import team7.simple.domain.question.dto.QuestionDetailResponseDto;
import team7.simple.domain.question.dto.QuestionRequestDto;
import team7.simple.domain.question.dto.QuestionThumbnailResponseDto;
import team7.simple.domain.question.dto.QuestionUpdateParam;
import team7.simple.domain.question.entity.Question;
import team7.simple.domain.question.repository.QuestionJpaRepository;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.service.UnitService;
import team7.simple.domain.question.error.exception.CQuestionNotFoundException;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.error.exception.CUserNotFoundException;
import team7.simple.domain.user.repository.UserJpaRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionJpaRepository questionJpaRepository;
    private final UnitService unitService;
    private final UserJpaRepository userJpaRepository;

    @Transactional
    public Long createQuestion(Long unitId, QuestionRequestDto questionRequestDto, User authUser) {
        User user = userJpaRepository.findById(authUser.getId()).orElseThrow(CUserNotFoundException::new);
        Unit unit = unitService.getUnitById(unitId);
        Question question = questionRequestDto.toEntity(unit, user);

        return questionJpaRepository.save(question).getId();
    }

    @Transactional
    public Long updateQuestion(Long questionId, QuestionUpdateParam questionUpdateParam, User user) {
        Question question = getQuestionById(questionId);
        if (!question.getUser().getAccount().equals(user.getAccount())){
            throw new CAccessDeniedException();
        }
        question.update(questionUpdateParam.getTitle(), questionUpdateParam.getContent());

        return question.getId();
    }

    @Transactional
    public void deleteQuestion(Long questionId, User user) {
        Question question = getQuestionById(questionId);
        if (!question.getUser().getAccount().equals(user.getAccount())){
            throw new CAccessDeniedException();
        }
        questionJpaRepository.delete(question);
    }

    /**
     * Unit에 대한 List<Question>을 반환합니다.
     * @param unitId 강의 아이디
     * @return QuestionDetailResponseDto (질문 아이디, 질문 제목, 답변 수, 질문 타임라인)
     */
    @Transactional
    public List<QuestionThumbnailResponseDto> getQuestionList(Long unitId) {
        Unit unit = unitService.getUnitById(unitId);
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
