import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.test.parking.model.Company;
import com.test.parking.repository.CompanyRepository;
import com.test.parking.service.CompanyService;
import com.test.parking.service.ParkingSpaceService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private ParkingSpaceService parkingSpaceService;

    @InjectMocks
    private CompanyService companyService;

    @Test
    public void testAddCompany() {
        Company company = new Company(); // Assume a constructor or setter methods to set properties
        when(companyRepository.save(any(Company.class))).thenReturn(company);

        Company result = companyService.addCompany(company);
        verify(companyRepository).save(company);
        verify(parkingSpaceService).companySpaces(company, company.getMotorcyclesSpace(), company.getCarsSpace());
        assertEquals(company, result);
    }

    @Test
    public void testListCompany() {
        List<Company> companies = Arrays.asList(new Company(), new Company());
        when(companyRepository.findAll()).thenReturn(companies);

        List<Company> result = companyService.listCompany();
        verify(companyRepository).findAll();
        assertEquals(companies, result);
    }

    @Test
    public void testGetCompanyById() {
        Company company = new Company();
        long id = 1L;
        when(companyRepository.findById(id)).thenReturn(Optional.of(company));

        Company result = companyService.getCompanyById(id);
        verify(companyRepository).findById(id);
        assertEquals(company, result);
    }

    @Test
    public void testDeleteCompany() {
        long id = 1L;
        String expectedResponse = "Company id: " + id + ", has been deleted successfully.";

        String response = companyService.deleteCompany(id);
        verify(companyRepository).deleteById(id);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testUpdateCompany() {
        long id = 1L;
        Company existingCompany = new Company();
        Company updatedCompany = new Company(); // Set properties accordingly

        when(companyRepository.findById(id)).thenReturn(Optional.of(existingCompany));
        when(companyRepository.save(any(Company.class))).thenReturn(updatedCompany);

        Company result = companyService.updateCompany(updatedCompany, id);
        verify(companyRepository).findById(id);
        verify(companyRepository).save(existingCompany);
        verify(parkingSpaceService).companySpacesUpdate(existingCompany, existingCompany.getMotorcyclesSpace(), updatedCompany.getMotorcyclesSpace(), existingCompany.getCarsSpace(), updatedCompany.getCarsSpace());
        assertEquals(updatedCompany, result);
    }
}
