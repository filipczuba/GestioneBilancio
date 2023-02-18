package Utils;

import java.io.File;
import java.io.IOException;

import javax.swing.SwingUtilities;

import Model.Bilancio;

/**
 * Classe che estende la classe {@link java.lang.Thread}, implementando la funzione run, permettendo
 * al programma di salavare automaticamente il contenuto della tabella nel file /data/temp.bal ogni 200 ms.
 */
public class AutoSave extends Thread {
    
    /**
     *Attributo di tipo {@link Bilancio}, sarà quello che verrà salvato automaticamente.
     */
    private Bilancio bilancio;

    /**
     * Costruttore della classe AutoSave, assegna un valore all'attributo bilancio di questa istanza.
     * 
     * @param bilancio oggetto di tipo {@link Bilancio} da assegnare all'attrbibuto bilancio di questa istanza.
     */
    public AutoSave(Bilancio bilancio) {

        this.bilancio=bilancio;
    }


    /**
     * Override del metodo {@link java.lang.Thread#run()}, implementa il salvataggio automatico ogni 200 ms del contenuto di bilancio
     * sul file /data/temp.bal.
     */
    @Override
    public void run() {
        
        while (true) {
            try {

                Thread.sleep(200);

                    SwingUtilities.invokeLater(() -> {
                        try {

                            File file = new File(System.getProperty("user.dir")+"/data/temp.bal");
                            if(!file.exists()) {

                                file.createNewFile();
                            }

                            bilancio.saveFile(file);
                        }
                        catch(IOException ioEx) {

                            ioEx.printStackTrace();
                        }
                        });

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }
}



