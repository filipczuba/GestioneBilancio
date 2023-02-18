package FileManagement;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import Utils.ExtensionGrab;

/**
 * Classe che estende {@link FileFilter}, serve per creare filtrare i file di
 * tipo ".csv" tramite il {@link FileChooser}.
 */
public class CSVFilter extends FileFilter {
    
    /**
     * Controlla l'estensione di un oggetto {@link File} e restituisce true se è una cartella o ha estensione ".csv".
     * @param f {@link File} del quale controllare l'estensione.
     * 
     * @return true se ha l'estensione ".csv" o è una cartella (selezionabili), false viceversa.
     */
    @Override
    public boolean accept(File f){
        if (f.isDirectory()) {
            return true;
        }

        String extension = ExtensionGrab.getExtension(f);

        if(extension == null) {
            return false;
        }

        if(extension.equalsIgnoreCase("csv")) {
            return true;
        }

        return false;
    }

    /**
     * Sovrascrive il metodo getDescription() di FileFilter.
     * 
     * @return Descrizione del File Filter.
     */
    @Override
    public String getDescription(){
        return "File CSV (.csv)";
    }


}
