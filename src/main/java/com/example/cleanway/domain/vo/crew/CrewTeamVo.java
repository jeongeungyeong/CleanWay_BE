package com.example.cleanway.domain.vo.crew;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class CrewTeamVo {
    @Schema(description = "크루 이름")
    private String crewName;
    @Schema(description = "크루 번호")
    private Long crewNumber;
    @Schema(description = "유저 번호")
    private Long userNumber;
    @Schema(description = "유저 닉네임")
    private String userNickname;
    @Schema(description = "유저 프로필 url")
    private String profileImagePath;
    @Schema(description = "프로젝트 번호")
    private Long crewProjectNumber;
    @Schema(description = "프로젝트 역할 번호")
    private Long projectRoleNumber;
    @Schema(description = "프로젝트 역할 이름")
    private String projectRoleName;
    @Schema(description = "프로젝트 생성 시간")
    private String projectWriteTime;
    @Schema(description = "프로젝트 제목")
    private String projectTitle;
    @Schema(description = "프로젝트 내용")
    private String projectContent;
    @Schema(description = "프로젝트 진행 날짜")
    private String projectDate;
    @Schema(description = "프로젝트 진행 시간")
    private String projectTime;
    @Schema(description = "프로젝트 출발지 이름")
    private String projectSName;
    @Schema(description = "프로젝트 출발지경도")
    private Double projectSLng;
    @Schema(description = "프로젝트 출발지위도")
    private Double projectSLat;
    @Schema(description = "프로젝트 경유지 이름")
    private String projectVName;
    @Schema(description = "프로젝트 경유지경도")
    private Double projectVLng;
    @Schema(description = "프로젝트 경유지위도")
    private Double projectVLat;
    @Schema(description = "프로젝트 도착지 이름")
    private String projectDName;
    @Schema(description = "프로젝트 도착지 경도")
    private Double projectDLng;
    @Schema(description = "프로젝트 도착지 위도")
    private Double projectDLat;
    @Schema(description = "프로젝트 가입 날짜")
    private String projectJoinDate;
    @Schema(description = "프로젝트 참여 정원")
    private Long projectRecruitment;
    @Schema(description = "프로젝트 현재 참여 인원")
     private Long projectMemberCount;
    @Schema(description = "완료된 프로젝트 여부")
    private String isPastProject;
}
