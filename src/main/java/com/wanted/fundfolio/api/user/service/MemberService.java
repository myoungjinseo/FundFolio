package com.wanted.fundfolio.api.user.service;

import com.wanted.fundfolio.api.user.dto.SignRequest;
import com.wanted.fundfolio.api.user.dto.TokenResponse;
import com.wanted.fundfolio.domain.member.entity.Member;
import com.wanted.fundfolio.domain.member.entity.Role;
import com.wanted.fundfolio.domain.member.repo.MemberRepository;
import com.wanted.fundfolio.global.exception.ErrorCode;
import com.wanted.fundfolio.global.exception.ErrorException;
import com.wanted.fundfolio.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public TokenResponse login(SignRequest signRequest) {
        UserDetails userDetails = memberRepository.findByUsername(signRequest.getUsername())
                .orElseThrow(() -> new ErrorException(ErrorCode.NON_EXISTENT_MEMBER));

        if (!passwordEncoder.matches(signRequest.getPassword(), userDetails.getPassword())) {
            throw new ErrorException(ErrorCode.NON_EXISTENT_MEMBER);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

        return tokenProvider.createToken(authentication);

    }

    @Transactional
    public String signUp(SignRequest signRequest){
        Set<Role> set = new HashSet<>();
        set.add(Role.ROLE_USER);

        Member member = Member.builder()
                .username(signRequest.getUsername())
                .password(passwordEncoder.encode(signRequest.getPassword()))
                .roles(set)
                .build();

        memberRepository.save(member);
        return "회원가입 완료";

    }

    public Member findMember(String username){
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new ErrorException(ErrorCode.NON_EXISTENT_MEMBER));
    }
}
