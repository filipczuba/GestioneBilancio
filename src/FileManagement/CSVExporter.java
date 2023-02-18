package FileManagement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.table.TableModel;

/**
 * Classe che estende la classe {@link Exporter} e definisce il metodo exportFile() per esportare i file CSV.
 */
public class CSVExporter extends Exporter{
    
    /**
     * Costruttore della classe {@link CSVExporter}, utilizza il costruttore di {@link Exporter}.
     * @param tableModel {@link TableModel} da assegnare all'attrbiuto tableModel.
     * @param file {@link File} da assegnare all'atributo file.
     */
    public CSVExporter(TableModel tableModel, File file) {
        super(tableModel, file);
    }

    /**
     * Classe per l'esportazione dei file CSV.
     * 
     * @throws IOException se non è valido il file, non è sufficiente lo spazio o il programma non ha accesso al file system.
     */
    @Override
    public  void exportFile() throws IOException {
        
        FileWriter CSVWriter;
        try {

            //Apre il FileWriter
            CSVWriter = new FileWriter(file);

            //Nella prima riga scrive i nomi delle colonne, separandoli con ";".
            for(int i =0; i<tableModel.getColumnCount() ; i++) {

                CSVWriter.append((tableModel.getColumnName(i)));

                if(i!=tableModel.getColumnCount()-1) {

                    CSVWriter.append(';');
                }
            }

            //A capo
            CSVWriter.append("\n");

            //Salva ogni voce su ogni riga, salvando Data, Descrizione e Ammontare,
            //separandoli con ";" e mandando a capo a ogni voce.
            for(int i = 0 ; i< tableModel.getRowCount(); i++) {

                for(int j = 0 ; j < tableModel.getColumnCount(); j++) {

                    CSVWriter.append(tableModel.getValueAt(i,j).toString());

                    if(j!=tableModel.getColumnCount()-1) {

                        CSVWriter.append(';');
                    }
                }

                CSVWriter.append("\n");
            }

            //Chiude il fileWriter.
            CSVWriter.flush();
            CSVWriter.close();
            
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    
}
