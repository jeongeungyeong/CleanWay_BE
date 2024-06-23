package com.example.cleanway.controller.mypage;

import com.example.cleanway.domain.dto.route.CleanRouteDto;
import com.example.cleanway.domain.dto.user.UserDto;
import com.example.cleanway.domain.vo.mypage.*;
import com.example.cleanway.service.mypage.MypageService;
import com.example.cleanway.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.List;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
@Tag(name="My Page",description = "마이페이지 API")
public class MypageController {
    final MypageService mypageService;
    final UserService userService;


    //    회원정보 가져오기
    @GetMapping("/info")
    public ResponseEntity<MyPloggingVo> getMemberInfo(HttpServletRequest req){
        // 토큰에서 이메일 정보 가져오기
        String userEmail = userService.getEmailFromToken(req);
        System.out.println("클라이언트가 보내는 토큰: " + req.getHeader("Authorization"));
        System.out.println("토큰에서 추출한 이메일: " + userEmail);

        // 토큰 검증 및 유저 정보 가져오기
        UserDto user = userService.findKakaoEmail(userEmail);

        // 유저 정보가 null이 아닌 경우에만 처리
        if (user != null) {
            // 토큰에 포함된 유저 번호를 가져옵니다.
            Long userNumber = user.getUserNumber();
            System.out.println("유저 번호: " + userNumber);

            // 유저 번호를 사용하여 회원 정보를 가져옵니다.
            MyPloggingVo memberDetail = mypageService.findUser(userNumber);

            // 회원 정보가 존재하는 경우에는 해당 정보를 반환하고, 없는 경우에는 404를 반환합니다.
            if (memberDetail != null) {
                return ResponseEntity.ok(memberDetail);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // 닉네임 수정 엔드포인트

    @PatchMapping("/info")
    public ResponseEntity<String> updateNickname(@RequestParam String newNickname,
                                               HttpServletRequest req) {
        // 토큰에서 이메일 정보 가져오기
        String userEmail = userService.getEmailFromToken(req);
        System.out.println("토큰에서 추출한 이메일: " + userEmail);

        // 토큰 검증 및 유저 정보 가져오기
        UserDto user = userService.findKakaoEmail(userEmail);


        try {
            // 사용자 번호 가져오기
            Long userNumber = user.getUserNumber();
            String userNickName = user.getUserNickname();

            // 닉네임 업데이트를 위해 요청 객체 생성
            MyInfoVo myInfoVo = new MyInfoVo();
            myInfoVo.setUserNumber(userNumber);
            myInfoVo.setUserNickname(userNickName);
            myInfoVo.setNewNickname(newNickname);

            // 닉네임 업데이트
            mypageService.modifyNickname(myInfoVo);

            // 성공 메시지 반환
            return ResponseEntity.ok("닉네임이 성공적으로 변경되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("닉네임 변경 중 오류가 발생했습니다.");
        }
    }

//    내 장소 보기
@GetMapping("/myspot")
@Operation(summary = "내 장소 리스트 조회", description = "저장한 제보 스팟을 조회합니다.")
public List<MySpotVo> mySpotList (HttpServletRequest req){
    // 토큰에서 이메일 정보 가져오기
    String userEmail = userService.getEmailFromToken(req);
    System.out.println("토큰에서 추출한 이메일: " + userEmail);

    // 토큰 검증 및 유저 정보 가져오기
    UserDto user = userService.findKakaoEmail(userEmail);

    // 사용자 번호 가져오기
    Long userNumber = user.getUserNumber();

    return mypageService.mySpotList(userNumber);
}

    //    내 루트 보기
@GetMapping("/myroute")
@Operation(summary = "내 루트 리스트 조회", description = "저장한 루트를 조회합니다.")
public List<CleanRouteDto> myRouteList (HttpServletRequest req){
    Long userNumber = 1L;
    return mypageService.myRouteList(userNumber);
}


//    참여한 플로깅 목록
@GetMapping("/myplogging")
@Operation(summary = "내 플로깅 리스트 조회", description = "참여한 플로깅 목록을 조회합니다.")
public List<MyProjectVo> myProjectList (HttpServletRequest req){
    // 토큰에서 이메일 정보 가져오기
    String userEmail = userService.getEmailFromToken(req);
    System.out.println("토큰에서 추출한 이메일: " + userEmail);

    // 토큰 검증 및 유저 정보 가져오기
    UserDto user = userService.findKakaoEmail(userEmail);

    // 사용자 번호 가져오기
    Long userNumber = user.getUserNumber();

    return mypageService.myProjectList(userNumber);
}

    
}
