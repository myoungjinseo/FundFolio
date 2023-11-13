package com.wanted.fundfolio.domain.budget.entity;

import com.wanted.fundfolio.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String amount;

    @Column
    private LocalDate date;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Member_id")
    private Member member;

    @OneToMany(mappedBy = "budget", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<BudgetCategory> budgetCategories;

}
