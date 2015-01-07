package ofs.messaging;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Test;

/**
 * 
 * @author Ramanan Natarajan Only the set and get methods for binary data have been
 *         tested as to make sure that they are deep copied. All other cases are
 *         trivial and hence not covered
 *
 */
public class TestPayload {

	@Test
	public final void testGetbData() throws IOException {
		// fail("Not yet implemented");

		Payload p = new Payload();
		p.setPayLoadFormat(PayloadFormat.BINARY);

		Path path = Paths.get("IMG_0895.MOV");
		byte[] data = null;

		data = Files.readAllBytes(path);
		p.setbData(data);

		// reading back from payload object and comparing to what was read
		// comparing data got from the getMethod with originalData
		boolean result = Arrays.equals(p.getbData(), data);
		assertEquals(result, true);

	}

	@Test
	public final void testSetbData() throws IOException {
		Payload p = new Payload();
		p.setPayLoadFormat(PayloadFormat.BINARY);

		Path path = Paths.get("IMG_0895.MOV");
		byte[] data = null;

		data = Files.readAllBytes(path);
		p.setbData(data);

		assertEquals(Arrays.equals(p.getbData(), data), true);

	}

}
