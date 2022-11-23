package team7.simple.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.user.dto.PasswordUpdateParam;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.repository.UserJpaRepository;
import team7.simple.domain.user.error.exception.CUserNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userJpaRepository;
    public void changePassword(String userId, PasswordUpdateParam passwordUpdateParam){
        User user = userJpaRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        user.setPassword(passwordEncoder.encode(passwordUpdateParam.getPassword()));
    }
}
