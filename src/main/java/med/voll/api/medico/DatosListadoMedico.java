package med.voll.api.medico;

public record DatosListadoMedico(
    Long id,
    String nombre,
    Especialidad especialidad,
    String documento,
    String email
){
    public DatosListadoMedico(Medico medico) {
        this(medico.getId(), medico.getNombre(), medico.getEspecialidad(), medico.getDocumento(), medico.getEmail());
    }
}
