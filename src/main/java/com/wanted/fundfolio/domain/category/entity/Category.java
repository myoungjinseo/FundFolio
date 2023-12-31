package com.wanted.fundfolio.domain.category.entity;

import com.wanted.fundfolio.domain.budget.entity.BudgetCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<BudgetCategory> budgetCategories;
    public static List<CategoryType> categoryList(){
        return List.of(CategoryType.values());
    }



}
