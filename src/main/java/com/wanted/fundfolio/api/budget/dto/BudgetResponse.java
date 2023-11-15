package com.wanted.fundfolio.api.budget.dto;

import com.wanted.fundfolio.domain.budget.entity.Budget;
import com.wanted.fundfolio.domain.budget.entity.BudgetCategory;
import com.wanted.fundfolio.domain.category.entity.Category;
import com.wanted.fundfolio.domain.category.entity.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.YearMonth;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetResponse {
    private long amount;

    @DateTimeFormat(pattern = "yyyy-MM")
    private YearMonth date;
    private String username;
    private CategoryType categoryType;



    public static BudgetResponse of(String username, BudgetCategory budgetCategory,Budget budget, Category category){
        return new BudgetResponse(budgetCategory.getAmount(),YearMonth.from(budget.getDate()),username,category.getCategoryType());
    }
}
