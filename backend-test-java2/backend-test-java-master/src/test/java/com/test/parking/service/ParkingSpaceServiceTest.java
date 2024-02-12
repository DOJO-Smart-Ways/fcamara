package com.test.parking.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.test.parking.model.Company;
import com.test.parking.model.ParkingSpace;
import com.test.parking.model.SpaceStatus;
import com.test.parking.repository.ParkingSpaceRepository;
import com.test.parking.service.ParkingSpaceService;

import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
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
    public void testCompanySpaces_BothMotorcyclesAndCars() {
        Company company = new Company(); // assuming Company is a simple POJO
        int motorcycles = 5;
        int cars = 3;

        parkingSpaceService.companySpaces(company, motorcycles, cars);

        Mockito.verify(parkingRepository, Mockito.times(motorcycles + cars)).save(Mockito.any(ParkingSpace.class));
    }
	@Test
	public void testCompanySpaces_OnlyMotorcycles() {
		Company company = new Company(); // assuming Company is a simple POJO
		int motorcycles = 4;
		int cars = 0;

		parkingSpaceService.companySpaces(company, motorcycles, cars);

		Mockito.verify(parkingRepository, Mockito.times(motorcycles)).save(Mockito.any(ParkingSpace.class));
	}

	@Test
	public void testCompanySpaces_OnlyCars() {
		Company company = new Company(); // assuming Company is a simple POJO
		int motorcycles = 0;
		int cars = 3;

		parkingSpaceService.companySpaces(company, motorcycles, cars);

		Mockito.verify(parkingRepository, Mockito.times(cars)).save(Mockito.any(ParkingSpace.class));
	}

	@Test
	public void testCompanySpaces_None() {
		Company company = new Company(); // assuming Company is a simple POJO
		int motorcycles = 0;
		int cars = 0;

		parkingSpaceService.companySpaces(company, motorcycles, cars);

		Mockito.verify(parkingRepository, Mockito.times(0)).save(Mockito.any(ParkingSpace.class));
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
		Mockito.verify(parkingRepository, Mockito.times(newMotorcyclesSpaces - motorSpacesBefore)).save(Mockito.any(ParkingSpace.class));
	}

	@Test
	public void testCompanySpacesUpdate_DecreaseMotorcycles() {
		Company company = new Company();
		int motorSpacesBefore = 5;
		int newMotorcyclesSpaces = 2;
		int carSpacesBefore = 3;
		int newCarsSpaces = 3;

		// Mocking the repository call to return a list of ParkingSpace
		List<ParkingSpace> spaces = new ArrayList<>();
        spaces.add(new ParkingSpace());
		spaces.add(new ParkingSpace());
		spaces.add(new ParkingSpace());

		Mockito.when(parkingRepository.findAllSpaces("motorcycle")).thenReturn(spaces);

		parkingSpaceService.companySpacesUpdate(company, motorSpacesBefore, newMotorcyclesSpaces, carSpacesBefore, newCarsSpaces);

		// Verify correct number of motorcycle spaces are deleted
		Mockito.verify(parkingRepository, Mockito.times(motorSpacesBefore - newMotorcyclesSpaces)).delete(Mockito.any(ParkingSpace.class));
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
		Mockito.verify(parkingRepository, Mockito.times(newCarsSpaces - carSpacesBefore)).save(Mockito.any(ParkingSpace.class));
	}

	@Test
	public void testCompanySpacesUpdate_DecreaseCars() {
		Company company = new Company();
		int motorSpacesBefore = 2;
		int newMotorcyclesSpaces = 2;
		int carSpacesBefore = 5;
		int newCarsSpaces = 2;

		// Mocking the repository call to return a list of ParkingSpace
		List<ParkingSpace> spaces = new ArrayList<>();
        spaces.add(new ParkingSpace());
		spaces.add(new ParkingSpace());
		spaces.add(new ParkingSpace());
		spaces.add(new ParkingSpace());

		Mockito.when(parkingRepository.findAllSpaces("car")).thenReturn(spaces);

		parkingSpaceService.companySpacesUpdate(company, motorSpacesBefore, newMotorcyclesSpaces, carSpacesBefore, newCarsSpaces);

		// Verify correct number of car spaces are deleted
		Mockito.verify(parkingRepository, Mockito.times(carSpacesBefore - newCarsSpaces)).delete(Mockito.any(ParkingSpace.class));
	}


	@Test
	public void testAvailableSpace() {
		String spaceType = "motorcycle";

		parkingSpaceService.availableSpace(spaceType);

		Mockito.verify(parkingRepository).findOneAvailableSpace(spaceType);
	}

	@Test
	public void testChangeStatusToFree() {
		ParkingSpace space = new ParkingSpace();
		int status = 1; // FREE

		parkingSpaceService.changeStatus(space, status);

		Assertions.assertEquals(SpaceStatus.FREE, space.getSpaceStatus());
		Mockito.verify(parkingRepository).save(space);
	}

	@Test
	public void testChangeStatusToOccupied() {
		ParkingSpace space = new ParkingSpace();
		int status = 2; // OCCUPIED

		parkingSpaceService.changeStatus(space, status);

		Assertions.assertEquals(SpaceStatus.OCCUPIED, space.getSpaceStatus());
		Mockito.verify(parkingRepository).save(space);
	}

	@Test
	public void testChangeStatusInvalidCode() {
		ParkingSpace space = new ParkingSpace();
		int invalidStatus = 3;

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			parkingSpaceService.changeStatus(space, invalidStatus);
		});
	}

	@Test
	public void testCompanySpaces_ExceptionScenario() {
		Company company = new Company();
		int motorcycles = 1;
		int cars = 1;

		Mockito.doThrow(new RuntimeException()).when(parkingRepository).save(Mockito.any(ParkingSpace.class));

		Assertions.assertThrows(RuntimeException.class, () -> {
			parkingSpaceService.companySpaces(company, motorcycles, cars);
		});
	}

	@Test
	public void testAvailableSpace_NoSpacesAvailable() {
		String spaceType = "car";
		Mockito.when(parkingRepository.findOneAvailableSpace(spaceType)).thenReturn(null);

		Assertions.assertNull(parkingSpaceService.availableSpace(spaceType));
	}

	@Test
	public void testChangeStatus_PersistencyCheck() {
		ParkingSpace space = new ParkingSpace();
		space.setSpaceStatus(SpaceStatus.FREE);
		int status = 2; // OCCUPIED

		parkingSpaceService.changeStatus(space, status);

		Assertions.assertEquals(SpaceStatus.OCCUPIED, space.getSpaceStatus());
		Mockito.verify(parkingRepository).save(space);
	}


}
