package com.example.cleanway.controller.crew;

import com.example.cleanway.domain.dto.crew.*;
import com.example.cleanway.domain.dto.user.UserDto;
import com.example.cleanway.domain.response.CrewSearchResponse;
import com.example.cleanway.domain.response.MyCrewSearchResponse;
import com.example.cleanway.domain.vo.crew.*;
import com.example.cleanway.service.crew.CrewProjectService;
import com.example.cleanway.service.crew.CrewService;
import com.example.cleanway.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crew-project")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Crew Project", description = "크루 프로젝트 API")
public class CrewProjectController {
    private final CrewService crewService;
    private final CrewProjectService crewProjectService;
    private final UserService userService;

//    크루방 상세 보기

    @GetMapping("/team/{crewNumber}")
    @Operation(summary = "크루방 상세 조회", description = "크루방 정보를 조회합니다.")
    public CrewRoomDetailVo crewTeamList(@PathVariable Long crewNumber) {
        CrewRoomDetailVo crewRoomDetailVo = new CrewRoomDetailVo();
//        크루 top3
        List<CrewTop3Vo> crewTop3VoList = crewProjectService.findCrewTOP3List(crewNumber);
        crewRoomDetailVo.setCrewTop3VoList(crewTop3VoList);
//        예정 크루 프로젝트 목록 조회
        List<CrewTeamVo> crewProjectList = crewProjectService.findCrewProjectList(crewNumber);
        crewRoomDetailVo.setCrewProjectList(crewProjectList);

//        크루 방 상세 정보 전달
        return crewRoomDetailVo;
    }

//    크루원 상세 보기
    @GetMapping("/team/{crewNumber}/admin")
    @Operation(summary = "크루원 정보 상세 조회", description = "크루원 정보를 조회합니다.")
    public List<CrewMemberVo> crewMemberList(@PathVariable Long crewNumber){
        return crewProjectService.crewMemberList(crewNumber);
    }

