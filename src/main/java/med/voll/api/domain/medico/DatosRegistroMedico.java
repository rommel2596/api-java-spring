package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRegistroMedico(
        @NotBlank(message = "Nombre es obligatorio")
        String nombre,

        @NotBlank(message = "Email es obligatorio")
        @Email
        String email,

        @NotBlank(message = "Telefono es obligatorio")
        String telefono,

        @NotBlank(message = "Documento es obligatorio")
        @Pattern(regexp = "\\d{4,6}")
        String documento,

        @NotNull
        Especialidad especialidad,

        @NotNull
        @Valid
        DatosDireccion direccion
        ) {

}