package com.test.parking.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.test.parking.model.Company;
import com.test.parking.model.Ticket;
import com.test.parking.model.Vehicle;
import com.test.parking.service.CompanyService;
import com.test.parking.service.TicketService;
import com.test.parking.service.VehicleService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/parking")
public class ParkingController {
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private TicketService ticketService;
	
	@PostMapping(value = "/company")
	@ResponseStatus(HttpStatus.CREATED)
	public Company registerCompany(@Valid @RequestBody Company company) {
		return companyService.addCompany(company);
	}
	
	@GetMapping(value = "/company")
	public List<Company> listCompany() {
		return companyService.listCompany();
	}
	
	@GetMapping(value = "/company/{id}")
	public Company listOneCompany(@PathVariable long id) {
		return companyService.getCompanyById(id);
	}
	
	@PutMapping(value = "/company/{id}")
	public Company updateCompany(@Valid @RequestBody Company company, @PathVariable long id) {
		return companyService.updateCompany(company, id);
	}
	
	@DeleteMapping(value = "/company/{id}")
	public String deleteCompany(@PathVariable long id) {
		companyService.deleteCompany(id);
		return "Company id: " + id + ", has been successfuly deleted";
	}
	
	@PostMapping(value = "/vehicle")
	public Vehicle registerVehicle(@Valid @RequestBody Vehicle vehicle) {
		return vehicleService.addVehicle(vehicle);
	}
	
	@GetMapping(value = "/vehicle")
	public List<Vehicle> listVehicle() {
		return vehicleService.listVehicle();
	}
	
	@GetMapping(value = "/vehicle/{id}")
	public Vehicle listOneVehicle(@PathVariable long id) {
		return vehicleService.getVehicleById(id);
	}
	
	@PutMapping(value = "/vehicle/{id}")
	public Vehicle updateVehicle(@Valid @RequestBody Vehicle vehicle, @PathVariable long id) {
		return vehicleService.updateVehicle(vehicle, id);
	}
	
	@DeleteMapping(value = "/vehicle/{id}")
	public String deleteVehicle(@PathVariable long id) {
		vehicleService.deleteVehicle(id);
		return "Vehicle id: " + id + ", has been successfuly deleted";
	}
	
	@PostMapping(value = "/ticket_in/{vehicle_id}")
	public Ticket registerTicket(@PathVariable long vehicle_id) {
		return ticketService.entranceTicket(vehicle_id);
	}
	
	@GetMapping(value = "/ticket")
	public List<Ticket> listTicket() {
		return ticketService.listTicket();
	}
	
	@PostMapping(value = "/ticket_out/{ticket_id}")
	public Ticket exitTicket(@PathVariable long ticket_id) {
		return ticketService.exitTicket(ticket_id);
	}
}
