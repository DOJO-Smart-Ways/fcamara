import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.test.parking.model.Company;
import com.test.parking.repository.CompanyRepository;
import com.test.parking.service.CompanyService;
import com.test.parking.service.ParkingSpaceService;

public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private ParkingSpaceService parkingService;

    @InjectMocks
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCompany() {
        Company company = new Company(); // Assuming Company class has appropriate setters to set details
        company.setMotorcyclesSpace(10);
        company.setCarsSpace(20);

        when(companyRepository.save(any(Company.class))).thenReturn(company);

        Company savedCompany = companyService.addCompany(company);

        assertNotNull(savedCompany);
        verify(companyRepository).save(company);
        verify(parkingService).companySpaces(any(Company.class), eq(10), eq(20));
    }

    @Test
    void testListCompany() {
        List<Company> companies = Arrays.asList(new Company(), new Company()); // Assuming Company class is set up correctly
        when(companyRepository.findAll()).thenReturn(companies);

        List<Company> result = companyService.listCompany();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(companyRepository).findAll();
    }
    @Test
    void testGetCompanyByIdFound() {
        Company company = new Company(); // setup company details as necessary
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));

        Company result = companyService.getCompanyById(1L);

        assertNotNull(result);
        verify(companyRepository).findById(1L);
    }

    @Test
    void testGetCompanyByIdNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        Company result = companyService.getCompanyById(1L);

        assertNull(result);
        verify(companyRepository).findById(1L);
    }
    @Test
    void testDeleteCompany() {
        long companyId = 1L;
        String expectedMessage = "Company id: " + companyId + ", has been deleted successfully.";

        String actualMessage = companyService.deleteCompany(companyId);

        assertEquals(expectedMessage, actualMessage);
        verify(companyRepository).deleteById(companyId);
    }
    @Test
    void testUpdateCompany() {
        long companyId = 1L;
        Company existingCompany = new Company();
        existingCompany.setCarsSpace(15);
        existingCompany.setMotorcyclesSpace(5);

        Company newCompany = new Company();
        newCompany.setCarsSpace(20);
        newCompany.setMotorcyclesSpace(10);

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(existingCompany));
        when(companyRepository.save(any(Company.class))).thenReturn(newCompany);

        Company updatedCompany = companyService.updateCompany(newCompany, companyId);

        assertNotNull(updatedCompany);
        verify(companyRepository).save(any(Company.class));
        verify(parkingService).companySpacesUpdate(any(Company.class), eq(5), eq(10), eq(15), eq(20));
    }

}
