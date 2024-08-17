package com.baeda.baeda.user;

import com.baeda.baeda.common.exception.BusinessLogicException;
import com.baeda.baeda.common.exception.ErrorCode;
import com.baeda.baeda.common.util.EnumConverter;
import com.baeda.baeda.user.dto.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(CreateUserRequest request){
        User user = User.builder()
            .email(request.email())
            .nickname(request.nickname())
            .myTeam(EnumConverter.convertToTeam(request.team()))
            .encodedPassword(passwordEncoder.encode(request.password()))
            .build();

        repository.save(user);
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
            .orElseThrow(() -> new BusinessLogicException(ErrorCode.USER_NOT_FOUND));
    }
}
