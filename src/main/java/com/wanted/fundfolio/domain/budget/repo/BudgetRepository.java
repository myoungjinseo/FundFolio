package com.wanted.fundfolio.domain.budget.repo;


import com.wanted.fundfolio.domain.budget.entity.Budget;
import com.wanted.fundfolio.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface BudgetRepository extends JpaRepository<Budget,Long> {
    Budget findByMemberAndDate(Member member, LocalDate date);
}
