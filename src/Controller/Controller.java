package Controller;

import Model.*;
import Utils.AutoSave;
import Utils.ExtensionGrab;
import View.*;

import FileManagement.*;

import java.awt.event.*;
import java.awt.print.PrinterException;

import java.io.File;
import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Classe Controller.
 * Gestisce le interazioni tra {@link View} e {@link Model}, assegnando a ciascun componente della GUI un evento.
 * Contiene diverse classi interne, ciascuna per ciascun evento implementato.
 * 
 * @author Filip Czuba
 * @version 2.0
 */
public class Controller  {

    /**
     * Oggetto di tipo {@link InputView}, pannello responsabile degli input.
     */
    private InputView inputView;
    
    /**
     * Oggetto di tipo {@link TableView}, pannello contenente la tabella.
     */
    private TableView tableView;
    
    /**
     * Oggetto di tipo {@link MainView}, frame che racchiude i pannelli e visualizza la GUI.
     */
    private MainView mainView;

    /**
     * Oggetto di tipo {@link Bilancio}, modella i dati contenuti nella tabella.  
     */
    private Bilancio bilancio;

    /**
     * Oggetto di tipo {@link TableModel}, implementazione del modello della tabella e dei metodi per interagire con questa.
     */
    private TableModel tableModel;

    /**
     *Oggetto di tipo {@link Exporter}, gestisce l'esportazione della tabella su file.
     */
    private Exporter exporter;

    /**
     * Costruttore della classe Controller.
     * Prende in input tutte le componenti di View, ai quali assegna degli eventi, definiti nelle sottoclassi di controller.
     * 
     * @param bilancio oggetto {@link Bilancio} da assegnare all'attributo {@code bilancio}.
     * @param mainView oggetto {@link MainView} da assegnare all'attributo {@code mainView}.
     * @param inputView oggetto {@link InputView} da assegnare all'attributo {@code inputView}.
     * @param tableView oggetto {@link TableView} da asseggnare all'attributo {@code tableView}.
     * @param menuBarView oggetto {@link MenuBarView} da assegnare all'attributo {@code menuBarView}.
     * @param tableModel oggetto {@link TableModel} da assegnare all'attributo {@code tableModel}.
     */
    public Controller(Bilancio bilancio, MainView mainView, InputView inputView, TableView tableView, MenuBarView menuBarView, TableModel tableModel){
        
        //Assegna agli attributi gli oggetti passati per parametro.
        this.bilancio = bilancio;
        this.inputView = inputView;
        this.tableView = tableView;
        this.mainView = mainView;
        this.tableModel = tableModel;
        
        //Associa agli elementi di inputView, tableView e menuBarView degli eventi.
        inputView.addAddListener(new AddListener());
        inputView.addEditListener(new EditListener());
        inputView.addSearchListener(new SearchListener());
        inputView.addFilterListener(new FilterListener());
        tableView.addRemoveListener(new RemoveListener());
        menuBarView.addSaveListener(new SaveListener());
        menuBarView.addLoadListener(new LoadListener());
        menuBarView.addTextExportListener(new TextExportListener());
        menuBarView.addCSVExportListener(new CSVExportListener());
        menuBarView.addPrintListener(new printListener());
    }

    /**
     * Inizializza un'istanza di autoSave e la fa partire con il metodo {@link java.lang.Thread#run()}.
     * @see Utils.AutoSave
     */
    public void startAutoSave() {
        AutoSave autoSave = new AutoSave(bilancio);
        autoSave.run();
    }

    /**
     * Carica il file salvato automaticamente, contenuto in /data/temp.bal, quando viene chiamato.
     * Usato per caricare il salvataggio automatico all'apertura del programma.
     */
    public void loadOnStartup() {
        File file = new File((System.getProperty("user.dir")+"/data/temp.bal"));
        if(file.exists()) {
            try {
                bilancio.loadFile(file);
            } catch(IOException ioEx) {
                ioEx.printStackTrace();
            }
        }
        mainView.setDataUpdate(bilancio);
    }

