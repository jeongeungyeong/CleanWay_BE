package com.example.cleanway.domain.vo.crew;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CrewTeamVo {
    private Long crewNumber;
    private Long userNumber;
    private String crewName;
    private String crewWriteTime;
    private String crewContent;
    private Long crewRecruitment;
    private Long crewRoleNumber;
    private Long crewProjectNumber;
//    프로젝트 생성 시간
    private String projectWriteTime;
//    프로젝트 모집 인원
    private Long projectRecruitment;
//    프로젝트 진행 날짜
    private String projectDate;
//    프로젝트 진행 시간
    private String projectTime;
    private String userNickname;
//    프로젝트 참여 인원
     private Long projectJoinMember;
}
