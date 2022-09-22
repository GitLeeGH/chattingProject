package com.example.chattproject.service;

import com.example.chattproject.dto.MemberDTO;

public interface UserService {

    // 로그인
    public MemberDTO login(MemberDTO memberDTO);

    // 회원가입
    void join(MemberDTO memberDTO);




}
