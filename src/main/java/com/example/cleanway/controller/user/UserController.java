package com.example.cleanway.controller.user;


import com.example.cleanway.domain.dto.user.UserDto;
import com.example.cleanway.domain.response.kakao.KakaoInfo;
import com.example.cleanway.service.user.OAuthService;
import com.example.cleanway.service.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao")
@Tag(name = "User", description = "카카오 회원가입/로그인 API")
public class UserController {
    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    @Autowired
    private OAuthService oAuthService;
    @Autowired
    private UserService userService;

// 프론트 로그인 화면
    @GetMapping("/login")
    public ResponseEntity<Void> redirectToKakaoAuth() {
        String kakaoAuthUrl = String.format("https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code", clientId, redirectUri);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(kakaoAuthUrl));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/callback")
    public ResponseEntity<String> kakaoCallback(@RequestParam("code") String code,
                                                HttpSession session){
        // SETP1 : 인가코드 받기
        System.out.println("코드  " +code);

        // STEP2: 인가코드를 기반으로 토큰(Access Token) 발급
        String accessToken = null;
        try {
            accessToken = oAuthService.getAccessToken(code);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("엑세스 토큰  "+accessToken);

        // STEP3: 토큰를 통해 사용자 정보 조회
        KakaoInfo kakaoInfo = null;
        try {
            kakaoInfo = oAuthService.getKakaoInfo(accessToken);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("이메일 확인 "+kakaoInfo.getEmail());
/*        System.out.println("카카오id 확인 "+kakaoInfo.getId());*/

        // STEP4: 카카오 사용자 정보 확인
        UserDto kakaoMember = oAuthService.ifNeedKakaoInfo(kakaoInfo);

        // STEP5: 사용자 토큰 제공
        String token = oAuthService.login(kakaoMember.getUserEmail());
        System.out.println("서버 제공 토큰 "+token);

        // STEP6: 강제 로그인
        // 세션에 회원 정보 저장 & 세션 유지 시간 설정
        if (kakaoMember != null) {
            session.setAttribute("loginMember", kakaoMember);
            // session.setMaxInactiveInterval( ) : 세션 타임아웃을 설정하는 메서드
            // 로그인 유지 시간 설정 (3600초 == 1시간)
            session.setMaxInactiveInterval(60 * 60);
            // 로그아웃 시 사용할 카카오토큰 추가
            session.setAttribute("kakaoToken", accessToken);
        // 추가적으로 사용자 정보를 세션에 저장
            session.setAttribute("userNumber", kakaoMember.getUserNumber());
            session.setAttribute("userNickname", kakaoMember.getUserNickname());
            System.out.println("세션 번호 확인 "+kakaoMember.getUserNumber());
        }

        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .header(HttpHeaders.LOCATION, "/mypage/info")
                .build();
    }

    //    프론트에서 유저 정보 받아서 로그인(+회원가입)
   @PostMapping("/login")
   public ResponseEntity<?> kakaoLogin(@RequestBody KakaoInfo kakaoInfo){
        UserDto user = oAuthService.ifNeedKakaoInfo(kakaoInfo);
        String token = oAuthService.login(user.getUserEmail());
        System.out.println("토큰 번호 확인: " + token);
        return ResponseEntity.ok(token);
   }

    @GetMapping("member/logout")
    public String kakaoLogout(HttpSession session, HttpServletResponse response) {
        String accessToken = (String) session.getAttribute("kakaoToken");

        if (accessToken != null && !"".equals(accessToken)) {
            try {
                oAuthService.kakaoDisconnect(accessToken);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("accessToken is null");
        }

        return "redirect:/kakao/login";
    }

    @GetMapping("/logout")
    public void kakaoLogout(HttpServletResponse response) throws IOException {
        // 로그아웃 리다이렉트 URI 설정
        String logoutRedirectUri = "http://localhost:10000/kakao/logout/callback";

        // 로그아웃 요청 URL 생성
        String kakaoLogoutUrl = "https://kauth.kakao.com/oauth/logout?client_id=" + clientId + "&logout_redirect_uri=" + logoutRedirectUri;

        // 카카오 로그아웃 요청을 통해 카카오계정과 함께 로그아웃하기 위한 GET 요청 보내기
        response.sendRedirect(kakaoLogoutUrl);
    }

    @GetMapping("/logout/callback")
    public ResponseEntity<Void> redirectToLoginPage() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/kakao/login"));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

}
