package com.test.parking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.test.parking.model.ParkingSpace;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {

	@Query(value = "SELECT * FROM parking_space WHERE space_type = ?1 ORDER BY space_number DESC;", nativeQuery = true)
	List<ParkingSpace> findAllSpaces(String spaceType);
	
	@Query(value = "SELECT * FROM parking_space WHERE space_type = ?1 AND space_status = \"FREE\" LIMIT 1;", nativeQuery = true)
	ParkingSpace findOneAvailableSpace(String spaceType);
}
