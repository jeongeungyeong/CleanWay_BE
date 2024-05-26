package com.example.cleanway.domain.dto.crew;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CleanCrewProjectDto {
    private Long crewProjectNumber;
    private Long crewNumber;
    private String projectTitle;
    private String projectWriteTime;
    private String projectContent;
    private Long projectRecruitment;
    private String projectDate;
    private String projectTime;
    private Long projectRoleNumber;
    private Long userNumber;
    @Builder.Default
    private Double projectSLng = 0.0;
    @Builder.Default
    private Double projectSLat = 0.0;
    @Builder.Default
    private Double projectVLng = 0.0;
    @Builder.Default
    private Double projectVLat = 0.0;
    @Builder.Default
    private Double projectDLng = 0.0;
    @Builder.Default
    private Double projectDLat=0.0;
    @Builder.Default
    private String projectSName="출발지 없음";
    @Builder.Default
    private String projectVName = "경유지 없음";
    @Builder.Default
    private String projectDName="도착지 없음";
    @Builder.Default
    private Double projectV2Lng = 0.0;
    @Builder.Default
    private Double projectV2Lat = 0.0;
    @Builder.Default
    private String projectV2Name="경유지2 없음";
    @Builder.Default
    private Double projectV3Lng = 0.0;
    @Builder.Default
    private Double projectV3Lat = 0.0;
    @Builder.Default
    private String projectV3Name = "경유지3 없음";
    @Builder.Default
    private String projectTag1 = "없음";
    @Builder.Default
    private String projectTag2 = "없음";
    @Builder.Default
    private String projectTag3 = "없음";
    @Builder.Default
    private String projectTag4 = "없음";

}
