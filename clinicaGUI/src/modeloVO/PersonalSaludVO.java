
package modeloVO;

import java.util.Date;
public class PersonalSaludVO extends  PersonaVO {
    
    private String especialidad;
    

    public PersonalSaludVO() {
        
    }

    public PersonalSaludVO(String especialidad, String documento, String nombre, String direccion, String telefono, String genero, Date fechaNacimiento, String estado) {
        super(documento, nombre, direccion, telefono, genero, fechaNacimiento, estado);
        this.especialidad = especialidad;
        
    }
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }  
}
