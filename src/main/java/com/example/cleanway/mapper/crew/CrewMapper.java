package com.example.cleanway.mapper.crew;

import com.example.cleanway.domain.dto.crew.CleanCrewDto;
import com.example.cleanway.domain.dto.crew.CleanCrewProjectDto;
import com.example.cleanway.domain.dto.crew.CleanMyCrewDto;
import com.example.cleanway.domain.vo.crew.CrewDetailVo;
import com.example.cleanway.domain.vo.crew.CrewVo;
import com.example.cleanway.domain.vo.crew.MyCrewVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CrewMapper {
    // 크루 모집 리스트(홈)
    public  List<CrewVo> selectCrewList();

    //크루 모집 글쓰기
//    크루 작성자 = 자동 크루장
//    크루 모집인원 = 최초 프로젝트 일치
    public void crewInsert(CleanCrewDto cleanCrewDto);
//크루 모집 상세보기
public List<CrewDetailVo> selectCrewDetail(Long crewNumber);
    //    크루 참여
    public void crewJoinInsert(CleanMyCrewDto cleanMyCrewDto);
    //내 크루 목록 보기
    public List<MyCrewVo> selectMyCrewList(Long userNumber);

}
