package team7.simple.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.answer.entity.Answer;
import team7.simple.domain.answer.error.exception.CAnswerNotFoundException;
import team7.simple.domain.answer.repository.AnswerJpaRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AnswerFindService {
    private final AnswerJpaRepository answerJpaRepository;

    @Transactional
    public Answer getAnswerById(Long id){
        return answerJpaRepository.findById(id).orElseThrow(CAnswerNotFoundException::new);
    }
}
