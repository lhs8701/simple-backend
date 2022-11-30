package team7.simple.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.answer.dto.AnswerResponseDto;
import team7.simple.domain.answer.entity.Answer;
import team7.simple.domain.answer.error.exception.CAnswerNotFoundException;
import team7.simple.domain.answer.repository.AnswerJpaRepository;
import team7.simple.domain.question.service.QuestionFindService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerFindService {
    private final AnswerJpaRepository answerJpaRepository;
    private final QuestionFindService questionFindService;

    @Transactional
    public Answer getAnswerById(Long id){
        return answerJpaRepository.findById(id).orElseThrow(CAnswerNotFoundException::new);
    }
}
