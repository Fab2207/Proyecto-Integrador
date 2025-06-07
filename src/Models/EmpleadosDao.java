package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EmpleadosDao {

    //Instancia Conexion
    ConeccionMySQL cn = new ConeccionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    //Variables para el envio de datos entre las interfaces
    public static int id_user = 0;
    public static String nombre_user = "";
    public static String usuario_user = "";
    public static String direccion_user = "";
    public static String celular_user = "";
    public static String correo_user = "";
    public static String rol_user = "";

    //Metodo Login
    public Empleados loginQuery(String usuario, String contraseña) {
        String query = "SELECT*FROM empleados WHERE usuario = ? AND password = ?";
        Empleados employee = new Empleados();

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);

            //Enviar parametros
            pst.setString(1, usuario);
            pst.setString(2, contraseña);

            rs = pst.executeQuery();

            if (rs.next()) {
                employee.setId(rs.getInt("id"));
                id_user = employee.getId();

                employee.setNombre(rs.getString("nombre"));
                nombre_user = employee.getNombre();

                employee.setUsuario(rs.getString("usuario"));
                usuario_user = employee.getUsuario();

                employee.setNombre(rs.getString("direccion"));
                direccion_user = employee.getDireccion();

                employee.setNombre(rs.getString("celular"));
                celular_user = employee.getCelular();

                employee.setNombre(rs.getString("correo"));
                correo_user = employee.getCorreo_electronico();

                employee.setNombre(rs.getString("rol"));
                rol_user = employee.getRol();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el empleado" + e);
        }
        return employee;
    }

    //Registrar empleado
    public boolean registrarEmpleadoQuery(Empleados employee) {
        String query = "INSERT into empleados(id, nombre, usuario, direccion,"
                + "celular, correo_electronico, contraseña, rol, crear_usuario,"
                + "actualizar_usuario) VALUES(?,?,?,?,?,?,?,?,?,?)";

        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);

            pst.setInt(1, employee.getId());
            pst.setString(2, employee.getNombre());
            pst.setString(3, employee.getUsuario());
            pst.setString(4, employee.getDireccion());
            pst.setString(5, employee.getCelular());
            pst.setString(6, employee.getCorreo_electronico());
            pst.setString(7, employee.getContraseña());
            pst.setString(8, employee.getRol());

            pst.setTimestamp(9, dateTime);
            pst.setTimestamp(10, dateTime);

            pst.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al registrar empleado: " + e);
            return false;
        }
    }

    //Listar empleado
    public List listaEmpeladosQuery(String value) {
        List<Empleados> list_employees = new ArrayList();
        String query = "SELECT *FROM empleados ORDER BY rol ASC";
        String query_buscar_empleado = "SELECT *FROM empleados WHERE"
                + "id LIKE '%" + value + "%'";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_buscar_empleado);
                rs = pst.executeQuery();
            }
            while (rs.next()) {
                Empleados employee = new Empleados();

                employee.setId(rs.getInt("id"));
                employee.setNombre(rs.getString("nombre"));
                employee.setUsuario(rs.getString("usuario"));
                employee.setDireccion(rs.getString("direccion"));
                employee.setCelular(rs.getString("celular"));
                employee.setCorreo_electronico(rs.getString("email"));

                list_employees.add(employee);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_employees;
    }
    
    //Modificar empleado
//    public boolean modificarEmpleadoQuery(Empleados employee){
//        String query = "UPDATE employee SET nombre =?, usuario= ?,"
//                +
//    }

}
