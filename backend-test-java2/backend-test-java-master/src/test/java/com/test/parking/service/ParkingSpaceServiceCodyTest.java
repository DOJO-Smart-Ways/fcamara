import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import com.test.parking.model.Company;
import com.test.parking.model.ParkingSpace;
import com.test.parking.model.SpaceStatus;
import com.test.parking.repository.ParkingSpaceRepository;
import com.test.parking.service.ParkingSpaceService;

public class ParkingSpaceServiceTest {

  @Mock
  private ParkingSpaceRepository parkingRepository;

  private ParkingSpaceService parkingSpaceService;

  @Before
  public void setup() {
    parkingSpaceService = new ParkingSpaceService();
    parkingSpaceService.setParkingRepository(parkingRepository); 
  }

  @Test
  public void testCompanySpaces_valid() {
    Company company = new Company("Test Company");
    int motorcycles = 5;
    int cars = 10;

    parkingSpaceService.companySpaces(company, motorcycles, cars);

    verify(parkingRepository, times(motorcycles)).save(any(ParkingSpace.class));
    verify(parkingRepository, times(cars)).save(any(ParkingSpace.class));
  }

  @Test 
  public void testCompanySpaces_differentTypes() {
    Company company = new Company("Test Company");
    int motorcycles = 3;
    int cars = 5;

    parkingSpaceService.companySpaces(company, motorcycles, cars);
    
    List<ParkingSpace> motorcycleSpaces = parkingRepository.findAllSpaces("motorcycle");
    assertEquals(motorcycles, motorcycleSpaces.size());

    List<ParkingSpace> carSpaces = parkingRepository.findAllSpaces("car");
    assertEquals(cars, carSpaces.size());
  }

  @Test(expected=IllegalArgumentException.class)
  public void testChangeStatus_invalid() {
    ParkingSpace space = new ParkingSpace();
    parkingSpaceService.changeStatus(space, 5); 
  }

  @Test
  public void testChangeStatus() {
    ParkingSpace space = new ParkingSpace();
    space.setSpaceStatus(SpaceStatus.FREE);

    parkingSpaceService.changeStatus(space, SpaceStatus.OCCUPIED.getValue());

    assertEquals(SpaceStatus.OCCUPIED, space.getSpaceStatus());
  }

}