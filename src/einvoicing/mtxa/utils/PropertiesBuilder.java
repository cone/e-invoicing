/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package einvoicing.mtxa.utils;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author carlos
 */
public class PropertiesBuilder {
    private HashMap properties;
    
    public PropertiesBuilder(){
        properties = new HashMap();
    }
    
    public void setDefaultproperties(){
        properties.put("destination", "./Generated");
        properties.put("xmlorigin", "./XML");
        properties.put("requestor", "12211111-1111-1111-1111-111111111111");
        properties.put("transaction", "CONVERT_NATIVE_XML");
        properties.put("entity", "AAA010101AAA");
        properties.put("wsUser", "12211111-1111-1111-1111-111111111111");
        properties.put("wsUserName", "MX.AAA010101AAA.USER_MOTORMESA_COLIMA");
    }     
    
    public void putUserProperties(){
        checkForUserProperties("destination","xmlorigin","requestor","transaction","entity","wsUser","wsUserName");
    }
    
    private void checkForUserProperties(String... keys){
        Properties props = new Properties();
        String aux;
        try{
            props.load(new FileInputStream("./db.properties"));
            for(String key:keys){
                aux=props.getProperty(key);
                if (!aux.trim().equals("")){
                    properties.put(key, aux);
                }
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error de inicializacion",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void checkWellFormRoutes(){
        String origin = (String)properties.get("xmlorigin");
        String destination = (String)properties.get("destination");
        
        int ol = origin.length();
        if(origin.charAt(ol-1)!='/') {
            origin=origin+"/";
        }
        ol = destination.length();
        if(destination.charAt(ol-1)!='/') {
            destination=destination+"/";
        }
        origin = origin.replace("\\", "/");
        destination = destination.replace('\\', '/');
        
        properties.put("xmlorigin", origin);
        properties.put("destination", destination);
    }
    
    public HashMap getProperties(){
        return properties;
    }
}
