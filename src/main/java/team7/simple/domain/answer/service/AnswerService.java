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
import java.util.List;
import java.util.stream.Collectors;

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
        answer.setContent(answerUpdateParam.getContent());

        return answerId;
    }

    @Transactional
    public void deleteAnswer(Long answerId) {
        Answer answer = getAnswerById(answerId);
        answerJpaRepository.delete(answer);
    }

    public Answer getAnswerById(Long id){
        return answerJpaRepository.findById(id).orElseThrow(CAnswerNotFoundException::new);
    }

    /**
     * 질문에 대한 답변 목록을 반환합니다.
     * @param questionId 질문 아이디
     * @return AnswerResponseDto (답변 아이디, 답변 내용, 답변 등록 일자, 답변 수정 일자)
     */
    public List<AnswerResponseDto> getAnswerList(Long questionId) {
        List<Answer> answerList = questionService.getQuestionById(questionId).getAnswerList();
        return answerList.stream().map(AnswerResponseDto::new).collect(Collectors.toList());
    }
}
