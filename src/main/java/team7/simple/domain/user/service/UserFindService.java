package team7.simple.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.error.exception.CUserNotFoundException;
import team7.simple.domain.user.repository.UserJpaRepository;


@Service
@RequiredArgsConstructor
public class UserFindService {

    private final UserJpaRepository userJpaRepository;

    @Transactional
    public User getUserById(String userId) {
        return userJpaRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
    }

    @Transactional
    public User getUserByAccount(String account){
        return userJpaRepository.findByAccount(account).orElseThrow(CUserNotFoundException::new);
    }
}
