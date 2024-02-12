package com.test.parking.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.parking.model.ParkingSpace;
import com.test.parking.model.Ticket;
import com.test.parking.model.Vehicle;
import com.test.parking.repository.ParkingSpaceRepository;
import com.test.parking.repository.TicketRepository;
import com.test.parking.repository.VehicleRepository;

@Service
public class TicketService {
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private ParkingSpaceService parkingService;
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private ParkingSpaceRepository parkingRepository;
	
	public Ticket entranceTicket(long id) {
		Vehicle vehicle = vehicleRepository.findById(id).orElse(null);
		ParkingSpace availableSpace = parkingService.availableSpace(vehicle.getVehicleType());
		Ticket ticket = new Ticket();
		ticket.setVehicle(vehicle);
		ticket.setParkingSpace(availableSpace);
		parkingService.changeStatus(availableSpace, 2);
		return ticketRepository.save(ticket);
	}
	
	//receber o id do ticket
	public Ticket exitTicket(long id) {
		Ticket ticket = ticketRepository.findById(id).orElse(null);
		ParkingSpace space = parkingRepository.findById(ticket.getParkingSpace().getId()).orElse(null);
		parkingService.changeStatus(space, 1);
		ticket.setExitTime(Timestamp.from(Instant.now()));
		return ticketRepository.save(ticket);
	}
	
	public List<Ticket> listTicket() {
		return ticketRepository.findAll();
	}
}
