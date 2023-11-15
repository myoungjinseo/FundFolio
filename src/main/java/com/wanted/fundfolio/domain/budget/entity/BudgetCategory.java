package com.wanted.fundfolio.domain.budget.entity;

import com.wanted.fundfolio.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(BudgetCategoryId.class)
public class BudgetCategory {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Budget_id")
    private Budget budget;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Category_id")
    private Category category;

    @Column
    private long amount;
}
