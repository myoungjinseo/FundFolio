package com.wanted.fundfolio.domain.budget.repo;


import com.wanted.fundfolio.domain.budget.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget,Long> {
}
