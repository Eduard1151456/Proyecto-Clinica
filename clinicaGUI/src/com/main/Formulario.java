
package com.main;

import clinica.RegistroCovid_19;
import com.raven.component.Menu;
import com.raven.component.MostrarEstadistica;
import com.raven.component.RegistroPersonalSalud;
import com.raven.component.RegistroPaciente;
import com.raven.component.PruebaSemanal;
import com.raven.event.EventMenu;
import controlador.Controlador;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modeloDAO.ClinicaDAO;
import modeloDAO.Conexion;
import modeloDAO.PacienteDAO;
import modeloDAO.PersonalSaludDAO;
import modeloVO.ClinicaVO;
import modeloVO.PacienteVO;
import modeloVO.PersonalSaludVO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
public class Formulario extends javax.swing.JFrame {

    private RegistroPaciente registroPaciente;
    private RegistroPersonalSalud registroPersonal;
    private MostrarEstadistica estadistica;
    private PruebaSemanal prueba;
    private PacienteDAO pacienteDao;
    private PersonalSaludDAO personalDAO;
    private RegistroCovid_19 r;
    private ClinicaDAO clinicaDao;
    private ClinicaVO clinicaVO;

    public Formulario(RegistroCovid_19 registro) throws SQLException, ClassNotFoundException {

        this.setTitle("Registro Covid_19");
        this.setIconImage(new ImageIcon("com/raven/icon/icono.png").getImage());
        r = registro;

        initComponents();
     Connection conexion = Conexion.obtener(); 

    clinicaDao = new ClinicaDAO(conexion);
    pacienteDao = new PacienteDAO(conexion);
    personalDAO = new PersonalSaludDAO(conexion);

        clinicaVO = new ClinicaVO();
        this.setVisible(true);
        getContentPane().setBackground(new Color(236, 236, 236));
        MainProgram.getInstance().setSlideShow(slideshow);
        EventMenu event = new EventMenu() {
            @Override
            public void selected(int index) {
                slideshow.slideTo(index + 1);
            }
        };
        Menu menu = new Menu();
        menu.initMenu(event);
        registroPaciente = new RegistroPaciente("Registro Pacientes");
        registroPaciente.addActionListener(new MyActionListener());
        RegistroPaciente subForm2 = new RegistroPaciente("Salir");
        registroPersonal = new RegistroPersonalSalud("Registro Personal Salud");
        registroPersonal.addActionListener(new MyActionListener());
        estadistica = new MostrarEstadistica("Reporte Estadisticas COVID-19");
        estadistica.addActionListener(new MyActionListener());
        prueba = new PruebaSemanal("Registro Semanal Pruevas Personal Salud");
        prueba.addActionListener(new MyActionListener());
        slideshow.initSlideshow(menu, registroPaciente,subForm2, registroPersonal, estadistica, prueba);


    }

