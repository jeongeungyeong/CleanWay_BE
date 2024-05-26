package com.example.cleanway.domain.dto.crew;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class ProjectRequestDto {
    private CleanCrewProjectDto cleanCrewProjectDto;
    private List<Double> projectVLng;
    private List<Double> projectVLat;
    private List<String> projectVName;
    private List<String> projectTagList;
}
