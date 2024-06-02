package com.example.cleanway.service.user;

import com.example.cleanway.config.JwtTokenProvider;
import com.example.cleanway.domain.dto.user.UserDto;
import com.example.cleanway.mapper.user.UserMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private static final String PROFILE_IMAGES_PATH = "src/main/resources/static/images/profiles";
    private static final List<String> profileImages = new ArrayList<>();


    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    // 서버가 시작될 때 프로필 이미지 리스트를 초기화
    @PostConstruct
    private void init() {
        File folder = new File(PROFILE_IMAGES_PATH);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".jpg") || name.endsWith(".png"));
        if (files != null) {
            for (File file : files) {
                profileImages.add(file.getName());
            }
        }
    }

//    카카오 이메일로 회원 조회
public UserDto findKakaoEmail(String userEmail) {
    UserDto userDto = userMapper.selectKakaoEmail(userEmail);
    return userDto;
}
//    회원가입
    public void insertUserInfo(UserDto userDto){
        userMapper.insertUserInfo(userDto);
    }
//    랜덤 이미지
public String getRandomProfileImage() {
    int randomIndex = new Random().nextInt(profileImages.size());
    return profileImages.get(randomIndex);
}

    // 토큰으로 유저 이메일 정보 가져 오기
    public String getEmailFromToken(HttpServletRequest request) {
        // 헤더에서 토큰을 추출합니다.
        String token = extractTokenFromHeader(request);

        // 추출한 토큰을 사용하여 이메일 정보를 디코딩합니다.
        String userEmail = jwtTokenProvider.getSubject(token);

        return userEmail;
    }



//    토큰 정보 추출
    public String extractTokenFromHeader(HttpServletRequest request) {
        // "Authorization" 헤더에서 토큰을 추출합니다.
        String bearerToken = request.getHeader("Authorization");

        // "Bearer " 문자열을 분리하여 실제 토큰값을 추출합니다.
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후의 문자열을 반환합니다.
        }

        return null; // 토큰이 없는 경우 null을 반환합니다.
    }

    // 토큰 정보 추출
/*    public String extractTokenFromHeader(HttpServletRequest request) {
        // "Authorization" 헤더에서 토큰을 추출합니다.
        String token = request.getHeader("Authorization");

        // 토큰이 있는지 확인하고 반환합니다.
        if (token != null && !token.isEmpty()) {
            return token; // JWT 문자열을 반환합니다.
        }

        return null; // 토큰이 없는 경우 null을 반환합니다.
    }*/
}
