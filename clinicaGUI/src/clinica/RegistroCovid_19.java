/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;

import com.main.Formulario;
import controlador.Controlador;
import java.sql.SQLException;
import java.util.ArrayList;
import modeloDAO.ClinicaDAO;
import modeloDAO.Conexion;
import modeloVO.ClinicaVO;


/**
 *
 * @author Eduard
 */
public class RegistroCovid_19 {

    private ArrayList<ClinicaVO> clinica;

    
     
    public ArrayList<ClinicaVO> getClinica() {
        return clinica;
    }

    public void setClinica(ArrayList<ClinicaVO> clinica) {
        this.clinica = clinica;
    }

    
  
   public static ArrayList<ClinicaVO> crearClinicas() {
        ArrayList<ClinicaVO> clinicas = new ArrayList<>();
        
        clinicas.add(new ClinicaVO("Clínica de Urgencias la Merced", "Cl 4 # 3-87 Latino"));
        clinicas.add(new ClinicaVO("Clínica Santa Ana", "Avenida 11 e # 8-41 Colsag"));
        clinicas.add(new ClinicaVO("Clínica Del Norte", "Av 1 # 18-11 Barrio Blanco"));
        clinicas.add(new ClinicaVO("Clínica Los Andes", "Av 1 # 17-21 Bo Blanco"));
        clinicas.add(new ClinicaVO("Clínica Los Samanes", "Av 12 e # 4-30"));
        
        return clinicas;
    }
   public  ClinicaVO buscarClinicaPorNombre(String nombre) {
        for (ClinicaVO clinic : this.clinica) {
            if (clinic.getNombre().equals(nombre)) {
                return clinic;
            }
        }
        return null;
    }
   
  public static void main(String [] args) throws SQLException {
       RegistroCovid_19 registro = new RegistroCovid_19();
       Formulario formulario =new Formulario(registro);
       registro.setClinica(crearClinicas());
       Controlador controlador = new Controlador(registro);
       System.out.println("el el main sirve");
         

    }
   
}
    
    

