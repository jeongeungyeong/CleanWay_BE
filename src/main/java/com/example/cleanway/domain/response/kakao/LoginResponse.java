package com.example.cleanway.domain.response.kakao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String userEmail;
    private String kakaoNickname;
    private String profileImagePath;
    private String token;
}
