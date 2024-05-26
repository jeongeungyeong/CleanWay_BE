package com.example.cleanway.domain.vo.crew;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CrewVo {
    @Schema(description = "크루 번호")
    private Long crewNumber;
    @Schema(description = "회원 번호")
    private Long userNumber;
    @Schema(description = "크루 이름")
    private String crewName;
    @Schema(description = "크루 등록 시간")
    private String crewWriteTime;
    @Schema(description = "크루 내용")
    private String crewContent;
    @Schema(description = "크루 인원")
    private Long crewRecruitment;
    @Schema(description = "크루 역할 번호")
    private Long crewRoleNumber;
    @Schema(description = "크루 역할 이름")
    private String crewRoleName;
    @Schema(description = "크루 현재참여 인원")
    private Long memberCount;
    @Schema(description = "회원 닉네임")
    private String userNickname;
}
