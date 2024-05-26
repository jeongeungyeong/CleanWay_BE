package com.example.cleanway.domain.dto.crew;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CleanMyProjectDto {
    private Long myProjectJoinNumber;
    private Long userNumber;
    private Long crewNumber;
    private Long crewProjectNumber;
    private Long projectRoleNumber;
    private String projectJoinDate;
}
