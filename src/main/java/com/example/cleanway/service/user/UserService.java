package com.example.cleanway.service.user;

import com.example.cleanway.domain.dto.user.UserDto;
import com.example.cleanway.mapper.user.UserMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private static final String PROFILE_IMAGES_PATH = "src/main/resources/static/images/profiles";
    private static final List<String> profileImages = new ArrayList<>();

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



}
