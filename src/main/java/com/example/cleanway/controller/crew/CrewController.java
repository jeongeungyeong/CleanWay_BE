package com.example.cleanway.controller.crew;

import com.example.cleanway.domain.dto.crew.CleanMyCrewDto;
import com.example.cleanway.domain.dto.crew.CleanMyProjectDto;
import com.example.cleanway.domain.dto.crew.CrewRequestDto;
import com.example.cleanway.domain.dto.user.UserDto;
import com.example.cleanway.domain.response.CrewSearchResponse;
import com.example.cleanway.domain.vo.crew.CrewDetailVo;
import com.example.cleanway.domain.vo.crew.CrewVo;
import com.example.cleanway.service.crew.CrewProjectService;
import com.example.cleanway.service.crew.CrewService;
import com.example.cleanway.service.mypage.MypageService;
import com.example.cleanway.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/crew")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Crew", description = "크루 API")
public class CrewController {
    private final CrewService crewService;
    private final CrewProjectService crewProjectService;
    private final MypageService mypageService;
    private final UserService userService;

    //    크루 홈 리스트
    @GetMapping("/list")
    @Operation(summary = "크루 리스트 조회", description = "등록된 크루 목록을 조회합니다.")
    public List<CrewVo> crewList(){
        List<CrewVo> crewList = crewService.findCrewList();

        return crewList;
    }
//    크루 검색어 써치
    @GetMapping("/search")
    @Operation(summary = "크루 검색어 조회", description = "등록된 크루를 검색을 통해 조회합니다.")
    public ResponseEntity<CrewSearchResponse> searchCrewByWord(@RequestParam("searchWord") String searchWord) {
        List<CrewVo> crewByWordList = crewService.findCrewByWord(searchWord);
        CrewSearchResponse response = new CrewSearchResponse(crewByWordList, searchWord);
        return ResponseEntity.ok(response);
    }

// 크루 등록
    @PostMapping("/add")
    @Operation(summary = "크루 등록", description = "크루 등록합니다. 최초 등록시 프로젝트도 함께 등록")
    public ResponseEntity<String> addCrew(@Valid @RequestBody CrewRequestDto crewRequestDto,
                                          BindingResult bindingResult,
                                          HttpServletRequest req){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("요청 데이터가 올바르지 않습니다.");
        }

        Logger logger = LoggerFactory.getLogger(CrewController.class);

        logger.info("Received crewRequestDto: {}", crewRequestDto);
        // 토큰에서 이메일 정보 가져오기
        String userEmail = userService.getEmailFromToken(req);
        System.out.println("토큰에서 추출한 이메일: " + userEmail);

        // 토큰 검증 및 유저 정보 가져오기
        UserDto user = userService.findKakaoEmail(userEmail);
        // 사용자 번호 가져오기
            Long userNumber = user.getUserNumber();

        //크루 등록
        crewRequestDto.getCleanCrewDto().setUserNumber(userNumber);
        crewService.crewRegister(crewRequestDto);
        Long crewNumber = crewRequestDto.getCleanCrewDto().getCrewNumber();
        Long crewRecruitment = crewRequestDto.getCleanCrewDto().getCrewRecruitment();
        String projectTitle = crewRequestDto.getCleanCrewDto().getCrewName()+"의 첫 번째 크루 프로젝트에 참여해보세요!";
        String crewContent = crewRequestDto.getCleanCrewDto().getCrewContent();
        String crewTag1 = crewRequestDto.getCleanCrewDto().getCrewTag1();
        String crewTag2 = crewRequestDto.getCleanCrewDto().getCrewTag2();
        String crewTag3 = crewRequestDto.getCleanCrewDto().getCrewTag3();
        String crewTag4 = crewRequestDto.getCleanCrewDto().getCrewTag4();


        // 크루 참여 (크루장으로 참여)
        CleanMyCrewDto cleanMyCrewDto = new CleanMyCrewDto();
        cleanMyCrewDto.setCrewNumber(crewNumber);
        cleanMyCrewDto.setCrewRoleNumber(1L);
        cleanMyCrewDto.setUserNumber(userNumber);
        crewService.crewJoinRegister(cleanMyCrewDto);

