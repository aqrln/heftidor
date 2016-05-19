package ninja.aqrln.editor.io;

import ninja.aqrln.editor.dom.Document;

import javax.swing.JOptionPane;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Alexey Orlenko
 */
public class DocumentSerializer {
    public static void save(Document document, String filename) {
        String oldName = document.getName();
        document.setName(getDocumentName(filename));

        try (FileOutputStream fileStream = new FileOutputStream(filename);
             ObjectOutputStream stream = new ObjectOutputStream(fileStream)) {
            stream.writeObject(document);
        } catch (IOException ioError) {
            document.setName(oldName);

            JOptionPane.showMessageDialog(null, "Cannot save to file " + filename, "Error", JOptionPane.ERROR_MESSAGE);
            ioError.printStackTrace();
        }
    }

    public static Document load(String filename) {
        Document document = null;

        try (FileInputStream fileStream = new FileInputStream(filename);
             ObjectInputStream stream = new ObjectInputStream(fileStream)) {
            document = (Document) stream.readObject();
        } catch (IOException ioError) {
            JOptionPane.showMessageDialog(null, "Cannot load file " + filename, "Error", JOptionPane.ERROR_MESSAGE);
            ioError.printStackTrace();
        } catch (ClassNotFoundException classError) {
            JOptionPane.showMessageDialog(null, "Internal error happened", "Error", JOptionPane.ERROR_MESSAGE);
            classError.printStackTrace();
        }

        if (document != null) {
            document.setName(getDocumentName(filename));
        }

        return document;
    }

    private static String getDocumentName(String filename) {
        Path path = Paths.get(filename);
        String file = path.getFileName().toString();
        return file.substring(0, file.lastIndexOf('.'));
    }
}
