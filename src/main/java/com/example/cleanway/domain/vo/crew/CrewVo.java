package com.example.cleanway.domain.vo.crew;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CrewVo {
    private Long crewNumber;
    private Long userNumber;
    private String crewName;
    private String crewWriteTime;
    private String crewContent;
    private Long crewRecruitment;
    private Long crewRoleNumber;
    private String crewRoleName;
    private Long memberCount;
    private String userNickname;
}
