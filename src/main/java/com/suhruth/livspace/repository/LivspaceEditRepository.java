package com.suhruth.livspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suhruth.livspace.model.LivspaceEdit;

@Repository
public interface LivspaceEditRepository extends JpaRepository<LivspaceEdit, Integer> {

}
