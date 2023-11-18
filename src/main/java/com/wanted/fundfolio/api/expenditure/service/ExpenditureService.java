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
import java.util.Optional;

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

    public ExpenditureResponse read(String username, Long id) {
        Member member = memberService.findMember(username);
        Expenditure expenditure = findExpenditure(id);
        if (member != expenditure.getMember()) {
            throw new ErrorException(ErrorCode.NON_EXISTENT_MEMBER);
        }
        return ExpenditureResponse.of(expenditure);
    }

    public ExpenditureRecommendResponse recommendToday(String username) {
        Member member = memberService.findMember(username);
        List<Category> categories = categoryService.categoryList();
        long todayAmount = todayAmount(member);
        List<ExpenditureAmountByCategoryResponse> categoryByAmountList = categoryByAmountList(member,categories);
        return ExpenditureRecommendResponse.of(categoryByAmountList, todayAmount);

    }

    public List<ExpenditureAmountByCategoryResponse> categoryByAmountList(Member member,List<Category> categories) {
        List<ExpenditureAmountByCategoryResponse> categoryByAmountList = new ArrayList<>();
        int date = remainDays();
        for (Category category : categories) {
            long todayAmountByCategory = todayAmountByCategory(member, category, date);
            ExpenditureAmountByCategoryResponse dto = ExpenditureAmountByCategoryResponse.of(category.getCategoryType(), todayAmountByCategory);
            categoryByAmountList.add(dto);
        }
        return categoryByAmountList;
    }

    public long todayAmountByCategory(Member member,Category category, int date){
        Long budgetAmountByCategory = budgetService.findAmountByCategoryMember(member, category);
        Long amountByCategory = expenditureRepository.findAmountByCategory(category, member).orElse(0L);
        return Math.round((budgetAmountByCategory - amountByCategory) / date / 100.0) * 100;
    }

    public long todayAmount(Member member) {
        int date = remainDays();
        Long amountAll = expenditureRepository.findAmountAll(member);
        Long budgetAmountAll = budgetService.findAmountAllByMember(member);
        return Math.round((budgetAmountAll - amountAll) / date / 100.0) * 100;
    }

    public int remainDays() {
        return YearMonth.now().atEndOfMonth().getDayOfMonth() - LocalDate.now().getDayOfMonth() + 1;
    }

    public ExpenditureGuideTodayResponse guideToday(String username) {
        Member member = memberService.findMember(username);
        List<Category> categories = categoryService.categoryList();
        Long todayAmount = expenditureRepository.findTodayAmount(member,LocalDate.now()).orElse(0L);
        int date = remainDays();
        List<ExpenditureAmountResponse> monthAmountByCategory = new ArrayList<>();
        for (Category category : categories) {
            long todayAmountByCategory = expenditureRepository.findTodayAmountByCategory(category, member,LocalDate.now()).orElse(0L);
            long categoryByAmountList = todayAmountByCategory(member,category,date);
            long dangerPercent = todayAmountByCategory * 100/ categoryByAmountList ;
            ExpenditureAmountResponse dto = ExpenditureAmountResponse.of(category.getCategoryType(), categoryByAmountList, todayAmountByCategory, dangerPercent);
            monthAmountByCategory.add(dto);
        }
        return ExpenditureGuideTodayResponse.of(todayAmount,monthAmountByCategory);
    }

    public ExpenditureStatisticsResponse statistics(String username){
        Member member = memberService.findMember(username);
        ComparedResponse comparedResponse1 = monthResponse(member);
        ComparedResponse comparedResponse2 = dayResponse(member);
        ComparedResponse comparedResponse3 = memberResponse(member);
        return ExpenditureStatisticsResponse.of(comparedResponse1,comparedResponse2,comparedResponse3);
    }

    public ComparedResponse monthResponse(Member member){
        LocalDate now = LocalDate.now();
        LocalDate oneDayOfMonth = YearMonth.now().atDay(1);
        LocalDate oneDayOfLastMonth = YearMonth.now().minusMonths(1).atDay(1);
        long comparedAmountAllOfMonth = expenditureRepository.findComparedAmountAll(member, oneDayOfMonth, now).orElse(0L);
        List<Category> categories = categoryService.categoryList();
        List<ExpenditureAmountByCategoryResponse> monthComparedList = new ArrayList<>();
        LocalDate minusMonths = now.minusMonths(1);
        for (Category category : categories) {
            long findLastMonth = expenditureRepository.findComparedAmountByCategory(category, member,  oneDayOfLastMonth,minusMonths).orElse(0L);
            if(findLastMonth != 0L){
                Long findMonth = expenditureRepository.findComparedAmountByCategory(category, member, oneDayOfMonth, now).orElse(0L);
                long monthCompared = findMonth * 100 / findLastMonth;
                ExpenditureAmountByCategoryResponse compared = ExpenditureAmountByCategoryResponse.of(category.getCategoryType(),monthCompared);
                monthComparedList.add(compared);
            }
        }

        if (comparedAmountAllOfMonth == 0L){
            throw new ErrorException(ErrorCode.NON_EXISTENT_MEMBER);
        }
        Long comparedAmountAllOfLastMonth = expenditureRepository.findComparedAmountAll(member, oneDayOfLastMonth, now.minusMonths(1)).orElse(0L);
        long comparedAmountAll = comparedAmountAllOfMonth * 100 / comparedAmountAllOfLastMonth;
        return ComparedResponse.of(comparedAmountAll, monthComparedList);

    }

    public ComparedResponse dayResponse(Member member){
        LocalDate now = LocalDate.now();
        LocalDate oneDayOfMonth = YearMonth.now().atDay(1);
        LocalDate oneDayOfLastMonth = LocalDate.now().minusDays(7);
        long comparedAmountAllOfMonth = expenditureRepository.findComparedAmountAll(member, oneDayOfMonth, now).orElse(0L);
        List<Category> categories = categoryService.categoryList();
        List<ExpenditureAmountByCategoryResponse> monthComparedList = new ArrayList<>();
        for (Category category : categories) {
            long findLastMonth = expenditureRepository.findComparedAmountByCategory(category, member, now.minusDays(7), oneDayOfLastMonth).orElse(0L);
            if(findLastMonth != 0L){
                Long findMonth = expenditureRepository.findComparedAmountByCategory(category, member, oneDayOfMonth, now).orElse(0L);
                long monthCompared = findMonth * 100 / findLastMonth;
                ExpenditureAmountByCategoryResponse compared = ExpenditureAmountByCategoryResponse.of(category.getCategoryType(),monthCompared);
                monthComparedList.add(compared);
            }
        }

        if (comparedAmountAllOfMonth == 0L){
            throw new ErrorException(ErrorCode.NON_EXISTENT_MEMBER);
        }
        Long comparedAmountAllOfLastMonth = expenditureRepository.findTodayAmount(member, oneDayOfLastMonth).orElse(0L);
        long comparedAmountAll = comparedAmountAllOfMonth * 100 / comparedAmountAllOfLastMonth;
        return ComparedResponse.of(comparedAmountAll, monthComparedList);

    }
    public ComparedResponse memberResponse(Member member){
        LocalDate now = LocalDate.now();
        Long todayBudget = budgetService.findTodayTotalAmount(member, YearMonth.now().atDay(1));
        List<Category> categories = categoryService.categoryList();
        List<ExpenditureAmountByCategoryResponse> memberComparedList = new ArrayList<>();

        for (Category category : categories) {
            Long todayBudgetByCategory = budgetService.findTodayTotalAmountByCategory(member, category,  YearMonth.now().atDay(1));
            if (todayBudgetByCategory != 0L){
                long todayExpenditureByCategory = expenditureRepository.findTodayTotalAmountByCategory(member, category, now).orElse(0L);
                long res =  todayBudgetByCategory* 100 / todayExpenditureByCategory;
                ExpenditureAmountByCategoryResponse response = ExpenditureAmountByCategoryResponse.of(category.getCategoryType(), res);
                memberComparedList.add(response);
            }
        }

        if (todayBudget == 0L){
            throw new ErrorException(ErrorCode.NON_EXISTENT_MEMBER);
        }
        Long todayExpenditure = expenditureRepository.findTodayTotalAmount(member, now).orElse(0L);
        long comparedAmountAll =  todayBudget * 100 / todayExpenditure;
        return ComparedResponse.of(comparedAmountAll, memberComparedList);
    }


}
