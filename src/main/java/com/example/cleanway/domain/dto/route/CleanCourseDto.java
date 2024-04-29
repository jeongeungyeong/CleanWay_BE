package com.example.cleanway.domain.dto.route;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CleanCourseDto {
    private Long courseNumber;
    private Long courseCategory;
    private String courseCategoryNm;
    private Long southNorthDiv;
    private String southNorthDivNm;
    private String areaGu;
    private String distance;
    private String leadTime;
    private Long courseLevel;
    private String courseName;
    private String detailCourse;
    private Long cpiIdx;
    private String cpiName;
    private Double x;
    private Double y;
    private String cpiContent;
}
