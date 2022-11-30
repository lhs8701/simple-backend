package team7.simple.domain.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.question.dto.QuestionDetailResponseDto;
import team7.simple.domain.question.dto.QuestionThumbnailResponseDto;
import team7.simple.domain.question.entity.Question;
import team7.simple.domain.question.error.exception.CQuestionNotFoundException;
import team7.simple.domain.question.repository.QuestionJpaRepository;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.service.UnitFindService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionFindService {

    private final UnitFindService unitFindService;
    private final QuestionJpaRepository questionJpaRepository;


    public Question getQuestionById(Long id){
        return questionJpaRepository.findById(id).orElseThrow(CQuestionNotFoundException::new);
    }
}
