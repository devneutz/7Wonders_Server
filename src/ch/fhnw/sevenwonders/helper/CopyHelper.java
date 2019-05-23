package ch.fhnw.sevenwonders.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ch.fhnw.sevenwonders.interfaces.IBoard;

/**
 * http://javatechniques.com/blog/faster-deep-copies-of-java-objects/
 * @author joeln
 *
 */
public class CopyHelper {

	/**
     * Returns a copy of the object, or null if the object cannot
     * be serialized.
     */
    public static IBoard copy(IBoard orig) {
    	IBoard obj = null;
        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(orig);
            out.flush();
            out.close();

            // Make an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()));
            obj = (IBoard)in.readObject();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return obj;
    }
}
