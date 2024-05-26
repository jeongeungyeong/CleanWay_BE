package com.example.cleanway.domain.vo.crew;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CrewProjectVo {
    @Schema(description = "크루 번호")
    private Long crewNumber;
    @Schema(description = "회원 번호")
    private Long userNumber;
    @Schema(description = "크루 이름")
    private String crewName;
    private String crewWriteTime;
    private String crewContent;
    private Long crewRecruitment;
    private Long crewRoleNumber;
    private Long crewProjectNumber;
    private String projectWriteTime;
    private String projectContent;
    private Long projectRecruitment;
    private String projectDate;
    private String projectTime;
    private Long projectRoleNumber;
    private Double projectSLng;
    private Double projectSLat;
    private Double projectVLng;
    private Double projectVLat;
    private Double projectDLng;
    private Double projectDLat;
    private String projectRoleName;
    private String crewRoleName;
    private String userNickname;
    private Long projectRouteNumber;
    private String projectSName;
    private String projectVName;
    private String projectDName;
    private String projectTitle;
}
