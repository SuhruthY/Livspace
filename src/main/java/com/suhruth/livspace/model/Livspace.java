package com.suhruth.livspace.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livspace {
	
	@Id
	private int id;
	private String name;
	private String type;
	private String style;
	private int width;
	private int length;
	private String imageLink;

}
