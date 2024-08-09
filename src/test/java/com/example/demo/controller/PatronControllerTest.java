package com.example.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.model.dto.PatronDto;
import com.example.demo.service.impl.PatronServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatronController.class)
public class PatronControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronServiceImpl patronService;

    private PatronDto patronDto;

    @BeforeEach
    public void setup() {
    	patronDto = new PatronDto();
    	patronDto.setId(1L);
    	patronDto.setName("John Doe");
    	patronDto.setEmail("john.doe@example.com");
    	patronDto.setPhoneNumber("01234567890");
    }

    @Test
    public void testCreatePatron() throws Exception {
        when(patronService.createPatron(any(PatronDto.class))).thenReturn(patronDto);

        String requestBody = "{ \"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"mobile\": \"01234567890\" }";

        mockMvc.perform(post("/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("01234567890"));

        verify(patronService, times(1)).createPatron(any(PatronDto.class));
    }

    @Test
    public void testUpdatePatronById() throws Exception {
        when(patronService.updatePatronById(anyLong(), any(PatronDto.class))).thenReturn(patronDto);

        String requestBody = "{ \"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"mobile\": \"01234567890\" }";

        mockMvc.perform(put("/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("01234567890"));

        verify(patronService, times(1)).updatePatronById(anyLong(), any(PatronDto.class));
    }

    @Test
    public void testGetAllPatrons() throws Exception {
        List<PatronDto> patrons = Arrays.asList(patronDto);
        when(patronService.getAllPatrons()).thenReturn(patrons);

        mockMvc.perform(get("/patrons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[0].phoneNumber").value("01234567890"));

        verify(patronService, times(1)).getAllPatrons();
    }

    @Test
    public void testGetPatronById() throws Exception {
        when(patronService.getPatronById(anyLong())).thenReturn(patronDto);

        mockMvc.perform(get("/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("01234567890"));

        verify(patronService, times(1)).getPatronById(anyLong());
    }

    @Test
    public void testDeletePatronById() throws Exception {
        doNothing().when(patronService).deletePatronById(anyLong());

        mockMvc.perform(delete("/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(patronService, times(1)).deletePatronById(anyLong());
    }
}
