package com.wanted.fundfolio.domain.expenditure.repo;


import com.wanted.fundfolio.domain.expenditure.entity.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenditureRepository extends JpaRepository<Expenditure,Long>,ExpenditureRepositoryCustom {
}
