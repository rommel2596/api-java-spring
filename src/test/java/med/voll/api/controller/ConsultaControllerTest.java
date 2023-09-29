package med.voll.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import med.voll.api.domain.medico.Especialidad;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@AutoConfigureJsonTesters
public class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DatosAgendarConsulta> agendarConsultaJacksonTester;

    @Autowired
    private JacksonTester<DatosDetalleConsulta> detallesConsultaJacksonTester;

    @MockBean
    private AgendaDeConsultaService agendaDeConsultaService;

    @Test
    @DisplayName("Deberia retornar estado http 400 cuando los datos ingresados sean invalidos")
    void agendarEscenario1() throws Exception {
        var response = mvc.perform(MockMvcRequestBuilders
                .post("/consultas")).andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Deberia retornar estado http 200 cuando los datos son validos")
    void agendarEscenario2() throws Exception {

        var fecha = LocalDateTime.now().plusHours(1);

        var datos = new DatosDetalleConsulta(null, 1L, 1L, fecha);

        when(agendaDeConsultaService.agendar( any())).thenReturn(datos);

        var consulta = new DatosAgendarConsulta(1L, 1L, 1L, fecha, Especialidad.CARDIOLOGIA);
        var consulta2 = datos;


        var response = mvc.perform(MockMvcRequestBuilders
                .post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(agendarConsultaJacksonTester.write(consulta).getJson()))
                .andReturn().getResponse();
                

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        var jsonEsperado = detallesConsultaJacksonTester.write(consulta2).getJson();

        assertEquals(jsonEsperado, response.getContentAsString());
    }
}
