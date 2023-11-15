package com.wanted.fundfolio.api.budget.service;

import com.wanted.fundfolio.api.budget.dto.BudgetRecommendResponse;
import com.wanted.fundfolio.api.budget.dto.BudgetRequest;
import com.wanted.fundfolio.api.budget.dto.BudgetResponse;
import com.wanted.fundfolio.api.category.service.CategoryService;
import com.wanted.fundfolio.api.user.service.MemberService;
import com.wanted.fundfolio.domain.budget.entity.Budget;
import com.wanted.fundfolio.domain.budget.entity.BudgetCategory;
import com.wanted.fundfolio.domain.budget.repo.BudgetCategoryRepository;
import com.wanted.fundfolio.domain.budget.repo.BudgetRepository;
import com.wanted.fundfolio.domain.category.entity.Category;
import com.wanted.fundfolio.domain.member.entity.Member;
import com.wanted.fundfolio.domain.member.repo.MemberRepository;
import com.wanted.fundfolio.global.exception.ErrorCode;
import com.wanted.fundfolio.global.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final CategoryService categoryService;
    private final BudgetCategoryRepository budgetCategoryRepository;
    private final MemberService memberService;

    @Transactional
    public BudgetResponse save(String username,BudgetRequest budgetRequest){
        Member member = memberService.findMember(username);
        Budget findBudget = budgetRepository.findByMemberAndDate(member, budgetRequest.getDate().atDay(1));
        if(findBudget ==null){
            findBudget = Budget.builder()
                    .member(member)
                    .date(budgetRequest.getDate().atDay(1))
                    .build();
            budgetRepository.save(findBudget);
        }


        Category category = categoryService.save(budgetRequest.getCategoryType());

        BudgetCategory budgetCategory = BudgetCategory.builder()
                .budget(findBudget)
                .category(category)
                .amount(budgetRequest.getAmount())
                .build();
        budgetCategoryRepository.save(budgetCategory);

        return BudgetResponse.of(member.getUsername(),budgetCategory,findBudget, category);
        
    }

    public List<BudgetRecommendResponse> recommend(){
        List<Category> categories = categoryService.categoryList();
        List<BudgetRecommendResponse> amountList = new ArrayList<>();
        long amountAll = budgetCategoryRepository.findAmountAll();
        for (Category category : categories){
            long amountByCategory = budgetCategoryRepository.findAmountByCategory(category);
            long result = amountByCategory * 100 /amountAll ;
            BudgetRecommendResponse dto = BudgetRecommendResponse.of(category.getCategoryType(), result);
            amountList.add(dto);
        }
        return amountList;
    }
}
