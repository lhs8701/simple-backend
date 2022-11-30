package team7.simple.domain.answer.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.answer.dto.AnswerUpdateParam;
import team7.simple.domain.answer.entity.Answer;
import team7.simple.domain.answer.repository.AnswerJpaRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AnswerAdminService {

    private final AnswerFindService answerFindService;
    private final AnswerJpaRepository answerJpaRepository;

    /**
     * 답변을 수정합니다. 관리자용 API입니다.
     * @param answerId 수정할 답변 아이디
     * @param answerUpdateParam 답변 수정 파라미터 (답변 내용)
     * @return 수정된 답변 아이디
     */
    @Transactional
    public Long updateAnswer(Long answerId, AnswerUpdateParam answerUpdateParam) {
        Answer answer = answerFindService.getAnswerById(answerId);
        answer.update(answerUpdateParam.getContent());
        return answerId;
    }


    /**
     * 답변을 삭제 합니다. 관리자용 API입니다.
     * @param answerId 삭제할 답변 아이디
     */
    @Transactional
    public void deleteAnswer(Long answerId) {
        Answer answer = answerFindService.getAnswerById(answerId);
        answerJpaRepository.delete(answer);
    }
}
