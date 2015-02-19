/**
 * 
 */
package ofs.messaging;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

import ofs.messaging.Client.Exceptions.MessagePublishingFailedException;

/**
 * @author Ramanan Natarajan
 *         <p>
 *         Description A utility class that contains an assortion of commonly used methods
 */
public class Util {

	/**
	 * Empty constructor
	 */

	public static UUID getUUID() {

		return UUID.randomUUID();

	}

	// toByteArray and toObject are taken from: http://tinyurl.com/69h8l7x
	/**
	 * @param obj
	 *            the object that needs to be converted into byte[]
	 * @return a byte[]
	 * @throws IOException
	 *             Description This method converts an Object to a byte[]
	 */
	public static byte[] toByteArray(Object obj) throws IOException {
		byte[] bytes = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
		} catch (Exception e) {
			//e.printStackTrace();

			throw new MessagePublishingFailedException("Conversion to byte array failed",e);
			
		} finally {

			if (oos != null) {
				oos.close();
			}
			if (bos != null) {
				bos.close();
			}
			obj = null;
		}
		return bytes;
	}

	/**
	 * <p>
	 * Description This method converts a byte[] to an object
	 * 
	 * @param bytes
	 *            that need to be converted into an object
	 * @return an object representation
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * 
	 */
	public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
		Object obj = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			obj = ois.readObject();
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (ois != null) {
				ois.close();
			}
			bytes = null;
		}
		return obj;
	}

	/**
	 * @param bytes
	 *            that requires to be converted into a string
	 * @return String representation of the byte[]
	 *         <p>
	 *         Description Converts a byte[] to String
	 */
	public static String toString(byte[] bytes) {
		return new String(bytes);
	}

}
