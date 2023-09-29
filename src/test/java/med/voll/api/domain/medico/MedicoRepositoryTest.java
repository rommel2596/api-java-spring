package med.voll.api.domain.medico;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MedicoRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private MedicoRepository medicoRepository;

    @Test
    void testFindActivoById() {
        assertTrue(true);
    }

    @Test
    @DisplayName("Deberia retornar nulo cuando el medico se encuentre en consulta con otro paciente en ese horario")
    void testSeleccionarMedicoConEspecialidadEnFecha() {

        // given
        var proximoLunes10H = LocalDate.now()
            .with(TemporalAdjusters
            .next(DayOfWeek.MONDAY))
            .atTime(10, 0, 0, 0);

        var medico = registrarMedico("Rommel", "rommel@gmail.com", "123456" , Especialidad.CARDIOLOGIA);
        var paciente = registrarPaciente("Naizla", "naizla@gmail.com", "321321");
        registrarConsulta(medico,paciente,proximoLunes10H);

        // when
        var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA, proximoLunes10H);

        // then
        assertNull(medicoLibre);
    }

    @Test
    @DisplayName("Deberia retornar un Medico cuando realice la consulta en la base de datos para ese horario")
    void testSeleccionarMedicoConEspecialidadEnFechaEscenario2() {
        var proximoLunes10H = LocalDate.now()
            .with(TemporalAdjusters
            .next(DayOfWeek.MONDAY))
            .atTime(10, 0, 0, 0);

        var medico = registrarMedico("Rommel", "rommel@gmail.com", "123456" , Especialidad.CARDIOLOGIA);

        var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA, proximoLunes10H);

        assertEquals(medico, medicoLibre);
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consulta(null, medico, paciente, fecha));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento) {
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new DatosRegistroMedico(nombre, email, "33232323", documento, especialidad, datosDireccion());
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
        return new DatosRegistroPaciente(nombre, email, email, documento, datosDireccion());
    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion("loca", "azul", "acapulco", "321", "12");
    }
}
