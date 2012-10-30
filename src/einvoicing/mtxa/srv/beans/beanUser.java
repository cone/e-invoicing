/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package einvoicing.mtxa.srv.beans;

/**
 *
 * @author Carlos Gutierrez
 */
public class beanUser {
    private String id;
    private String pass;
    private String nom;
    private int suc;
    private int dep;
    private int idemp;
    
    public beanUser(){
        id="";
        pass="";
        nom="";
        suc=0;
        dep=0;
        idemp=0;
    }
    
    //SETTERS
    public void setId(String id){
        this.id = id;
    }
    
    public void setPassword(String pass){
        this.pass = pass;
    }
    
    public void setNombre(String nom){
        this.nom = nom;
    }
      
    public void setSucursal(int suc){
        this.suc = suc;
    }
            
    public void setDepartamento(int dep){
        this.dep = dep;
    }
    
    public void setIdEmpresa(int idemp){
        this.idemp = idemp;
    }
    
    //GETTERS
    public String getId(){
        return id;
    }
    
    public String getPassword(){
        return  pass;
    }
    
    public String getNombre(){
        return  nom;
    }
    
    public int getSucursal(){
        return suc;
    }
    
    public int getDepartamento(){
        return dep;
    }
    
    public int getIdEmpresa(){
        return idemp;
    }
}