    /**
     * Classe che definisce l'evento per aggiungere una voce alla tabella.
     */
    class AddListener implements ActionListener{

        /**
         * Override del metodo {@link java.awt.event.ActionListener#actionPerformed()}, implementa l'aggiunta
         * di una voce alla tabella prendendo i dati contenuti all'interno dei JTextField dentro a InputView.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            
            //Verifica che non ci siano problemi con i dati inseriti dentro i JtextField
            boolean sanityCheck = true;

            //Imposta il formato della data a gg-mm-aaaa e inizializza un oggetto Date a null.
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date data = null;

            //Inizializza un oggetto Double a null.
            Double ammontare = null;
            
            //Parse della data dal JTextField textData con la gestione del ParseException lanciato dal metodo parse().
            try {

                data = dateFormat.parse(inputView.getTextData());
            } catch (ParseException pEx) {

                pEx.printStackTrace();
                sanityCheck=false;
            }

            //Parse della descrizione dal JTextField textDescrizione
            String descrizione = inputView.getTextDescrizione();

            //Parse dell'ammontare dal JTextField textAmmontare con la gestione del NumberFormatException lanciato dal metodo parseDouble().
            try {

                ammontare = Double.parseDouble(inputView.getTextAmmontare());
            } catch(NumberFormatException nfEx) {

                nfEx.printStackTrace();
                JOptionPane.showMessageDialog(inputView, "Il valore inserito non è valido.", "Errore", JOptionPane.ERROR_MESSAGE);
                sanityCheck=false;
            }

            //Controlla se la data inserita rispetta il formato impostato. Lancia l'errore anche se textData era vuoto.
            if(!inputView.getTextData().equals(dateFormat.format(data))) {

                JOptionPane.showMessageDialog(inputView, "Il formato della data è errato, quello corretto è gg-mm-aaaa.", 
                "Errore", JOptionPane.ERROR_MESSAGE);
                sanityCheck=false;
            }

            //Controlla se l'ammontare inserito è zero. Nel caso fosse null ciò è gestito dal NumberFormatException.
            if(ammontare==0) {

                JOptionPane.showMessageDialog(inputView, "L'ammontare non può essere nullo.", "Errore", JOptionPane.ERROR_MESSAGE);
                sanityCheck=false;
            }
            
            /*
            * Se non c'è stato alcun errore e sanityCheck è rimasto true, allora la voce viene aggiunta al modello tabella e
            * la tabella viene aggiornata.
            * Non vengono svuotati i campi se gli input sono errati per dare la possibilità all'utente di correggere eventuali errori.
            */
            if(sanityCheck) {

                Voce voce = new Voce(data, descrizione, ammontare);
                bilancio.aggiungiVoce(voce);
                mainView.setDataUpdate(bilancio);
                inputView.resetInputs();
            }
        }
    }

    /**
     * Classe che definisce l'evento per modificare una voce presente nella tabella.
     */
    class EditListener implements ActionListener {

        /**
         * Override del metodo {@link java.awt.event.ActionListener#actionPerformed()}, implementa la modifica
         * di una voce selezionata nella tabella prendendo i dati contenuti all'interno dei JTextField dentro a InputView.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            //Verifica che non ci siano problemi con i dati inseriti nei JTextField
            boolean sanityCheck = true;

            //Imposta il formato della data a gg-mm-aaaa e inizializza un oggetto Date a null.
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date data = null;
            
            //Inizializza un oggetto Double a null.
            Double ammontare = null;

            //Parse della data dal JTextField textData con la gestione del ParseException lanciato dal metodo parse().
            try {

                data = dateFormat.parse(inputView.getTextData());
            } catch (ParseException pEx) {

                pEx.printStackTrace();
                sanityCheck=false;
            }

            //Parse della descrizione dal JTextField textDescrizione
            String descrizione = inputView.getTextDescrizione();
            
            //Parse dell'ammontare dal JTextField textAmmontare con la gestione del NumberFormatException lanciato dal metodo parseDouble().
            try {

                ammontare = Double.parseDouble(inputView.getTextAmmontare());
            } catch(NumberFormatException nfEx) {

                nfEx.printStackTrace();
                JOptionPane.showMessageDialog(inputView, "Il valore inserito non è valido.", "Errore", JOptionPane.ERROR_MESSAGE);
                sanityCheck=false;
            }

            //Controlla se la data inserita rispetta il formato impostato. Lancia l'errore anche se textData era vuoto.
            if(!inputView.getTextData().equals(dateFormat.format(data))) {

                JOptionPane.showMessageDialog(inputView, "Il formato della data è errato, quello corretto è gg-mm-aaaa.", 
                "Errore", JOptionPane.ERROR_MESSAGE);
                sanityCheck=false;
            }
            
            //Controlla se l'ammontare inserito è zero. Nel caso fosse null ciò è gestito dal NumberFormatException.
            if(ammontare==0) {

                JOptionPane.showMessageDialog(inputView, "L'ammontare non può essere nullo.", "Errore", JOptionPane.ERROR_MESSAGE);
                sanityCheck=false;
            }

            //Controlla se è stata selezionata una voce nella tabella.
            if(tableView.getSelectedRow()==-1) {

                JOptionPane.showMessageDialog(inputView, "E' necessario selezionare una voce da modificare.", "Errore", JOptionPane.ERROR_MESSAGE);
                sanityCheck=false;
            }
            
            //Controlla se l'ammontare inserito è zero. Nel caso fosse null ciò è gestito dal NumberFormatException.           
            if(sanityCheck) {
                //Crea la voce con i dati modificati, imposta la voce modifica nell'indice selezionato e aggiorna la tabella.
                Voce voce = new Voce(data, descrizione, ammontare);
                bilancio.modificaVoce(tableView.getSelectedRow(), voce);
                mainView.setDataUpdate(bilancio);
                inputView.resetInputs();
            }
        }
    }

    /**
     * Classe che definisce l'evento per cercare le voci che contengono una data stringa nella tabella.
     * 
     * NOTA BENE: È stata volontariamente omessa la possibilità di cercare anche tra data e ammontare a causa
     * dell'elevata presenza di numeri simili (essendo meno delle lettere), rendendo la visualizzazione della ricerca
     * molto confusa.
     */
    class SearchListener implements ActionListener {

        /**
         * Override del metodo {@link java.awt.event.ActionListener#actionPerformed()}, implementa la ricerca
         * di una voce contenuta nella tabella, prendendo i dati contenuti all'interno di textRicerca dentro a InputView,
         * iterando ogni volta che l'evento accade evidenziando la voce successiva in modo circolare.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            //Parse della stringa da cercare.
            String query = inputView.getTextRicerca();

            /*
             * Se la stringa è vuota deseleziono qualsiasi selezione, altrimenti evidenzio la voce successiva a
             * quella selezionata attualmente che contiene la stringa, ottenendone l'indice tramite il metodo
             * Bilancio.cercaVoce().
             */
            if(!query.isEmpty()) { 

                tableView.highlightRow(bilancio.cercaVoce(query,tableView.getSelectedRow()));
            }
            else {

                tableView.highlightRow(-1);
            }
        }
    }

    /**
     * Classe che definisce il filtraggio delle voci presenti nella tabella.
     */
    class FilterListener implements ActionListener {
        
        /**
         * Override del metodo {@link java.awt.event.ActionListener#actionPerformed()}, implementa il filtraggio
         * della tabella a seconda della data inserita in textFiltra e l'intervallo selezionato in comboBoxFiltra.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            //Prende lo status del bottone buttonFiltra
            String status = inputView.getButtonTextFiltra();

            /*
             * Se il testo contenuto in buttonFiltra è "Filtra" allora filtra le voci nella tabella secondo
             * la data e l'intervallo inseriti, altrimenti, se il testo è "Indietro" viene rimosso il fitro imposto
             * e visualizza di nuovo l'intera lista. In entrambi i casi, il testo viene cambiato da un all'altro se premuto.
             * 
             * NOTA BENE: Utils.AutoSave() non salva ciò che è in tabella ma bensì il contenuto dell'oggetto Bilancio; quando
             * viene creata la lista di oggetti filtrata viene creato un nuovo oggetto Bilancio che viene visualizzato dalla tabella,
             * senza andare a modificare quello principale, ovvero quello che il thread salva.
             * Le funzioni di export e stampa la svolgono sul contenuto della tabella, mentre quelle di salvataggio su Bilancio.
             */
            if(status.equals("Filtra")) {

                //Imposta il formato della data a gg-mm-aaaa e crea un'istanza di Calendar.
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Calendar calendar = Calendar.getInstance();

                //Prende l'intervallo da filtrare selezionato nella comboBox.
                int choice = inputView.getComboIntervallo();

                //Prende la stringa contenuta in textFiltra e inizializza gli estremi dell'intervallo a null.
                String startString = inputView.getTextFiltra();
                Date start = null;
                Date end = null;

                //Parse della data da startString con la gestione del ParseException lanciato dal metodo parse().
                try {

                    start = format.parse(startString);
                }catch(ParseException pEx) {
                    pEx.printStackTrace();
                }

                //Controlla la correttezza della data inserita come inizio dell'intervallo. Se è scorretta interromple l'evento.
                if((inputView.getTextFiltra().isEmpty()) || !inputView.getTextFiltra().equals(format.format(start))) {

                    JOptionPane.showMessageDialog(inputView, "Il formato della data è errato, quello corretto è gg-mm-aaaa.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Imposta l'istanza di Calendar con tempo d'inizio l'inizio dell'intervallo.
                calendar.setTime(start);

                //Genera la data di fine intervallo a seconda dell'intervallo selezionato nella comboBox.
                switch(choice) {
                    case 0:
                            break;
                    case 1:
                            calendar.add(Calendar.DAY_OF_YEAR, 6);  
                            break;   
                    case 2:
                            calendar.add(Calendar.MONTH,1);
                            break;
                    case 3: 
                            calendar.add(Calendar.YEAR,1);
                            break;
                    default:
                            break;
                }

                //Crea la data di fine intervallo come traslazione della data d'inizio.
                end = calendar.getTime();

                /*
                 * Viene creato un ArrayList di oggetti Voce a partire da quella contenuta in bilancio,
                 * controllando ciascuna voce e copiando quelle che rientrano nell'intervallo di date,
                 * estremi compresi.
                 */
                ArrayList<Voce> old = bilancio.getData();
                ArrayList<Voce> voci = new ArrayList<Voce>();
                for(Voce voce : old ) {
                    Date date = voce.getData();
                    if(date.compareTo(start) >= 0 && date.compareTo(end) <= 0) {
                        voci.add(voce);
                    }  
                }

                /*
                 * Crea una nuova istanza di Bilancio, contenente la nuova ArrayList di voci e la visualizza
                 * nella tabella.
                 * Successivamente, viene cambiato testo a bottoneFiltra a "Indietro" e vengono bloccare le funzionalità
                 * di aggiunta, modifica e rimozione delle voci.
                 */
                Bilancio newBilancio = new Bilancio(voci);
                mainView.setDataUpdate(newBilancio);
                inputView.setButtonTextFiltra("Indietro");
                inputView.switchGreyOut();
                tableView.switchGreyOut();
            } 
            else {

                /*
                 * Se bottoneFiltra era già "Indietro", allora viene rimosso il filtro, rivisualizzando la lista
                 * di voci originale e sbloccando le funzionalità di aggiunta, modifica e rimozione delle voci e
                 * svuotando il contenuto di textFiltra.
                 */
                mainView.setDataUpdate(bilancio);
                inputView.switchGreyOut();
                tableView.switchGreyOut();
                inputView.setButtonTextFiltra("Filtra");
                inputView.setTextFiltra("");
            }
        }
    }

    /**
     * Classe che definisce l'evento per la rimozione di una voce dalla tabella.
     */
    class RemoveListener implements ActionListener {
        
        /**
        * Override del metodo {@link java.awt.event.ActionListener#actionPerformed()}, implementa la rimozione
        * di una voce dalla tabella in base alla selezione di suddetta voce nella tabella.
        */
        @Override
        public void actionPerformed(ActionEvent e) {

            /*
             * Rimuove la voce dal bilancio e aggiorna la tabella. Se non c'è alcuna voce selezionata
             * Bilancio.rimuoviVoce() non fa nulla per costruzione.
             */
            bilancio.rimuoviVoce(tableView.getSelectedRow());
            mainView.setDataUpdate(bilancio);
        }
    }


    /**
     * Classe che definisce l'evento per il salvataggio del contenuto del bilancio.
     */
    class SaveListener implements ActionListener {

        /**
        * Override del metodo {@link java.awt.event.ActionListener#actionPerformed()}, implementa il salvataggio
        * del contenuto dell'istanza principale di bilancio.
        */
        @Override
        public void actionPerformed(ActionEvent e) {

            /*
             * Imposta il FileChooser a file di tipo .bal e gli passa un FileFilter per i file seriali,
             * inoltre impedise di selezionare un filtro per poter scegliere tutti i file.
             */
            FileChooser fc = new FileChooser(FileType.Serial);
            fc.addChoosableFileFilter(new SerialFilter());
            fc.setAcceptAllFileFilterUsed(false);

            /*
             * Mostra la finestra di selezione dei file e entra nel corpo dell'if se viene
             * selezionato l'opzione di ok.
             */
            if(fc.showSaveDialog(mainView) == JFileChooser.APPROVE_OPTION) {
                
                /**
                 * Se il file scelto non ha estensione o ha un'altra estensione la modifica e salva il file.
                 * Lancia una IOException se saveFile() fallisce.
                 */
                try {

                    File file = fc.getSelectedFile();

                    if(ExtensionGrab.getExtension(file) == null || !ExtensionGrab.getExtension(file).equalsIgnoreCase("bal")) {

                        file = new File(file.toString()+".bal");
                    }

                    bilancio.saveFile(file);
                }
                catch(IOException ioEx) {

                    JOptionPane.showMessageDialog(mainView, "Impossibile salvare i dati.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Classe che gestisce l'evento di caricamento dei contenuti della tabella da file.
     */
    class LoadListener implements ActionListener {
       
        /**
        * Override del metodo {@link java.awt.event.ActionListener#actionPerformed()}, implementa il caricamento
        * del contenuto del bilancio da file.
        */
        @Override
        public void actionPerformed(ActionEvent e) {

            //Imposta il FileChooser a file di tipo .bal e gli passa un FileFilter per i file seriali.
            FileChooser fc = new FileChooser(FileType.Serial);
            fc.addChoosableFileFilter(new SerialFilter());
            fc.setAcceptAllFileFilterUsed(true);

            /*
             * Carica i dati da file su bilancio e aggiorna la tabella se viene selezionata l'opzione ok.
             * Lancia un IOException se fallisce loadFile().
             */
            if(fc.showOpenDialog(mainView) == JFileChooser.APPROVE_OPTION) {

                try {

                    bilancio.loadFile(fc.getSelectedFile());
                    mainView.setDataUpdate(bilancio);
                }
                catch(IOException ioEx) {

                    JOptionPane.showMessageDialog(mainView, "Impossibile caricare i dati.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Classe che definisce l'evento per esportare i contenuti della tabella in un file di testo.
     */
    class TextExportListener implements ActionListener {
       
        /**
         * Override del metodo {@link java.awt.event.ActionListener#actionPerformed()}, implementa l'esportazione
         * del contenuto della tabella presente in TableView in un file di tipo .txt
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            /*
             * Imposta il FileChooser a file di tipo .txt e gli passa un FileFilter per i file di testo,
             * inoltre impedise di selezionare un filtro per poter scegliere tutti i file.
             */
            FileChooser fc = new FileChooser(FileType.PlainText);
            fc.addChoosableFileFilter(new PlainTextFilter());
            fc.setAcceptAllFileFilterUsed(false);

            /*
             * Mostra la finestra di selezione dei file e entra nel corpo dell'if se viene
             * selezionato l'opzione di ok.
             */
            if(fc.showSaveDialog(mainView) == JFileChooser.APPROVE_OPTION) {

                /**
                 * Se il file scelto non ha estensione o ha un'altra estensione la modifica e salva il file.
                 * Lancia una IOException se saveFile() fallisce.
                 */
                try {

                    File file = fc.getSelectedFile();

                    if(ExtensionGrab.getExtension(file) == null || !ExtensionGrab.getExtension(file).equalsIgnoreCase("txt")) {

                        file = new File(file.toString()+".txt");
                    }

                    /*
                     * L'oggetto exporter, originariamente istanza di Exporter, viene ridefinito come
                     * PlainTextExporter per l'esportazione su file di testo.
                     */
                    exporter = new PlainTextExporter(tableModel, file);
                    exporter.exportFile();
                } catch(IOException ioEx) {

                    JOptionPane.showMessageDialog(mainView, "Impossibile esportare i dati.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Classe che definisce l'evento per esportare i contenuti della tabella in un file CSV.
     */
    class CSVExportListener implements ActionListener {
        
        /**
         * Override del metodo {@link java.awt.event.ActionListener#actionPerformed()}, implementa l'esportazione
         * del contenuto della tabella presente in TableView in un file di tipo .csv
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            /*
             * Imposta il FileChooser a file di tipo .txt e gli passa un FileFilter per i file CSV,
             * inoltre impedise di selezionare un filtro per poter scegliere tutti i file.
             */
            FileChooser fc = new FileChooser(FileType.CSV);
            fc.addChoosableFileFilter(new CSVFilter());
            fc.setAcceptAllFileFilterUsed(false);

            /*
             * Mostra la finestra di selezione dei file e entra nel corpo dell'if se viene
             * selezionato l'opzione di ok.
             */
            if(fc.showSaveDialog(mainView) == JFileChooser.APPROVE_OPTION) {

                /**
                 * Se il file scelto non ha estensione o ha un'altra estensione la modifica e salva il file.
                 * Lancia una IOException se saveFile() fallisce.
                 */
                try {

                    File file = fc.getSelectedFile();

                    if(ExtensionGrab.getExtension(file) == null || !ExtensionGrab.getExtension(file).equalsIgnoreCase("csv")) {

                        file = new File(file.toString()+".csv");
                    }

                    /*
                     * L'oggetto exporter, originariamente istanza di Exporter, viene ridefinito come
                     * CSVExporter per l'esportazione su file di testo.
                     */
                    exporter = new CSVExporter(tableModel, file);
                    exporter.exportFile();
                }
                catch(IOException ioEx) {

                    JOptionPane.showMessageDialog(mainView, "Impossibile esportare i dati.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Classe che definisce l'evento per la gestione della stampa dei contenuti della tabella.
     */
    class printListener implements ActionListener {

        /**
         * Override del metodo {@link java.awt.event.ActionListener#actionPerformed()}, implementa la stampla
         * dei contenuti della tabella presente in TableView.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            /*
             * Stampa il contenuto della tabella. Lancia una PrinterException se
             * il gestore di stampa del sistema segnala un errore con lo stato della stampa o della stampante.
             */
            try {

                tableView.print();
            } catch (PrinterException prEx) {

                prEx.printStackTrace();
                JOptionPane.showMessageDialog(mainView,"Impossibile stampare.","Errore",JOptionPane.ERROR_MESSAGE); 
            }
        }
    }
}