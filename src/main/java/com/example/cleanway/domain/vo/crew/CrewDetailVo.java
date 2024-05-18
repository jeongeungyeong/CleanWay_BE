package com.example.cleanway.domain.vo.crew;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CrewDetailVo {
    private Long crewNumber;
    private Long userNumber;
    private String crewName;
    private String crewWriteTime;
    private String crewContent;
    private Long crewRecruitment;
    private Long crewRoleNumber;
    private Long crewProjectNumber;
    private String projectDate;
    private String projectTime;
    private Double projectSLng;
    private Double projectSLat;
    private String projectSName;
    private Double projectVLng;
    private Double projectVLat;
    private String projectVName;
    private Double projectDLng;
    private Double projectDLat;
    private String projectDName;
    @Schema(description = "크루 현재참여 인원")
    private Long memberCount;
    @Schema(description = "회원 닉네임")
    private String userNickname;
}
