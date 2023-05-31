/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloVO;

import java.util.Date;

/**
 *
 * @author Eduard
 */
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
