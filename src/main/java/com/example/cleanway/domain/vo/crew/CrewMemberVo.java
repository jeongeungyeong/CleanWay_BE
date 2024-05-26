package com.example.cleanway.domain.vo.crew;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CrewMemberVo {
    @Schema(description = "크루 이름")
    private String crewName;
    @Schema(description = "크루 번호")
    private Long crewNumber;
    @Schema(description = "유저 번호")
    private Long userNumber;
    @Schema(description = "유저 닉네임")
    private String userNickname;
    @Schema(description = "크루 역할 번호")
    private Long crewRoleNumber;
    @Schema(description = "크루 역할 이름")
    private String crewRoleName;
    @Schema(description = "크루 가입 날짜")
    private String crewJoinDate;
    @Schema(description = "내 크루 가입 고유번호")
    private Long myProjectJoinNumber;
}
