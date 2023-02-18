package Model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * Classe che definisce i metodi che {@link javax.swing.JTable} utilizzerà per interagire con un modello di dati tabulare.
 * 
 * @author Filip Czuba
 * @version 1.0
 */
public class TableModel extends AbstractTableModel{
    
    /**
     *Definisce i nomi delle colonne della {@link TableModel}, coincidono con gli attributi di {@link Voce}
     */
    private String[] nomiColonne = {"Data","Descrizione","Ammontare"};
    /**
     *{@link ArrayList} di oggetti {@link Voce}, rappresenta il contenitore delle voci nella tabella.
     */
    private ArrayList<Voce> voci;

    /**
     * Costruttore della classe TableModel, inizializza un {@link ArrayList} di oggetti {@link Voce} vuota.
     */
    public TableModel(){
        voci = new ArrayList<Voce>();
    }

    /** Ovverride del metodo {@link javax.swing.table.TableModel#getColumnCount()}.
     * 
     * @return Numero di colonne della tabella.
     */
    @Override
    public int getColumnCount(){
        return nomiColonne.length;
    }

    /**
     * Override del metodo {@link javax.swing.table.TableModel#getRowCount()}.
     * 
     * @return Numero di righe della tabella.
     */
    @Override
    public int getRowCount(){
        return voci.size();
    }

    /**
     * Override del metodo {@link javax.swing.table.AbstractTableModel#getColumnName(int)}
     * @param column indice della colonna di cui si vuole avere il nome.
     * 
     * @return Nome della colonna.
     */
    @Override
    public String getColumnName(int column){
        return nomiColonne[column];
    }


    /**
     * Override del metodo {@link javax.swing.table.TableModel#getValueAt(int, int)}
     * 
     * @param row indice di riga
     * @param column indice di colonna
     * 
     * @return valore contenuto nella cella di coordinate [riga;colonna].
     */
    @Override
    public Object getValueAt(int row, int column){
        Voce voce = voci.get(row);
        switch(column)
        {
            case 0: 
                return voce.getDataFormattata();
            case 1: 
                return voce.getDescrizione();
            case 2: 
                return voce.getAmmontare();
            default:
                return -1;
        }
    }

    /**
     * Setter dell'attributo voci.
     * @param voci {@link ArrayList} di oggetti di tipo {@link Voce} da assegnare all'attributo voci di questa istanza.
     */
    public void setData(ArrayList<Voce> voci){
        this.voci = voci;
    }

    /**
     * Getter dell'attributo voci.
     * 
     * @return {@link ArrayList} di oggetti di tipo {@link Voce} contenuti in {@link voci}
     */
    public ArrayList<Voce> getData(){
        return voci;
    }
    
}
