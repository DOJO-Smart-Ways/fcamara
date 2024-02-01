import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.test.parking.model.Vehicle;
import com.test.parking.repository.VehicleRepository;
import com.test.parking.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle(); // assuming a constructor or setters to initialize the vehicle
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);
    }

    @Test
    void testAddVehicle() {
        Vehicle savedVehicle = vehicleService.addVehicle(vehicle);
        assertNotNull(savedVehicle);
        verify(vehicleRepository, times(1)).save(vehicle);
    }

    @Test
    void testListVehicle() {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle);

        when(vehicleRepository.findAll()).thenReturn(vehicles);

        List<Vehicle> result = vehicleService.listVehicle();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(vehicleRepository, times(1)).findAll();
    }

    @Test
    void testGetVehicleByIdFound() {
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(vehicle));

        Vehicle foundVehicle = vehicleService.getVehicleById(1L);
        assertNotNull(foundVehicle);
        verify(vehicleRepository, times(1)).findById(1L);
    }

    @Test
    void testGetVehicleByIdNotFound() {
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.empty());

        Vehicle foundVehicle = vehicleService.getVehicleById(1L);
        assertNull(foundVehicle);
        verify(vehicleRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteVehicle() {
        doNothing().when(vehicleRepository).deleteById(anyLong());
        
        String message = vehicleService.deleteVehicle(1L);
        assertEquals("Vehicle id: 1, has been deleted successfully.", message);
        verify(vehicleRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateVehicleFound() {
        Vehicle updatedVehicle = new Vehicle(); // Initialize with new values
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(updatedVehicle);

        Vehicle result = vehicleService.updateVehicle(updatedVehicle, 1L);
        assertNotNull(result);
        verify(vehicleRepository, times(1)).findById(1L);
        verify(vehicleRepository, times(1)).save(vehicle);
    }

    @Test
    void testUpdateVehicleNotFound() {
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.empty());

        Vehicle result = vehicleService.updateVehicle(new Vehicle(), 1L);
        assertNull(result);
        verify(vehicleRepository, times(1)).findById(1L);
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    void testAddVehicleException() {
        when(vehicleRepository.save(any(Vehicle.class))).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            vehicleService.addVehicle(new Vehicle());
        });

        assertEquals("Database error", exception.getMessage());
        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

    @Test
    void testListVehicleEmpty() {
        when(vehicleRepository.findAll()).thenReturn(Collections.emptyList());

        List<Vehicle> result = vehicleService.listVehicle();
        assertTrue(result.isEmpty());
        verify(vehicleRepository, times(1)).findAll();
    }

    @Test
    void testUpdateVehiclePartialUpdate() {
        Vehicle partialUpdateVehicle = new Vehicle();
        partialUpdateVehicle.setBrand("New Brand");

        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(i -> i.getArguments()[0]);

        Vehicle result = vehicleService.updateVehicle(partialUpdateVehicle, 1L);
        assertNotNull(result);
        assertEquals("New Brand", result.getBrand());
        verify(vehicleRepository, times(1)).findById(1L);
        verify(vehicleRepository, times(1)).save(vehicle);
    }

    @Test
    void testGetVehicleByIdWithInvalidId() {
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.empty());

        Vehicle result = vehicleService.getVehicleById(-1L);
        assertNull(result);
        verify(vehicleRepository, times(1)).findById(-1L);
    }

    @Test
    void testDeleteVehicleException() {
        doThrow(new RuntimeException("Database error")).when(vehicleRepository).deleteById(anyLong());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            vehicleService.deleteVehicle(1L);
        });

        assertEquals("Database error", exception.getMessage());
        verify(vehicleRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateVehicleWithNullVehicle() {
        Vehicle result = vehicleService.updateVehicle(null, 1L);
        assertNull(result);
        verify(vehicleRepository, never()).findById(anyLong());
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    void testDeleteVehicleNonExistentId() {
        doNothing().when(vehicleRepository).deleteById(anyLong());

        String message = vehicleService.deleteVehicle(999L);
        assertEquals("Vehicle id: 999, has been deleted successfully.", message);
        verify(vehicleRepository, times(1)).deleteById(999L);
    }

    @Test
    void testListVehicleFailure() {
        when(vehicleRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            vehicleService.listVehicle();
        });

        assertEquals("Database error", exception.getMessage());
        verify(vehicleRepository, times(1)).findAll();
    }

    @Test
    void testGetVehicleByIdWithNullId() {
        Vehicle result = vehicleService.getVehicleById(null);
        assertNull(result);
        verify(vehicleRepository, never()).findById(anyLong());
    }

    @Test
    void testUpdateVehicleWhenExistingVehicleIsNull() {
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.empty());

        Vehicle updatedVehicle = new Vehicle();
        Vehicle result = vehicleService.updateVehicle(updatedVehicle, 1L);
        assertNull(result);
        verify(vehicleRepository, times(1)).findById(1L);
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }



}
