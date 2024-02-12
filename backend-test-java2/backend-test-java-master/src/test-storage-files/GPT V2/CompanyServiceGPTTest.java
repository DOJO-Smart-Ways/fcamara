package com.test.parking.service;

import com.test.parking.model.Company;
import com.test.parking.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

class CompanyServiceGPTTest {

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
        when(companyRepository.save(any(Company.class))).thenReturn(company);
        companyService.addCompany(company);
        verify(parkingSpaceService, times(1)).companySpaces(any(Company.class), anyInt(), anyInt());
    }

    @Test
    void listCompany() {
        when(companyRepository.findAll()).thenReturn(Arrays.asList(new Company(), new Company()));
        List<Company> result = companyService.listCompany();
        assertEquals(2, result.size());
    }

    @Test
    void getCompanyById() {
        long id = 1L;
        when(companyRepository.findById(id)).thenReturn(Optional.of(new Company()));
        Company result = companyService.getCompanyById(id);
        assertNotNull(result);
    }

    @Test
    void deleteCompany() {
        long id = 1L;
        String expectedResponse = "Company id: " + id + ", has been deleted successfuly.";
        assertEquals(expectedResponse, companyService.deleteCompany(id));
        verify(companyRepository, times(1)).deleteById(id);
    }

    @Test
    void updateCompany() {
        long id = 1L;
        Company existingCompany = new Company();
        when(companyRepository.findById(id)).thenReturn(Optional.of(existingCompany));
        when(companyRepository.save(any(Company.class))).thenReturn(existingCompany);

        Company companyToUpdate = new Company(); // Set properties as needed
        Company updatedCompany = companyService.updateCompany(companyToUpdate, id);

        verify(parkingSpaceService, times(1)).companySpacesUpdate(any(Company.class), anyInt(), anyInt(), anyInt(), anyInt());
        assertNotNull(updatedCompany);
    }
}
