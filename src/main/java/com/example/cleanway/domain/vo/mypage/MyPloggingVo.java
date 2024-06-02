package com.example.cleanway.domain.vo.mypage;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyPloggingVo {
    private Long userNumber;
    private String userEmail;
    private String userNickname;
    private String profileImagePath;
    private Long myPlogging;
}
