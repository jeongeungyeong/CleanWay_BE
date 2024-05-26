package com.example.cleanway.domain.response;

import com.example.cleanway.domain.vo.crew.CrewVo;
import com.example.cleanway.domain.vo.crew.MyCrewVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyCrewSearchResponse {
    private List<MyCrewVo> myCrewByWordList;
    private String searchWord;
}
