package com.suhruth.livspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suhruth.livspace.model.Livspace;

@Repository
public interface LivspaceRepository extends JpaRepository<Livspace, Integer> {

}
