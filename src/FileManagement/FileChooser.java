package FileManagement;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import Utils.ExtensionGrab;

/**
 * Classe che estende {@link JFileChooser} e permette di scegliere i file a seconda del FileFilter e,
 * se la finestra è di salvataggio, chiede conferma se si vuole sovrascrivere un file.
 */
public class FileChooser extends JFileChooser {
    
    /**
     * Oggetto {@link FileType} che permette la selezione del tipo del file.
     */
    FileType fileType;

    /**
     * Costruttore della classe {@link FileChooser}
     * 
     * @param fileType tipo di file da scegliere.
     */
    public FileChooser(FileType fileType) {
        this.fileType=fileType;
    }

    @Override
    public void approveSelection(){
        File file = getSelectedFile();

        switch(fileType){
            case CSV:{
                if(ExtensionGrab.getExtension(file) == null || !ExtensionGrab.getExtension(file).equalsIgnoreCase("csv")){
                    file = new File(file.toString() + ".csv");
                }
            } break;
            case PlainText: {
                if(ExtensionGrab.getExtension(file) == null || !ExtensionGrab.getExtension(file).equalsIgnoreCase("txt")){
                    file = new File(file.toString() + ".txt");
                }
            } break;
            case Serial: {
                if(ExtensionGrab.getExtension(file) == null || !ExtensionGrab.getExtension(file).equalsIgnoreCase("bal")){
                    file = new File(file.toString() + ".bal");
                }
            } break;
        }

        if(file.exists() && getDialogType() == SAVE_DIALOG){
            int result = JOptionPane.showConfirmDialog(this,"Sovrascrivere file?","File esistente.",JOptionPane.YES_NO_CANCEL_OPTION);
            switch(result) {
                case JOptionPane.YES_OPTION:
                    super.approveSelection();
                    return;

                case JOptionPane.NO_OPTION:
                    return;

                case JOptionPane.CLOSED_OPTION:
                    return;

                case JOptionPane.CANCEL_OPTION:
                    cancelSelection();
                    return;
            }
        }

        super.approveSelection();
        
    }
}
