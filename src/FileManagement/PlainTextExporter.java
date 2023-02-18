package FileManagement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.table.TableModel;


/**
 * Classe che estende la classe {@link Exporter} e definisce il metodo exportFile() per esportare i file di testo.
 */
public class PlainTextExporter extends Exporter { 

    /**
    * Costruttore della classe {@link CSVExporter}, utilizza il costruttore di {@link Exporter}.
    * @param tableModel {@link TableModel} da assegnare all'attrbiuto tableModel.
    * @param file {@link File} da assegnare all'atributo file.
    */
    public PlainTextExporter(TableModel tableModel, File file) {
        super(tableModel, file);
    }

    /**
    * Classe per l'esportazione dei file di testo.
    * 
    * @throws IOException se non è valido il file, non è sufficiente lo spazio o il programma non ha accesso al file system.
    */
    @Override
    public void exportFile() throws IOException {
        
            FileWriter TextWriter;
            try{
                //Apre il FileWriter
                TextWriter = new FileWriter(file);

                //Nella prima riga scrive i nomi delle colonne, separandoli con uno spazio.
                for(int i =0; i<tableModel.getColumnCount() ; i++) {

                    TextWriter.append((tableModel.getColumnName(i)));
                    TextWriter.append(' ');
                }

                //A capo
                TextWriter.append("\n");

                //Salva ogni voce su ogni riga, salvando Data, Descrizione e Ammontare,
                //separandoli con uno spazio e mandando a capo a ogni voce.
                for(int i = 0 ; i < tableModel.getRowCount(); i++) {
                    
                    for(int j = 0 ; j < tableModel.getColumnCount(); j++) {

                        TextWriter.append(tableModel.getValueAt(i,j).toString());
                        TextWriter.append(' ');
                    }

                    TextWriter.append("\n");
                }

                //Chiude il fileWriter.
                TextWriter.flush();
                TextWriter.close();
                
            } catch(IOException e) {
                e.printStackTrace();
            }
    }
    
}