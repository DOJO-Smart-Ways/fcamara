package com.test.parking.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.parking.model.Company;
import com.test.parking.repository.CompanyRepository;

@Service
public class CompanyService {
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private ParkingSpaceService parkingService;
	
	public Company addCompany(Company company) {
		int motorcycles = company.getMotorcyclesSpace();
		int cars = company.getCarsSpace();
		Company savedCompany = companyRepository.save(company);
		parkingService.companySpaces(company, motorcycles, cars);
		return savedCompany;
	}
	
	public List<Company> listCompany() {
		return companyRepository.findAll();
	}
	
	public Company getCompanyById(long id) {
		return companyRepository.findById(id).orElse(null);
	}
	
	public String deleteCompany(long id) {
		companyRepository.deleteById(id);
		return "Company id: " + id + ", has been deleted successfuly.";
	}
	
	public Company updateCompany(Company company, long id) {
		Company existingCompany = companyRepository.findById(id).orElse(null);
		
		int carSpaceBefore = existingCompany.getCarsSpace();
		int newCarsSpaces = company.getCarsSpace();
		
		int motorSpaceBefore = existingCompany.getMotorcyclesSpace();
		int newMotorcyclesSpaces = company.getMotorcyclesSpace();
		
		existingCompany.setName(company.getName());
		existingCompany.setCnpj(company.getCnpj());
		existingCompany.setAddress(company.getAddress());
		existingCompany.setPhone(company.getPhone());
		existingCompany.setMotorcyclesSpace(company.getMotorcyclesSpace());
		existingCompany.setCarsSpace(company.getCarsSpace());
		
		parkingService.companySpacesUpdate(existingCompany, motorSpaceBefore, newMotorcyclesSpaces, carSpaceBefore, newCarsSpaces);
		
		return companyRepository.save(existingCompany);
	}
}
