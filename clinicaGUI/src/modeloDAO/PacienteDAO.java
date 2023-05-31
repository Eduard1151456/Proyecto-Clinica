/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloDAO;

import modeloVO.ClinicaVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modeloVO.PacienteVO;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Eduard
 */
public class PacienteDAO {
//      Este es un método  para guardar la información del paciente en una base de datos utilizando la API JDBC (Java Database Connectivity).
//      El método toma tres parámetros: un objeto de conexión de base de datos, un objeto de tipo PacienteVO 
//      (que representa información del paciente) y un objeto de tipo ClinicaVO (que representa información sobre la clínica médica).

//      El método primero crea un objeto de declaración preparado utilizando el objeto de conexión y una cadena de consulta SQL
//      que inserta la información del paciente en una tabla llamada "paciente". 
//      La consulta contiene marcadores de posición para el número de documento del paciente, nombre, dirección, número de teléfono, 
//      sexo, fecha de nacimiento, estado de salud actual, lugar de origen, fecha de detección, estado del tratamiento y posibles contactos. 
//      Luego, establece los valores de los placeholders utilizando los métodos correspondientes del objeto PacienteVO.
//      Los valores de fecha se convierten en objetos java.sql.Date mediante el constructor de la clase Date.
//
//      Finalmente, la declaración preparada se ejecuta utilizando el método executeUpdate(), que devuelve el número de filas afectadas por la consulta. 
//      Si la consulta tiene éxito, el objeto de paciente se agrega a la lista de pacientes en el objeto ClinicaVO.
//
//      Si se lanza una excepción de SQL durante la ejecución de la consulta, se captura y 
//      se vuelve a lanzar como una nueva SQLException con el mismo mensaje de error.
    
    public void guardar(Connection conexion, PacienteVO paciente) throws SQLException {
        try {

            PreparedStatement consulta;
            consulta = conexion.prepareStatement("INSERT INTO paciente (documento, nombre, direccion, telefono, genero, fecha_nacimiento, estado, lugar_procedencia, fecha_deteccion, tratado, personas_posible_contacto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            consulta.setString(1, paciente.getDocumento());
            consulta.setString(2, paciente.getNombre());
            consulta.setString(3, paciente.getDireccion());
            consulta.setString(4, paciente.getTelefono());
            consulta.setString(5, paciente.getGenero());
            consulta.setDate(6, new java.sql.Date(paciente.getFechaNacimiento().getTime()));
            consulta.setString(7, paciente.getEstado());
            consulta.setString(8, paciente.getLugarProcendia());
             if (paciente.getFechaDeteccion() != null) {
            consulta.setDate(9, new java.sql.Date(paciente.getFechaDeteccion().getTime()));
        } else {
            consulta.setNull(9, java.sql.Types.DATE);
        }
            consulta.setString(10, paciente.getTratado());
            consulta.setString(11, paciente.getPersonasPosibleContacto());
            consulta.executeUpdate();
           
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

  
    
//     public PacienteVO buscar(Connection conexion, PacienteVO paciente, ClinicaVO clinica) throws SQLException {
//       PacienteVO resultado = null;
//   try {
//      PreparedStatement consulta;
//      consulta = conexion.prepareStatement("SELECT * FROM pacientes WHERE documento = ? AND nombre = ? AND direccion = ? AND telefono = ? AND genero = ? AND fecha_nacimiento = ? AND estado = ? AND lugar_procedencia = ? AND fecha_deteccion = ? AND tratado = ? AND personas_posible_contacto = ? AND clinica_id = ?");
//      consulta.setString(1, paciente.getDocumento());
//      consulta.setString(2, paciente.getNombre());
//      consulta.setString(3, paciente.getDireccion());
//      consulta.setString(4, paciente.getTelefono());
//      consulta.setString(5, paciente.getGenero());
//      consulta.setDate(6, new java.sql.Date(paciente.getFechaNacimiento().getTime()));
//      consulta.setString(7, paciente.getEstado());
//      consulta.setString(8, paciente.getLugarProcendia());
//      consulta.setDate(9, new java.sql.Date(paciente.getFechaDeteccion().getTime()));
//      consulta.setString(10, paciente.getTratado());
//      consulta.setString(11, paciente.getPersonasPosibleContacto());
//      consulta.setString(12, clinica.getNombre()); // Agregar la referencia a la clínica correspondiente
//      ResultSet rs = consulta.executeQuery();
//
//      if (rs.next()) {
//         resultado = new PacienteVO();
//         resultado.setDocumento(rs.getString("documento"));
//         resultado.setNombre(rs.getString("nombre"));
//         resultado.setDireccion(rs.getString("direccion"));
//         resultado.setTelefono(rs.getString("telefono"));
//         resultado.setGenero(rs.getString("genero"));
//         resultado.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
//         resultado.setEstado(rs.getString("estado"));
//         resultado.setLugarProcendia(rs.getString("lugar_procedencia"));
//         resultado.setFechaDeteccion(rs.getDate("fecha_deteccion"));
//         resultado.setTratado(rs.getString("tratado"));
//         resultado.setPersonasPosibleContacto(rs.getString("personas_posible_contacto"));
//      }
//
//      rs.close();
//      consulta.close();
//   } catch (SQLException ex) {
//      throw new SQLException(ex);
//   }
//   return resultado;
//}
    public boolean eliminarPaciente(Connection conexion, String documento) {
        boolean eliminacionExitosa = false;
        PreparedStatement consulta = null;

        try {
            // Preparar la consulta SQL de eliminación
            consulta = conexion.prepareStatement("DELETE FROM pacientes WHERE documento = ? AND clinica_id = ?");

            // Establecer el valor de los parámetros en la consulta
            consulta.setString(1, documento);
            

            // Ejecutar la consulta de eliminación
            int filasEliminadas = consulta.executeUpdate();
            if (filasEliminadas > 0) {
                eliminacionExitosa = true;

                
             
            }
        } catch (SQLException e) {

        }

        return eliminacionExitosa;
    }

    public void actualizar(Connection conexion, PacienteVO paciente) throws SQLException {
        try {
            PreparedStatement consulta;
            consulta = conexion.prepareStatement("UPDATE pacientes SET nombre = ?, direccion = ?, telefono = ?, genero = ?, fecha_nacimiento = ?, estado = ?, lugar_procedencia = ?, fecha_deteccion = ?, tratado = ?, personas_posible_contacto = ? WHERE documento = ? AND clinica_id = ?");
            consulta.setString(1, paciente.getNombre());
            consulta.setString(2, paciente.getDireccion());
            consulta.setString(3, paciente.getTelefono());
            consulta.setString(4, paciente.getGenero());
            consulta.setDate(5, new java.sql.Date(paciente.getFechaNacimiento().getTime()));
            consulta.setString(6, paciente.getEstado());
            consulta.setString(7, paciente.getLugarProcendia());
            consulta.setDate(8, new java.sql.Date(paciente.getFechaDeteccion().getTime()));
            consulta.setString(9, paciente.getTratado());
            consulta.setString(10, paciente.getPersonasPosibleContacto());
            consulta.setString(11, paciente.getDocumento());
           

            consulta.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    public void actualizarEstado(Connection conexion, String documento, String nuevoEstado, ClinicaVO clinica) throws SQLException {
        try {
            PreparedStatement consulta;
            consulta = conexion.prepareStatement("UPDATE pacientes SET estado = ? WHERE documento = ? AND clinica_id = ?");
            consulta.setString(1, nuevoEstado);
            consulta.setString(2, documento);
            consulta.setString(3, clinica.getNombre()); // Agregar la referencia a la clínica correspondiente

            consulta.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }
}
