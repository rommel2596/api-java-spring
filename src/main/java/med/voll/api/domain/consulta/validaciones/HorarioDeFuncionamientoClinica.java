package med.voll.api.domain.consulta.validaciones;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;

@Component
public class HorarioDeFuncionamientoClinica implements ValidadorDeConsultas {
    public void validar(DatosAgendarConsulta datos) {
        var domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());
        var antesDeApertura = datos.fecha().getHour()<7;
        var despuesDeCierre = datos.fecha().getHour()>19;
        
        if (domingo || antesDeApertura || despuesDeCierre) {
            throw new ValidationException("El Horario de atención de la clínica es de Lunes a Sábado de 07:00 a 19:00 horas");
        }
    }
}
