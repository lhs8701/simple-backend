package team7.simple.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.answer.dto.AnswerRequestDto;
import team7.simple.domain.answer.dto.AnswerResponseDto;
import team7.simple.domain.answer.dto.AnswerUpdateParam;
import team7.simple.domain.answer.entity.Answer;
import team7.simple.domain.answer.repository.AnswerJpaRepository;
import team7.simple.domain.question.entity.Question;
import team7.simple.domain.question.service.QuestionFindService;
import team7.simple.domain.question.service.QuestionService;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.error.exception.CUserNotFoundException;
import team7.simple.domain.user.repository.UserJpaRepository;
import team7.simple.global.error.advice.exception.CAccessDeniedException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerFindService answerFindService;
    private final AnswerJpaRepository answerJpaRepository;
    private final QuestionService questionService;

    private final QuestionFindService questionFindService;
    private final UserJpaRepository userJpaRepository;

    /**
     * 답변을 등록합니다.
     * @param questionId 질문 아이디
     * @param answerRequestDto 답변 내용
     * @param authUser 사용자
     * @return 등록된 답변 아이디
     */
    @Transactional
    public Long createAnswer(Long questionId, AnswerRequestDto answerRequestDto, User authUser) {
        User user = userJpaRepository.findById(authUser.getId()).orElseThrow(CUserNotFoundException::new);
        Question question = questionFindService.getQuestionById(questionId);
        Answer answer = answerRequestDto.toEntity(question, user);

        return answerJpaRepository.save(answer).getId();
    }


    /**
     * 답변을 수정합니다. 답변의 등록자만 수정할 권한이 있습니다.
     * @param answerId 수정할 답변 아이디
     * @param answerUpdateParam 답변 수정 파라미터 (답변 내용)
     * @param user 사용자
     * @return 수정된 답변 아이디
     */
    @Transactional
    public Long updateAnswer(Long answerId, AnswerUpdateParam answerUpdateParam, User user) {
        Answer answer = answerFindService.getAnswerById(answerId);
        if (!answer.getUser().getAccount().equals(user.getAccount())){
            throw new CAccessDeniedException();
        }
        answer.update(answerUpdateParam.getContent());

        return answerId;
    }


    /**
     * 답변을 삭제합니다. 답변의 등록자만 삭제할 권한이 있습니다.
     * @param answerId 삭제할 답변 아이디
     * @param user 사용자
     */
    @Transactional
    public void deleteAnswer(Long answerId, User user) {
        Answer answer = answerFindService.getAnswerById(answerId);
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
        List<Answer> answerList = questionFindService.getQuestionById(questionId).getAnswerList();
        return answerList.stream().map(AnswerResponseDto::new).collect(Collectors.toList());
    }


}
