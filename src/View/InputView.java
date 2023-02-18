package View;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.text.SimpleDateFormat;

import java.util.Calendar;


/**
 * Classe che gestisce il pannello su cui sono presenti tutti i campi tramite i quali l'utente può interagire con la tabella
 * e assegna agli elementi i loro {@link ActionListener}.
 * 
 * @author Filip Czuba
 * @version 1.0
 */
public class InputView extends JPanel {
    /**
     *{@link JTextField} che prendono in input dati da tastiera.
     */
    private JTextField textAmmontare, textDescrizione, textData, textRicerca, textFiltra;

    /**
     * {@link JButton} che permettono all'utente di interagire con il programma.
     */
    private JButton bottoneAggiungi, bottoneModifica, bottoneCerca, bottoneFiltra;

    /**
     * {@link JLabel} che descrivono ciò che ogni {@link JTextField} prende in input.
     */
    private JLabel labelAmmontare, labelDescrizione, labelData, labelCerca;

    /**
     * {@link JComboBox} che permette all'utente di selezionare il periodo di cui mostrare le voci.
     */
    private JComboBox<String> comboIntervalloFiltra;
    
    /**
     * Costruttore della classe {@link InputView}, inizializza tutte le componenti e le inserisce all'interno di
     * un pannello utilizzando un GridBagLayout.
     */
    public InputView(){

        //Imposta il Layout con cui verranno rappresentati i componenti.
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        //Impostazione di ciascun componente nella sua posizione nel GridBagLayout
        labelData = new JLabel("Data:");
        gbc.gridx=0;
        gbc.gridy=0;
        add(labelData, gbc);

        labelDescrizione = new JLabel("Descrizione:");
        gbc.gridx=0;
        gbc.gridy=1;
        add(labelDescrizione, gbc);

        labelAmmontare = new JLabel("Ammontare:");
        gbc.gridx=0;
        gbc.gridy=2;
        add(labelAmmontare, gbc);

        labelCerca = new JLabel("Cerca:");
        gbc.gridx=2;
        gbc.gridy=0;
        add(labelCerca, gbc);

        textData = new JTextField(20);
        gbc.gridx=1;
        gbc.gridy=0;
        add(textData, gbc);

        textDescrizione = new JTextField(20);
        gbc.gridx=1;
        gbc.gridy=1;
        add(textDescrizione, gbc);

        textAmmontare = new JTextField(20);
        gbc.gridx=1;
        gbc.gridy=2;
        add(textAmmontare, gbc);

        textRicerca = new JTextField(20);
        gbc.gridx=3;
        gbc.gridy=0;
        add(textRicerca, gbc);

        textFiltra = new JTextField(20);
        gbc.gridx=3;
        gbc.gridy=2;
        add(textFiltra, gbc);

        bottoneAggiungi = new JButton("Aggiungi");
        gbc.gridx=0;
        gbc.gridy=3;
        add(bottoneAggiungi, gbc);

        bottoneModifica = new JButton("Modifica");
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx=1;
        gbc.gridy=3;
        add(bottoneModifica, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        bottoneCerca = new JButton("Cerca");
        gbc.gridx=2;
        gbc.gridy=1;
        add(bottoneCerca, gbc);

        bottoneFiltra = new JButton("Filtra");
        gbc.gridx=2;
        gbc.gridy=3;
        add(bottoneFiltra, gbc);

        //La ComboBox viene inizializzata con le 4 opzioni presenti nell'array e all'avvio seleziona "Giorno".
        String opzioniComboFiltra[] = {"Giorno","Settimana","Mese","Anno"};
        comboIntervalloFiltra = new JComboBox<>(opzioniComboFiltra);
        comboIntervalloFiltra.setSelectedIndex(0);
        gbc.gridx=2;
        gbc.gridy=2;
        add(comboIntervalloFiltra, gbc);
        
        //Imposta per la prima volta tutti i TextField ai loro valori di default e rende visibile il Pannello.
        resetInputs();
        setVisible(true);
    }
    
    /**
     * Getter per il contenuto di textAmmontare
     * @return contenuto del JTextField come stringa.
     */
    public String getTextAmmontare() {
        return textAmmontare.getText();
    }

    /**
     * Getter per il contenuto di textDescrizione
     * @return contenuto del JTextField come stringa.
     */
    public String getTextDescrizione() {
        return textDescrizione.getText();
    }

   /**
    * Getter per il contenuto di textData
    * @return contenuto del JTextField come stringa.
    */
    public String getTextData() {
        return textData.getText();
    }

    /**
     * Setter per il contenuto di textData
     * @param set stringa da impostare all'interno del JTextField.
     */
    public void setTextData(String set){
        textData.setText(set);
    }

    /**
     * Getter per il contenuto di textRicerca
     * @return contenuto del JTextField come stringa.
     */
    public String getTextRicerca() {
        return textRicerca.getText();
    }

    /**
     * Getter per il contenuto di textFiltra
     * @return contenuto del JTextField come stringa.
     */
    public String getTextFiltra() {
        return textFiltra.getText();
    }
    
    /**
     * Getter per il testo interno a bottoneFiltra
     * @return testo del JButton come stringa.
     */
    public String getButtonTextFiltra() {
        return bottoneFiltra.getText();
    }

    /**
     * Setter per il testo interno a bottoneFiltra
     * @param set stringa da impostare come testo del JButton
     */
    public void setButtonTextFiltra(String set) {
        bottoneFiltra.setText(set);
    }

    /**
     * Setter per il contenuto di textFiltra
     * @param set stringa da impostare all'interno del JTextField.
     */
    public void setTextFiltra(String set) {

        textFiltra.setText(set);
    }

    /**
     * Getter per l'indice della voce attualmente selezionata da comboIntervalloFiltra .
     * Per costruzione, una voce sarà sempre selezionata, di default "Oggi".
     * @return indice della voce attualmente selezionata
     */
    public int getComboIntervallo() {

        return comboIntervalloFiltra.getSelectedIndex();
    }

    /**
     * Metodo che imposta tutti i JTextField ai valori di default: textAmmontare e
     * textDescrizione vengono impostati a stringhe vuote, mentre textData viene
     * impostato alla data del giorno corrente in formato gg-mm-aaaa.
     * @see java.text.SimpleDateFormat
     */
    public void resetInputs() {
        //Imposta il formato della data e crea un'istanza di Calendar alla data di oggi.
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        //Setta textData alla data di oggi.
        textData.setText(sdf.format(calendar.getTime()));

        //Setta textAmmontare e textDescrizione a stringhe vuote.
        textAmmontare.setText("");
        textDescrizione.setText("");
    }

    /**
     * Metodo che, quando chiamato, inverte lo status degli elementi presenti in questo Pannello,
     * escluso bottoneFiltra. Questo metodo viene utilizzato per impedire altre operazioni
     * quando è attivo un filtro sulla tabella. Lo status attuale dei bottoni (attivi o meno)
     * viene rilevato, invertito logicamente e riassegnato a ciascun bottone, per cui è possibile realizzare
     * un meccanismo di flip-flop senza alcuna variabile o senza alcun parametro.
     */
    public void switchGreyOut() {

        bottoneAggiungi.setEnabled(!bottoneAggiungi.isEnabled());
        bottoneModifica.setEnabled(!bottoneModifica.isEnabled());
        bottoneCerca.setEnabled(!bottoneCerca.isEnabled());
        textFiltra.setEnabled(!textFiltra.isEnabled());
        comboIntervalloFiltra.setEnabled(!comboIntervalloFiltra.isEnabled());
    }


    /**
     * Metodo che associa un {@link ActionListener} al JButton bottoneAggiungi.
     * @param addListener evento avviato dalla pressione del JButton.
     */
    public void addAddListener(ActionListener addListener) {

        bottoneAggiungi.addActionListener(addListener);
    }

    /**
     * Metodo che associa un {@link ActionListener} al JButton bottoneModifica.
     * @param editListener evento avviato dalla pressione del JButton.
     */
    public void addEditListener(ActionListener editListener) {

        bottoneModifica.addActionListener(editListener);
    }

    /**
     * Metodo che associa un {@link ActionListener} al JButton bottoneCerca.
     * @param searchListener evento avviato dalla pressione del JButton.
     */
    public void addSearchListener(ActionListener searchListener) {

        bottoneCerca.addActionListener(searchListener);
    }

    /**
     * Metodo che associa un {@link ActionListener} al JButton bottoneFiltra.
     * @param filterListener evento avviato dalla pressione del JButton.
     */
    public void addFilterListener(ActionListener filterListener) {

        bottoneFiltra.addActionListener(filterListener);
    }
}