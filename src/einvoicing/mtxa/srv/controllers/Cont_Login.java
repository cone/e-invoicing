/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package einvoicing.mtxa.srv.controllers;
import einvoicing.mtxa.srv.beans.beanUser;
import einvoicing.mtxa.srv.models.Model_suse;
import einvoicing.mtxa.srv.views.Login;
import einvoicing.mtxa.srv.controllers.Cont_EI;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 *
 * @author Carlos Gutierrez
 */
public class Cont_Login {
    private Model_suse model;
    private Login view;
    private int tries;
    
    public Cont_Login(){
        model = new Model_suse();
        view = new Login();
        model.connect();
        tries = 0;
        view.AddWAView(new WL());
        view.addALAcept(new actionAcept());
        //view.control().addKeyListener(new keyEvents());
        view.userField().addKeyListener(new keyEvents());
        view.passwordField().addKeyListener(new keyEvents());
        view.aceptButton().addKeyListener(new keyEvents());
        view.control().setVisible(true);
    }
    
    public void validateUser(){
        if(tries<3){
            beanUser user = view.getData();
            user = model.getValidatedUser(user.getId().trim(), user.getPassword().trim());
            tries++;
            if(user != null){
                JOptionPane.showMessageDialog(view.control(),"Usuario validado!");
                view.control().setVisible(false);
                view.control().dispose();
                model.disconnect();
                Cont_EI con = new Cont_EI(user);
            }
            else{
                JOptionPane.showMessageDialog(view.control(),"Datos incorrectos! \n intento numero "+tries);
                view.userField().requestFocus();
            }
        }
        else{
            System.exit(0);
        }
    }
    
    class actionAcept implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            validateUser();
        }
    }
    
    class keyEvents extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent k){
            if(k.getKeyCode()==KeyEvent.VK_ENTER)
                validateUser();
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
