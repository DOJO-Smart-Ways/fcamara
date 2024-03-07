package com.test.parking.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.test.parking.model.ParkingSpace;
import com.test.parking.model.Ticket;
import com.test.parking.model.Vehicle;
import com.test.parking.repository.ParkingSpaceRepository;
import com.test.parking.repository.TicketRepository;
import com.test.parking.repository.VehicleRepository;
import com.test.parking.service.ParkingSpaceService;
import com.test.parking.service.TicketService;

public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private ParkingSpaceService parkingService;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private ParkingSpaceRepository parkingRepository;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEntranceTicket() {
        long vehicleId = 1L;
        Vehicle vehicle = new Vehicle(); // Assuming Vehicle is a properly defined entity
        vehicle.setVehicleType("car");
        ParkingSpace availableSpace = new ParkingSpace();
        Ticket ticket = new Ticket();

        when(vehicleRepository.findById(vehicleId)).thenReturn(java.util.Optional.of(vehicle));
        when(parkingService.availableSpace(anyString())).thenReturn(availableSpace);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket resultTicket = ticketService.entranceTicket(vehicleId);

        assertNotNull(resultTicket);
        verify(vehicleRepository).findById(vehicleId);
        verify(parkingService).availableSpace(vehicle.getVehicleType());
        verify(parkingService).changeStatus(availableSpace, 2);
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    void testExitTicket() {
        long ticketId = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ParkingSpace space = new ParkingSpace();
        space.setId(2L);
        ticket.setParkingSpace(space);

        when(ticketRepository.findById(ticketId)).thenReturn(java.util.Optional.of(ticket));
        when(parkingRepository.findById(space.getId())).thenReturn(java.util.Optional.of(space));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket resultTicket = ticketService.exitTicket(ticketId);

        assertNotNull(resultTicket.getExitTime());
        verify(ticketRepository).findById(ticketId);
        verify(parkingService).changeStatus(space, 1);
        verify(ticketRepository).save(ticket);
    }

    @Test
    void testListTicket() {
        List<Ticket> tickets = List.of(new Ticket(), new Ticket());
        when(ticketRepository.findAll()).thenReturn(tickets);

        List<Ticket> resultTickets = ticketService.listTicket();

        assertFalse(resultTickets.isEmpty());
        verify(ticketRepository).findAll();
    }

    @Test
    void testEntranceTicket_FullParking() {
        long vehicleId = 1L;
        Vehicle vehicle = new Vehicle(); // Set up vehicle with a type
        vehicle.setVehicleType("car");

        long ticketId = 1L;
        Ticket ticket = new Ticket();
        ticket.setVehicle(vehicle);

        when(ticketRepository.save(ticket)).thenReturn(ticket);
        when(vehicleRepository.findById(vehicleId)).thenReturn(java.util.Optional.of(vehicle));
        when(parkingService.availableSpace(vehicle.getVehicleType())).thenReturn(null);

        Ticket resultTicket = ticketService.entranceTicket(vehicleId);

        assertNull(resultTicket.getParkingSpace());
        verify(parkingService).availableSpace(vehicle.getVehicleType());
        // Assuming the service handles null parking spaces appropriately
    }

    @Test
    void testExitTicket_VerifyExitTime() {
        long ticketId = 1L;
        Ticket ticket = new Ticket();
        ParkingSpace space = new ParkingSpace();
        ticket.setParkingSpace(space);

        when(ticketRepository.save(ticket)).thenReturn(ticket);
        when(ticketRepository.findById(ticketId)).thenReturn(java.util.Optional.of(ticket));
        when(parkingRepository.findById(space.getId())).thenReturn(java.util.Optional.of(space));

        Ticket updatedTicket = ticketService.exitTicket(ticketId);

        assertNotNull(updatedTicket.getExitTime());
        assertTrue(updatedTicket.getExitTime().after(Timestamp.from(Instant.now().minusSeconds(1))));
        verify(parkingService).changeStatus(space, 1);
    }


}
