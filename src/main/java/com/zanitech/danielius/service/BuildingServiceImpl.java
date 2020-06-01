package com.zanitech.danielius.service;

import com.zanitech.danielius.dao.BuildingRepository;
import com.zanitech.danielius.entity.Building;
import com.zanitech.danielius.exceptions.BuildingNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.util.*;


@Service
public class BuildingServiceImpl implements BuildingService{

    double ApartmentTax = 0.05;
    double houseTax = 0.08;
    double industrialTax = 0.1;
    double commercialTax = 0.25;

    @Autowired
    BuildingRepository buildingRepository;

    @Override
    public List<Building> findAll() {
        return buildingRepository.findAll();
    }

    @Override
    public Building findById(long theId) {
        Optional<Building> result = buildingRepository.findById(theId);

        Building theBuilding = null;

        if(result.isPresent()){
            theBuilding = result.get();
        }

        return theBuilding;
    }

    @Override
    public List<Building> findByOwner(String owner) {

        return buildingRepository.findByOwner(owner);
    }

    @Override
    public void save(Building theBuilding) {
        theBuilding.getPropType().toLowerCase();
        buildingRepository.save(theBuilding);
    }

    @Override
    public void delete(long theId) {


        buildingRepository.deleteById(theId);
    }

    @Override
    public List<Optional<Building>> findByStreetAndType(String street, String type, double size) {

        List<Building> result;
        result = buildingRepository.findByStreetAndType(street, type);

        if(result == null){
            throw new BuildingNotFoundException("Building on this street " + street + " and property type of" + type + " is not found!!");
        }

        HashMap hashMap = addDiferenceToHashMap(size, result);

        Map<Float, Long> sorted = sortHashMap(hashMap);

        List<Optional<Building>> endpoint = sortByNearSize(sorted);

        endpoint.sort(((o1, o2) -> Double.compare(o2.get().getValue(), o1.get().getValue())));


        return endpoint;
        
    }

    public List<Optional<Building>> sortByNearSize(Map<Float, Long> sorted) {
        List<Long> ids = new ArrayList<Long>(sorted.values());

        Optional<Building> rez = null;
        List<Optional<Building>> endpoint = new ArrayList<>();
        int lenght = ids.size();

        Iterator it = ids.iterator();

        int i = 0;
        while(i < 3 && i < lenght ){
            rez = buildingRepository.findById(ids.get(i));
            endpoint.add(rez);
            System.out.println(it.hasNext());
            System.out.println(lenght);
            i++;
        }
        return endpoint;
    }

    public Map<Float, Long> sortHashMap(HashMap hashMap) {
        Map<Float, Long> sorted = new TreeMap<Float, Long>(hashMap);
        Set set = sorted.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry me = (Map.Entry)iterator.next();
        }
        return sorted;
    }

    public HashMap addDiferenceToHashMap(double size, List<Building> result) {
        HashMap hashMap = new HashMap();
        for(Building theBuilding : result){
            float difference = 0;
            difference = (float) (size - theBuilding.getSize());
            if(difference < 0){
                difference = difference * -1;
            }
            hashMap.put(difference, theBuilding.getId());
            System.out.println(difference);
        }
        return hashMap;
    }

    @Override
    public double calculatedTaxes(String owner) {
        double tax = 0;

        List<Building> result = buildingRepository.findByOwner(owner);

        if(result.isEmpty()){
            throw new BuildingNotFoundException("Owner which has property - " + owner + " not found!");
        }

        int lenght = result.size();

        for(int i = 0; i < lenght; i++){
            Building theBuilding = result.get(i);

            switch (theBuilding.getPropType()){
                case "apartment":
                    tax = tax + (ApartmentTax * theBuilding.getValue());
                    break;
                case "house":
                    tax = tax + (houseTax * theBuilding.getValue());
                    break;
                case "commercial":
                    tax = tax + (commercialTax * theBuilding.getValue());
                    break;
                case "industrial":
                    tax = tax + (industrialTax * theBuilding.getValue());
                    break;

            }
        }
        tax = Double.parseDouble(new DecimalFormat("##.##").format(tax));
        return tax;
    }

}
