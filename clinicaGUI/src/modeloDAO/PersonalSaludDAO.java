
package modeloDAO;

import modeloVO.PersonalSaludVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class PersonalSaludDAO {
    private Connection conexion;

    public PersonalSaludDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void guardar(PersonalSaludVO personalSalud) throws SQLException {
    try {
        PreparedStatement consulta;
        String query = "SELECT COUNT(*) FROM personal_salud WHERE documento = ?";
        consulta = conexion.prepareStatement(query);
        consulta.setString(1, personalSalud.getDocumento());
        ResultSet resultado = consulta.executeQuery();
        resultado.next();
        int count = resultado.getInt(1);

        if (count > 0) {
          
            JOptionPane.showMessageDialog(null, "El documento ya está registrado en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

            query = "INSERT INTO personal_salud (documento, nombre, direccion, telefono, genero, fecha_nacimiento, estado, especialidad) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            consulta = conexion.prepareStatement(query);
            consulta.setString(1, personalSalud.getDocumento());
            consulta.setString(2, personalSalud.getNombre());
            consulta.setString(3, personalSalud.getDireccion());
            consulta.setString(4, personalSalud.getTelefono());
            consulta.setString(5, personalSalud.getGenero());
            consulta.setDate(6, new java.sql.Date(personalSalud.getFechaNacimiento().getTime()));
            consulta.setString(7, personalSalud.getEstado());
            consulta.setString(8, personalSalud.getEspecialidad());

            consulta.executeUpdate();
        }

    } catch (SQLException ex) {
        throw new SQLException(ex);
    }
}

    public void actualizarEstado(String documento, String nuevoEstado) throws SQLException {
        try {
            PreparedStatement consulta;
            consulta = conexion.prepareStatement("UPDATE personal_salud SET estado = ? WHERE documento = ?");
            consulta.setString(1, nuevoEstado);
            consulta.setString(2, documento);

            consulta.executeUpdate();
            consulta.close();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    public PersonalSaludVO buscar(String documento) throws SQLException {
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