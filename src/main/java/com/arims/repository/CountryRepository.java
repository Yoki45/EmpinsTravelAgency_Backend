package com.arims.repository;

import com.arims.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CountryRepository extends JpaRepository<Country,Long> {
}
