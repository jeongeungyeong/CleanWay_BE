package com.example.cleanway.domain.response.kakao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakaoInfo {
    private Long id;
    private String nickname;
    private String email;
}
