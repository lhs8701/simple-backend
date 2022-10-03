package team7.simple.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team7.simple.domain.user.repository.UserJpaRepository;
import team7.simple.global.error.advice.exception.CUserNotFoundException;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String userAccount) throws UsernameNotFoundException {
        return userJpaRepository.findByAccount(userAccount).orElseThrow(CUserNotFoundException::new);
    }
}