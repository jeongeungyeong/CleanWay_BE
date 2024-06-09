package com.example.cleanway.service.user;


import com.example.cleanway.config.AuthTokensGenerator;
import com.example.cleanway.config.JwtTokenProvider;
import com.example.cleanway.domain.dto.user.AuthTokens;
import com.example.cleanway.domain.dto.user.UserDto;
import com.example.cleanway.domain.response.kakao.KakaoInfo;
import com.example.cleanway.domain.response.kakao.LoginResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuthService {
    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    private final UserService userService;
    private final AuthTokensGenerator authTokensGenerator;
    private final JwtTokenProvider  jwtTokenProvider;

//    토큰 받기
    public String getAccessToken(String code) throws JsonProcessingException{
//        HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);


        // (필요 시) 클라이언트 시크릿 추가
         body.add("client_secret", "tC8yy4QrF34Lz2d0PY9BXSWqfl83Cx8s");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return jsonNode.get("access_token").asText();
    }

//액세스 토큰으로 사용자 정보 가져오기
public KakaoInfo getKakaoInfo(String accessToken) throws JsonProcessingException {
    // HTTP Header 생성
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    // HTTP 요청 보내기
    HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
    RestTemplate rt = new RestTemplate();
    ResponseEntity<String> response = rt.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.POST,
            kakaoUserInfoRequest,
            String.class
    );

    // responseBody에 있는 정보 꺼내기
    String responseBody = response.getBody();
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(responseBody);

/*    Long id = jsonNode.get("id").asLong();*/
    String email = jsonNode.get("kakao_account").get("email").asText();
    String nickname = jsonNode.get("properties")
            .get("nickname").asText();

/*    return new KakaoInfo(id, nickname, email);*/
    return new KakaoInfo(nickname, email);
}

// 카카오 사용자 정보 확인 및 회원가입
public UserDto ifNeedKakaoInfo (KakaoInfo kakaoInfo) {
    // DB에 중복되는 email이 있는지 확인
    String userEmail = kakaoInfo.getEmail();
    UserDto kakaoMember = userService.findKakaoEmail(userEmail);

    // 회원가입
    if (kakaoMember == null) {
        String kakaoNickname = kakaoInfo.getNickname();
        // 이메일로 임시 id 발급
        int idx= userEmail.indexOf("@");
        String kakaoId = userEmail.substring(0, idx);

        // 랜덤 프로필 이미지 선택
        String randomProfileImage = userService.getRandomProfileImage();

        UserDto userDto = new UserDto();
        userDto.setUserEmail(userEmail);
        userDto.setUserNickname(kakaoNickname);
        userDto.setProfileImagePath("/images/profiles/" + randomProfileImage); // 프로필 이미지 경로 설정
        userService.insertUserInfo(userDto);
        // DB 재조회
        kakaoMember = userService.findKakaoEmail(userEmail);
    }
    return kakaoMember;
}

// 강제 로그인 및 토큰 발급
public String login(String userEmail){
// 토큰 만료 시간 설정 (5시간)
    long now = (new Date()).getTime();
    Date expiryDate = new Date(now+1000*60*60*5);

//    토큰 생성
    return jwtTokenProvider.accessTokenGenerate(userEmail,expiryDate);
}



// 카카오 전체 로그아웃
    public void kakaoDisconnect(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoLogoutRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v1/user/logout",
                HttpMethod.POST,
                kakaoLogoutRequest,
                String.class
        );

        // responseBody에 있는 정보를 꺼냄
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        Long id = jsonNode.get("id").asLong();
        System.out.println("반환된 id: "+id);
    }


}
