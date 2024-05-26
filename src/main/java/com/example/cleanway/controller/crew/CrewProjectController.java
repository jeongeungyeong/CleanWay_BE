package com.example.cleanway.controller.crew;

import com.example.cleanway.domain.dto.crew.CleanCrewProjectDto;
import com.example.cleanway.domain.dto.crew.CleanMyProjectDto;
import com.example.cleanway.domain.dto.crew.ProjectRequestDto;
import com.example.cleanway.domain.response.CrewSearchResponse;
import com.example.cleanway.domain.response.MyCrewSearchResponse;
import com.example.cleanway.domain.vo.crew.*;
import com.example.cleanway.service.crew.CrewProjectService;
import com.example.cleanway.service.crew.CrewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crew-project")
@RequiredArgsConstructor
@Tag(name = "Crew Project", description = "크루 프로젝트 API")
public class CrewProjectController {
    private final CrewService crewService;
    private final CrewProjectService crewProjectService;

//    크루방 상세 보기
    @GetMapping("/team/{crewNumber}")
    @Operation(summary = "크루방 상세 조회", description = "크루방 정보를 조회합니다.")
    public List<CrewTeamVo> crewTeamList(@PathVariable Long crewNumber){
       return crewProjectService.findCrewProjectList(crewNumber);
    }

//    크루원 상세 보기
    @GetMapping("/team/{crewNumber}/member")
    @Operation(summary = "크루원 정보 상세 조회", description = "크루원 정보를 조회합니다.")
    public List<CrewMemberVo> crewMemberList(@PathVariable Long crewNumber){
        return crewProjectService.crewMemberList(crewNumber);
    }

    // 유저 삭제
    @DeleteMapping("/delete/{crewNumber}/{userNumber}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long crewNumber,
                                           @PathVariable Long userNumber,
                                           @SessionAttribute("userNumber") Long loggedInUserNumber) {
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

/*//    크루 프로젝트 등록
    @PostMapping("/{crewNumber}/add")
    @Operation(summary = "크루 프로젝트 등록", description = "크루 프로젝트 등록합니다.")
    public ResponseEntity<String> makeProject(@PathVariable Long crewNumber,
                                              @Valid @RequestBody ProjectRequestDto projectRequestDto,
                                              BindingResult bindingResult,
                                              @SessionAttribute("userNumber") Long userNumber){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("요청 데이터가 올바르지 않습니다.");
        }
//        크루 이름 가져오기

        // 크루원인지 확인
        if (!crewProjectService.isCrewMember(crewNumber, userNumber)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("크루에 먼저 가입해주세요!");
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
    }*/

    //    크루 프로젝트 등록
    @PostMapping("/{crewNumber}/add")
    @Operation(summary = "크루 프로젝트 등록", description = "크루 프로젝트 등록합니다.")
    public ResponseEntity<String> makeProject(@PathVariable Long crewNumber,
                                              @Valid @RequestBody ProjectRequestDto projectRequestDto,
                                              BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("요청 데이터가 올바르지 않습니다.");
        }
        Long userNumber = 1L;
        // 크루원인지 확인
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
        return crewProjectService.findProjectDetailList(crewNumber,crewProjectNumber);
    }

    //    크루원 평가하기



/*//    크루 프로젝트 참여하기
    @PostMapping("/join/{crewNumber}/{crewProjectNumber}")
    @Operation(summary = "크루 프로젝트원 참여", description = "사용자가 크루 프로젝트에 참여합니다.")
    public ResponseEntity<String> joinProject (@PathVariable Long crewNumber,
                                               @PathVariable Long crewProjectNumber,
                                               @SessionAttribute("userNumber") Long userNumber)
    {
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
    }*/
//    크루 프로젝트 참여하기
@PostMapping("/join/{crewNumber}/{crewProjectNumber}")
@Operation(summary = "크루 프로젝트원 참여", description = "사용자가 크루 프로젝트에 참여합니다.")
public ResponseEntity<String> joinProject (@PathVariable Long crewNumber,
                                           @PathVariable Long crewProjectNumber)
{
    Long userNumber = 1L;
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
    
//    크루 프로젝트원 상세 조회
@GetMapping("/detail/{crewNumber}/{crewProjectNumber}/member")
@Operation(summary = "크루프로젝트원 정보 상세 조회", description = "크루원 프로젝트원 정보를 조회합니다.")
public List<ProjectMemberVo> projectMemberList(@PathVariable Long crewNumber,
                                            @PathVariable Long crewProjectNumber){
    return crewProjectService.projectMemberList(crewNumber,crewProjectNumber);
}


//    이달의 크루원 top3

//    크루장 - 크루원 탈퇴
//    크루장 - 크루 삭제
//    프로젝트장 - 프로젝트원 강퇴
//    프로젝트장 - 프로젝트 삭제
//    프로젝트원 - 프로젝트 삭제

/*    //    내 크루 목록 보기
    @GetMapping("/mycrew")
    @Operation(summary = "내 크루 리스트 조회", description = "참여한 크루 목록을 조회합니다.")
    public List<MyCrewVo> myCrewList(HttpServletRequest req){
        // 세션에서 userNumber 가져오기
        Long userNumber = (Long) req.getSession().getAttribute("userNumber");
        System.out.println(userNumber);
        return crewService.myCrewList(userNumber);
    }*/

    //    내 크루 목록 보기
    @GetMapping("/mycrew")
    @Operation(summary = "내 크루 리스트 조회", description = "참여한 크루 목록을 조회합니다.")
    public List<MyCrewVo> myCrewList(){
        // 세션에서 userNumber 가져오기
        Long userNumber = 1L;
        System.out.println(userNumber);
        return crewService.myCrewList(userNumber);
    }

/*//    내 크루 목록 검색어 조회
  @GetMapping("/mycrew/search")
   @Operation(summary = "내 크루 검색어 조회", description = "가입된 내 크루를 검색을 통해 조회합니다.")
  public ResponseEntity<MyCrewSearchResponse> searchCrewByWord(@RequestParam("searchWord") String searchWord,
                                                               HttpServletRequest req,
                                                               @SessionAttribute("userNumber") Long userNumber) {
    List<MyCrewVo> myCrewByWordList = crewService.findMyCrewByWord(userNumber,searchWord);

    MyCrewSearchResponse response = new MyCrewSearchResponse(myCrewByWordList, searchWord);

    return ResponseEntity.ok(response);
}*/

    //    내 크루 목록 검색어 조회
    @GetMapping("/mycrew/search")
    @Operation(summary = "내 크루 검색어 조회", description = "가입된 내 크루를 검색을 통해 조회합니다.")
    public ResponseEntity<MyCrewSearchResponse> searchCrewByWord(@RequestParam("searchWord") String searchWord) {
        Long userNumber = 1L;
        List<MyCrewVo> myCrewByWordList = crewService.findMyCrewByWord(userNumber,searchWord);

        MyCrewSearchResponse response = new MyCrewSearchResponse(myCrewByWordList, searchWord);

        return ResponseEntity.ok(response);
    }

    //    크루원 - 크루탈퇴
}
