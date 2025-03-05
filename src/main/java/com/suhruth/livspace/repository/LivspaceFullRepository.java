package com.suhruth.livspace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.suhruth.livspace.model.LivspaceFull;

@Repository
public interface LivspaceFullRepository extends JpaRepository<LivspaceFull, Integer> {

}
