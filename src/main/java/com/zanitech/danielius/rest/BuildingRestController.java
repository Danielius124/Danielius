package com.zanitech.danielius.rest;

import com.sun.istack.NotNull;
import com.zanitech.danielius.entity.Building;
import com.zanitech.danielius.exceptions.BuildingErrorResponse;
import com.zanitech.danielius.exceptions.BuildingNotFoundException;
import com.zanitech.danielius.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
public class BuildingRestController {

    @Autowired
    BuildingService buildingService;

    @GetMapping("/buildings")
    public List<Building> getAllBuildings(){
        return buildingService.findAll();
    }

    @GetMapping("/buildings/{buildingId}")
    public Building getBuilding(@PathVariable long buildingId){
        Building theBuilding = buildingNotFoundExc(buildingId);

        return theBuilding;
    }


    @PostMapping("/buildings") // custom validation needed
    @ResponseStatus(HttpStatus.CREATED)
    public Building addBuilding(@Valid @RequestBody Building theBuilding) {

        theBuilding.setId(0);


        buildingService.save(theBuilding);

        return theBuilding;
    }

    @PutMapping("/buildings")
    public Building updateBuilding(@Valid @RequestBody Building theBuilding){
        buildingService.save(theBuilding);

        return theBuilding;
    }

    @DeleteMapping("/buildings/{buildingId}")
    public String deleteBuildingById(@PathVariable long buildingId){

        deleteExceptionCheck(buildingId);
        buildingService.delete(buildingId);

        return "Building with id - " + buildingId + " was successfully deleted!";
    }


    @GetMapping("/tax/{owner}")
    public double taxByOwnedProperties(@PathVariable String owner){
        return buildingService.calculatedTaxes(owner);
    }

    @GetMapping("/buildingsOnStreet")
    public List<Optional<Building>> findByStreetAndType(@RequestParam Optional<String> street,
                                                        @RequestParam Optional<String> type,
                                                        @RequestParam Optional<Double> size){
        return buildingService.findByStreetAndType(street.orElse("_"), type.orElse("_"), size.orElse((double) 0));
    }

    // Exception Handlers

    @ExceptionHandler
    public ResponseEntity<BuildingErrorResponse> handleException(BuildingNotFoundException exc){

        BuildingErrorResponse error = new BuildingErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    public ResponseEntity<BuildingErrorResponse> handleException (Exception exc){

        BuildingErrorResponse error = new BuildingErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    public void deleteExceptionCheck(@PathVariable long buildingId) {
        Building theBuilding = buildingService.findById(buildingId);

        if(theBuilding == null){
            throw new BuildingNotFoundException("Building with this" + buildingId + " id is not found");
        }
    }

    public Building buildingNotFoundExc(@PathVariable long buildingId) {
        Building theBuilding = buildingService.findById(buildingId);

        if(theBuilding == null || (buildingId <= 0)){
            throw new BuildingNotFoundException("Building not with id - " + buildingId + " not found");
        } return theBuilding;
    }
}
