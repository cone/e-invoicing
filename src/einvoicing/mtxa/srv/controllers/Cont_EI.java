/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package einvoicing.mtxa.srv.controllers;

/**
 *
 * @author Carlos Gutierrez
 */
import einvoicing.mtxa.srv.beans.beanEInvoice;
import einvoicing.mtxa.srv.beans.beanUser;
import einvoicing.mtxa.srv.models.Model_suse;
import einvoicing.mtxa.srv.views.PBar;
import einvoicing.mtxa.srv.views.View_EI;
import einvoicing.mtxa.utils.PropertiesBuilder;
import einvoicing.mtxa.utils.PropertiesMaker;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import mx.com.fact.www.schema.ws.MySuiteHandler;

public class Cont_EI implements Observer{
    private Model_suse model;
    private MySuiteHandler msh;
    private View_EI view;
    private PBar bar;
    private beanUser currentUser;
    private HashMap properties;
    private File[] listOfFiles;
    private final ImageIcon erroricon = new ImageIcon(getClass().getResource("/images/stop.png"));
    
    public Cont_EI(beanUser currentUser){
        this.currentUser = currentUser;
        
        properties = getProperties();
        verifyFileExistence((String)properties.get("xmlorigin"));
        verifyFileExistence((String)properties.get("destination"));
        
        File root = new File((String)properties.get("xmlorigin"));
        listOfFiles = root.listFiles();
        
        model = new Model_suse();
        view = new View_EI();
        view.AddWAView(new WL());
        view.control().setVisible(true);
        view.addALRefresh(new RefreshEvt());
        view.addALAcept(new AceptEvt());
        view.userLabel().setText("Usuario: "+currentUser.getNombre()+" - Sucursal: "+currentUser.getSucursal());
        
        model.addObserver(this);
        model.connect();
        getData();
    }
    
    private void verifyFileExistence(String route){
        File file=new File(route);
        boolean exists = file.exists();
        if (!exists) {
            boolean success = (new File(route)).mkdir();
            if (success) {
                System.out.println("Directory: "+ route + " created");
            } 
        }
    }
    
    private HashMap getProperties(){
        PropertiesBuilder pbuilder = new PropertiesBuilder();
        PropertiesMaker pmaker = new PropertiesMaker(pbuilder);
        pmaker.ConstructWithDefaults();
        return pmaker.getProperties();
    }
    
    @Override
    public void update(Observable o, Object arg){
        getData();
    }
    
    public void getData(){
        view.clrscr();
        ArrayList<beanEInvoice> data = model.getData(currentUser.getSucursal(), currentUser.getDepartamento(), currentUser.getIdEmpresa());
        for(beanEInvoice item : data){
            findProperXml(item);
        }
        if(!data.isEmpty()){
            for(beanEInvoice item : data){
                view.addItem(item);
            }
                view.table().setRowSelectionInterval(0, 0);
        }
        else{
            JOptionPane.showMessageDialog(view.control(), "No se han encontrado facturas sin generar!");
        }
    }
    
    public void correHilo(){
        EventQueue.invokeLater(new Runnable()
         {
            @Override
            public void run()
            {
               int response = JOptionPane.showConfirmDialog(null, "Las facturas son las correctas?", "Confirmar",
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
               if(response == JOptionPane.YES_OPTION){
                   Hilo h = new Hilo();
                   h.start();
               }
            }
         });
    }
    
    public boolean findProperXml(beanEInvoice item){
        int l = listOfFiles.length;
        boolean flag = false;
        for (int i = 0; i < l; i++) {
           if(listOfFiles[i].exists()){
            if (listOfFiles[i].isFile()) {
              String aux = listOfFiles[i].getName();
              String noext = aux.substring(0, aux.lastIndexOf("."));
              if(noext.equals(item.getInvoiceNumber())){
                      item.setExists("Encontrado.");
                      flag = true;
              }
            }
           }
        }
        return flag;
    }
    
    public void generateResponses(){
        String errors = new String();
        msh = new MySuiteHandler();
        msh.setGenerationRoute((String)properties.get("destination"));
        msh.setEinvoiceRoute((String)properties.get("xmlorigin"));
        msh.setRequestor((String)properties.get("requestor"));
        msh.setTransaction((String)properties.get("transaction"));
        msh.setCountry("MX");
        msh.setEntity((String)properties.get("entity"));
        msh.setUser((String)properties.get("wsUser")); 
        msh.setUserName((String)properties.get("wsUserName"));
        msh.setData2("PDF HTML XML");
        ArrayList<beanEInvoice> data = view.getSelectedRows();
        if(data.isEmpty()){
            JOptionPane.showMessageDialog(view.control(), "Porfavor seleccione la(s) Factura(s)");
            return;
        }
        bar = new PBar(data.size(),view.control(),"Enviado datos...Porfavor espere.");
        bar.show();
        String[] toUpdate = new String[data.size()];
        int aux=0;
        for(beanEInvoice item : data){
            if(!item.getExists().equals("No Encontrado.")){
                System.out.println(msh.requestFiscalStamp(item.getInvoiceNumber().trim()));
                bar.increaseBarValue(1);
                    if(!msh.getResult()){
                        String errmes="";
                        if(!msh.getDescription().trim().equals(""))
                            errmes = msh.getDescription().trim();
                        else if(!msh.getHint().trim().equals(""))
                            errmes=msh.getHint().trim();
                            else
                                errmes=msh.getData();

                        errors+=item.getInvoiceNumber()+" => "+errmes+"\n";
                    }
                    else{
                        if(msh.generatePDFResponse(item.getInvoiceNumber().trim())){
                            toUpdate[aux]=item.getInvoiceNumber().trim();
                            aux++;
                        }
                        else
                            errors+=item.getInvoiceNumber()+" => Error al generar el PDF. Verifique la validez de su XML.\n";
                                
                    }
            }
            else
                errors+=item.getInvoiceNumber()+" => Documento XML no encontrado.\n";
        }
        for(String item : toUpdate){
            if(item!=null)
                model.updateData(item);
        }
        getData();
        System.out.println("Proceso terminado!!!");
        if(errors.equals(""))
                JOptionPane.showMessageDialog(view.control(), "Proceso terminado!!");
        else
                JOptionPane.showMessageDialog(view.control(), errors, "Errores detectados",JOptionPane.ERROR_MESSAGE,erroricon);
        bar.close();
    }
    
    public class test implements Runnable{
        @Override
        public void run(){
            Hilo h = new Hilo();
            h.start();
        }
    }
    
    public class Hilo extends Thread{
        @Override
        public void run(){
            generateResponses();
        }
    }
    
    public class RefreshEvt implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e)
            {
                getData();
                //bar.increaseBarValue(1);
            }
    }
    
    public class AceptEvt implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e)
            {
                 /*test t = new test();
                 t.run();*/
                correHilo();
                //generateResponses();
                //bar.close();
            }
    }
    
    public class WL extends WindowAdapter { 
        @Override
        public void windowClosing( WindowEvent evt ) { 
            model.disconnect();
            System.exit( 0 ); 
        } 
    } 
}
