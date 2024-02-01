package com.test.parking.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.test.parking.model.Vehicle;
import com.test.parking.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class VehicleServiceTest {

    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        vehicleService = new VehicleService();
        vehicleService.vehicleRepository = vehicleRepository;
    }

    @Test
    public void testAddVehicle() {
        Vehicle vehicle = new Vehicle(); // Assume properties are set accordingly
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        Vehicle result = vehicleService.addVehicle(vehicle);
        verify(vehicleRepository).save(vehicle);
        assertEquals(vehicle, result);
    }

    @Test
    public void testListVehicle() {
        List<Vehicle> vehicles = Arrays.asList(new Vehicle(), new Vehicle());
        when(vehicleRepository.findAll()).thenReturn(vehicles);

        List<Vehicle> result = vehicleService.listVehicle();
        verify(vehicleRepository).findAll();
        assertEquals(vehicles, result);
    }

    @Test
    public void testGetVehicleById() {
        long id = 1L;
        Vehicle vehicle = new Vehicle();
        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));

        Vehicle result = vehicleService.getVehicleById(id);
        verify(vehicleRepository).findById(id);
        assertEquals(vehicle, result);
    }

    @Test
    public void testDeleteVehicle() {
        long id = 1L;
        String expectedResponse = "Vehicle id: " + id + ", has been deleted successfully.";

        String response = vehicleService.deleteVehicle(id);
        verify(vehicleRepository).deleteById(id);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testUpdateVehicle() {
        long id = 1L;
        Vehicle existingVehicle = new Vehicle();
        Vehicle updatedVehicle = new Vehicle(); // Set properties accordingly

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(existingVehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(updatedVehicle);

        Vehicle result = vehicleService.updateVehicle(updatedVehicle, id);
        verify(vehicleRepository).findById(id);
        verify(vehicleRepository).save(existingVehicle);
        assertEquals(updatedVehicle, result);
    }
}
