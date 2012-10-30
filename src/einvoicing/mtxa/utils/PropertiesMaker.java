/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package einvoicing.mtxa.utils;
import java.util.HashMap;

/**
 *
 * @author carlos
 */
public class PropertiesMaker {
    PropertiesBuilder pbuilder;
    
    public PropertiesMaker(PropertiesBuilder pbuilder){
        this.pbuilder = pbuilder;
    }
    
    public void ConstructWithDefaults(){
        pbuilder.setDefaultproperties();
        pbuilder.putUserProperties();
        pbuilder.checkWellFormRoutes();
    }
    
    public void ConstructWithOutDefaults(){
        pbuilder.putUserProperties();
        pbuilder.checkWellFormRoutes();
    }
    
    public HashMap getProperties(){
        return pbuilder.getProperties();
    }
}
