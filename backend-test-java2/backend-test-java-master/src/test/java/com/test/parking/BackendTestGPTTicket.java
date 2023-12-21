package com.test.parking.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.test.parking.model.ParkingSpace;
import com.test.parking.model.Ticket;
import com.test.parking.model.Vehicle;
import com.test.parking.repository.ParkingSpaceRepository;
import com.test.parking.repository.TicketRepository;
import com.test.parking.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TicketServiceTest {

    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private ParkingSpaceService parkingService;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private ParkingSpaceRepository parkingRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ticketService = new TicketService();
        ticketService.ticketRepository = ticketRepository;
        ticketService.parkingService = parkingService;
        ticketService.vehicleRepository = vehicleRepository;
        ticketService.parkingRepository = parkingRepository;
    }

    @Test
    public void testEntranceTicket() {
        long vehicleId = 1L;
        Vehicle vehicle = new Vehicle(); // Assume properties are set accordingly
        ParkingSpace availableSpace = new ParkingSpace();
        Ticket ticket = new Ticket();

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(parkingService.availableSpace(anyString())).thenReturn(availableSpace);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket result = ticketService.entranceTicket(vehicleId);
        verify(vehicleRepository).findById(vehicleId);
        verify(parkingService).availableSpace(vehicle.getVehicleType());
        verify(parkingService).changeStatus(availableSpace, 2);
        verify(ticketRepository).save(ticket);
        assertEquals(ticket, result);
    }

    @Test
    public void testExitTicket() {
        long ticketId = 1L;
        Ticket ticket = new Ticket();
        ParkingSpace space = new ParkingSpace();
        ticket.setParkingSpace(space);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
        when(parkingRepository.findById(space.getId())).thenReturn(Optional.of(space));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket result = ticketService.exitTicket(ticketId);
        verify(ticketRepository).findById(ticketId);
        verify(parkingRepository).findById(space.getId());
        verify(parkingService).changeStatus(space, 1);
        verify(ticketRepository).save(ticket);
        assertNotNull(result.getExitTime());
    }

    @Test
    public void testListTicket() {
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketRepository.findAll()).thenReturn(tickets);

        List<Ticket> result = ticketService.listTicket();
        verify(ticketRepository).findAll();
        assertEquals(tickets, result);
    }
}
