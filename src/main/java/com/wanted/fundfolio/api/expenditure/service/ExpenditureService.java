package com.wanted.fundfolio.api.expenditure.service;

import com.wanted.fundfolio.api.budget.service.BudgetService;
import com.wanted.fundfolio.api.category.service.CategoryService;
import com.wanted.fundfolio.api.expenditure.dto.*;
import com.wanted.fundfolio.api.user.service.MemberService;
import com.wanted.fundfolio.domain.category.entity.Category;
import com.wanted.fundfolio.domain.expenditure.entity.Expenditure;
import com.wanted.fundfolio.domain.expenditure.repo.ExpenditureRepository;
import com.wanted.fundfolio.domain.member.entity.Member;
import com.wanted.fundfolio.global.exception.ErrorCode;
import com.wanted.fundfolio.global.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpenditureService {

    private final MemberService memberService;
    private final CategoryService categoryService;
    private final ExpenditureRepository expenditureRepository;

    private final BudgetService budgetService;

    @Transactional
    public Long create(ExpenditureRequest request, String username) {
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
    public void update(Long id, ExpenditureRequest request, String username) {
        Member member = memberService.findMember(username);
        Expenditure expenditure = findExpenditure(id);
        if (member != expenditure.getMember()) {
            throw new ErrorException(ErrorCode.NON_EXISTENT_MEMBER);
        }
        Category category = categoryService.save(request.getCategoryType());
        expenditure.update(request, category);

    }

    @Transactional
    public void delete(Long id, String username) {
        Member member = memberService.findMember(username);
        Expenditure expenditure = findExpenditure(id);
        if (member != expenditure.getMember()) {
            throw new ErrorException(ErrorCode.NON_EXISTENT_MEMBER);
        }
        expenditureRepository.deleteById(id);
    }

    @Transactional
    public Expenditure findExpenditure(Long id) {
        return expenditureRepository.findById(id)
                .orElseThrow(() -> new ErrorException(ErrorCode.NON_EXISTENT_EXPENDITURE));
    }

    @Transactional
    public void excludingTotal(Long id, String username) {
        Expenditure expenditure = findExpenditure(id);
        Member member = memberService.findMember(username);
        if (member != expenditure.getMember()) {
            throw new ErrorException(ErrorCode.NON_EXISTENT_MEMBER);
        }
        expenditure.updateExclude();
    }

    public ExpenditureReadListResponse readListAll(String username, ExpenditureReadRequest request) {
        Member member = memberService.findMember(username);
        List<ExpenditureReadResponse> list = expenditureRepository.findList(request, member, request.getCategoryType());
        Category category = categoryService.save(request.getCategoryType());
        Long amountByCategory = expenditureRepository.findAmountByCategory(category, member).orElse(0L);
        Long amountAll = expenditureRepository.findAmountAll(member);

        return ExpenditureReadListResponse.of(list, amountAll, amountByCategory);

    }

    public ExpenditureResponse read(String username, Long id ){
        Member member = memberService.findMember(username);
        Expenditure expenditure = findExpenditure(id);
        if (member != expenditure.getMember()) {
            throw new ErrorException(ErrorCode.NON_EXISTENT_MEMBER);
        }
        return ExpenditureResponse.of(expenditure);
    }

    public ExpenditureRecommendResponse recommendToday(String username){
        Member member = memberService.findMember(username);
        List<Category> categories = categoryService.categoryList();
        List<ExpenditureAmountByCategoryResponse> categoryByAmountList = new ArrayList<>();
        int date = remainDays();
        long todayAmount = todayAmount(member,date);
        for(Category category : categories){
            ExpenditureAmountByCategoryResponse dto = categoryByAmountList(category, member, date);
            categoryByAmountList.add(dto);
        }
        return ExpenditureRecommendResponse.of(categoryByAmountList,todayAmount);

    }

    public ExpenditureAmountByCategoryResponse categoryByAmountList(Category category,Member member,int date){
        Long budgetAmountByCategory = budgetService.findAmountByCategoryMember(member, category);
        Long amountByCategory = expenditureRepository.findAmountByCategory(category, member).orElse(0L);
        long todayAmountByCategory = Math.round((budgetAmountByCategory - amountByCategory)/date /100.0)*100;
        return ExpenditureAmountByCategoryResponse.of(category.getCategoryType(), todayAmountByCategory);
    }

    public long todayAmount(Member member,int date){
        Long amountAll = expenditureRepository.findAmountAll(member);
        Long budgetAmountAll = budgetService.findAmountAllByMember(member);
        return Math.round((budgetAmountAll - amountAll) / date/100.0)*100;
    }

    public int remainDays(){
        return YearMonth.now().atEndOfMonth().getDayOfMonth() - LocalDate.now().getDayOfMonth() + 1;
    }


}
