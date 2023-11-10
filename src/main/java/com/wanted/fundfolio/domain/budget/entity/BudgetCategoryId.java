package com.wanted.fundfolio.domain.budget.entity;

import com.wanted.fundfolio.domain.category.entity.Category;
import lombok.*;

import java.io.Serializable;
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BudgetCategoryId implements Serializable {
    private Budget budget;
    private Category category;
}
