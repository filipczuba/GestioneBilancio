package View;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Controller.Controller;

import java.awt.*;

import Model.Bilancio;
import Model.TableModel;

/**
 * Classe che ragruppa tutte le componenti definite nel package all'interno di un unico frame, visualizzato
 * su un thread EDT.
 * 
 * @author FilipCzuba
 * @version 1.0
 */
public class MainView extends JFrame {
    
    /**
     * Pannello che contiene tutti i campi di input.
     */
    private InputView inputView;

    /**
     * Pannello che contiene la rappresentazione grafica della tabella.
     */
    private TableView tableView;

    /**
     * Modello astratto della tabella che ne definirà i metodi.
     */
    private TableModel tableModel;

    /**
     * MenuBar da uilizzare in questo Frame.
     */
    private MenuBarView menuBarView;

    /**
     * Modello delle informazioni contenute nella tabella e metodi relativi per la loro manipolazione. 
     */
    private Bilancio bilancio;

    /**
     * Controller che lega i componenti visibili nella GUI con eventi.
     */
    private Controller controller;

    /**
     * Costruttore della classe MainView. Vengono assegnati tutti i componenti e il Controller che li lega assieme.
     * Il frame viene eseguito all'interno di un thread EDT (In quanto buona parte dei metodi interni a Swing non sono thread safe) 
     * e viene fatto partire il salvataggio automatico con il metodo startAutoSave() di controller.
     */
    public MainView(){

        //Inizializzazione di tutti i componenti.
        bilancio = new Bilancio();
        tableModel = new TableModel();
        tableView = new TableView(tableModel);
        inputView = new InputView();
        menuBarView = new MenuBarView();
        controller = new Controller(bilancio,this,inputView, tableView, menuBarView, tableModel);

        //Viene visualizzata la GUI in un thread EDT.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showGUI();
            }
        });

        //Viene fatto partire il salvataggio automatico.
        controller.startAutoSave();
    }

    /**
     * Imposta i pannelli e la MenuBar all'interno del frame, poi configura il frame e lo rende visibile.
     */
    private void showGUI(){
        
        setJMenuBar(menuBarView);
        setLayout(new BorderLayout());
        add(inputView, BorderLayout.NORTH);
        add(tableView,BorderLayout.CENTER);
        controller.loadOnStartup();
        setSize(getPreferredSize());
        pack();
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Aggiorna il TableModel con un nuovo insieme di dati, poi viene aggiornata la JTable e viene ricalcolato il totale delle voci.
     * @param bilancio oggetto da cui viene preso il nuovo insieme di dati tramite {@link Model.Bilancio#getData()}.
     */
    public void setDataUpdate(Bilancio bilancio){
        tableView.setTableData(bilancio.getData());
        tableView.updateTable();
        menuBarView.setTotale(bilancio.calcolaTotale().toString());
    }
}
