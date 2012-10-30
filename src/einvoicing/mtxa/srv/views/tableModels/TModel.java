/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package einvoicing.mtxa.srv.views.tableModels;

/**
 *
 * @author Carlos Gutierrez
 */
import javax.swing.table.*;
import javax.swing.table.TableModel;
import javax.swing.event.*;
import java.util.ArrayList;
import einvoicing.mtxa.srv.beans.beanEInvoice;


public class TModel implements TableModel
{
    

    public int getColumnCount() {
        return 5;
    }
    

    public int getRowCount() {
        return datos.size();
    }
    

    public Object getValueAt(int rowIndex, int columnIndex) {
        beanEInvoice aux;
        
        aux = (beanEInvoice)(datos.get(rowIndex));

        switch (columnIndex)
        {
            case 0:
                return aux.getInvoiceNumber();
            case 1:
                return aux.getFolio();
            case 2:
                return aux.getCustomerName();
            case 3:
                return new Float(aux.getTotal());
            case 4:
                return aux.getExists();
            default:
                return null;
        }
    }
    

    public void deleteEInvoice (int fila)
    {
        datos.remove(fila);
        
        TableModelEvent evento = new TableModelEvent (this, fila, fila, 
            TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        

        avisaSuscriptores (evento);
    }
    
    public void deleteAllEInvoices ()
    {
        datos.clear();
        
        TableModelEvent evento = new TableModelEvent (this, -1, -1, 
            TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        

        avisaSuscriptores (evento);
    }
    

    public void addEInvoice (beanEInvoice nuevabeanEInvoice)
    {
        datos.add (nuevabeanEInvoice);
        
        TableModelEvent evento;
        evento = new TableModelEvent (this, this.getRowCount()-1,this.getRowCount()-1, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT);


        avisaSuscriptores (evento);
    }
    

    public void addTableModelListener(TableModelListener l) {

        listeners.add (l);
    }
    

    public Class getColumnClass(int columnIndex) {

        switch (columnIndex)
        {
            case 0:

                return String.class;
            case 1:

                return String.class;
            case 2:

                return String.class;
            case 3:

                return Float.class;
            case 4:

                return String.class;
            default:

                return Object.class;
        }
    }
    

    public String getColumnName(int columnIndex) 
    {

        switch (columnIndex)
        {
            case 0:
                return "Numero de factura";
            case 1:
                return "Numero de documento";
            case 2:
                return "Cliente";
            case 3:
                return "Total";
            case 4:
                return "Archivo XML";
            default:
                return null;
        }
    }
    

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Permite que la celda sea editable.
        return false;
    }
    

    public void removeTableModelListener(TableModelListener l) {

        listeners.remove(l);
    }
    

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) 
    {
        beanEInvoice aux;
        aux = (beanEInvoice)(datos.get(rowIndex));
        
        switch (columnIndex)
        {
            case 0:
                aux.setInvoiceNumber((String)aValue);
                break;
            case 1:
                aux.setFolio((String)aValue);
                break;
            case 2:
                aux.setCustomerName((String)aValue);
                break;
            case 3:
                aux.setTotal((Float)aValue);
                break;
            case 4:
                aux.setExists((String)aValue);
                break;
            default:
                break;
        }
        
        TableModelEvent evento = new TableModelEvent (this, rowIndex, rowIndex, 
            columnIndex);

        avisaSuscriptores (evento);
    }
    
    public ArrayList getData(){
        return datos;
    }
    
    public beanEInvoice getItem(int index){
        return (beanEInvoice)datos.get(index);
    }
    

    private void avisaSuscriptores (TableModelEvent evento)
    {
        int i;

        for (i=0; i<listeners.size(); i++)
            ((TableModelListener)listeners.get(i)).tableChanged(evento);
    }
    

    private ArrayList datos = new ArrayList();
    
    private ArrayList listeners = new ArrayList();
}