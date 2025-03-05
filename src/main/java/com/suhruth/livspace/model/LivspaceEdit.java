package com.suhruth.livspace.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="livspace")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivspaceEdit {
	
	@Id
	private int id;
	private String name;
	private double latitude;
	private double longitude;
	private double acousticInsulationRating;
	private double thermalInsulationEfficiency;
	private double humidityResistance;
	private double customerInterestScore;
	private Date lastPaymentDate;
	private double pendingDues;
	private String imageLink;
	
}