        //크루 프로젝트 등록
        crewRequestDto.getProjectRequestDto().getCleanCrewProjectDto().setCrewNumber(crewNumber);
        crewRequestDto.getProjectRequestDto().getCleanCrewProjectDto().setProjectRecruitment(crewRecruitment);
        crewRequestDto.getProjectRequestDto().getCleanCrewProjectDto().setUserNumber(userNumber);
        crewRequestDto.getProjectRequestDto().getCleanCrewProjectDto().setProjectTitle(projectTitle);
        crewRequestDto.getProjectRequestDto().getCleanCrewProjectDto().setProjectContent(crewContent);
//        루트 선택 방식
//        태그 내용 입력
        crewRequestDto.getProjectRequestDto().getCleanCrewProjectDto().setProjectTag1(crewTag1);
        crewRequestDto.getProjectRequestDto().getCleanCrewProjectDto().setProjectTag2(crewTag2);
        crewRequestDto.getProjectRequestDto().getCleanCrewProjectDto().setProjectTag3(crewTag3);
        crewRequestDto.getProjectRequestDto().getCleanCrewProjectDto().setProjectTag4(crewTag4);
        crewProjectService.crewProjectRegister(crewRequestDto.getProjectRequestDto());
        Long crewProjectNumber = crewRequestDto.getProjectRequestDto().getCleanCrewProjectDto().getCrewProjectNumber();
        //        크루 프로젝트 참여
        CleanMyProjectDto cleanMyProjectDto = new CleanMyProjectDto();
        cleanMyProjectDto.setCrewProjectNumber(crewProjectNumber);
        cleanMyProjectDto.setUserNumber(userNumber);
        cleanMyProjectDto.setProjectRoleNumber(1L);
        cleanMyProjectDto.setCrewNumber(crewNumber);
        crewProjectService.projectJoinRegister(cleanMyProjectDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("크루가 성공적으로 등록됐습니다!");
    }



//    크루 디테일 화면
@GetMapping("/detail/{crewNumber}")
@Operation(summary = "크루 상세 조회", description = "크루 상세 조회합니다.")
public List<CrewDetailVo> crewDetail(@PathVariable Long crewNumber){
    return crewService.findCrewDetail(crewNumber);
}

// 크루원 참여하기
    @PostMapping("/join/{crewNumber}")
    @Operation(summary = "크루원 참여", description = "사용자가 크루에 참여합니다.")
    public ResponseEntity<String> joinCrew(@PathVariable Long crewNumber,
                                           HttpServletRequest req){
// 토큰에서 이메일 정보 가져오기
        String userEmail = userService.getEmailFromToken(req);
        System.out.println("토큰에서 추출한 이메일: " + userEmail);

        // 토큰 검증 및 유저 정보 가져오기
        UserDto user = userService.findKakaoEmail(userEmail);
        // 사용자 번호 가져오기
            Long userNumber = user.getUserNumber();

        //        크루 이름 가져오기
        String crewName = crewProjectService.getCrewName(crewNumber);
        if (crewName == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("크루를 찾을 수 없습니다.");
        }

//        크루 프로젝트 번호 갖고 오기
        Long crewProjectNumber = crewService.findCrewProjectNumber(crewNumber, crewName);
        if (crewProjectNumber == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("크루 첫 번째 프로젝트를 찾을 수 없습니다.");
        }

        //        크루 참여
        CleanMyCrewDto cleanMyCrewDto = new CleanMyCrewDto();
        cleanMyCrewDto.setCrewNumber(crewNumber);
        cleanMyCrewDto.setUserNumber(userNumber);
        cleanMyCrewDto.setCrewRoleNumber(2L);
        // 크루 프로젝트 참여
        CleanMyProjectDto cleanMyProjectDto = new CleanMyProjectDto();
        cleanMyProjectDto.setCrewProjectNumber(crewProjectNumber);
        cleanMyProjectDto.setUserNumber(userNumber);
        cleanMyProjectDto.setProjectRoleNumber(2L);
        cleanMyProjectDto.setCrewNumber(crewNumber);

        try {
            crewService.crewJoinRegister(cleanMyCrewDto);
            crewProjectService.projectJoinRegister(cleanMyProjectDto);
//            크루 참여가 성공하면 리다이렉트
            return ResponseEntity.status(HttpStatus.SEE_OTHER)
                    .header(HttpHeaders.LOCATION, "/crew-project/team/"+crewNumber)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("크루 참여에 실패했습니다.");
        }
    }


}
