/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package einvoicing.mtxa.srv.views;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import einvoicing.mtxa.srv.beans.beanUser;
import java.awt.event.WindowAdapter;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Carlos Gutierrez
 */
public class Login {
    private JFrame view;
    private JTextField user;
    private JPasswordField pass;
    private JButton acept;
    
    public Login(){
        CreateGUI();
    }
    
    private void CreateGUI(){
        view = new JFrame();
        user = new JTextField();
        pass = new JPasswordField(9);
        acept = new JButton("Aceptar");
        
        user.setPreferredSize(new Dimension(100,25));
        pass.setPreferredSize(new Dimension(100,25));
        
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BorderLayout());
        
        JPanel north = new ImagePanel();
            north.setPreferredSize(new Dimension(800,50));
        JPanel center = new JPanel();
            center.setLayout(new BorderLayout());
            JPanel centerN = new JPanel();
            JLabel ul = new JLabel("Usuario:        ");
            centerN.add(ul); centerN.add(user);
            JPanel centerM = new JPanel();
            JLabel pl = new JLabel("Contraseña: ");
            centerM.add(pl); centerM.add(pass);
            center.add(centerN,BorderLayout.NORTH);
            center.add(centerM,BorderLayout.CENTER);
        JPanel south = new JPanel();
        south.add(acept);
        
        wrapper.add(north, BorderLayout.NORTH);
        wrapper.add(center, BorderLayout.CENTER);
        wrapper.add(south, BorderLayout.SOUTH);
        
        view.add(wrapper);
        view.setPreferredSize(new Dimension(400,200));
        view.setIconImage(new ImageIcon(getClass().getResource("/images/icon.gif")).getImage());
        view.pack();
        view.setLocationRelativeTo(null);
        view.setTitle("Sistema de Facturacion Electronica");
        view.setResizable(false);
    }
    
    public beanUser getData(){
        beanUser buser = new beanUser();
        buser.setId(user.getText());
        buser.setPassword(pass.getText());
        return buser;
    }
    
    public JFrame control(){
        return view;
    }
    
    public JTextField userField(){
        return user;
    }
    
    public JTextField passwordField(){
        return pass;
    }
    
    public JButton aceptButton(){
        return acept;
    }
    
    public void addALAcept(ActionListener al){
        acept.addActionListener(al);
    }
    
    public void AddWAView(WindowAdapter wa){
        view.addWindowListener(wa);
    }
    
    class ImagePanel extends JPanel{
        private Image image;
        
        public ImagePanel(){
            String url = "/images/head1.png";
            image= new ImageIcon(getClass().getResource(url)).getImage();
        }
        
        @Override
        public void paintComponent( Graphics g )
        {
          super.paintComponent( g );
          Graphics2D g2d = (Graphics2D) g;
          g2d.drawImage(image, 0, 0, null);
        }
    }
}