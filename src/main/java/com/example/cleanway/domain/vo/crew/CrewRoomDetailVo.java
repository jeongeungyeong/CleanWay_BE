package com.example.cleanway.domain.vo.crew;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class CrewRoomDetailVo {
    @Schema(description = "크루원 top3")
    private List<CrewTop3Vo> crewTop3VoList;
    @Schema(description = "크루 예정 프로젝트 리스트")
    private List<CrewTeamVo> crewProjectList;
}
