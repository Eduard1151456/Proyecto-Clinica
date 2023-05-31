/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloDAO;

import modeloVO.ClinicaVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import modeloVO.PersonalSaludVO;

/**
 *
 * @author Eduard
 */
public class PersonalSaludDAO {
//    El método guarda información sobre personal de salud en una base de datos. Crea una sentencia preparada con la información y la ejecuta utilizando 
//    el método executeUpdate (), que devuelve el número de filas afectadas por la consulta. Si la consulta es exitosa,
//    el objeto de personal de salud se agrega a la lista de personal en el objeto ClinicaVO.

//    Si se produce una excepción SQL durante la ejecución de la consulta, se captura y se vuelve a lanzar como una nueva SQLException
//    con el mismo mensaje de error.
//    Si todo sale bien, agrega la información a una lista. Si ocurre un error, lo reporta como una excepción.
    public void guardar(Connection conexion, PersonalSaludVO personalSalud) throws SQLException {
        try {
            PreparedStatement consulta;
            consulta = conexion.prepareStatement("INSERT INTO personal_salud (documento, nombre, direccion, telefono, genero, fecha_nacimiento,estado, especialidad) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            consulta.setString(1, personalSalud.getDocumento());
            consulta.setString(2, personalSalud.getNombre());
            consulta.setString(3, personalSalud.getDireccion());
            consulta.setString(4, personalSalud.getTelefono());
            consulta.setString(5, personalSalud.getGenero());
            consulta.setDate(6, new java.sql.Date(personalSalud.getFechaNacimiento().getTime()));
            consulta.setString(7, personalSalud.getEstado());
            consulta.setString(8, personalSalud.getEspecialidad());
            
            consulta.executeUpdate();

            
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }
//     el método actualiza el estado de un miembro del personal de salud en la base de datos 
//     y registra la información en la lista de personal de la clínica correspondiente.
//    primero crea un objeto de sentencia preparada utilizando el objeto de conexión y 
//    una cadena de consulta SQL que actualiza el estado del miembro del personal de salud 
//    en la tabla "personal_salud". La consulta utiliza marcadores de posición para el nuevo estado,
//    el documento y el ID de la clínica del miembro del personal de salud. Luego, establece los valores de los marcadores de posición 
//    utilizando los parámetros del método.

//    Finalmente, la sentencia preparada se ejecuta utilizando el método executeUpdate (), que devuelve el número de filas afectadas por la consulta. 
//    Si la consulta es exitosa, el método cierra la consulta y retorna. Si se produce una excepción SQL durante la ejecución de la consulta,
//    se captura y se vuelve a lanzar como una nueva SQLException con el mismo mensaje de error.
    public void actualizarEstado(Connection conexion, String documento, String nuevoEstado) throws SQLException {
        try {
            PreparedStatement consulta;
            consulta = conexion.prepareStatement("UPDATE personal_salud SET estado = ? WHERE documento = ? ");
            consulta.setString(1, nuevoEstado);
            consulta.setString(2, documento);
            
            consulta.executeUpdate();
            consulta.close();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }
// el método buscar, busca información de un miembro del personal de salud en la base de datos y
// devuelve un objeto PersonalSaludVO que contiene la información correspondiente.
// primero crea un objeto de sentencia preparada utilizando el objeto de conexión y una cadena de consulta SQL que selecciona 
// la información del miembro del personal de salud en la tabla "personal_salud" que corresponde al documento proporcionado.
// Luego, establece el valor del marcador de posición utilizando el parámetro del método.
//
//Después, se ejecuta la sentencia preparada utilizando el método executeQuery (), que devuelve un objeto ResultSet 
// que contiene los resultados de la consulta.
//Si el resultado contiene una fila, la información se extrae del objeto ResultSet y se utiliza para crear un objeto PersonalSaludVO. 
//El objeto PersonalSaludVO se establece con los valores correspondientes a cada columna en la fila.

//Finalmente, el método cierra el objeto ResultSet y la sentencia preparada antes de devolver el objeto PersonalSaludVO creado.
// Si se produce una excepción SQL durante la ejecución de la consulta, se captura y se vuelve a lanzar como una nueva SQLException 
// con el mismo mensaje de error.
    public PersonalSaludVO buscar(Connection conexion, String documento) throws SQLException {
        PersonalSaludVO resultado = null;
        try {
            PreparedStatement consulta;
            consulta = conexion.prepareStatement("SELECT * FROM personal_salud WHERE documento = ?");
            consulta.setString(1, documento);
            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {
                resultado = new PersonalSaludVO();
                resultado.setDocumento(rs.getString("documento"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setDireccion(rs.getString("direccion"));
                resultado.setTelefono(rs.getString("telefono"));
                resultado.setGenero(rs.getString("genero"));
                resultado.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                resultado.setEstado(rs.getString("estado"));
                resultado.setEspecialidad(rs.getString("especialidad"));
                
            }

            rs.close();
            consulta.close();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return resultado;
    }

}
