/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package einvoicing.mtxa.srv.beans;

/**
 *
 * @author Carlos Gutierrez
 */
public class beanEInvoice {
    private String nf;
    private String name;
    private String folio;
    private float total;
    private String exists;
    
    public beanEInvoice(){
        nf="";
        name="";
        folio="";
        total=0;
        exists="No Encontrado.";
    }
    
    //SETTERS
    public void setInvoiceNumber(String nf){
        this.nf = nf;
    }
    
    public void setCustomerName(String name){
        this.name = name;
    }
    
    public void setFolio(String folio){
        this.folio = folio;
    }
    
    public void setTotal(float total){
        this.total = total;
    }
    
    public void setExists(String exists){
        this.exists = exists;
    }
    //GETTERS
    public String getInvoiceNumber(){
        return nf;
    }
    
    public String getCustomerName(){
        return name;
    }
    
    public String getFolio(){
        return folio;
    }
    
    public float getTotal(){
        return total;
    }
    
    public String getExists(){
        return exists;
    }
}
