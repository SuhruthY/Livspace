package com.suhruth.livspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suhruth.livspace.repository.LivspaceRepository;

@RestController
@RequestMapping("/api")
public class LivspaceRestController {
	
	@Autowired
	LivspaceRepository repo;
	
	@GetMapping
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(repo.findAll());
	}

}
