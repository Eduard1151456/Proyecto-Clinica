
package modeloDAO;
import clinica.RegistroCovid_19;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.sql.ResultSet;
import modeloVO.ClinicaVO;
public class ClinicaDAO {
    public void guardar(Connection conexion, RegistroCovid_19 registro) throws SQLException {
    try {       
        PreparedStatement consultaConteo = conexion.prepareStatement("SELECT COUNT(*) AS total FROM clinica");
        ResultSet resultado = consultaConteo.executeQuery();
        resultado.next();
        int totalClinicas = resultado.getInt("total");
        if (totalClinicas < 5) {  
            for (ClinicaVO c : registro.getClinica()) {
                PreparedStatement consultaInsercion = conexion.prepareStatement("INSERT IGNORE INTO clinica (nombre, direccion) VALUES (?, ?)");
                consultaInsercion.setString(1, c.getNombre());
                consultaInsercion.setString(2, c.getDireccion());
                consultaInsercion.executeUpdate();
            }
        } else {
            System.out.println("No se pueden insertar más clínicas");
        }
    } catch (SQLException ex) {
        throw new SQLException(ex);
    }
}
    public String contarEstados() throws SQLException, ClassNotFoundException {
        Connection conexion = null;
        PreparedStatement consulta = null;
        ResultSet resultados = null;
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> estados = new HashMap<>();

        try {
            conexion = obtenerConexion();
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
