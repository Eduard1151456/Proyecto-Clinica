
package modeloDAO;

import modeloVO.ClinicaVO;
import modeloVO.PacienteVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class PacienteDAO {
    private Connection conexion;

    public PacienteDAO(Connection conexion) {
        this.conexion = conexion;
    }

   public void guardar(PacienteVO paciente) throws SQLException {
    try {
        PreparedStatement consulta;
        String query = "SELECT COUNT(*) FROM paciente WHERE documento = ?";
        consulta = conexion.prepareStatement(query);
        consulta.setString(1, paciente.getDocumento());
        ResultSet resultado = consulta.executeQuery();
        resultado.next();
        int count = resultado.getInt(1);

        if (count > 0) {
            JOptionPane.showMessageDialog(null, "El documento ya está registrado en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            query = "INSERT INTO paciente (documento, nombre, direccion, telefono, genero, fecha_nacimiento, estado, lugar_procedencia, fecha_deteccion, tratado, personas_posible_contacto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            consulta = conexion.prepareStatement(query);
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
        }

    } catch (SQLException ex) {
        throw ex;
    }
}

    public boolean eliminarPaciente(String documento) {
        boolean eliminacionExitosa = false;
        PreparedStatement consulta = null;
        try {
            consulta = conexion.prepareStatement("DELETE FROM pacientes WHERE documento = ? AND clinica_id = ?");
            consulta.setString(1, documento);
            int filasEliminadas = consulta.executeUpdate();
            if (filasEliminadas > 0) {
                eliminacionExitosa = true;
            }
        } catch (SQLException e) {
        }
        return eliminacionExitosa;
    }

    public void actualizar(PacienteVO paciente) throws SQLException {
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

    public void actualizarEstado(String documento, String nuevoEstado, ClinicaVO clinica) throws SQLException {
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