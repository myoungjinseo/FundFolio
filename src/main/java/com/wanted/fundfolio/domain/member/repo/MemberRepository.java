package com.wanted.fundfolio.domain.member.repo;


import com.wanted.fundfolio.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