    // 유저 삭제
    @DeleteMapping("/team/{crewNumber}/admin/delete/{userNumber}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long crewNumber,
                                           @PathVariable Long userNumber,
                                           HttpServletRequest req) {

        // 토큰에서 이메일 정보 가져오기
        String userEmail = userService.getEmailFromToken(req);
        System.out.println("토큰에서 추출한 이메일: " + userEmail);

        // 토큰 검증 및 유저 정보 가져오기
        UserDto user = userService.findKakaoEmail(userEmail);
        // 토큰에 포함된 유저 번호를 가져옵니다.
        Long loggedInUserNumber = user.getUserNumber();
        System.out.println("유저 번호: " + loggedInUserNumber);

        // 크루장 여부 확인
        CrewMemberVo crewLeader = crewProjectService.findCrewLeader(crewNumber);
        if (crewLeader != null && crewLeader.getUserNumber().equals(loggedInUserNumber)) {
            // 크루장인 경우
            if (userNumber.equals(loggedInUserNumber)) {
                // 자기 자신을 삭제할 수 없음을 알림
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } else {
                // 다른 크루원을 삭제함
                crewProjectService.removeCrewMember(userNumber);
                return ResponseEntity.noContent().build();
            }
        } else {
            // 크루장이 아닌 경우 삭제할 수 없음을 알림
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    //    크루 프로젝트 등록
    @PostMapping("/{crewNumber}/add")
    @Operation(summary = "크루 프로젝트 등록", description = "크루 프로젝트 등록합니다.")
    public ResponseEntity<String> makeProject(@PathVariable Long crewNumber,
                                              @Valid @RequestBody ProjectRequestDto projectRequestDto,
                                              BindingResult bindingResult,
                                              HttpServletRequest req){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("요청 데이터가 올바르지 않습니다.");
        }
        // 토큰에서 이메일 정보 가져오기
        String userEmail = userService.getEmailFromToken(req);
        System.out.println("토큰에서 추출한 이메일: " + userEmail);

        // 토큰 검증 및 유저 정보 가져오기
        UserDto user = userService.findKakaoEmail(userEmail);
        // 사용자 번호 가져오기
        Long userNumber = user.getUserNumber();

        if (!crewProjectService.isCrewMember(crewNumber, userNumber)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("크루에 먼저 가입해주세요!");
        }
//        크루 이름 가져오기
        String crewName = crewProjectService.getCrewName(crewNumber);
        if (crewName == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("크루를 찾을 수 없습니다.");
        }

//        프로젝트 등록
        projectRequestDto.getCleanCrewProjectDto().setCrewNumber(crewNumber);
        projectRequestDto.getCleanCrewProjectDto().setUserNumber(userNumber);
        crewProjectService.crewProjectRegister(projectRequestDto);
        Long crewProjectNumber = projectRequestDto.getCleanCrewProjectDto().getCrewProjectNumber();
        //크루 프로젝트 참여
        CleanMyProjectDto cleanMyProjectDto = new CleanMyProjectDto();
        cleanMyProjectDto.setCrewProjectNumber(crewProjectNumber);
        cleanMyProjectDto.setUserNumber(userNumber);
        cleanMyProjectDto.setProjectRoleNumber(1L);
        cleanMyProjectDto.setCrewNumber(crewNumber);
        crewProjectService.projectJoinRegister(cleanMyProjectDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("프로젝트가 성공적으로 등록되었습니다!");
    }

//    크루 프로젝트 상세보기


    @GetMapping("/detail/{crewNumber}/{crewProjectNumber}")
    @Operation(summary = "크루 프로젝트 상세 조회", description = "크루 프로젝트 정보를 조회합니다.")
    public List<CrewTeamVo> projectDetailList(@PathVariable Long crewNumber,
                                              @PathVariable Long crewProjectNumber){
        log.info("Request received for crewNumber: {}, crewProjectNumber: {}", crewNumber, crewProjectNumber);
        List<CrewTeamVo> details = crewProjectService.findProjectDetailList(crewNumber, crewProjectNumber);
        log.info("Response: {}", details);
        return details;
    }


//    크루 프로젝트 참여하기
    @PostMapping("/join/{crewNumber}/{crewProjectNumber}")
    @Operation(summary = "크루 프로젝트원 참여", description = "사용자가 크루 프로젝트에 참여합니다.")
    public ResponseEntity<String> joinProject (@PathVariable Long crewNumber,
                                               @PathVariable Long crewProjectNumber,
                                               HttpServletRequest req)
    {   // 토큰에서 이메일 정보 가져오기
        String userEmail = userService.getEmailFromToken(req);
        System.out.println("토큰에서 추출한 이메일: " + userEmail);

        // 토큰 검증 및 유저 정보 가져오기
        UserDto user = userService.findKakaoEmail(userEmail);
        // 사용자 번호 가져오기
         Long userNumber = user.getUserNumber();

        if (!crewProjectService.isCrewMember(crewNumber, userNumber)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("크루에 먼저 가입해주세요!");
        }
//        크루 이름 가져오기
        String crewName = crewProjectService.getCrewName(crewNumber);
        if (crewName == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("크루를 찾을 수 없습니다.");
        }

        CleanMyProjectDto cleanMyProjectDto = new CleanMyProjectDto();
        cleanMyProjectDto.setUserNumber(userNumber);
        cleanMyProjectDto.setCrewNumber(crewNumber);
        cleanMyProjectDto.setCrewProjectNumber(crewProjectNumber);
        cleanMyProjectDto.setProjectRoleNumber(2L);
        try{
            crewProjectService.projectJoinRegister(cleanMyProjectDto);
            return ResponseEntity.status(HttpStatus.SEE_OTHER)
                    .header(HttpHeaders.LOCATION,"/detail/"+crewNumber+"/"+crewProjectNumber)
                    .build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("크루 프로젝트 참여에 실패했습니다.");
        }
    }


//    크루 프로젝트원 상세 조회 - 크루장&프로젝트장(관리자 서비스)
@GetMapping("/detail/{crewNumber}/{crewProjectNumber}/member")
@Operation(summary = "크루프로젝트원 정보 상세 조회", description = "크루원 프로젝트원 정보를 조회합니다.")
public List<ProjectMemberVo> projectMemberList(@PathVariable Long crewNumber,
                                            @PathVariable Long crewProjectNumber){
    return crewProjectService.projectMemberList(crewNumber,crewProjectNumber);
}

    //    내 크루 목록 보기
    @GetMapping("/mycrew")
    @Operation(summary = "내 크루 리스트 조회", description = "참여한 크루 목록을 조회합니다.")
    public List<MyCrewVo> myCrewList(HttpServletRequest req){
       // 토큰에서 이메일 정보 가져오기
        String userEmail = userService.getEmailFromToken(req);
        System.out.println("토큰에서 추출한 이메일: " + userEmail);

        // 토큰 검증 및 유저 정보 가져오기
        UserDto user = userService.findKakaoEmail(userEmail);
        // 사용자 번호 가져오기
            Long userNumber = user.getUserNumber();
        System.out.println(userNumber);
        return crewService.myCrewList(userNumber);
    }



//    내 크루 목록 검색어 조회
  @GetMapping("/mycrew/search")
   @Operation(summary = "내 크루 검색어 조회", description = "가입된 내 크루를 검색을 통해 조회합니다.")
  public ResponseEntity<MyCrewSearchResponse> searchCrewByWord(@RequestParam("searchWord") String searchWord,
                                                               HttpServletRequest req ) {
    // 토큰에서 이메일 정보 가져오기
        String userEmail = userService.getEmailFromToken(req);
        System.out.println("토큰에서 추출한 이메일: " + userEmail);

        // 토큰 검증 및 유저 정보 가져오기
        UserDto user = userService.findKakaoEmail(userEmail);
     // 사용자 번호 가져오기
        Long userNumber = user.getUserNumber();

    List<MyCrewVo> myCrewByWordList = crewService.findMyCrewByWord(userNumber,searchWord);

    MyCrewSearchResponse response = new MyCrewSearchResponse(myCrewByWordList, searchWord);

    return ResponseEntity.ok(response);
}

}
