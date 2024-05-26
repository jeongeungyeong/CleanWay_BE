package com.example.cleanway.domain.vo.mypage;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyInfoVo {
    private Long userNumber;
    private String userEmail;
    private String userNickname;
    private String newNickname;
}
