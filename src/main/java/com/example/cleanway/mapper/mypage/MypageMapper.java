package com.example.cleanway.mapper.mypage;

import com.example.cleanway.domain.dto.route.CleanRouteDto;
import com.example.cleanway.domain.dto.user.UserDto;
import com.example.cleanway.domain.vo.mypage.MyInfoVo;
import com.example.cleanway.domain.vo.mypage.MyRouteVo;
import com.example.cleanway.domain.vo.mypage.MySpotVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MypageMapper {
     //    회원정보 조회
    UserDto selectUser (Long userNumber);
     //    닉네임 업데이트
    void updateNickname(MyInfoVo myInfoVo);
    //    내 장소 보기
    public List<MySpotVo> selectMySpot(Long userNumber);
    //    내 루트 보기
    public List<CleanRouteDto> selectRouteList(Long userNumber);
}
