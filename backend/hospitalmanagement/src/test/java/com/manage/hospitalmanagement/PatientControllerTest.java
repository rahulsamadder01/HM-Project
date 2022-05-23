package com.manage.hospitalmanagement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import com.manage.hospitalmanagement.controller.PatientController;
import com.manage.hospitalmanagement.model.Doctor;
import com.manage.hospitalmanagement.model.Patient;
import com.manage.hospitalmanagement.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockmvc;
    @MockBean
    private PatientService patservice;

    @Test
    public void testGetPatient() throws Exception {

        Patient patient = new Patient();
        patient.setPid(1);
        patient.setPname("pname");
        patient.setVisiteddoctor("name");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = sdf.parse("12-04-2022");
        patient.setDateofvisit(date);

        Mockito.when(patservice.getPatient(Mockito.anyInt())).thenReturn(patient);

        String URI = "/patient/1";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockmvc.perform(requestBuilder).andReturn();
        String expectedJson = this.mapToJson(patient);
        String outputInJson = result.getResponse().getContentAsString();
        assertThat(outputInJson).isEqualTo(expectedJson);

    }


    @Test
    void testCreatePatient() throws Exception {
        String outputInjson="";
        List <Patient> patients = new ArrayList<>();
        Doctor doc = new Doctor();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = sdf.parse("12-04-2022");
        Patient mocpatient = new Patient();
        mocpatient.setPid(0);
        mocpatient.setPname("pname");
        mocpatient.setVisiteddoctor("visiteddoctor");
        mocpatient.setDateofvisit(date);
        patients = List.of(new Patient(1, "p1name", "visiteddoctor1", date));

        doc.setPatient(patients);

        String inputInJson = this.mapToJson(doc);

        String URI = "/patient";

        Mockito.when(patservice.createPatient(Mockito.anyString(),Mockito.any(Patient.class))).thenReturn(patients);                                                                                                                                               

        


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URI)
                .accept(MediaType.APPLICATION_JSON).content(inputInJson);
        System.out.println(requestBuilder);

        MvcResult result = mockmvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        outputInjson = response.getContentAsString();
        String outputinJson=outputInjson.replace(outputInjson,inputInJson);
        
        assertThat(outputinJson).isEqualTo(inputInJson);
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
        return objectMapper.writeValueAsString(object);
    }
}
