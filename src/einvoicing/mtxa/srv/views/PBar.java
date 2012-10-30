/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package einvoicing.mtxa.srv.views;

/**
 *
 * @author Carlos Gutierrez
 */
import javax.swing.JProgressBar;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Dialog;

public class PBar{
    private JProgressBar pb;
    private JDialog view;
    private int max;
    private int curr;
    private JFrame parent;
           
    public PBar(int max, JFrame parent, String title){
        this.max = max;
        this.parent = parent;
        curr = 0;
        createGUI(parent, title);
    }
    
    private void createGUI(JFrame parent, String title){
        pb = new JProgressBar(0,max);
        pb.setPreferredSize(new Dimension(400,30));
        view = new JDialog(parent);
        JPanel pane = new JPanel(new FlowLayout());
        pane.add(pb);
        view.add(pane);
        view.pack();
        view.setLocationRelativeTo(null);
        view.setTitle(title);
        //view.setModalityType(Dialog.ModalityType.TOOLKIT_MODAL);
        //view.setModal(true);
    }
    
    public void show(){
        view.setVisible(true);
        parent.setEnabled(false);
    }
    
    public void close(){
        view.setVisible(false);
        parent.setEnabled(true);
        view.dispose();
    }
    
    public void increaseBarValue(int inc){
        curr+=inc;
        pb.setValue(curr);
        pb.setStringPainted(true);
    }
    
}
