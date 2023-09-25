package med.voll.api.medico;

public record DatosListadoMedico(
    String nombre,
    Especialidad especialidad,
    String documento,
    String email
){
    public DatosListadoMedico(Medico medico) {
        this(medico.getNombre(), medico.getEspecialidad(), medico.getDocumento(), medico.getEmail());
    }
}
