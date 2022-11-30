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

    /**
     * 질문을 수정합니다. 관리자용 API입니다.
     * @param questionId 질문 아이디
     * @param questionUpdateParam 질문 수정 파라미터 (질문 제목, 질문 내용)
     * @return 수정한 질문 아이디
     */
    @Transactional
    public Long updateQuestion(Long questionId, QuestionUpdateParam questionUpdateParam) {
        Question question = questionFindService.getQuestionById(questionId);
        question.update(questionUpdateParam.getTitle(), questionUpdateParam.getContent());

        return question.getId();
    }


    /**
     * 질문을 삭제합니다. 관리자용 API입니다.
     * @param questionId 질문 아이디
     */
    @Transactional
    public void deleteQuestion(Long questionId) {
        Question question = questionFindService.getQuestionById(questionId);
        questionJpaRepository.delete(question);
    }
}
