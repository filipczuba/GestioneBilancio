package Utils;

import java.io.File;


/**
 * Classe che si occupa di fornire un metodo statico per ottenere l'estensione di un file sotto forma di stringa.
 * 
 * @author Filip Czuba
 * @version 1.0
 */
public class ExtensionGrab {


    /**
     * Metodo statico che restituisce il tipo di file sotto forma di stringa.
     * 
     * @param f file ti cui si vuole avere l'estensione.
     * @return l'estensione sotto forma di {@link String} priva di punto.
     */
    public static String getExtension(File f){
        String extension = null;
        String string = f.getName();
        int index = string.lastIndexOf('.');

        if(index > 0 && index < string.length()-1){
            extension = string.substring(index+1).toLowerCase();
        }

        return extension;
    }
}
