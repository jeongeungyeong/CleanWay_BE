package com.example.cleanway.service.crew;

import com.example.cleanway.domain.dto.crew.CleanCrewDto;
import com.example.cleanway.domain.dto.crew.CleanMyCrewDto;
import com.example.cleanway.domain.vo.crew.CrewDetailVo;
import com.example.cleanway.domain.vo.crew.CrewVo;
import com.example.cleanway.domain.vo.crew.MyCrewVo;
import com.example.cleanway.mapper.crew.CrewMapper;
import lombok.RequiredArgsConstructor;
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
//    크루 모집 등록
    public void crewRegister(CleanCrewDto cleanCrewDto){
        crewMapper.crewInsert(cleanCrewDto);
    }
//    크루 모집 상세보기
    public List<CrewDetailVo>  findCrewDetail(Long crewNumber){
        return crewMapper.selectCrewDetail(crewNumber);
    }

//    크루 참여하기
    public void crewJoinRegister(CleanMyCrewDto cleanMyCrewDto){
        crewMapper.crewJoinInsert(cleanMyCrewDto);
    }
//    내 크루 참여 목록
    public List<MyCrewVo> myCrewList(Long userNumber){
        return crewMapper.selectMyCrewList(userNumber);
    }

}
