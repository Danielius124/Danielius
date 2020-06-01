package com.zanitech.danielius.rest;

import com.zanitech.danielius.entity.Building;
import com.zanitech.danielius.service.BuildingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BuildingRestController.class)
class BuildingRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuildingServiceImpl buildingService;

    @Test
    void getAllBuildings() throws Exception {
        when(buildingService.findAll()).thenReturn(
                Arrays.asList(new Building(102, "vilnius", "pavilnioniu",
                        3, "Danielius", 50, 50000, "house"),
                        new Building(103, "vilnius", "pavilnioniu",
                                33, "Danielius", 503, 500003, "house")));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/buildings")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":102,\"city\":\"vilnius\",\"street\":\"pavilnioniu\",\"number\":3," +
                        "\"owner\":\"Danielius\",\"size\":50,\"value\":50000,\"propType\":\"house\"}," +
                        "{\"id\":103,\"city\":\"vilnius\",\"street\":\"pavilnioniu\",\"number\":33,\"owner\":\"Danielius\"" +
                        ",\"size\":503.0,\"value\":500003,\"propType\":\"house\"}]"))
                .andReturn();
    }

    @Test
    void getBuilding() throws Exception {
        when(buildingService.findById(102)).thenReturn(new Building(102, "vilnius", "pavilnioniu",
                                3, "Danielius", 50, 50000, "house"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/buildings/102")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":102,\"city\":\"vilnius\",\"street\":\"pavilnioniu\",\"number\":3," +
                        "\"owner\":\"Danielius\",\"size\":50,\"value\":50000,\"propType\":\"house\"}"))
                .andReturn();
    }

}