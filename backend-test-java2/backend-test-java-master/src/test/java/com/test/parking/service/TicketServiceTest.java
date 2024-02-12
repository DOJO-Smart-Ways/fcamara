package com.test.parking.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.test.parking.model.*;
import com.test.parking.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TicketServiceTest {

    @InjectMocks
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
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEntranceTicket_VehicleExists_SpaceAvailable() {
        long vehicleId = 1L;
        Vehicle vehicle = new Vehicle(); // Assume Vehicle is a valid entity
        vehicle.setId(vehicleId);
        vehicle.setVehicleType("");

        ParkingSpace availableSpace = new ParkingSpace(); // Assume ParkingSpace is a valid entity

        long ticketId = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setVehicle(vehicle);
        ticket.setParkingSpace(availableSpace);

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(parkingService.availableSpace(vehicle.getVehicleType())).thenReturn(availableSpace);
        when(ticketRepository.save(any())).thenReturn(ticket);

        Ticket result = ticketService.entranceTicket(vehicleId);

        assertNotNull(result);
        assertEquals(vehicle, result.getVehicle());
        assertEquals(availableSpace, result.getParkingSpace());
        verify(parkingService).changeStatus(availableSpace, 2);
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    public void testEntranceTicket_VehicleDoesNotExist() {
        long vehicleId = 1L;
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> {
            ticketService.entranceTicket(vehicleId);
        });

        verify(parkingService, never()).availableSpace(any());
        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    public void testEntranceTicket_NoAvailableSpace() {
        long vehicleId = 1L;
        Vehicle vehicle = new Vehicle(); // Assume Vehicle is a valid entity
        vehicle.setId(vehicleId);
        vehicle.setVehicleType("");

        long ticketId = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);

        when(ticketRepository.save(any())).thenReturn(ticket);
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(parkingService.availableSpace(vehicle.getVehicleType())).thenReturn(null);

        Ticket result = ticketService.entranceTicket(vehicleId);

        assertNull(result.getParkingSpace());
        verify(parkingService, never()).changeStatus(any(ParkingSpace.class), anyInt());
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    public void testExitTicket_NormalBehavior() {
        long ticketId = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ParkingSpace space = new ParkingSpace();
        space.setId(1L);
        ticket.setParkingSpace(space);

        when(ticketRepository.save(ticket)).thenReturn(ticket);
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
        when(parkingRepository.findById(space.getId())).thenReturn(Optional.of(space));

        Ticket result = ticketService.exitTicket(ticketId);

        assertNotNull(result.getExitTime());
        verify(parkingService).changeStatus(space, 1);
        verify(ticketRepository).save(ticket);
    }

    @Test
    public void testExitTicket_TicketDoesNotExist() {
        long ticketId = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ParkingSpace space = new ParkingSpace();
        space.setId(1L);
        ticket.setParkingSpace(space);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

        Ticket result = ticketService.exitTicket(ticketId);

        assertNull(result);
        verify(parkingService, never()).changeStatus(any(ParkingSpace.class), anyInt());
    }

    @Test
    public void testExitTicket_ParkingSpaceDoesNotExist() {
        long ticketId = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ParkingSpace space = new ParkingSpace();
        space.setId(1L);
        ticket.setParkingSpace(space);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
        when(parkingRepository.findById(space.getId())).thenReturn(Optional.empty());

        Ticket result = ticketService.exitTicket(ticketId);

        assertNull(result);
        verify(parkingService, never()).changeStatus(any(ParkingSpace.class), anyInt());
        verify(ticketRepository).save(ticket); // Assuming the ticket is still saved without the exit time
    }

    @Test
    public void testListTicket_NormalBehavior() {
        List<Ticket> expectedTickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketRepository.findAll()).thenReturn(expectedTickets);

        List<Ticket> result = ticketService.listTicket();

        assertNotNull(result);
        assertEquals(expectedTickets.size(), result.size());
    }

    @Test
    public void testListTicket_EmptyList() {
        when(ticketRepository.findAll()).thenReturn(Collections.emptyList());

        List<Ticket> result = ticketService.listTicket();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testEntranceTicket_RepositoryThrowsException() {
        long vehicleId = 1L;
        when(vehicleRepository.findById(vehicleId)).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> {
            ticketService.entranceTicket(vehicleId);
        });
    }

    @Test
    public void testExitTicket_RepositoryThrowsException() {
        long ticketId = 1L;
        when(ticketRepository.findById(ticketId)).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> {
            ticketService.exitTicket(ticketId);
        });
    }


}