package com.zanitech.danielius.service;

import com.zanitech.danielius.dao.BuildingRepository;
import com.zanitech.danielius.entity.Building;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class BuildingServiceImplTest {

    String owner = "Danielius";

    @InjectMocks
    private BuildingServiceImpl service;

    @Mock
    private BuildingRepository repository;

    @Test
    void calculatedTaxes_Basic()  {
        when(repository.findByOwner(owner)).thenReturn(Arrays.asList(new Building(102, "Vilnius", "pavilnioniu", 3, "Danielius", 50, 50000, "house")));
        double buildings = service.calculatedTaxes(owner);
        assertEquals(4000, buildings);
    }


    @Test
    void calculatedTaxes_Some()  {
       when(repository.findByOwner(owner)).thenReturn(Arrays.asList(new Building(102, "Vilnius", "pavilnioniu", 3, "Danielius", 50, 50000, "apartment"),
               new Building(103, "Vilnius", "pavilnioniu", 3, "Danielius", 50, 50000, "apartment"),
               new Building(104, "Vilnius", "pavilnioniu", 3, "Danielius", 50, 50000, "apartment")));

       double buildings = service.calculatedTaxes(owner);
        assertEquals(7500, buildings);
    }

    @Test
    void calculatedTaxes_SomeDifferentPropertyType()  {
        when(repository.findByOwner(owner)).thenReturn(Arrays.asList(new Building(102, "Vilnius", "pavilnioniu", 3, "Danielius", 50, 50000, "house"),
                new Building(103, "Vilnius", "pavilnioniu", 3, "Danielius", 50, 50000, "apartment"),
                new Building(104, "Vilnius", "pavilnioniu", 3, "Danielius", 50, 50000, "commercial")));

        double buildings = service.calculatedTaxes(owner);
        assertEquals(19000, buildings);
    }

}