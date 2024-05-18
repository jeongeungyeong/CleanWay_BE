package com.example.cleanway.controller.crew;

import com.example.cleanway.domain.dto.crew.CleanMyCrewDto;
import com.example.cleanway.domain.dto.crew.CrewRequestDto;
import com.example.cleanway.domain.vo.crew.CrewDetailVo;
import com.example.cleanway.domain.vo.crew.CrewVo;
import com.example.cleanway.domain.vo.crew.MyCrewVo;
import com.example.cleanway.service.crew.CrewProjectService;
import com.example.cleanway.service.crew.CrewService;
import com.example.cleanway.service.mypage.MypageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/crew")
@RequiredArgsConstructor
@Tag(name = "Crew", description = "크루 API")
public class CrewController {
    private final CrewService crewService;
    private final CrewProjectService crewProjectService;
    private final MypageService mypageService;

    //    크루 홈 리스트
    @GetMapping("/list")
    @Operation(summary = "크루 리스트 조회", description = "등록된 크루 목록을 조회합니다.")
    public List<CrewVo> crewList(){
        List<CrewVo> crewList = crewService.findCrewList();

        return crewList;
    }
// 크루 등록
    @PostMapping("/add")
    @Operation(summary = "크루 등록", description = "크루 등록합니다. 최초 등록시 프로젝트도 함께 등록")
    public ResponseEntity<String> addCrew(@Valid @RequestBody CrewRequestDto crewRequestDto,
                                          BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("요청 데이터가 올바르지 않습니다.");
        }

        //크루 등록
        crewService.crewRegister(crewRequestDto.getCleanCrewDto());
        Long crewNumber = crewRequestDto.getCleanCrewDto().getCrewNumber();
        Long crewRecruitment = crewRequestDto.getCleanCrewDto().getCrewRecruitment();
//        유저 넘버는 추후 세션
        Long UserNumber = crewRequestDto.getCleanCrewDto().getUserNumber();
        String crewContent = crewRequestDto.getCleanCrewDto().getCrewContent();
        Long crewRoleNumber = crewRequestDto.getCleanCrewDto().getCrewRoleNumber();

        // 크루 참여 (크루장으로 참여)
        crewRequestDto.getCleanMyCrewDto().setCrewNumber(crewNumber);
        crewRequestDto.getCleanMyCrewDto().setCrewRoleNumber(1L);
//        추후 유저 세션
        crewRequestDto.getCleanMyCrewDto().setUserNumber(UserNumber);
        crewService.crewJoinRegister(crewRequestDto.getCleanMyCrewDto());

        //크루 프로젝트 등록
        crewRequestDto.getCleanCrewProjectDto().setCrewNumber(crewNumber);
        crewRequestDto.getCleanCrewProjectDto().setProjectRecruitment(crewRecruitment);
//        추후 세션에서 유저 넘버 등록
        crewRequestDto.getCleanCrewProjectDto().setUserNumber(UserNumber);
        crewRequestDto.getCleanCrewProjectDto().setProjectContent(crewContent);
//        루트 선택 방식
        crewProjectService.crewProjectRegister(crewRequestDto.getCleanCrewProjectDto());
        Long crewProjectNumber = crewRequestDto.getCleanCrewProjectDto().getCrewProjectNumber();
        Long crewProjectRoleNumber = crewRequestDto.getCleanCrewProjectDto().getProjectRoleNumber();
        //        크루 프로젝트 참여
        crewRequestDto.getCleanMyProjectDto().setCrewProjectNumber(crewProjectNumber);
        crewRequestDto.getCleanMyProjectDto().setUserNumber(UserNumber);
        crewRequestDto.getCleanMyProjectDto().setProjectRoleNumber(1L);
        crewRequestDto.getCleanMyProjectDto().setCrewNumber(crewNumber);
        crewProjectService.projectJoinRegister(crewRequestDto.getCleanMyProjectDto());

        return ResponseEntity.status(HttpStatus.CREATED).body("크루가 성공적으로 등록됐습니다!");
    }

//    크루 디테일 화면 > 크루 참여하기
@GetMapping("/detail/{crewNumber}")
@Operation(summary = "크루 상세 조회", description = "크루 상세 조회합니다.")
public List<CrewDetailVo> crewDetail(@PathVariable Long crewNumber){
    return crewService.findCrewDetail(crewNumber);
}

// 크루원 참여하기
    @PostMapping("/join/{crewNumber}")
    @Operation(summary = "크루원 참여", description = "사용자가 크루에 참여합니다.")
    public ResponseEntity<String> joinCrew(@PathVariable Long crewNumber){
//        사용자 번호 (세션에서 가져오기)
//        임시로 사용자 번호 1로 설정
        Long userNumber = 1L;
        CleanMyCrewDto cleanMyCrewDto = new CleanMyCrewDto();
        cleanMyCrewDto.setCrewNumber(crewNumber);
        cleanMyCrewDto.setUserNumber(userNumber);
        cleanMyCrewDto.setCrewRoleNumber(2L);
        try {
            crewService.crewJoinRegister(cleanMyCrewDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("크루 참여가 성공적으로 완료되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("크루 참여에 실패했습니다.");
        }
    }

}
