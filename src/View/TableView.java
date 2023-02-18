package View;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;

import java.util.ArrayList;

import Model.TableModel;
import Model.Voce;


/**
 * Classe che si occupa della rappresentazione grafica della tabella e relativi metodi.
 * 
 * @author Filip Czuba
 * @version 1.0
 */
public class TableView extends JPanel {
    
    /**
     * Oggetto JTable, rappresenta graficamente la tabella.
     */
    private JTable table;
    
    /**
     * Oggetto TableModel, rappresenta l'implementazione dei metodi di una JTable. s
     */
    private TableModel tableModel;
    
    /**
     * Oggetto JPopupMenu, utilizzato per realizzare un menu che compare alla pressione del tasto destro.
     */
    private JPopupMenu popupMenu;
    
    /**
     * Oggetto JMenuItem, voce del JPopupMenu.
     */
    private JMenuItem menuItemElimina;

    /**
     * Costruttore della classe TableView. Inizializza la JTable, il suo TableModel e
     * assegna un MouseListener per il tasto destro del mouse alla tabella.
     * @param tableModel modello della JTable che si vuole rappresentare.
     */
    public TableView(TableModel tableModel) {
        
        //Assegna l'attributo TableModel e inizializza la tabella, inserendola in un JScrollPane.
        //La tabella viene configurata in modo tale da poter selezionare solo una riga alla volta.
        this.tableModel = tableModel;
        table = new JTable(this.tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        //Crea un PopupMenu con come opzione la voce "Elimina".
        popupMenu = new JPopupMenu();
        menuItemElimina = new JMenuItem("Elimina");
        popupMenu.add(menuItemElimina);

        //Evento che, alla pressione del tasto destro sulla tabella, rende visibile il PopupMenu.
        table.addMouseListener(new MouseAdapter(){
            
            @Override
            public void mousePressed(MouseEvent me) {

                if(me.getButton()==MouseEvent.BUTTON3 && getSelectedRow()!=-1) {

                    popupMenu.show(table, me.getX(), me.getY());
                }
            }
        });

        //Rende visibile l'intero pannello.
        setVisible(true);
    }

    /**
     * Aggiorna i dati visualizzati all'interno della tabella.
     */
    public void updateTable() {

        tableModel.fireTableDataChanged();
    }

    /**
     * Setter per i dati contenuti nella tabella.
     * 
     * @param voci {@link ArrayList} di oggetti {@link Voce} da assegnare al TableModel.
     */
    public void setTableData(ArrayList<Voce> voci) {

        tableModel.setData(voci);
    }

    /**
     * Evidenzia la riga nella tabella il cui indice è stato inserito. 
     * Se viene inserito -1 allora vengono deselezionate qualsiasi righe.
     * @param index indice della riga da evidenziare o -1.
     */
    public void highlightRow(int index) {

        if(index != -1) {

            table.changeSelection(index,0,false,false);
        }
        else {

            table.clearSelection();
        }
    } 

    /**
     * Getter per l'indice della riga attualmente selezionata.
     * 
     * @return indice della riga attualmente selezionata o -1 se nessuna lo è.
     */
    public int getSelectedRow() {

        return table.getSelectedRow();
    }

    /**
     * Apre il menu di stampa, permettendo all'utente di stampare le voci attualmente contenute nella tabella.
     * Utilizza la funzione per la stampa già definita per gli oggetti di tipo {@link JTable}.
     * 
     * @see javax.swing.JTable#print()
     * 
     * @throws PrinterException se il sistema operativo segnala un problema legato alla stampante o processo di stampa.
     */
    public void print() throws PrinterException {

        table.print(); 
    }

    /**
     * Metodo che, quando chiamato, inverte lo status di menuItemElimina.
     * Questo metodo viene utilizzato per impedire altre operazioni quando è attivo un filtro sulla tabella.
     * Lo status attuale del menuItem (attivo o meno) viene rilevato, invertito logicamente e riassegnato a questo,
     * per cui è possibile realizzare un meccanismo di flip-flop senza alcuna variabile o senza alcun parametro.
     */
    public void switchGreyOut() {
        menuItemElimina.setEnabled(!menuItemElimina.isEnabled());
    }

    /**
     * Metodo che associa un {@link ActionListener} al JMenuItem menuItemElimina.
     * @param removeListener evento avviato dalla pressione del JMenuItem.
     */
    public void addRemoveListener(ActionListener removeListener) {

        menuItemElimina.addActionListener(removeListener);
    }
}
