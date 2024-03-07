package com.test.parking.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.test.parking.model.Vehicle;
import com.test.parking.repository.VehicleRepository;
import com.test.parking.service.VehicleService;

public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddVehicle() {
        Vehicle vehicle = new Vehicle(); // Assume Vehicle is a properly defined entity
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        Vehicle savedVehicle = vehicleService.addVehicle(vehicle);

        assertNotNull(savedVehicle);
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    void testListVehicle() {
        List<Vehicle> vehicles = List.of(new Vehicle()); // Assume Vehicle is properly defined
        when(vehicleRepository.findAll()).thenReturn(vehicles);

        List<Vehicle> resultVehicles = vehicleService.listVehicle();

        assertFalse(resultVehicles.isEmpty());
        verify(vehicleRepository).findAll();
    }

    @Test
    void testGetVehicleById() {
        long id = 1L;
        Vehicle vehicle = new Vehicle(); // Setup a mock vehicle with expected properties
        when(vehicleRepository.findById(id)).thenReturn(java.util.Optional.of(vehicle));

        Vehicle result = vehicleService.getVehicleById(id);

        assertNotNull(result);
        verify(vehicleRepository).findById(id);
    }

    @Test
    void testDeleteVehicle() {
        long id = 1L;
        doNothing().when(vehicleRepository).deleteById(id);
        String response = vehicleService.deleteVehicle(id);

        assertEquals("Vehicle id: " + id + ", has been deleted successfuly.", response);
        verify(vehicleRepository).deleteById(id);
    }

    @Test
    void testUpdateVehicle() {
        long id = 1L;
        Vehicle originalVehicle = new Vehicle(); // Set up the original vehicle state
        Vehicle updatedVehicle = new Vehicle(); // Set up the desired updated state for the vehicle
        // Assume both original and updated vehicles have set properties for the test

        when(vehicleRepository.findById(id)).thenReturn(java.util.Optional.of(originalVehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(updatedVehicle);

        Vehicle result = vehicleService.updateVehicle(updatedVehicle, id);

        assertNotNull(result);
        verify(vehicleRepository).save(any(Vehicle.class));
    }


}
