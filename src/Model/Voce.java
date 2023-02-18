package Model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Classe che rappresenta una voce del bilancio.
 * Contiene una data in formato gg-mm-aaaa, una descrizione e l'ammontare.
 * La classe fornisce getter e setter per tutti gli attributi, così come per ottenere la data in formato String
 * e un metodo toString() per l'oggetto Voce stesso.
 * 
 * @author Filip Czuba
 * @version 1.0
 */
public class Voce implements Serializable{

    /**
     *Attributo di tipo {@link Date}, rappresenta la data in cui è avvenuta una transazione.
     */
    private Date data;
    /**
     *Attributo di tipo {@link String}, rappresenta la descrizione di una data transazione.
     */
    private String descrizione;
    /**
     *Attributo di tipo {@link Double}, rappresenta il valore pecuniario di una transazione.
     */
    private Double ammontare;

    /**
     * Costruttore della classe Voce.
     * 
     * @param data la data da assegnare a questa istanza.
     * @param descrizione la descrizione da assegnare a questa istanza.
     * @param ammontare l'ammontare da assegnare a questa istanza.
     */
    public Voce(Date data, String descrizione, Double ammontare) {
        this.data=data;
        this.descrizione = descrizione;
        this.ammontare = ammontare;
    }

    /**
     * Getter dell'attributo Data.
     * 
     * @return data in formato gg-mm-aaaa di tipo Date.
     * @see java.text.SimpleDateFormat
     */
    public Date getData() {
        return data;
    }

    /**
     * Restituisce la data come String.
     * 
     * @return Data dell'oggetto Voce di tipo String nel formato gg-mm-aaaa.
     * @see java.text.SimpleDateFormat
     */
    public String getDataFormattata(){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(data);
    }

    /**
     * Setter dell'attributo Data.
     * 
     * @param data variabile di tipo Date che rappresenta la data.
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * Setter dell'attributo Data.
     * 
     * @param data variabile di tipo String che rappresenta la data in formato gg-mm-aaaa.
     * @see java.text.SimpleDateFormat
     */
    public void setData(String data){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            this.data = format.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter dell'attributo Descrizione.
     * 
     * @return contenuto dell'attributo Descrizione.
     */
    public String getDescrizione() {
        return descrizione;
    }
    
    /**
     * Setter dell'attributo Descrizione.
     * 
     * @param descrizione oggetto di tipo String da assegnare all'attributo Descrizione.
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Getter dell'attributo Ammontare.
     * 
     * @return contenuto dell'attributo Ammontare.
     */
    public Double getAmmontare() {
        return ammontare;
    }


    /**
     * Setter dell'attributo Ammontare
     * 
     * @param ammontare oggetto di tipo Double da assegnare all'attributo Ammontare.
     */
    public void setAmmontare(Double ammontare) {
        this.ammontare = ammontare;
    }


    
    /**
     * Restituisce una rappresentazione testuale dell'oggetto di tipo {@link Voce}, override
     * del metodo {@link java.lang.Object#toString()}.
     * 
     * @return rappresentazione dell'oggetto di tipo String.
     */
    @Override
    public String toString() {
        return (this.getDataFormattata()+" "+this.getDescrizione()+" "+this.getAmmontare().toString());
    }
}
