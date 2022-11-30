package team7.simple.domain.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.question.entity.Question;
import team7.simple.domain.question.error.exception.CQuestionNotFoundException;
import team7.simple.domain.question.repository.QuestionJpaRepository;
import team7.simple.domain.unit.service.UnitFindService;

@Service
@RequiredArgsConstructor
public class QuestionFindService {

    private final QuestionJpaRepository questionJpaRepository;


    public Question getQuestionById(Long id){
        return questionJpaRepository.findById(id).orElseThrow(CQuestionNotFoundException::new);
    }
}
