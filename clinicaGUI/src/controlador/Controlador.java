/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import clinica.RegistroCovid_19;
import com.main.Formulario;

import com.raven.component.RegistroPaciente;
import com.raven.component.RegistroPersonalSalud;

/**
 *
 * @author Eduard
 */
public class Controlador {

    private Formulario formulario = null;
    private RegistroPaciente registroPaciente;
    private RegistroPersonalSalud registroPersonalSalud;
 
    private RegistroCovid_19 r;

    public com.raven.swing.slideshow.Slideshow slideshow;
         
    public Controlador(RegistroCovid_19 registro) {

        super();
       
        registroPaciente = new RegistroPaciente("");
        registroPersonalSalud = new RegistroPersonalSalud("");

    
        r = registro;

        System.out.println("el constructor de controlador sirve");
    }

}
