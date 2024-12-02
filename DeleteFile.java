import javax.swing.*;
import java.nio.file.*;

public class DeleteFile {
    public static void apagarArquivo() {
        try {
            JFileChooser chooser = new JFileChooser();

            chooser.setDialogTitle("Selecione o arquivo que deseja apagar");
            chooser.setApproveButtonText("Apagar arquivo");
            int returnVal = chooser.showOpenDialog(null);
            String fileFullPath = "";
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                fileFullPath = chooser.getSelectedFile().getAbsolutePath();
            } else {
                System.out.println("O usuário não selecionou o arquivo.");
            }

            Path pathOrigin = Paths.get(fileFullPath);
            Files.delete(pathOrigin);
            System.out.println("Arquivo apagado com sucesso!");
        } catch (Exception e) {
            System.out.println("Não foi possível apagar o arquivo.");
        }
    }

    public static void main(String[] args) throws Exception {
        apagarArquivo();
    }
}
