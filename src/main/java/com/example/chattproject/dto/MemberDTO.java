package com.example.chattproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MemberDTO {

    @Id
    private String _id;

    // 가입 이메일
    private String email;

    // 비밀번호
    private String password;

    // 지역
    private String location;

    public MemberDTO toEntity(){
        return MemberDTO.builder()
                ._id(_id)
                .email(email)
                .password(password)
                .location(location)
                .build();
    }

}
