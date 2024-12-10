package com.mysite.platform.user;

import com.mysite.platform.exception.DataNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 생성자 주입: UserRepository와 PasswordEncoder를 주입받습니다.
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 모든 사용자 조회
     * @return List<SiteUser> - 모든 사용자 리스트 반환
     */
    public List<SiteUser> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * ID로 사용자 조회
     * @param userId 사용자 ID
     * @return SiteUser - 해당 ID의 사용자
     * @throws DataNotFoundException - 사용자 미발견 시 예외 발생
     */
    public SiteUser getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found with ID: " + userId));
    }

    /**
     * 새 사용자 생성
     * @param username 사용자 이름
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호 (암호화 처리됨)
     * @return SiteUser - 생성된 사용자 객체
     */
    public SiteUser create(String username, String email, String password) {
        SiteUser newUser = new SiteUser();
        newUser.setUsername(username);
        newUser.setEmail(email);
        // 비밀번호를 BCrypt로 암호화하여 저장
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(UserRole.USER);
        return userRepository.save(newUser);
    }

    /**
     * 비밀번호 확인
     * @param rawPassword 입력된 원래 비밀번호
     * @param encodedPassword 저장된 암호화된 비밀번호
     * @return boolean - 비밀번호 일치 여부
     */
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
