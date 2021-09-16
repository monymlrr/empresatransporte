/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empresa_transportes;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author monirami
 */
public class MySQL {

    private static Connection con;

    private static final String USUARIO = "root";
    private static final String PASS = "";
    private static final String URL = "jdbc:mysql://localhost/empresa_transporte";

    public void conectar() {

        con = null;

        try {
            con = (Connection) DriverManager.getConnection(URL, USUARIO, PASS);
            if (con != null) {
                System.out.println("conexion exitosa");
            }
        } catch (SQLException e) {
            System.out.println("error en la conexion: " + e);
        }

    }
    
    public void cerrarConexion(){
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("error al cerrar la conexion: "+e);
        }
    }

    
    public ArrayList<String> consultarEmpleados(){
        
        String query = "SELECT id_empleado, nombre_empleado FROM empleados";
        List<String> empleados = new ArrayList<String>();
        PreparedStatement ps;
        ResultSet rs;
        
        conectar();
        
        try {            
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                String id = rs.getString("id_empleado");
                String nombre = rs.getString("nombre_empleado");
                String valor = id + ". " + nombre;
                //System.out.println("valor es: "+valor);
                empleados.add(valor);
            }
            
        } catch (SQLException e) {
            System.out.println("error al obtener los datos: "+e);
        }
        
        cerrarConexion();
        
        return (ArrayList<String>) empleados;
        
    }
    
    public int consultarSalario(int id_empleado){
        
        int salario = 0;
        String query = "SELECT salario FROM empleados WHERE id_empleado = "+id_empleado+";";
        PreparedStatement ps;
        ResultSet rs;
        
        conectar();
        
        try {            
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                salario = rs.getInt("salario");
            }
            
        } catch (SQLException e) {
            System.out.println("error al obtener los datos: "+e);
        }
        
        cerrarConexion();
        
        return salario;
        
    }
    
    public void actualizarHorasTrabajadas(int horas, int id_empleado){
        
        int horas_extras = 0;
        int salario = consultarSalario(id_empleado);
        int salario_extra = Math.round(((salario / 30)*28)/180);
        
        if(horas > 48){
           horas_extras = horas - 48; 
        }
                
        String query = "UPDATE empleados SET horas_trabajadas = "+horas+", horas_extras = "+horas_extras+", salario_extra = "+(salario_extra*horas_extras)+" WHERE id_empleado = "+id_empleado+";";
        
        PreparedStatement ps;
        
        conectar();
        
        try {            
            ps = con.prepareStatement(query);
            ps.executeUpdate();
            System.out.println("horas actualizadas correctamente");
            JOptionPane.showMessageDialog(null, "horas actualizadas correctamente");
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar las horas: "+e);
            JOptionPane.showMessageDialog(null, "Error al actualizar las horas: "+e);
        }
        
        cerrarConexion();
    }
    
    public Empleado consultarDatosEmpleado(int id_empleado){
        
        String query = "SELECT * FROM empleados WHERE id_empleado = "+id_empleado+";";
        Empleado empleado = new Empleado();
        PreparedStatement ps;
        ResultSet rs;
        
        conectar();
        
        try {            
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                empleado.id = rs.getInt("id_empleado");
                empleado.nombre = rs.getString("nombre_empleado");
                empleado.salario = rs.getInt("salario");
                empleado.horas_trabajadas = rs.getInt("horas_trabajadas");
                empleado.horas_extras = rs.getInt("horas_extras");
                empleado.salario_extra = rs.getInt("salario_extra");
                System.out.println("datos obtenidos correctamente");
            }
            
        } catch (SQLException e) {
            System.out.println("error al obtener los datos: "+e);
        }
        
        cerrarConexion();
        
        return empleado;
        
    }
    
    public void guardarArticulo(String nombre, int cantidad){
                        
        String query = "INSERT INTO articulos (id_articulo, nombre_articulo, cantidad) VALUES (NULL, '"+nombre+"', "+cantidad+");";
        
        PreparedStatement ps;
        
        conectar();
        
        try {            
            ps = con.prepareStatement(query);
            ps.executeUpdate();
            System.out.println("articulo guardado correctamente");
            JOptionPane.showMessageDialog(null, "articulo guardado correctamente");
            
        } catch (SQLException e) {
            System.out.println("Error al guardar el articulo: "+e);
            JOptionPane.showMessageDialog(null, "Error al guardar el articulo: "+e);
        }
        
        cerrarConexion();
    }


}
