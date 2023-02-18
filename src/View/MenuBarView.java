package View;

import javax.swing.*;

import java.awt.event.*;

/**
 * Classe che gesisce l'aspetto della MenuBar del programma e assegna agli elementi i loro {@link ActionListener}.
 * 
 * @author Filip Czuba
 * @version 2.0
 */
public class MenuBarView extends JMenuBar{

    /**
     * Oggetto {@link JMenu} che contiene tutte le voci legate all'interazione con i file.
     */
    private JMenu menuFile;

    /**
     * Oggetto {@link JMenu}, sottomenu di @param menuFile
     */
    private JMenu menuEsportaCome;

    /**
     * Oggetto {@link JMenuItem}, voce del menu @param menuFile
     */
    private JMenuItem menuItemSalva;

    /**
     * Oggetto {@link JMenuItem}, voce del menu @param menuFile 
     */
    private JMenuItem menuItemCarica;

    /**
    * Oggetto {@link JMenuItem}, voce del sottomenu @param menuEsportaCome 
    */
    private JMenuItem menuItemEsportaCSV;

    /**
    * Oggetto {@link JMenuItem}, voce del sottomenu @param menuEsportaCome 
    */
    private JMenuItem menuItemEsportaPlainText;

    /**
     * Oggetto {@link JMenuItem}, voce della JMenuBar
     */
    private JMenuItem menuItemStampa;

    /**
     * Oggetto {@link JLabel}, premette il valore del totale.
     */
    private JLabel labelTotale;

    /**
     * Oggetto {@link JLabel}, contiene il valore del totale.
     */
    private JLabel labelValoreTotale;

    /**
     * Costruttore della classe {@link MenuBarView}, inizializza tutti i componenti e 
     * li pone al loro posto all'interno della MenuBar.
     */
    public MenuBarView() {

        //Inizializzazione degli elementi dei menu.
        menuFile = new JMenu("File");
        menuEsportaCome = new JMenu("Esporta come");
        menuItemStampa = new JMenuItem("Stampa");
        menuItemSalva = new JMenuItem("Salva");
        menuItemCarica = new JMenuItem("Carica");
        menuItemEsportaCSV = new JMenuItem("CSV");
        menuItemEsportaPlainText= new JMenuItem("Testo");

        //Assegnamento dei vari menu e sottomenu.
        menuFile.add(menuItemSalva);
        menuFile.add(menuItemCarica);
        menuFile.addSeparator();
        menuFile.add(menuEsportaCome);
        menuEsportaCome.add(menuItemEsportaCSV);
        menuEsportaCome.add(menuItemEsportaPlainText);
        add(menuFile);
        add(menuItemStampa);

        
        //Inizializzazione delle label responsabili per la visualizzazione del totale.
        labelTotale = new JLabel("Totale: ");
        labelValoreTotale = new JLabel("0.0");

        //Allinea a destra le label responsabili per la visualizzazione del totale.
        add(Box.createHorizontalGlue());
        add(labelTotale);
        add(labelValoreTotale);    
    }

    /**
     * Metodo che associa un {@link ActionListener} al JMenuItem menuItemSalva.
     * @param saveListener evento avviato dalla pressione del JMenuItem.
     */
    public void addSaveListener(ActionListener saveListener){
        menuItemSalva.addActionListener(saveListener);
    }

    /**
     * Metodo che associa un {@link ActionListener} al JMenuItem menuItemCarica.
     * @param loadListener evento avviato dalla pressione del JMenuItem.
     */
    public void addLoadListener(ActionListener loadListener){
        menuItemCarica.addActionListener(loadListener);
    }

    /**
     * Metodo che associa un {@link ActionListener} al JMenuItem menuItemEsportaPlainText.
     * @param textExportListener evento avviato dalla pressione del JMenuItem.
     */
    public void addTextExportListener(ActionListener textExportListener){
        menuItemEsportaPlainText.addActionListener(textExportListener);
    }

    /**
     * Metodo che associa un {@link ActionListener} al JMenuItem menuItemEsportaCSV.
     * @param csvExportListener evento avviato dalla pressione del JMenuItem.
     */
    public void addCSVExportListener(ActionListener csvExportListener){
        menuItemEsportaCSV.addActionListener(csvExportListener);
    }

    /**
     * Metodo che associa un {@link ActionListener} al JMenuItem menuItemStampa.
     * @param printListener evento avviato dalla pressione del JMenuItem.
     */
    public void addPrintListener(ActionListener printListener){
        menuItemStampa.addActionListener(printListener);
    }

    /**
     * Assegna alla JLabel labelValoreTotale un determinato valore.
     * @param text Stringa da assegnare alla label.
     */
    public void setTotale(String text){
        labelValoreTotale.setText(text);
    }
}