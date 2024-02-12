package com.test.parking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.parking.model.Company;
import com.test.parking.model.ParkingSpace;
import com.test.parking.model.SpaceStatus;
import com.test.parking.repository.ParkingSpaceRepository;

@Service
public class ParkingSpaceService {
	
	@Autowired
	private ParkingSpaceRepository parkingRepository;
	
	public void companySpaces(Company company, int motorcycles, int cars) {
		
		if (motorcycles != 0) {
			for (int i = 1; i <= motorcycles; i++) {
				ParkingSpace motoSpace = new ParkingSpace();
				motoSpace.setSpaceNumber(i);
				motoSpace.setSpaceType("motorcycle");
				motoSpace.setCompany(company);
				parkingRepository.save(motoSpace);
			}
		}
		
		if (cars != 0) {
			for (int i = 1; i <= cars; i++) {
				ParkingSpace carSpace = new ParkingSpace();
				carSpace.setSpaceNumber(i);
				carSpace.setSpaceType("car");
				carSpace.setCompany(company);
				parkingRepository.save(carSpace);
			}
		}
	}
	
	public void companySpacesUpdate(Company company, int motorSpacesBefore, int newMotorcyclesSpaces,  int carSpacesBefore, int newCarsSpaces) {
		
		if (motorSpacesBefore < newMotorcyclesSpaces) {
			for (int i = motorSpacesBefore+1; i <= newMotorcyclesSpaces; i++) {
				ParkingSpace motoSpace = new ParkingSpace();
				motoSpace.setSpaceNumber(i);
				motoSpace.setSpaceType("motorcycle");
				motoSpace.setCompany(company);
				parkingRepository.save(motoSpace);
			}
		} else if (motorSpacesBefore > newMotorcyclesSpaces) {
			List<ParkingSpace> spacesToDelete = parkingRepository.findAllSpaces("motorcycle");
			for (int i = 0; i < motorSpacesBefore - newMotorcyclesSpaces; i++) {
				parkingRepository.delete(spacesToDelete.get(i));
			}
		}

		if (carSpacesBefore < newCarsSpaces) {
			for (int i = carSpacesBefore+1; i <= newCarsSpaces; i++) {
				ParkingSpace carSpace = new ParkingSpace();
				carSpace.setSpaceNumber(i);
				carSpace.setSpaceType("car");
				carSpace.setCompany(company);
				parkingRepository.save(carSpace);
			}
		} else if (carSpacesBefore > newCarsSpaces) {
			List<ParkingSpace> spacesToDelete = parkingRepository.findAllSpaces("car");
			for (int i = 0; i < carSpacesBefore - newCarsSpaces; i++) {
				parkingRepository.delete(spacesToDelete.get(i));
			}
		}
		
		
	}
	
	public ParkingSpace availableSpace(String spaceType) {
		ParkingSpace parkingSpace = parkingRepository.findOneAvailableSpace(spaceType);
		return parkingSpace;
	}
	
	/*
	 * Method to change status of a parking space
	 * from FREE to OCCUPIED.
	 * @param space The ParkingSpace entity to set the status
	 * @param status the status you want to set. 1 for FREE or 2 for OCCUPIED
	 * 
	 * */
	public void changeStatus(ParkingSpace space, int status) {
		switch (status) {
		case 1: 
			space.setSpaceStatus(SpaceStatus.FREE);
			parkingRepository.save(space);
			break;
		case 2:
			space.setSpaceStatus(SpaceStatus.OCCUPIED);
			parkingRepository.save(space);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + status);
		}
		
	}
}
