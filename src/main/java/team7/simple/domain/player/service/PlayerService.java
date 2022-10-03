package team7.simple.domain.player.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team7.simple.domain.player.dto.ExitRequestDto;
import team7.simple.domain.player.dto.StartRequestDto;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.repository.UserJpaRepository;
import team7.simple.global.error.advice.exception.CUserNotFoundException;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final UserJpaRepository userJpaRepository;
    @Value("${path.front_page}")
    String frontPath;
    public String start(StartRequestDto startRequestDto){
        User user = userJpaRepository.findById(startRequestDto.getUserId()).orElseThrow(CUserNotFoundException::new);
        return frontPath +
                "?userId=" + String.valueOf(user.getUserId()) +
                "&courseId=" + String.valueOf(startRequestDto.getCourseId()) +
                "&unitId=" + String.valueOf(startRequestDto.getUnitId());
    }

    public void exit(ExitRequestDto exitRequestDto) {
    }
}
