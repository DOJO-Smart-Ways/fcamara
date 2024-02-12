package com.test.parking.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "parking_space")
public class ParkingSpace extends Auditable implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private int spaceNumber;
	
	@Column(nullable = false)
	private String spaceType;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private SpaceStatus spaceStatus = SpaceStatus.FREE;
	
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;
	
	@JsonIgnore
    @OneToMany(mappedBy = "parkingSpace", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Ticket> tickets;
	
}
