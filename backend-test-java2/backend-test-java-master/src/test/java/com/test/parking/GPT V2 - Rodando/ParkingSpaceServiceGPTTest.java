import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ParkingSpaceServiceGPTTest {

    @Mock
    private ParkingSpaceRepository parkingRepository;

    @InjectMocks
    private ParkingSpaceService parkingSpaceService;

    @Test
    public void testCompanySpaces_BothMotorcyclesAndCars() {
        Company company = new Company(); // assuming Company is a simple POJO
        int motorcycles = 5;
        int cars = 3;

        parkingSpaceService.companySpaces(company, motorcycles, cars);

        verify(parkingRepository, times(motorcycles + cars)).save(Mockito.any(ParkingSpace.class));
    }
	@Test
	public void testCompanySpaces_OnlyMotorcycles() {
		Company company = new Company(); // assuming Company is a simple POJO
		int motorcycles = 4;
		int cars = 0;

		parkingSpaceService.companySpaces(company, motorcycles, cars);

		verify(parkingRepository, times(motorcycles)).save(Mockito.any(ParkingSpace.class));
	}

	@Test
	public void testCompanySpaces_OnlyCars() {
		Company company = new Company(); // assuming Company is a simple POJO
		int motorcycles = 0;
		int cars = 3;

		parkingSpaceService.companySpaces(company, motorcycles, cars);

		verify(parkingRepository, times(cars)).save(Mockito.any(ParkingSpace.class));
	}

	@Test
	public void testCompanySpaces_None() {
		Company company = new Company(); // assuming Company is a simple POJO
		int motorcycles = 0;
		int cars = 0;

		parkingSpaceService.companySpaces(company, motorcycles, cars);

		verify(parkingRepository, times(0)).save(Mockito.any(ParkingSpace.class));
	}

	@Test
	public void testCompanySpacesUpdate_IncreaseMotorcycles() {
		Company company = new Company(); // Assuming Company is a simple POJO
		int motorSpacesBefore = 2;
		int newMotorcyclesSpaces = 5;
		int carSpacesBefore = 3;
		int newCarsSpaces = 3;

		parkingSpaceService.companySpacesUpdate(company, motorSpacesBefore, newMotorcyclesSpaces, carSpacesBefore, newCarsSpaces);

		// Verify new motorcycle spaces are added
		verify(parkingRepository, times(newMotorcyclesSpaces - motorSpacesBefore)).save(Mockito.any(ParkingSpace.class));
	}

	@Test
	public void testCompanySpacesUpdate_DecreaseMotorcycles() {
		Company company = new Company();
		int motorSpacesBefore = 5;
		int newMotorcyclesSpaces = 2;
		int carSpacesBefore = 3;
		int newCarsSpaces = 3;

		// Mocking the repository call to return a list of ParkingSpace
		when(parkingRepository.findAllSpaces("motorcycle")).thenReturn(/* Mocked list of ParkingSpace */);

		parkingSpaceService.companySpacesUpdate(company, motorSpacesBefore, newMotorcyclesSpaces, carSpacesBefore, newCarsSpaces);

		// Verify correct number of motorcycle spaces are deleted
		verify(parkingRepository, times(motorSpacesBefore - newMotorcyclesSpaces)).delete(Mockito.any(ParkingSpace.class));
	}

	@Test
	public void testCompanySpacesUpdate_IncreaseCars() {
		Company company = new Company();
		int motorSpacesBefore = 2;
		int newMotorcyclesSpaces = 2;
		int carSpacesBefore = 1;
		int newCarsSpaces = 4;

		parkingSpaceService.companySpacesUpdate(company, motorSpacesBefore, newMotorcyclesSpaces, carSpacesBefore, newCarsSpaces);

		// Verify new car spaces are added
		verify(parkingRepository, times(newCarsSpaces - carSpacesBefore)).save(Mockito.any(ParkingSpace.class));
	}

	@Test
	public void testCompanySpacesUpdate_DecreaseCars() {
		Company company = new Company();
		int motorSpacesBefore = 2;
		int newMotorcyclesSpaces = 2;
		int carSpacesBefore = 5;
		int newCarsSpaces = 2;

		// Mocking the repository call to return a list of ParkingSpace
		when(parkingRepository.findAllSpaces("car")).thenReturn(/* Mocked list of ParkingSpace */);

		parkingSpaceService.companySpacesUpdate(company, motorSpacesBefore, newMotorcyclesSpaces, carSpacesBefore, newCarsSpaces);

		// Verify correct number of car spaces are deleted
		verify(parkingRepository, times(carSpacesBefore - newCarsSpaces)).delete(Mockito.any(ParkingSpace.class));
	}


	@Test
	public void testAvailableSpace() {
		String spaceType = "motorcycle";

		parkingSpaceService.availableSpace(spaceType);

		verify(parkingRepository).findOneAvailableSpace(spaceType);
	}

	@Test
	public void testChangeStatusToFree() {
		ParkingSpace space = new ParkingSpace();
		int status = 1; // FREE

		parkingSpaceService.changeStatus(space, status);

		assertEquals(SpaceStatus.FREE, space.getSpaceStatus());
		verify(parkingRepository).save(space);
	}

	@Test
	public void testChangeStatusToOccupied() {
		ParkingSpace space = new ParkingSpace();
		int status = 2; // OCCUPIED

		parkingSpaceService.changeStatus(space, status);

		assertEquals(SpaceStatus.OCCUPIED, space.getSpaceStatus());
		verify(parkingRepository).save(space);
	}

	@Test
	public void testChangeStatusInvalidCode() {
		ParkingSpace space = new ParkingSpace();
		int invalidStatus = 3;

		assertThrows(IllegalArgumentException.class, () -> {
			parkingSpaceService.changeStatus(space, invalidStatus);
		});
	}

	@Test
	public void testCompanySpaces_ExceptionScenario() {
		Company company = new Company();
		int motorcycles = 1;
		int cars = 1;

		Mockito.doThrow(new RuntimeException()).when(parkingRepository).save(Mockito.any(ParkingSpace.class));

		assertThrows(RuntimeException.class, () -> {
			parkingSpaceService.companySpaces(company, motorcycles, cars);
		});
	}

	@Test
	public void testCompanySpaces_InvalidInput() {
		Company company = new Company();
		int motorcycles = -1;
		int cars = -1;

		assertThrows(IllegalArgumentException.class, () -> {
			parkingSpaceService.companySpaces(company, motorcycles, cars);
		});
	}

	@Test
	public void testAvailableSpace_NoSpacesAvailable() {
		String spaceType = "car";
		when(parkingRepository.findOneAvailableSpace(spaceType)).thenReturn(null);

		assertNull(parkingSpaceService.availableSpace(spaceType));
	}

	@Test
	public void testChangeStatus_PersistencyCheck() {
		ParkingSpace space = new ParkingSpace();
		space.setSpaceStatus(SpaceStatus.FREE);
		int status = 2; // OCCUPIED

		parkingSpaceService.changeStatus(space, status);

		assertEquals(SpaceStatus.OCCUPIED, space.getSpaceStatus());
		verify(parkingRepository).save(space);
	}


}