    private class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equals("Registrar")) {
                System.out.println("el boton registrar 2 sirve ");

                try {
                    clinicaDao.guardar(r);
                } catch (SQLException ex) {
                    Logger.getLogger(Formulario.class.getName()).log(Level.SEVERE, null, ex);
                } 

                String documento = registroPaciente.jTextFieldDocumento1.getText();
                String nombre = registroPaciente.jTextFieldNombre1.getText();
                String direccion = registroPaciente.jTextFieldDireccion1.getText();
                String telefono = registroPaciente.jTextFieldTelefono1.getText();
                String genero = (String) registroPaciente.jComboBoxGenero1.getSelectedItem();
                Date fechaNacimiento = registroPaciente.jDateChooserFechaNacimiento.getDate();
                String estado = (String) registroPaciente.jComboBoxEstasoPaciente1.getSelectedItem();
                String lugarProcedencia = registroPaciente.jTextFieldLugarProcedencia1.getText();
                Date fechaDeteccion = registroPaciente.jDateChooserFechaDeteccion.getDate();
                String tratado = (String) registroPaciente.jComboBoxProceso.getSelectedItem();
                String personasPosibleContacto = registroPaciente.jTextFieldDocumentoContacto1.getText() + "    " + registroPaciente.jTextFieldNombreContacto1.getText() + "\n"
                        + registroPaciente.jTextFieldDocumentoContacto2.getText() + "    " + registroPaciente.jTextFieldNombreContacto2.getText() + "\n"
                        + registroPaciente.jTextFieldDocumentoContacto3.getText() + "    " + registroPaciente.jTextFieldNombreContacto3.getText() + "\n"
                        + registroPaciente.jTextFieldDocumentoContacto4.getText() + "    " + registroPaciente.jTextFieldNombreContacto4.getText() + "\n"
                        + registroPaciente.jTextFieldDocumentoContacto5.getText() + "    " + registroPaciente.jTextFieldNombreContacto5.getText();
                System.out.println("el boton registar paciente 3 sirve");

                // Validar el campo "documento"
                if (!isNumeric(documento)) {
                    JOptionPane.showMessageDialog(registroPaciente, "El documento debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar el campo "telefono"
                if (!isNumeric(telefono)) {
                    JOptionPane.showMessageDialog(registroPaciente, "El teléfono debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar el campo "fechaNacimiento"
                if (fechaNacimiento == null) {
                    JOptionPane.showMessageDialog(registroPaciente, "La fecha de nacimiento no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //validar que no hayan campo de texto vacios
                if (documento.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(registroPaciente, "campo de documento vacio .", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //validar que no esten vacios los campos de texto obligatiotirios
                if (nombre.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(registroPaciente, "campo de nombre vacio .", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (direccion.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(registroPaciente, "campo de direccion vacio .", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (telefono.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(registroPaciente, "campo de telefono vacio .", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (lugarProcedencia.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(registroPaciente, "campo de lugar de Procedencia vacio .", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crear el objeto PacienteVO con los datos ingresados por el usuario
                PacienteVO paciente = new PacienteVO(lugarProcedencia, fechaDeteccion, tratado, personasPosibleContacto, documento, nombre, direccion, telefono, genero, fechaNacimiento, estado);
                
                
                registroPaciente.jLabel3.setText("Registro Exitoso");
                try {
                    //llamar el metodo guardar del la clase pacienteDAO para hacer la conexion con la base de datos y llebar a cabo el registro
                    pacienteDao.guardar(paciente);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

//este  controlador de evento se utiliza para validar los datos ingresados por el usuario en un formulario de registro de personal de la salud
//y guardar los datos en una base de datos si se cumplen ciertos criterios. Además, 
// proporciona retroalimentación al usuario en caso de que se detecten errores al ingresar los datos.
            if (e.getActionCommand().equals("Registrar Personal ")) {
                String documento = registroPersonal.jTextFieldDocumentoPer.getText();
                String nombre = registroPersonal.jTextFieldNombrePer.getText();
                String direccion = registroPersonal.jTextFieldDireccion.getText();
                String telefono = registroPersonal.jTextFieldTelefono.getText();
                String genero = (String) registroPersonal.jComboBoxGenero.getSelectedItem();
                Date fechaNacimiento = registroPersonal.jDateChooserFechaNacimiento.getDate();
                String estado = (String) registroPersonal.jComboBoxEstaso.getSelectedItem();
                String especialidad = (String) registroPersonal.jComboBoxEspecialidad.getSelectedItem();

                // Validar el campo "documento"
                if (!isNumeric(documento)) {
                    JOptionPane.showMessageDialog( registroPersonal, "El documento debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar el campo "telefono"
                if (!isNumeric(telefono)) {
                    JOptionPane.showMessageDialog( registroPersonal, "El teléfono debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar el campo "fechaNacimiento"
                if (fechaNacimiento == null) {
                    JOptionPane.showMessageDialog( registroPersonal, "La fecha de nacimiento no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //validar que no hayan campo de texto vacios
                if (documento.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog( registroPersonal, "campo de documento vacio .", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (nombre.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog( registroPersonal, "campo de nombre vacio .", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (direccion.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog( registroPersonal, "campo de direccion vacio .", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (telefono.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog( registroPersonal, "campo de telefono vacio .", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

               
                
               

                // Crear el objeto PersonalSaludVO con los datos ingresados por el usuario
                PersonalSaludVO personalSalud = new PersonalSaludVO(especialidad, documento, nombre, direccion, telefono, genero, fechaNacimiento, estado);
                try {
                    personalDAO.guardar(personalSalud);
                    registroPersonal.jLabelRegistro.setText("Registro Exitoso");
                } catch (SQLException ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("el boton guardar personal salud sirve");

            }
            if (e.getActionCommand().equals("Buscar")) {
                System.out.println("el boton buscar personal salud sirve");
                String documentoBuscar = "";
                documentoBuscar = prueba.jTextFieldDocumentoBusqueda.getText();
                PersonalSaludVO personalSalud = new PersonalSaludVO();

                try {
                    personalSalud = personalDAO.buscar(documentoBuscar);
                } catch (SQLException ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
                String mostrar = "Nombre: " + personalSalud.getNombre() + "  Especialidad: " + personalSalud.getEspecialidad() + "\n" + "Estado: " + personalSalud.getEstado();
                prueba.jTextAreaMostrarPersonal.setText(mostrar);

            }
            if (e.getActionCommand().equals("Actualizar")) {
                String documentoBuscar = prueba.jTextFieldDocumentoBusqueda.getText();
                String estado = (String) prueba.jComboBoxActualizarEstado.getSelectedItem();
                try {
                    personalDAO.actualizarEstado(documentoBuscar, estado);

                    prueba.jTextAreaMostrarPersonal.setText("");
                    prueba.jTextAreaMostrarPersonal.setText("El estado del personal de salud ha sido actualizado...");
                    System.out.println("el boton actualizar personal salud sirve");
                } catch (SQLException ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (e.getActionCommand().equals("Mostrar estadisticas")) {
                String datos = "";
                try {
                    datos = clinicaDao.contarEstados();
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
                DefaultPieDataset dataset = new DefaultPieDataset();
                dataset.setValue("SOSPECHOSO  " + obtenerCantidad(datos, "SOSPECHOSO"), obtenerCantidad(datos, "SOSPECHOSO"));
                dataset.setValue("POSITIVO  " + obtenerCantidad(datos, "POSITIVO"), obtenerCantidad(datos, "POSITIVO"));
                dataset.setValue("NEGATIVO  " + obtenerCantidad(datos, "NEGATIVO"), obtenerCantidad(datos, "NEGATIVO"));
                dataset.setValue("RECUPERADO  " + obtenerCantidad(datos, "RECUPERADO"), obtenerCantidad(datos, "RECUPERADO"));
                dataset.setValue("MUERTO" + obtenerCantidad(datos, "MUERTO"), obtenerCantidad(datos, "MUERTO"));
                JFreeChart chart = ChartFactory.createPieChart(
                        "Estados de COVID-19",
                        dataset,
                        true,
                        true,
                        false
                );
                PiePlot plot = (PiePlot) chart.getPlot();
                StandardPieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0}: {1}");
                plot.setStartAngle(290);
                plot.setDirection(Rotation.CLOCKWISE);
                plot.setForegroundAlpha(1.0f);
                plot.setSectionPaint("SOSPECHOSO", new Color(255, 128, 128));
                plot.setSectionPaint("POSITIVO", new Color(255, 255, 128));
                plot.setSectionPaint("NEGATIVO", new Color(128, 255, 128));
                plot.setSectionPaint("RECUPERADO", new Color(128, 255, 255));
                plot.setSectionPaint("MUERTO", new Color(128, 128, 255));
                ChartPanel chartPanel = new ChartPanel(chart);
                chartPanel.setPreferredSize(new Dimension(900, 500));
                estadistica.setLayout(new FlowLayout());
                estadistica.add(chartPanel);
                estadistica.revalidate();
                estadistica.repaint();
                System.out.println(datos);
                System.out.println(chartPanel);
            }
        }
    }

    private int obtenerCantidad(String datos, String estado) {
        String regex = estado + ": (\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(datos.replace("\n", ""));
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            return 0;
        }
    }

    public boolean isNumeric(String cadena) {
        try {
            Double.valueOf(cadena);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pictureBox1 = new com.raven.swing.PictureBox();
        slideshow = new com.raven.swing.slideshow.Slideshow();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        pictureBox1.setImage(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/background.jpg"))); // NOI18N

        slideshow.setToolTipText("");

        pictureBox1.setLayer(slideshow, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout pictureBox1Layout = new javax.swing.GroupLayout(pictureBox1);
        pictureBox1.setLayout(pictureBox1Layout);
        pictureBox1Layout.setHorizontalGroup(
            pictureBox1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slideshow, javax.swing.GroupLayout.DEFAULT_SIZE, 1162, Short.MAX_VALUE)
        );
        pictureBox1Layout.setVerticalGroup(
            pictureBox1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slideshow, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(pictureBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(pictureBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.PictureBox pictureBox1;
    private com.raven.swing.slideshow.Slideshow slideshow;
    // End of variables declaration//GEN-END:variables
}
