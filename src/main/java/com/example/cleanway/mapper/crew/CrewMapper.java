package com.example.cleanway.mapper.crew;

import com.example.cleanway.domain.dto.crew.CleanCrewDto;
import com.example.cleanway.domain.dto.crew.CleanCrewProjectDto;
import com.example.cleanway.domain.dto.crew.CleanMyCrewDto;
import com.example.cleanway.domain.dto.crew.CleanMyProjectDto;
import com.example.cleanway.domain.vo.crew.CrewDetailVo;
import com.example.cleanway.domain.vo.crew.CrewVo;
import com.example.cleanway.domain.vo.crew.MyCrewVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CrewMapper {
    // 크루 모집 리스트(홈)
    public  List<CrewVo> selectCrewList();
//    크루 리스트 서치(홈)
    public List<CrewVo> selectCrewByWord(String searchWord);
    //크루 모집 글쓰기
//    크루 작성자 = 자동 크루장
//    크루 모집인원 = 최초 프로젝트 일치
    public void crewInsert(CleanCrewDto cleanCrewDto);
//크루 모집 상세보기
public List<CrewDetailVo> selectCrewDetail(Long crewNumber);
//크루 첫 번째 프로젝트 번호 갖고 오기
Long getCrewProjectNumber(@Param("crewNumber") Long crewNumber, @Param("crewName") String crewName);
    //    크루 참여
    public void crewJoinInsert(CleanMyCrewDto cleanMyCrewDto);
// 특정 회원이 특정 크루에 참여했는지 검사 여부
    int selectMyCrewJoinCount (CleanMyCrewDto cleanMyCrewDto);
 //내 크루 목록 보기
    public List<MyCrewVo> selectMyCrewList(Long userNumber);
//    내 크루 검색어 써치
    public List<MyCrewVo> selectMyCrewByWord(Long userNumber,String searchWord);

}
