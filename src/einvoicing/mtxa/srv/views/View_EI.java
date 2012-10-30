/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package einvoicing.mtxa.srv.views;

/**
 *
 * @author Carlos Gutierrez
 */
import javax.swing.JTable;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionListener;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.util.ArrayList;
import einvoicing.mtxa.srv.views.tableModels.TModel;
import einvoicing.mtxa.srv.beans.beanEInvoice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.table.TableRowSorter;
import java.awt.Font;


public class View_EI{
    private JTable grid;
    private JFrame view;
    private TModel model;
    private JButton acept;
    private JButton refresh;
    private JMenuBar menub;
    private JMenu config;
    private JMenuItem database;
    private JLabel usuario;
    
    
    public View_EI(){
        createGUI();
    }
    
    
    private void createGUI(){
        view = new JFrame();
        
        /*menub = new JMenuBar();
        config = new JMenu("Configuracion");
            database = new JMenuItem("Configuracion de base de datos.");
        config.add(database);
        menub.add(config);
        view.setJMenuBar(menub);*/
        
        model = new TModel();
        grid = new JTable(model);
        //grid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        acept = new JButton("Enviar");
        acept.setIcon(new ImageIcon(getClass().getResource("/images/send.png")));
        refresh = new JButton("Refrescar");
        refresh.setIcon(new ImageIcon(getClass().getResource("/images/reload.png")));
        
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(grid);
        JPanel wrapper = new JPanel();
        JPanel north = new JPanel();
        north.setLayout(new BorderLayout());
        JPanel south = new JPanel(new FlowLayout());
        JPanel west = new JPanel();
        JPanel east = new JPanel();
        
        JPanel northN = new ImagePanel();
        usuario = new JLabel();
        usuario.setForeground(Color.white);
        northN.add(usuario);
        northN.setPreferredSize(new Dimension(800,50));
        JPanel northM = new JPanel();
        northM.add(new JLabel("Facturas Electronicas a generar:"));
        north.add(northN,BorderLayout.NORTH);
        north.add(northM,BorderLayout.CENTER);
        
        west.setBackground(Color.darkGray);
        west.setPreferredSize(new Dimension(30,100));
        
        east.setBackground(Color.darkGray);
        east.setPreferredSize(new Dimension(30,100));
        
        south.add(refresh);
        south.add(acept);
        
        wrapper.setLayout(new BorderLayout());
        wrapper.add(scroll,BorderLayout.CENTER);
        wrapper.add(north,BorderLayout.NORTH);
        wrapper.add(east,BorderLayout.EAST);
        wrapper.add(west,BorderLayout.WEST);
        wrapper.add(south,BorderLayout.SOUTH);
        
        grid.setRowSorter(new TableRowSorter(model));
        //grid.setFont(new Font("San-Serif", Font.PLAIN, 14));
        
        view.add(wrapper);
        view.setPreferredSize(new Dimension(700,500));
        view.setIconImage(new ImageIcon(getClass().getResource("/images/icon.gif")).getImage());
        view.pack();
        
        view.setLocationRelativeTo(null);
        //view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Envio Facturacion Electronica");
        
        /*view.addWindowListener( new WindowAdapter() { 
            @Override
            public void windowClosing( WindowEvent evt ) { 
                System.exit( 0 ); 
            } 
        } );*/
    }
    
    public JFrame control(){
        return view;
    }
    
    public JTable table(){
        return grid;
    }
    
    public JLabel userLabel(){
        return usuario;
    }
    
    public ArrayList<beanEInvoice> getSelectedRows(){
        int[] indexes = grid.getSelectedRows();
        ArrayList<beanEInvoice> data = new ArrayList<beanEInvoice>();
        for(int current : indexes){
            current = grid.convertRowIndexToModel(current);
            data.add(model.getItem(current));
        }
        return data;
    }
    
    public void addItem(beanEInvoice item){
        model.addEInvoice(item);
    }
    
    public void addALAcept(ActionListener al){
        acept.addActionListener(al);
    }
    
    public void addALRefresh(ActionListener al){
        refresh.addActionListener(al);
    }
    
    public ArrayList<beanEInvoice> getData(){
        return model.getData();
    }
    
    public void clrscr(){
        model.deleteAllEInvoices();
    }
    
    public void AddWAView(WindowAdapter wa){
        view.addWindowListener(wa);
    }
    
    class ImagePanel extends JPanel{
        private Image image;
        
        public ImagePanel(){
            String url = "/images/head1.png";
            image= new ImageIcon(getClass().getResource(url)).getImage();
            this.setLayout(new FlowLayout(FlowLayout.RIGHT));
            int intValue = Integer.parseInt( "0e5999",16);
            this.setBackground(new Color( intValue ));
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
