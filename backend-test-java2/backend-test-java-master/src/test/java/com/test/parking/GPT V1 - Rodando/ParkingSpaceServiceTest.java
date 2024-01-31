import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.test.parking.model.Company;
import com.test.parking.model.ParkingSpace;
import com.test.parking.model.SpaceStatus;
import com.test.parking.repository.ParkingSpaceRepository;
import com.test.parking.service.ParkingSpaceService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class ParkingSpaceServiceTest {

    @InjectMocks
    private ParkingSpaceService parkingSpaceService;

    @Mock
    private ParkingSpaceRepository parkingRepository;

    @Test
    public void testCompanySpaces() {
        Company company = new Company(); // Assume a constructor or setter methods to set properties
        int motorcycles = 5;
        int cars = 3;

        parkingSpaceService.companySpaces(company, motorcycles, cars);

        verify(parkingRepository, times(motorcycles + cars)).save(any(ParkingSpace.class));
    }

    @Test
    public void testCompanySpacesUpdate() {
        Company company = new Company();
        int motorSpacesBefore = 3;
        int newMotorcyclesSpaces = 5;
        int carSpacesBefore = 2;
        int newCarsSpaces = 4;

        List<ParkingSpace> spaces = new ArrayList<>();
        spaces.add(new ParkingSpace());

        when(parkingRepository.findAllSpaces("motorcycle")).thenReturn(spaces);
        when(parkingRepository.findAllSpaces("car")).thenReturn(spaces);

        parkingSpaceService.companySpacesUpdate(company, motorSpacesBefore, newMotorcyclesSpaces, carSpacesBefore, newCarsSpaces);

        verify(parkingRepository, times(newMotorcyclesSpaces - motorSpacesBefore + newCarsSpaces - carSpacesBefore)).save(any(ParkingSpace.class));
        verify(parkingRepository, atMost(2)).findAllSpaces(anyString());
        verify(parkingRepository, atMost(2)).delete(any(ParkingSpace.class));
    }

    @Test
    public void testAvailableSpace() {
        String spaceType = "car";
        ParkingSpace parkingSpace = new ParkingSpace();
        when(parkingRepository.findOneAvailableSpace(spaceType)).thenReturn(parkingSpace);

        ParkingSpace result = parkingSpaceService.availableSpace(spaceType);
        verify(parkingRepository).findOneAvailableSpace(spaceType);
        assertEquals(parkingSpace, result);
    }

    @Test
    public void testChangeStatus() {
        ParkingSpace space = new ParkingSpace();
        int status = 1; // FREE

        parkingSpaceService.changeStatus(space, status);

        verify(parkingRepository).save(space);
        assertEquals(space.getSpaceStatus(), SpaceStatus.FREE);
    }

    @Test
    public void testChangeStatusInvalidArgument() {
        ParkingSpace space = new ParkingSpace();
        int status = 3; // Invalid status

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            parkingSpaceService.changeStatus(space, status);
        });

        String expectedMessage = "Unexpected value: " + status;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
