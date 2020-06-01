package com.zanitech.danielius.dao;

import com.zanitech.danielius.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    @Query(value = "SELECT * FROM buildings u WHERE u.owner = :owner", nativeQuery = true)
    List<Building> findByOwner(@Param("owner") String owner);

    @Query(value = "SELECT * FROM buildings b WHERE b.street like %:street% AND b.property_type = :type", nativeQuery = true)
    List<Building> findByStreetAndType(@Param("street") String street, @Param("type") String type);
}
