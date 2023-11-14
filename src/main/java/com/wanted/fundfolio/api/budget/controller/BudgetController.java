package com.wanted.fundfolio.api.budget.controller;

import com.wanted.fundfolio.api.budget.dto.BudgetRequest;
import com.wanted.fundfolio.api.budget.dto.BudgetResponse;
import com.wanted.fundfolio.api.budget.service.BudgetService;
import com.wanted.fundfolio.domain.member.entity.Member;
import com.wanted.fundfolio.domain.member.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/budgets")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;
    private final MemberRepository memberRepository;
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody BudgetRequest budgetRequest) throws Exception {

        // 임의로 설정
        Member member = memberRepository.findById(1L).orElseThrow(Exception::new);
        BudgetResponse save = budgetService.save(member.getId(), budgetRequest);
        return ResponseEntity.ok().body(save);
    }
}
