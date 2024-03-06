import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.test.parking.model.Company;
import com.test.parking.model.ParkingSpace;
import com.test.parking.repository.ParkingSpaceRepository;
import com.test.parking.service.ParkingSpaceService;

public class ParkingSpaceServiceTest {

    @Mock
    private ParkingSpaceRepository parkingRepository;

    @InjectMocks
    private ParkingSpaceService parkingSpaceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCompanySpaces() {
        Company company = new Company(); // Assume Company is a properly defined entity
        int motorcycles = 5;
        int cars = 5;

        parkingSpaceService.companySpaces(company, motorcycles, cars);

        verify(parkingRepository, times(motorcycles + cars)).save(any(ParkingSpace.class));
    }

    @Test
    void testCompanySpacesUpdate_AddSpaces() {
        Company company = new Company();
        int motorSpacesBefore = 2;
        int newMotorcyclesSpaces = 3;
        int carSpacesBefore = 2;
        int newCarsSpaces = 3;

        parkingSpaceService.companySpacesUpdate(company, motorSpacesBefore, newMotorcyclesSpaces, carSpacesBefore, newCarsSpaces);

        verify(parkingRepository, times(newMotorcyclesSpaces - motorSpacesBefore + newCarsSpaces - carSpacesBefore)).save(any(ParkingSpace.class));
    }

    @Test
    void testCompanySpacesUpdate_RemoveSpaces() {
        Company company = new Company();
        int motorSpacesBefore = 3;
        int newMotorcyclesSpaces = 2;
        int carSpacesBefore = 3;
        int newCarsSpaces = 2;
        List<ParkingSpace> spaces = new ArrayList<>();
        spaces.add(new ParkingSpace());

        when(parkingRepository.findAllSpaces(anyString())).thenReturn(spaces);

        parkingSpaceService.companySpacesUpdate(company, motorSpacesBefore, newMotorcyclesSpaces, carSpacesBefore, newCarsSpaces);

        verify(parkingRepository).delete(any(ParkingSpace.class));
    }

    @Test
    void testChangeStatus() {
        ParkingSpace space = new ParkingSpace();
        int status = 2; // Assuming 2 is for OCCUPIED

        parkingSpaceService.changeStatus(space, status);

        assertEquals(space.getSpaceStatus(), SpaceStatus.OCCUPIED);
        verify(parkingRepository).save(space);
    }

    @Test
    void testCompanySpacesUpdate_NoChange() {
        Company company = new Company();
        int motorSpacesBefore = 2;
        int newMotorcyclesSpaces = 2;
        int carSpacesBefore = 2;
        int newCarsSpaces = 2;

        parkingSpaceService.companySpacesUpdate(company, motorSpacesBefore, newMotorcyclesSpaces, carSpacesBefore, newCarsSpaces);

        verify(parkingRepository, never()).save(any(ParkingSpace.class));
        verify(parkingRepository, never()).delete(any(ParkingSpace.class));
    }

    @Test
    void testChangeStatus_InvalidStatus() {
        ParkingSpace space = new ParkingSpace();
        int invalidStatus = 3; // Assuming only 1 (FREE) and 2 (OCCUPIED) are valid

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            parkingSpaceService.changeStatus(space, invalidStatus);
        });

        String expectedMessage = "Unexpected value: " + invalidStatus;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCompanySpaces_ZeroSpaces() {
        Company company = new Company();
        int motorcycles = 0;
        int cars = 0;

        parkingSpaceService.companySpaces(company, motorcycles, cars);

        verify(parkingRepository, never()).save(any(ParkingSpace.class));
    }

    @Test
    void testCompanySpacesUpdate_CorrectSpacesDeleted() {
        Company company = new Company();
        int motorSpacesBefore = 5;
        int newMotorcyclesSpaces = 3;
        List<ParkingSpace> motorcycleSpaces = List.of(new ParkingSpace(), new ParkingSpace(), new ParkingSpace(), new ParkingSpace(), new ParkingSpace());

        when(parkingRepository.findAllSpaces("motorcycle")).thenReturn(motorcycleSpaces);

        parkingSpaceService.companySpacesUpdate(company, motorSpacesBefore, newMotorcyclesSpaces, 5, 3);

        verify(parkingRepository, times(2)).delete(any(ParkingSpace.class)); // Expect 2 spaces to be deleted
    }

}
