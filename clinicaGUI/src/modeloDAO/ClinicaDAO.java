/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloDAO;

import clinica.RegistroCovid_19;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.sql.ResultSet;
import modeloVO.ClinicaVO;

/**
 *
 * @author Eduard
 */
public class ClinicaDAO {
//Este es un método para guardar información de clínicas en una base de datos utilizando JDBC (Java Database Connectivity) API. 
 //El método toma dos parámetros: un objeto de conexión a la base de datos y un objeto RegistroCovid_19 que contiene información de las clínicas médicas.
 //El método utiliza una serie de bloques try-catch para insertar la información de cada clínica médica en la tabla "clinica".
 //Dentro de cada bloque try-catch se crea un objeto de sentencia preparada utilizando el objeto de conexión y una cadena de consulta SQL 
 //que inserta la información de la clínica médica en la tabla "clinica". La consulta utiliza marcadores de posición para el nombre y
//la dirección de la clínica médica. Luego, se establecen los valores de los marcadores de posición 
//  utilizando los métodos correspondientes del objeto RegistroCovid_19.
    public void guardar(Connection conexion, RegistroCovid_19 registro) throws SQLException {
    try {
        // Consulta para contar el número de clínicas registradas
        PreparedStatement consultaConteo = conexion.prepareStatement("SELECT COUNT(*) AS total FROM clinica");
        ResultSet resultado = consultaConteo.executeQuery();
        resultado.next();
        int totalClinicas = resultado.getInt("total");

        // Verificar si hay espacio para insertar nuevas clínicas
        if (totalClinicas < 5) {
            // Insertar nuevas clínicas utilizando un bucle
            for (ClinicaVO c : registro.getClinica()) {
                PreparedStatement consultaInsercion = conexion.prepareStatement("INSERT IGNORE INTO clinica (nombre, direccion) VALUES (?, ?)");
                consultaInsercion.setString(1, c.getNombre());
                consultaInsercion.setString(2, c.getDireccion());
                consultaInsercion.executeUpdate();
            }
        } else {
            // No hay espacio para más clínicas
            System.out.println("No se pueden insertar más clínicas");
        }
    } catch (SQLException ex) {
        throw new SQLException(ex);
    }
}
    // el método contarEstados cuenta la cantidad de cada estado de salud de pacientes y miembros del personal de salud en la base de datos 
    //y devuelve un string que contiene la información correspondiente.
    //primero crea un objeto de conexión a la base de datos utilizando la clase Conexion y establece la conexión. Luego, 
    //crea un objeto de sentencia preparada utilizando el objeto de conexión y una cadena de consulta SQL que cuenta la cantidad de cada
    // estado de salud en la tabla "personal_salud" y en la tabla "paciente". La consulta agrupa los resultados por estado.
    //Después, se ejecuta la consulta utilizando el método executeQuery (), que devuelve un objeto ResultSet que contiene los resultados de la consulta.
    //El método utiliza un HashMap para almacenar los resultados de la consulta y cuenta la cantidad de cada estado de salud.
    //Finalmente, el método construye un string con la información de los estados y devuelve este string. Si se produce una excepción SQL o
    //ClassNotFoundException durante la ejecución del método, se captura y se vuelve a lanzar como una nueva SQLException con el mismo mensaje de error.



    public String contarEstados() throws SQLException, ClassNotFoundException {
        Connection conexion = null;
        PreparedStatement consulta = null;
        ResultSet resultados = null;
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> estados = new HashMap<>();

        try {
            conexion = obtenerConexion();

            // Consultar estados en la tabla "personal_salud"
            consulta = conexion.prepareStatement("SELECT estado, COUNT(*) FROM personal_salud GROUP BY estado");
            resultados = consulta.executeQuery();

            while (resultados.next()) {
                String estado = resultados.getString("estado");
                int cantidad = resultados.getInt(2);
                if (estados.containsKey(estado)) {
                    cantidad += estados.get(estado);
                }
                estados.put(estado, cantidad);
            }

            // Consultar estados en la tabla "paciente"
            consulta = conexion.prepareStatement("SELECT estado, COUNT(*) FROM paciente GROUP BY estado");
            resultados = consulta.executeQuery();

            while (resultados.next()) {
                String estado = resultados.getString("estado");
                int cantidad = resultados.getInt(2);
                if (estados.containsKey(estado)) {
                    cantidad += estados.get(estado);
                }
                estados.put(estado, cantidad);
            }

            // Construir el string con la información de los estados
//            sb.append("Estados:\n");
            sb.append("SOSPECHOSO: ").append(estados.getOrDefault("SOSPECHOSO", 0)).append("\n");
            sb.append("POSITIVO: ").append(estados.getOrDefault("POSITIVO", 0)).append("\n");
            sb.append("NEGATIVO: ").append(estados.getOrDefault("NEGATIVO", 0)).append("\n");
            sb.append("RECUPERADO: ").append(estados.getOrDefault("RECUPERADO", 0)).append("\n");
            sb.append("MUERTO: ").append(estados.getOrDefault("MUERTO", 0)).append("\n");

            return sb.toString();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            if (resultados != null) {
                resultados.close();
            }
            if (consulta != null) {
                consulta.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }
public static Connection obtenerConexion() throws SQLException, ClassNotFoundException {
    Connection conexion = Conexion.obtener();
    return conexion;
}
}
