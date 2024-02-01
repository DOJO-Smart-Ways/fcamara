package com.test.parking.service;

import com.test.parking.model.Company;
import com.test.parking.repository.CompanyRepository;
import com.test.parking.service.CompanyService;
import com.test.parking.service.ParkingSpaceService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private ParkingSpaceService parkingSpaceService;

    @InjectMocks
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCompany() {
        Company company = new Company(); // Assume Company class has appropriate setters
        Mockito.when(companyRepository.save(ArgumentMatchers.any(Company.class))).thenReturn(company);
        companyService.addCompany(company);
        Mockito.verify(parkingSpaceService, Mockito.times(1)).companySpaces(ArgumentMatchers.any(Company.class), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());
    }

    @Test
    void listCompany() {
        Mockito.when(companyRepository.findAll()).thenReturn(Arrays.asList(new Company(), new Company()));
        List<Company> result = companyService.listCompany();
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void getCompanyById() {
        long id = 1L;
        Mockito.when(companyRepository.findById(id)).thenReturn(Optional.of(new Company()));
        Company result = companyService.getCompanyById(id);
        Assertions.assertNotNull(result);
    }

    @Test
    void deleteCompany() {
        long id = 1L;
        String expectedResponse = "Company id: " + id + ", has been deleted successfuly.";
        Assertions.assertEquals(expectedResponse, companyService.deleteCompany(id));
        Mockito.verify(companyRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void updateCompany() {
        long id = 1L;
        Company existingCompany = new Company();
        Mockito.when(companyRepository.findById(id)).thenReturn(Optional.of(existingCompany));
        Mockito.when(companyRepository.save(ArgumentMatchers.any(Company.class))).thenReturn(existingCompany);

        Company companyToUpdate = new Company(); // Set properties as needed
        Company updatedCompany = companyService.updateCompany(companyToUpdate, id);

        Mockito.verify(parkingSpaceService, Mockito.times(1)).companySpacesUpdate(ArgumentMatchers.any(Company.class), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());
        Assertions.assertNotNull(updatedCompany);
    }
}
