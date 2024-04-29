package com.example.cleanway.domain.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Long userNumber;
    private String userEmail;
    private String userNickname;
}
