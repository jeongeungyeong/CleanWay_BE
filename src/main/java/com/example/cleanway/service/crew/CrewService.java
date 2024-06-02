package com.example.cleanway.service.crew;

import com.example.cleanway.domain.dto.crew.CleanCrewDto;
import com.example.cleanway.domain.dto.crew.CleanMyCrewDto;
import com.example.cleanway.domain.dto.crew.CrewRequestDto;
import com.example.cleanway.domain.vo.crew.CrewDetailVo;
import com.example.cleanway.domain.vo.crew.CrewVo;
import com.example.cleanway.domain.vo.crew.MyCrewVo;
import com.example.cleanway.mapper.crew.CrewMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class CrewService {
    private final CrewMapper crewMapper;

//    크루 리스트 조회
    public List<CrewVo> findCrewList(){
        List<CrewVo> crewList = crewMapper.selectCrewList();
        return crewList;
    }

//    크루 검색키워드 조회
    public List<CrewVo> findCrewByWord(String searchWord){
        return crewMapper.selectCrewByWord(searchWord);
    }


//    크루 모집 등록
    public void crewRegister(CrewRequestDto crewRequestDto){
        CleanCrewDto cleanCrewDto = crewRequestDto.getCleanCrewDto();

        List<String> crewTagList = crewRequestDto.getCrewTagList();

        if (crewTagList != null){
            if(crewTagList.size()>0) cleanCrewDto.setCrewTag1(crewTagList.get(0));
            if(crewTagList.size()>1) cleanCrewDto.setCrewTag2(crewTagList.get(1));
            if(crewTagList.size()>2) cleanCrewDto.setCrewTag3(crewTagList.get(2));
            if(crewTagList.size()>3) cleanCrewDto.setCrewTag4(crewTagList.get(3));
        } else {
//            기본값 설정
            if(cleanCrewDto.getCrewTag1() == null || cleanCrewDto.getCrewTag1().isEmpty()) {
                cleanCrewDto.setCrewTag1("없음");
            }
            if(cleanCrewDto.getCrewTag2() == null || cleanCrewDto.getCrewTag2().isEmpty()) {
                cleanCrewDto.setCrewTag2("없음");
            }
            if(cleanCrewDto.getCrewTag3() == null || cleanCrewDto.getCrewTag3().isEmpty()) {
                cleanCrewDto.setCrewTag3("없음");
            }
            if(cleanCrewDto.getCrewTag4() == null || cleanCrewDto.getCrewTag4().isEmpty()) {
                cleanCrewDto.setCrewTag4("없음");
            }
        }
        crewMapper.crewInsert(cleanCrewDto);
    }


// 크루 모집 상세보기
    public List<CrewDetailVo>  findCrewDetail(Long crewNumber){
        return crewMapper.selectCrewDetail(crewNumber);
    }
//    크루 첫 번째 프로젝트 번호 갖고 오기
    public Long findCrewProjectNumber(Long crewNumber, String crewName) {
        return crewMapper.getCrewProjectNumber(crewNumber, crewName);
    }
    //    크루 참여하기
    public void crewJoinRegister(CleanMyCrewDto cleanMyCrewDto){

        int count = crewMapper.selectMyCrewJoinCount(cleanMyCrewDto);
        if(count > 0) {
            throw new IllegalStateException("이미 가입한 크루입니다!");
        }
        crewMapper.crewJoinInsert(cleanMyCrewDto);
    }


//    내 크루 참여 목록
    public List<MyCrewVo> myCrewList(Long userNumber){
        return crewMapper.selectMyCrewList(userNumber);
    }

//    내 크루 참여 목록 검색
    public List<MyCrewVo> findMyCrewByWord(Long userNumber, String searchWord) {
    return crewMapper.selectMyCrewByWord(userNumber, searchWord);
}

}
