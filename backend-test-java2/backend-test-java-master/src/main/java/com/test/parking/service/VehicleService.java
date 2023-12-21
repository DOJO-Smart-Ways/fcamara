package com.test.parking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.parking.model.Vehicle;
import com.test.parking.repository.VehicleRepository;

@Service
public class VehicleService {
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	public Vehicle addVehicle(Vehicle vehicle) {
		return vehicleRepository.save(vehicle);
	}
	
	public List<Vehicle> listVehicle() {
		return vehicleRepository.findAll();
	}
	
	public Vehicle getVehicleById(long id) {
		return vehicleRepository.findById(id).orElse(null);
	}
	
	public String deleteVehicle(long id) {
		vehicleRepository.deleteById(id);
		return "Vehicle id: " + id + ", has been deleted successfuly.";
	}
	
	public Vehicle updateVehicle(Vehicle vehicle, long id) {
		Vehicle existingVehicle = vehicleRepository.findById(id).orElse(null);
		existingVehicle.setBrand(vehicle.getBrand());
		existingVehicle.setModel(vehicle.getModel());
		existingVehicle.setColor(vehicle.getColor());
		existingVehicle.setLicensePlate(vehicle.getLicensePlate());
		existingVehicle.setVehicleType(vehicle.getVehicleType());
		return vehicleRepository.save(existingVehicle);
	}
}
