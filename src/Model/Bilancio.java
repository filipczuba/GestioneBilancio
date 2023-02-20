package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

/**
 * Classe che rappresenta il bilancio.
 * Il bilancio è rappresentato tramite un {@link ArrayList} di {@link Voce}. Sono presenti i metodi tramite i 
 * quali è possibile aggiungere, rimuovere e modificare le voci, calcolare l'ammontare totale, cercare 
 * una voce, serializzare il bilancio e caricarlo da file.
 * 
 * @author Filip Czuba
 * @version 1.1
 */
public class Bilancio {
    

    /**
     * {@link ArrayList} di oggetti di tipo {@link Voce}.
     */
    private ArrayList<Voce> Bilancio;


    /**
     *  Costruttore vuoto per la classe Bilancio, istanzia l'ArrayList a una ArrayList vuota.
     */
    public Bilancio() {

        Bilancio = new ArrayList<Voce>();
    }

    /**
     * Costruttore per la classe Bilancio.
     * 
     * @param bilancio ArrayList di oggetti Voce da assegnare a questa istanza.
     */
    public Bilancio(ArrayList<Voce> bilancio) {
        Bilancio = bilancio;
    }


    /**
     * Aggiunge in coda un oggetto di tipo {@link Voce}.
     * 
     * @param voce parametro da aggiungere.
     */
    public void aggiungiVoce(Voce voce){
        Bilancio.add(voce);
    }


    /**
     * Rimuove l'oggetto {@link Voce} in una data posizione.
     * 
     * @param index indice dell'oggetto da rimuovere.
     */
    public void rimuoviVoce(int index){
        Bilancio.remove(index);
    }

    /**
     * Modifica un oggetto {@link Voce} in una data posizione, sostituendolo con un altro oggetto Voce.
     * @param index indice dell'oggetto da modificare.
     * @param nuovaVoce oggetto di tipo {@link Voce}, il quale sostituirà l'oggetto indicato.
     */
    public void modificaVoce(int index, Voce nuovaVoce){
        Bilancio.set(index,nuovaVoce);
    }

    /**
     * Calcola l'ammontare totale delle Voci all'interno di {@link Bilancio}
     * @return totale degli attributi Ammontare delle istanze di {@link Voce} nell'{@link ArrayList}
     */
    public Double calcolaTotale(){
        Double totale=0.0;

        for(Voce voce : Bilancio){
            totale+=voce.getAmmontare();
        }

        return totale;
    }

    /**
     * Restituisce l'indice del primo oggetto {@link Voce} successivo all'indice d'inizio che contiene nella sua descrizione una stringa specificata.
     * Se non viene trovato alcun oggetto che contiene la stringa, restituisce -1. La ricerca è svolta in modo circolare.
     * 
     * @param query stringa da cercare all'interno degli oggetti contenuti nell'{@link ArrayList}
     * @param startIndex indice da cui parte la ricerca.
     * @return indice in cui si trova l'oggetto trovato, -1 se non esiste alcun oggetto.
     */
    public int cercaVoce(String query, int startIndex) {
        int currentIndex = (startIndex+1)%Bilancio.size();
        do {
            if (Bilancio.get(currentIndex).getDescrizione().contains(query)) {
                return currentIndex;
            }
            currentIndex = (currentIndex + 1) % Bilancio.size();
        } while (currentIndex != startIndex);

        return -1;
    }

    /**
     * Getter dell'attributo Bilancio.
     * 
     * @return {@link ArrayList} di oggetti di tipo {@link Voce}
     */
    public ArrayList<Voce> getData(){
        return Bilancio;
    }

    /**
     * Setter dell'attributo Bilancio.
     * 
     * @param data {@link ArrayList} da assegnare all'attributo Bilancio di questa istanza.
     */
    public void setData(ArrayList<Voce> data){
        Bilancio = data;
    }

    /**
     * Serializza e salva su file il contenuto di {@link Bilancio}
     * @param file indica dove verrà salvato il seriale.
     * @throws IOException se l'oggetto {@link File} non è corretto, non c'è abbastanza spazio in memoria o il programma non ha i permessi per accedere allo spazio su disco.
     */
    public void saveFile(File file)throws IOException{
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        Voce[] arrayBilancio = Bilancio.toArray(new Voce[Bilancio.size()]);

        oos.writeObject(arrayBilancio);

        oos.close();
        fos.close();
    }

    /**
     * Carica da file il contenuto di {@link Bilancio}
     * @param file indica da quale file verranno caricati i dati.
     * @throws IOException se l'oggetto {@link File} non esiste, non può essere aperto, non è valido o lo stream è stato chiuso.
     */
    public void loadFile (File file) throws IOException{
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);

        try{
            Voce[] arrayBilancio = (Voce[])ois.readObject();

            Bilancio.clear();

            Bilancio.addAll(Arrays.asList(arrayBilancio));
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        ois.close();
        fis.close();
    }

}