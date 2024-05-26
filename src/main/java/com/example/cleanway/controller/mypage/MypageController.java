package com.example.cleanway.controller.mypage;

import com.example.cleanway.domain.dto.route.CleanRouteDto;
import com.example.cleanway.domain.dto.user.UserDto;
import com.example.cleanway.domain.vo.mypage.MyInfoVo;
import com.example.cleanway.domain.vo.mypage.MyRouteVo;
import com.example.cleanway.domain.vo.mypage.MySpotVo;
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
    public ResponseEntity<UserDto> getMemberInfo(HttpServletRequest req){
        // 사용자가 인증되었는지 확인합니다.
        HttpSession session = req.getSession(false); // 세션이 없으면 새로 만들지 않습니다.
        if (session == null || session.getAttribute("userNumber") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 세션에 있는 정보 출력
        System.out.println("현재 세션 정보:");
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            Object attributeValue = session.getAttribute(attributeName);
            System.out.println(attributeName + ": " + attributeValue);
        }

        Long userNumber = (Long) session.getAttribute("userNumber");
        // 정보를 가져옵니다.
        UserDto memberDetail = mypageService.findUser(userNumber);

        if (memberDetail != null) {
            return ResponseEntity.ok(memberDetail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // 닉네임 수정 엔드포인트
    @PatchMapping("/info")
    public ResponseEntity<Void> updateNickname(@RequestParam String newNickname, HttpServletRequest req) {
        // 사용자 인증 확인
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userNumber") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            // 세션에서 사용자 번호 가져오기
            Long userNumber = (Long) session.getAttribute("userNumber");

            // 닉네임 업데이트를 위해 요청 객체 생성
            MyInfoVo myInfoVo = new MyInfoVo();
            myInfoVo.setUserNumber(userNumber);
            myInfoVo.setNewNickname(newNickname);

            // 닉네임 업데이트
            mypageService.modifyNickname(myInfoVo);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    내 장소 보기
    @GetMapping("/myspot")
    @Operation(summary = "내 장소 리스트 조회", description = "저장한 제보 스팟을 조회합니다.")
    public List<MySpotVo> mySpotList (HttpServletRequest req){
        // 세션에서 userNumber 가져오기
        Long userNumber = (Long) req.getSession().getAttribute("userNumber");
        System.out.println("세션 회원번호 확인 "+ userNumber);
        return mypageService.mySpotList(userNumber);
    }

    //    내 루트 보기
@GetMapping("/myroute")
@Operation(summary = "내 루트 리스트 조회", description = "저장한 루트를 조회합니다.")
public List<CleanRouteDto> myRouteList (HttpServletRequest req){
    // 세션에서 userNumber 가져오기
    Long userNumber = (Long) req.getSession().getAttribute("userNumber");
    System.out.println("세션 회원번호 확인 "+ userNumber);
    return mypageService.myRouteList(userNumber);
}

// 참여한 플로깅
}
