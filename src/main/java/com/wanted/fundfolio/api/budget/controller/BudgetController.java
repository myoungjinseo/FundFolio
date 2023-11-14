package com.wanted.fundfolio.api.budget.controller;

import com.wanted.fundfolio.api.budget.dto.BudgetRequest;
import com.wanted.fundfolio.api.budget.dto.BudgetResponse;
import com.wanted.fundfolio.api.budget.service.BudgetService;
import com.wanted.fundfolio.domain.member.entity.Member;
import com.wanted.fundfolio.domain.member.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("api/v1/budgets")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody BudgetRequest budgetRequest, Principal principal) {

        // 임의로 설정
        String username = principal.getName().split(":")[0];
        BudgetResponse save = budgetService.save(username, budgetRequest);
        return ResponseEntity.ok().body(save);
    }
}
