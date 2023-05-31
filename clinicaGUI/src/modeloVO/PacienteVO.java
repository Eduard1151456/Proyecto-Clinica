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
public class PacienteVO extends  PersonaVO {
    
    private String lugarProcendia;//para determinar si ha viajado recientemente a algún país con contagio
    private Date FechaDeteccion;//fecha de deteccion del virus
    private String tratado;//lugar donde es tratado en caso de poder ser o ser portador del virus
    private String personasPosibleContacto;//CONTACTOS O PERSONAS QUE CONVIVEN CON LA PERSONA EN AISLAMIENTO/INTERNACION

    public PacienteVO() {
    }

    public PacienteVO(String lugarProcendia, Date FechaDeteccion, String tratado, String personasPosibleContacto, String documento, String nombre, String direccion, String telefono, String genero, Date fechaNacimiento, String estado) {
        super(documento, nombre, direccion, telefono, genero, fechaNacimiento, estado);
        this.lugarProcendia = lugarProcendia;
        this.FechaDeteccion = FechaDeteccion;
        this.tratado = tratado;
        this.personasPosibleContacto = personasPosibleContacto;
    }

    public String getLugarProcendia() {
        return lugarProcendia;
    }

    public void setLugarProcendia(String lugarProcendia) {
        this.lugarProcendia = lugarProcendia;
    }

    public Date getFechaDeteccion() {
        return FechaDeteccion;
    }

    public void setFechaDeteccion(Date FechaDeteccion) {
        this.FechaDeteccion = FechaDeteccion;
    }

    public String getTratado() {
        return tratado;
    }

    public void setTratado(String tratado) {
        this.tratado = tratado;
    }

    public String getPersonasPosibleContacto() {
        return personasPosibleContacto;
    }

    public void setPersonasPosibleContacto(String personasPosibleContacto) {
        this.personasPosibleContacto = personasPosibleContacto;
    }


}
    
    

