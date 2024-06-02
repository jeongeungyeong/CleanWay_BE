package com.example.cleanway.domain.vo.mypage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MyProjectVo {
    @Schema(description = "회원 번호")
    private Long userNumber;
    @Schema(description = "크루 번호")
    private Long crewNumber;
    @Schema(description = "크루 이름")
    private String crewName;
    @Schema(description = "프로젝트 역할 번호")
    private Long projectRoleNumber;
    @Schema(description = "프로젝트 역할 이름")
    private String projectRoleName;
    @Schema(description = "플로깅 프로젝트 제목")
    private String projectTitle;
    @Schema(description = "플로깅 프로젝트 내용")
    private String projectContent;
    @Schema(description = "크루 프로젝트 번호")
    private Long crewProjectNumber;
    @Schema(description = "크루 프로젝트 참여하기 날짜")
    private String projectJoinDate;
    @Schema(description = "크루 프로젝트 예정 날짜")
    private String projectDate;
    @Schema(description = "크루 프로젝트 예정 시간")
    private String projectTime;
    @Schema(description = "크루 프로젝트 출발지")
    private String projectSName;
    @Schema(description = "크루 프로젝트 경유지")
    private String projectVName;
    @Schema(description = "크루 프로젝트 도착지")
    private String projectDName;
    @Schema(description = "크루 프로젝트 모집인원")
    private Long projectRecruitment;
    @Schema(description = "크루 프로젝트 현재 참여인원")
    private Long projectMemberCount;
    @Schema(description = "완료된 프로젝트 여부")
    private String isPastProject;


}
