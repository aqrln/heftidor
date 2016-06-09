package ninja.aqrln.editor.io;

import ninja.aqrln.editor.dom.Document;
import ninja.aqrln.editor.io.xml.Reader;
import ninja.aqrln.editor.io.xml.Writer;

import javax.swing.JOptionPane;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handles reading and writing documents
 * @author Alexey Orlenko
 */
public class DocumentSerializer {

    /**
     * Save document to a file
     * @param document document to save
     * @param filename filename
     */
    public static void save(Document document, String filename) {
        String oldName = document.getName();
        document.setName(getDocumentName(filename));

        try (FileOutputStream stream = new FileOutputStream(filename)) {
            Writer encoder = new Writer(document);
            encoder.write(stream);
        } catch (IOException ioError) {
            document.setName(oldName);

            JOptionPane.showMessageDialog(null, "Cannot save to file " + filename, "Error", JOptionPane.ERROR_MESSAGE);
            ioError.printStackTrace();
        }
    }

    /**
     * Load document from a file
     * @param filename filename
     * @return Document instance
     */
    public static Document load(String filename) {
        Document document = null;

        try (FileInputStream stream = new FileInputStream(filename)) {
            Reader decoder = new Reader(stream);
            document = decoder.read();
        } catch (IOException ioError) {
            JOptionPane.showMessageDialog(null, "Cannot load file " + filename, "Error", JOptionPane.ERROR_MESSAGE);
            ioError.printStackTrace();
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
