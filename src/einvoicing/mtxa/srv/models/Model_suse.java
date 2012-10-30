/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package einvoicing.mtxa.srv.models;

/**
 *
 * @author Carlos Gutierrez
 */

import com.basis.jdbc.BasisDriver; 
import java.sql.DriverManager; 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Properties;
import java.io.FileInputStream;
import javax.swing.JOptionPane;
import einvoicing.mtxa.srv.beans.beanEInvoice;
import einvoicing.mtxa.srv.beans.beanUser;

public class Model_suse extends Observable{
    private String driver = "com.basis.jdbc.BasisDriver";
    private String url = "jdbc:basis:";
    private String server = "localhost";
    private String db = "Einvoicing";
    private String allurl = "";
    private String user = "admin";
    private String password = "admin123";
    private Connection connection;
    
    public Model_suse(String user, String password){
        this.user=user;
        this.password=password;
        init();
    }
    
    public Model_suse(){
        init();
    }
    
    public void setDriver(String driver){
        this.driver = driver;
    }
    
    public void setUrl(String url){
        this.url = url;
    }
    
    public void setServer(String server){
        this.server = server;
    }
    
    public void setDB(String db){
        this.db = db;
    }
    
    public void setUser(String user){
        this.user = user;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    private void init(){
        Properties props = new Properties();
        try{
        props.load(new FileInputStream("./db.properties"));
        db=props.getProperty("database");
        password=props.getProperty("password");
        server=props.getProperty("server");
        url=props.getProperty("url");
        user=props.getProperty("user");
        System.out.println("Properties loaded");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error de inicializacion",JOptionPane.ERROR_MESSAGE);
        }
        try{
            Class.forName (driver);
            allurl = url+server+"?database="+db; 
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error de inicializacion",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public ArrayList<beanEInvoice> getData(){
        ArrayList<beanEInvoice> data = new ArrayList<beanEInvoice>();
        try{
            Statement st = connection.createStatement();
            ResultSet rs= st.executeQuery("select * from factura where st=0");
            while(rs.next()){
                beanEInvoice item = new beanEInvoice();
                item.setInvoiceNumber(rs.getString("nf").trim());
                item.setFolio(rs.getString("folio").trim());
                item.setCustomerName(rs.getString("nom").trim());
                item.setTotal(rs.getFloat("total"));
                data.add(item);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
        return data;
    }
    
        public ArrayList<beanEInvoice> getData(int dep, int suc,  int ide){
        ArrayList<beanEInvoice> data = new ArrayList<beanEInvoice>();
        try{
            Statement st = connection.createStatement();
            ResultSet rs= st.executeQuery("select * from factura where st=0 and suc="+suc+" and dep="+dep+" and ide="+ide+" ");
            while(rs.next()){
                beanEInvoice item = new beanEInvoice();
                String aux = rs.getString("folio").trim();
                aux = aux.substring(1, aux.length());
                aux = new StringBuffer(aux).insert(1, "-").toString();
                item.setInvoiceNumber(rs.getString("nf").trim());
                item.setFolio(aux);
                item.setCustomerName(rs.getString("nom").trim());
                item.setTotal(rs.getFloat("total"));
                data.add(item);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error del Modelo",JOptionPane.ERROR_MESSAGE);
        }
        return data;
    }
    
    public void updateData(){
        try{
            Statement st = connection.createStatement();
            st.executeUpdate("update factura set st=1 where st=0");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error del Modelo ud(all)",JOptionPane.ERROR_MESSAGE);
        }
        /*setChanged();
        notifyObservers();*/
    }
    
    public void updateData(String nf){
        try{
            Statement st = connection.createStatement();
            st.executeUpdate("update factura set st=1 where st=0 and nf='"+nf+"'");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error del Modelo ud(single)",JOptionPane.ERROR_MESSAGE);
        }
        /*setChanged();
        notifyObservers();*/
    }
    
    public void connect(){
        try{
        connection = DriverManager.getConnection(allurl,user,password);
        System.out.println("Connected!");
        }
        catch(Exception e){
            String msg;
            if("".equals(e.getMessage())){
                
                if("".equals(e.toString())){
                    msg = e.toString();
                }
                else{
                    msg = "Error";
                }
            }
            else{
                msg = e.getMessage();
            }
            JOptionPane.showMessageDialog(null,msg,"Error al conectar",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void disconnect(){
        try{
        connection.close();
        System.out.println("Disconnected!");
        }
        catch(Exception e){
            String msg;
            if("".equals(e.getMessage())){
                
                if("".equals(e.toString())){
                    msg = e.toString();
                }
                else{
                    msg = "Error";
                }
            }
            else{
                msg = e.getMessage();
            }
            JOptionPane.showMessageDialog(null,msg,"Error al desconectar",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ====================== LOGIN =========================
    
    public beanUser getValidatedUser(String id, String pass){
        beanUser user = null;
        
        try{
            String query = "select * from usuarios where id=? and pass=?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, id);
            pst.setString(2, pass);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                user = new beanUser();
                user.setId(rs.getString("id").trim());
                user.setPassword(rs.getString("pass").trim());
                user.setIdEmpresa(rs.getInt("id_emp"));
                user.setDepartamento(rs.getInt("dep"));
                user.setSucursal(rs.getInt("suc"));
                user.setNombre(rs.getString("nombre").trim());
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error del Modelo",JOptionPane.ERROR_MESSAGE);
        }
        return user;
    }
}
