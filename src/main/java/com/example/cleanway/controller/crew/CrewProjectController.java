package com.example.cleanway.controller.crew;

import com.example.cleanway.domain.vo.crew.MyCrewVo;
import com.example.cleanway.service.crew.CrewProjectService;
import com.example.cleanway.service.crew.CrewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/crew-project")
@RequiredArgsConstructor
@Tag(name = "Crew Project", description = "크루 프로젝트 API")
public class CrewProjectController {
    private final CrewService crewService;
    private final CrewProjectService crewProjectService;

    //    내 크루 목록 보기
    @GetMapping("/mycrew")
    @Operation(summary = "내 크루 리스트 조회", description = "참여한 크루 목록을 조회합니다.")
    public List<MyCrewVo> myCrewList(@RequestParam("userNumber") Long userNumber){

        return crewService.myCrewList(userNumber);
    }
}
