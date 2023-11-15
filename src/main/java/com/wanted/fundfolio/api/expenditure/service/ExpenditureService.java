package com.wanted.fundfolio.api.expenditure.service;

import com.wanted.fundfolio.api.category.service.CategoryService;
import com.wanted.fundfolio.api.expenditure.dto.ExpenditureReadResponse;
import com.wanted.fundfolio.api.expenditure.dto.ExpenditureRequest;
import com.wanted.fundfolio.api.user.service.MemberService;
import com.wanted.fundfolio.domain.category.entity.Category;
import com.wanted.fundfolio.domain.expenditure.entity.Expenditure;
import com.wanted.fundfolio.domain.expenditure.repo.ExpenditureRepository;
import com.wanted.fundfolio.domain.member.entity.Member;
import com.wanted.fundfolio.global.exception.ErrorCode;
import com.wanted.fundfolio.global.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpenditureService {

    private final MemberService memberService;
    private final CategoryService categoryService;
    private final ExpenditureRepository expenditureRepository;
    @Transactional
    public Long create(ExpenditureRequest request, String username){
        Member member = memberService.findMember(username);
        Category category = categoryService.save(request.getCategoryType());
        Expenditure expenditure = Expenditure.builder()
                .amount(request.getAmount())
                .category(category)
                .content(request.getContent())
                .date(request.getDate())
                .member(member)
                .build();
        expenditureRepository.save(expenditure);
        return expenditure.getId();
    }

    @Transactional
    public void update(Long id,ExpenditureRequest request, String username){
        Member member = memberService.findMember(username);
        Expenditure expenditure = findExpenditure(id);
        if(member != expenditure.getMember()){
            throw new ErrorException(ErrorCode.NON_EXISTENT_MEMBER);
        }
        Category category = categoryService.save(request.getCategoryType());
        expenditure.update(request,category);

    }

    @Transactional
    public void delete(Long id, String username){
        Member member = memberService.findMember(username);
        Expenditure expenditure = findExpenditure(id);
        if(member != expenditure.getMember()){
            throw new ErrorException(ErrorCode.NON_EXISTENT_MEMBER);
        }
        expenditureRepository.deleteById(id);
    }

    @Transactional
    public Expenditure findExpenditure(Long id){
        return expenditureRepository.findById(id)
                .orElseThrow(() -> new ErrorException(ErrorCode.NON_EXISTENT_EXPENDITURE));
    }

//    public List<ExpenditureReadResponse> readListAll(){
//
//        return
//    }

    @Transactional
    public void excludingTotal(Long id, String username){
        Expenditure expenditure = findExpenditure(id);
        Member member = memberService.findMember(username);
        if(member != expenditure.getMember()){
            throw new ErrorException(ErrorCode.NON_EXISTENT_MEMBER);
        }
        expenditure.updateExclude();
    }
}
