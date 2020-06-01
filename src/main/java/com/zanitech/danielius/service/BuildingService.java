package com.zanitech.danielius.service;

import com.zanitech.danielius.entity.Building;


import java.util.List;
import java.util.Optional;

public interface BuildingService {

    public List<Building> findAll();

    public Building findById(long theId);

    public void save(Building theBuilding);

    public void delete(long theId);

    public List<Building> findByOwner(String owner);

    List<Optional<Building>> findByStreetAndType(String street, String type, double size);

    public double calculatedTaxes(String owner);
}
