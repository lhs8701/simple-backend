package team7.simple.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.answer.dto.AnswerRequestDto;
import team7.simple.domain.answer.dto.AnswerResponseDto;
import team7.simple.domain.answer.dto.AnswerUpdateParam;
import team7.simple.domain.answer.entity.Answer;
import team7.simple.domain.answer.error.exception.CAnswerNotFoundException;
import team7.simple.domain.answer.repository.AnswerJpaRepository;
import team7.simple.domain.auth.error.exception.CAccessDeniedException;
import team7.simple.domain.question.entity.Question;
import team7.simple.domain.question.service.QuestionService;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.error.exception.CUserNotFoundException;
import team7.simple.domain.user.repository.UserJpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerJpaRepository answerJpaRepository;
    private final QuestionService questionService;

    private final UserJpaRepository userJpaRepository;

    @Transactional
    public Long createAnswer(Long questionId, AnswerRequestDto answerRequestDto, User authUser) {
        User user = userJpaRepository.findById(authUser.getId()).orElseThrow(CUserNotFoundException::new);
        Question question = questionService.getQuestionById(questionId);
        Answer answer = answerRequestDto.toEntity(question, user);

        return answerJpaRepository.save(answer).getId();
    }

    @Transactional
    public Long updateAnswer(Long answerId, AnswerUpdateParam answerUpdateParam, User user) {
        Answer answer = getAnswerById(answerId);
        if (!answer.getUser().getAccount().equals(user.getAccount())){
            throw new CAccessDeniedException();
        }
        answer.update(answerUpdateParam.getContent());

        return answerId;
    }

    @Transactional
    public void deleteAnswer(Long answerId, User user) {
        Answer answer = getAnswerById(answerId);
        if (!answer.getUser().getAccount().equals(user.getAccount())){
            throw new CAccessDeniedException();
        }
        answerJpaRepository.delete(answer);
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

    @Transactional
    public Answer getAnswerById(Long id){
        return answerJpaRepository.findById(id).orElseThrow(CAnswerNotFoundException::new);
    }
}
