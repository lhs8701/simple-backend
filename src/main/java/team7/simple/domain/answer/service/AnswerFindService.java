package team7.simple.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.answer.dto.AnswerResponseDto;
import team7.simple.domain.answer.entity.Answer;
import team7.simple.domain.answer.error.exception.CAnswerNotFoundException;
import team7.simple.domain.answer.repository.AnswerJpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerFindService {
    private final AnswerJpaRepository answerJpaRepository;
    private final QuestionFindService questionFindService;
    /**
     * 질문에 대한 답변 목록을 반환합니다.
     * @param questionId 질문 아이디
     * @return AnswerResponseDto (답변 아이디, 답변 내용, 답변 등록 일자, 답변 수정 일자)
     */
    public List<AnswerResponseDto> getAnswerList(Long questionId) {
        List<Answer> answerList = questionFindService.getQuestionById(questionId).getAnswerList();
        return answerList.stream().map(AnswerResponseDto::new).collect(Collectors.toList());
    }


    @Transactional
    public Answer getAnswerById(Long id){
        return answerJpaRepository.findById(id).orElseThrow(CAnswerNotFoundException::new);
    }
}
