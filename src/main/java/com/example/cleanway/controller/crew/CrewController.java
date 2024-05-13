package com.example.cleanway.controller.crew;

import com.example.cleanway.domain.dto.crew.CrewRequestDto;
import com.example.cleanway.domain.vo.crew.CrewVo;
import com.example.cleanway.service.crew.CrewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crew")
@RequiredArgsConstructor
@Tag(name = "Crew", description = "크루 API")
public class CrewController {
    private final CrewService crewService;
//    크루 홈 리스트
    @GetMapping("/list")
    @Operation(summary = "크루 리스트 조회", description = "등록된 크루 목록을 조회합니다.")
    public List<CrewVo> crewList(){
        List<CrewVo> crewList = crewService.findCrewList();

        return crewList;
    }

    @PostMapping("/add")
    @Operation(summary = "크루 등록", description = "크루 등록합니다. 최초 등록시 프로젝트도 함께 등록")
    public ResponseEntity<String> addCrew(@Valid @RequestBody CrewRequestDto crewRequestDto,
                                          BindingResult bindingResult){
//      //크루 등록
        crewService.crewRegister(crewRequestDto.getCleanCrewDto());
        Long crewNumber = crewRequestDto.getCleanCrewDto().getCrewNumber();
        Long crewRecruitment = crewRequestDto.getCleanCrewDto().getCrewRecruitment();
        Long UserNumber = crewRequestDto.getCleanCrewDto().getUserNumber();
        String crewContent = crewRequestDto.getCleanCrewDto().getCrewContent();

        //크루 프로젝트 등록
        crewRequestDto.getCleanCrewProjectDto().setCrewNumber(crewNumber);
        crewRequestDto.getCleanCrewProjectDto().setProjectRecruitment(crewRecruitment);
        crewRequestDto.getCleanCrewProjectDto().setUserNumber(UserNumber);
        crewRequestDto.getCleanCrewProjectDto().setProjectContent(crewContent);
        crewService.crewProjectRegister(crewRequestDto.getCleanCrewProjectDto());

        return ResponseEntity.status(HttpStatus.CREATED).body("크루가 성공적으로 등록됐습니다!");
    }


}
