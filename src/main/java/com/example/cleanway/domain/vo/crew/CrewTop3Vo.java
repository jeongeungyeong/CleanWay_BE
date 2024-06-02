package com.example.cleanway.domain.vo.crew;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CrewTop3Vo {
    private Long rnum;
    private Long userNumber;
    private String userEmail;
    private String userNickname;
    private String profileImagePath;
    private Long projectCount;
}
