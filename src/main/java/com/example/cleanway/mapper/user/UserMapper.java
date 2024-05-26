package com.example.cleanway.mapper.user;

import com.example.cleanway.domain.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
//    사용자번호로 사용자 조회
    UserDto selectKakaoId(Long userNumber);
//    카카오 이메일로 사용자 조호
    UserDto selectKakaoEmail(String userEmail);
//    사용자 가입
    void insertUserInfo(UserDto userDto);

}
