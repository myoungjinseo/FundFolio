package com.wanted.fundfolio.api.budget.service;

import com.wanted.fundfolio.api.budget.dto.BudgetRequest;
import com.wanted.fundfolio.api.budget.dto.BudgetResponse;
import com.wanted.fundfolio.api.category.service.CategoryService;
import com.wanted.fundfolio.domain.budget.entity.Budget;
import com.wanted.fundfolio.domain.budget.entity.BudgetCategory;
import com.wanted.fundfolio.domain.budget.repo.BudgetCategoryRepository;
import com.wanted.fundfolio.domain.budget.repo.BudgetRepository;
import com.wanted.fundfolio.domain.category.entity.Category;
import com.wanted.fundfolio.domain.category.repo.CategoryRepository;
import com.wanted.fundfolio.domain.member.entity.Member;
import com.wanted.fundfolio.domain.member.repo.MemberRepository;
import com.wanted.fundfolio.global.exception.ErrorCode;
import com.wanted.fundfolio.global.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final CategoryService categoryService;
    private final BudgetCategoryRepository budgetCategoryRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public BudgetResponse save(Long memberId,BudgetRequest budgetRequest){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ErrorException(ErrorCode.NON_EXISTENT_MEMBER));

        Budget budget = Budget.builder()
                .member(member)
                .amount(budgetRequest.getAmount())
                .date(budgetRequest.getDate().atDay(1))
                .build();
        budgetRepository.save(budget);

        Category category = Category.builder()
                .categoryType(budgetRequest.getCategoryType())
                .build();
        categoryService.save(category);

        BudgetCategory budgetCategory = BudgetCategory.builder()
                .budget(budget)
                .category(category)
                .build();
        budgetCategoryRepository.save(budgetCategory);

        BudgetResponse response = BudgetResponse.of(member.getUsername(),budget, category);
        return response;
    }
}
