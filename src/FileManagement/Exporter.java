package FileManagement;

import java.io.File;
import java.io.IOException;

import javax.swing.table.TableModel;

/**
 * Classe astratta che definisce il template per tutte le classi che si occupano dell'esportazione dei dati su file.
 */
public abstract class Exporter {
    
    /**
     * Oggetto {@link TableModel} da cui vengono presi i dati per l'esportazione.
     * Protected in modo tale che possano accedervi le classi che ereditano da questa.
     */
    protected TableModel tableModel;

    /**
     * Oggetto {@link File} che indica dove verrà salvato il file.
     * Protected in modo tale che possano accedervi le classi che ereditano da questa.
     */
    protected File file;

    /**
     * Costruttore della classe Exporter.
     * 
     * @param tableModel {@link TableModel} da assegnare all'attrbiuto tableModel.
     * @param file {@link File} da assegnare all'atributo file.
     */
    public Exporter(TableModel tableModel, File file) {
        this.tableModel=tableModel;
        this.file=file;
    }

    /**
     * Classe astratta per l'esportazione dei file. Le classi figlie di {@link Exporter} dovranno ridefinirla
     * per lo specifico tipo di file di cui si occupano.
     * @throws IOException se non è valido il file, non è sufficiente lo spazio o il programma non ha accesso al file system.
     */
    public abstract void exportFile() throws IOException;
}

