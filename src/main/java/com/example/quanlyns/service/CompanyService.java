package com.example.quanlyns.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.quanlyns.entity.Company;
import com.example.quanlyns.repository.CompanyRepository;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company createCompany(Company company) {
        return this.companyRepository.save(company);
    }

    public Optional<Company> getCompany(Long id) {
        return this.companyRepository.findById(id);
    }

    public List<Company> getAllCompanies() {
        return this.companyRepository.findAll();
    }

    public Company updateCompany(Long id, Company company) {
        Optional<Company> cu1 = this.companyRepository.findById(id);
        Company cu2 = cu1.get();
        cu2.setName(company.getName());
        cu2.setLogo(company.getLogo());
        cu2.setAddress(company.getAddress());
        cu2.setDescription(company.getDescription());

        return this.companyRepository.save(cu2);

    }

    public void deleteCompany(Long id) {
        if (!this.companyRepository.existsById(id)) {
            throw new NoSuchElementException("Company not found");
        }
        this.companyRepository.deleteById(id);
    }

}
