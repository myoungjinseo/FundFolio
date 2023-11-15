package com.wanted.fundfolio.domain.expenditure.entity;

import com.wanted.fundfolio.api.expenditure.dto.ExpenditureRequest;
import com.wanted.fundfolio.domain.category.entity.Category;
import com.wanted.fundfolio.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expenditure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private long amount;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Member_id")
    private Member member;

    @Column
    @Builder.Default
    private boolean excludeTotal = true;

    public void updateExclude(){
        this.excludeTotal = false;
    }

    public void update(ExpenditureRequest request,Category category){
        this.amount = request.getAmount();
        this.date = request.getDate();
        this.category = category;
        this.content = request.getContent();
    }
}
