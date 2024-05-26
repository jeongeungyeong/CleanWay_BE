package com.example.cleanway.domain.response;

import com.example.cleanway.domain.vo.crew.CrewVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrewSearchResponse {
    private List<CrewVo> crewByWordList;
    private String searchWord;
}
