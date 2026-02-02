package com.example.quanlyns.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.quanlyns.entity.Company;
import com.example.quanlyns.entity.dto.ResultPaginationDTO;
import com.example.quanlyns.entity.response.ApiResponse;
import com.example.quanlyns.service.CompanyService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/company")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getAllCompany(
            @RequestParam(value = "current", defaultValue = "1") String sCurrent,
            @RequestParam(value = "pageSize", defaultValue = "10") String sPageSize) {

        int current = Integer.parseInt(sCurrent);
        int pageSize = Integer.parseInt(sPageSize);

        Pageable pageable = PageRequest.of(current - 1, pageSize);

        var result = new ApiResponse<>(HttpStatus.OK, "getAllCompany", this.companyService.getAllCompanies(pageable),
                null);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<ApiResponse<Company>> getCompany(@PathVariable Long id) {
        return companyService.getCompany(id).map(company -> {
            var response = new ApiResponse<>(HttpStatus.OK, "getCompanyById", company, null);
            return ResponseEntity.ok(response);

        }).orElseGet(() -> {
            ApiResponse<Company> errorResponse = new ApiResponse<>(HttpStatus.NOT_FOUND,
                    "Không tìm thấy Company với ID: " + id, null, "COMPANY_NOT_FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        });
    }

    @PostMapping("/company")
    public ResponseEntity<ApiResponse<Company>> createCompany(@Valid @RequestBody Company company) {
        var result = new ApiResponse<>(HttpStatus.OK, "createCompany", this.companyService.createCompany(company),
                null);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("company/{id}")
    public ResponseEntity<ApiResponse<Company>> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        var result = new ApiResponse<>(HttpStatus.OK, "updateCompany", this.companyService.updateCompany(id, company),
                null);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<ApiResponse<Company>> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        var result = new ApiResponse<Company>(HttpStatus.NO_CONTENT, "deleted", null, null);

        return ResponseEntity.ok().body(result);
    }

}
