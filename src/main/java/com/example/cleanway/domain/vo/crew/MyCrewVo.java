package com.example.cleanway.domain.vo.crew;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MyCrewVo {
    @Schema(description = "크루 번호")
    private Long crewNumber;
    @Schema(description = "회원 번호")
    private Long userNumber;
    @Schema(description = "크루 이름")
    private String crewName;
    @Schema(description = "크루 인원")
    private Long crewRecruitment;
    @Schema(description = "크루 역할 번호")
    private Long crewRoleNumber;
    @Schema(description = "크루 현재참여 인원")
    private Long memberCount;
    @Schema(description = "회원 닉네임")
    private String userNickname;
    @Schema(description = "크루 가입 날짜")
    private String crewJoinDate;
}
