package com.wanted.fundfolio.api.expenditure.controller;

import com.wanted.fundfolio.api.expenditure.dto.ExpenditureReadListResponse;
import com.wanted.fundfolio.api.expenditure.dto.ExpenditureReadRequest;
import com.wanted.fundfolio.api.expenditure.dto.ExpenditureRequest;
import com.wanted.fundfolio.api.expenditure.service.ExpenditureService;
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
}
