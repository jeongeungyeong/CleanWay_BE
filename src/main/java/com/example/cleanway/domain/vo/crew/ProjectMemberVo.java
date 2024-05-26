package com.example.cleanway.domain.vo.crew;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ProjectMemberVo {
    @Schema(description = "크루 이름")
    private String crewName;
    @Schema(description = "크루 번호")
    private Long crewNumber;
    @Schema(description = "프로젝트 번호")
    private Long crewProjectNumber;
    @Schema(description = "프로젝트 내용")
    private String projectTitle;
    @Schema(description = "프로젝트 내용")
    private String projectContent;
    @Schema(description = "유저 번호")
    private Long userNumber;
    @Schema(description = "유저 닉네임")
    private String userNickname;
    @Schema(description = "프로젝트 역할 번호")
    private Long projectRoleNumber;
    @Schema(description = "프로젝트 역할 이름")
    private String projectRoleName;
    @Schema(description = "프로젝트 가입 날짜")
    private String projectJoinDate;
    @Schema(description = "프로젝트 가입 회원 고유 번호")
    private Long myProjectJoinNumber;
}
