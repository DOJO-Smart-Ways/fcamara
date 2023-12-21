import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.test.parking.model.Company;
import com.test.parking.repository.CompanyRepository;
import com.test.parking.service.CompanyService;
import com.test.parking.service.ParkingSpaceService;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

  @Mock
  private CompanyRepository companyRepository;

  @Mock
  private ParkingSpaceService parkingService;

  @InjectMocks
  private CompanyService companyService;

  @Test
  public void testAddCompany() {
    Company input = new Company();
    input.setName("Test Company");

    Company expected = new Company();
    expected.setId(1L);
    expected.setName("Test Company");

    when(companyRepository.save(input)).thenReturn(expected);

    Company result = companyService.addCompany(input);

    assertNotNull(result);
    assertEquals(expected, result);
  }

  @Test
  public void testAddCompany_null() {
    Company result = companyService.addCompany(null);

    assertNull(result);
  }

  @Test
  public void testListCompany() {
    List<Company> expected = new ArrayList<>();
    expected.add(new Company());

    when(companyRepository.findAll()).thenReturn(expected);

    List<Company> result = companyService.listCompany();

    assertNotNull(result);
    assertEquals(expected, result); 
  }

  @Test
  public void testGetCompanyById() {
    long id = 1L;
    Company expected = new Company();
    expected.setId(id);

    when(companyRepository.findById(id)).thenReturn(Optional.of(expected));

    Company result = companyService.getCompanyById(id);

    assertNotNull(result);
    assertEquals(expected, result);
  }

  @Test
  public void testGetCompanyById_notFound() {
    long id = 1L;

    when(companyRepository.findById(id)).thenReturn(Optional.empty());

    Company result = companyService.getCompanyById(id);

    assertNull(result);
  }

  @Test
  public void testDeleteCompany() {
    long id = 1L;

    String expected = "Company id: " + id + ", has been deleted successfuly.";

    String result = companyService.deleteCompany(id);

    assertNotNull(result);
    assertEquals(expected, result);
  }

  @Test
  public void testUpdateCompany() {
    long id = 1L;
    Company input = new Company();
    input.setId(id);
    input.setName("Updated");

    Company existing = new Company();
    existing.setId(id);
    existing.setName("Original");

    when(companyRepository.findById(id)).thenReturn(Optional.of(existing));
    when(companyRepository.save(existing)).thenReturn(input);

    Company result = companyService.updateCompany(input, id);

    assertNotNull(result);
    assertEquals(input, result);
  }

  @Test
  public void testUpdateCompany_notFound() {
    long id = 1L;
    Company input = new Company();

    when(companyRepository.findById(id)).thenReturn(Optional.empty());

    Company result = companyService.updateCompany(input, id);

    assertNull(result);
  }

}