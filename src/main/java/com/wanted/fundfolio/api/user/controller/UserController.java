package com.wanted.fundfolio.api.user.controller;

import com.wanted.fundfolio.api.user.dto.SignRequest;
import com.wanted.fundfolio.api.user.dto.TokenResponse;
import com.wanted.fundfolio.api.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class UserController {
    private final MemberService memberService;

    @PostMapping("/signin")
    public TokenResponse login(@RequestBody SignRequest request){
        return memberService.login(request);

    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignRequest request){
        String response = memberService.signUp(request);
        return ResponseEntity.ok().body(response);
    }


}
