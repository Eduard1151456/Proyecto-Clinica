
package modeloVO;
import java.util.ArrayList;
public class ClinicaVO {
    private String nombre;
    private String direccion;
    
    private ArrayList<PacienteVO> listaPacientes;
    private ArrayList<PersonalSaludVO> listaPersonal;

    public ClinicaVO() {
        this.listaPacientes = new ArrayList<>();
        this.listaPersonal = new ArrayList<>();
    }
    public ClinicaVO( String nombre,String direccion) {
        this.direccion = direccion;
        this.nombre = nombre;
        this.listaPacientes = new ArrayList<>();
        this.listaPersonal = new ArrayList<>();
    }
    public PacienteVO buscarPaciente(String documento) {
    for (PacienteVO paciente : listaPacientes) {
        if (paciente.getDocumento().equals(documento)) {
            return paciente;
        }
    }  
    return null;
}  
    public PersonalSaludVO buscarPersonal(String documento) {
    for (PersonalSaludVO personal : listaPersonal) {
        if (personal.getDocumento().equals(documento)) {
            return personal;
        }
    }  
    return null;
}
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<PacienteVO> getListaPacientes() {
        return listaPacientes;
    }

    public void setListaPacientes(ArrayList<PacienteVO> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    public ArrayList<PersonalSaludVO> getListaPersonal() {
        return listaPersonal;
    }

    public void setListaPersonal(ArrayList<PersonalSaludVO> listaPersonal){
        this.listaPersonal = listaPersonal;
    }
}