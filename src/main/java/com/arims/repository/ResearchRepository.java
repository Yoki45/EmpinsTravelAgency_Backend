package com.arims.repository;


import com.arims.model.Research;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ResearchRepository extends JpaRepository<Research,Long> {
}
