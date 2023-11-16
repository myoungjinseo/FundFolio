package com.wanted.fundfolio.api.expenditure.controller;

import com.wanted.fundfolio.api.expenditure.dto.*;
import com.wanted.fundfolio.api.expenditure.service.ExpenditureService;
import com.wanted.fundfolio.domain.expenditure.entity.Expenditure;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expenditure")
public class ExpenditureController {
    private final ExpenditureService expenditureService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ExpenditureRequest request, Principal principal){
        String username = principal.getName().split(":")[0];
        Long response = expenditureService.create(request, username);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ExpenditureRequest request, Principal principal){
        String username = principal.getName().split(":")[0];
        expenditureService.update(id,request, username);
        return ResponseEntity.ok().body("수정 완료");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,Principal principal){
        String username = principal.getName().split(":")[0];
        expenditureService.delete(id, username);
        return ResponseEntity.ok().body("삭제");
    }

    @PostMapping("/exclude/{id}")
    public  ResponseEntity<?> excludingTotal(@PathVariable Long id,Principal principal){
        String username = principal.getName().split(":")[0];
        expenditureService.excludingTotal(id, username);
        return ResponseEntity.ok().body("합계 제외");
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> readListAll(ExpenditureReadRequest request,Principal principal){
        String username = principal.getName().split(":")[0];
        ExpenditureReadListResponse response = expenditureService.readListAll(username, request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable Long id, Principal principal){
        String username = principal.getName().split(":")[0];
        ExpenditureResponse read = expenditureService.read(username, id);
        return ResponseEntity.ok().body(read);
    }

    @GetMapping("/today")
    public ResponseEntity<?> recommendToday(Principal principal){
        String username = principal.getName().split(":")[0];
        ExpenditureRecommendResponse response = expenditureService.recommendToday(username);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/today/guide")
    public ResponseEntity<?> guideToday(Principal principal){
        String username = principal.getName().split(":")[0];
        ExpenditureGuideTodayResponse response = expenditureService.guideToday(username);
        return ResponseEntity.ok().body(response);
    }
}
