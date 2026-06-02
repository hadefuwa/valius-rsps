/**
 * 
 */
package valius.util.compress;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.zip.GZIPInputStream;

/**
 * @author ReverendDread
 * Aug 4, 2019
 */
public class GZIPUtil {
	
	public static byte[] decompress(File f) throws Exception {
		if (!f.exists()) {
			return null;
		}
		byte[] buffer = new byte[(int) f.length()];
		try (DataInputStream dis = new DataInputStream(new FileInputStream(f))) {
			dis.readFully(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] gzipInputBuffer = new byte[999999];
		int bufferlength = 0;
		try (GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(buffer))) {
			do {
				if (bufferlength == gzipInputBuffer.length) {
					System.out.println("Error inflating data.\nGZIP buffer overflow.");
					break;
				}
				int readByte = gzip.read(gzipInputBuffer, bufferlength, gzipInputBuffer.length - bufferlength);
				if (readByte == -1)
					break;
				bufferlength += readByte;
			} while (true);
			byte[] inflated = new byte[bufferlength];
			System.arraycopy(gzipInputBuffer, 0, inflated, 0, bufferlength);
			buffer = inflated;
			if (buffer.length < 10) {
				return null;
			}
		}
		return buffer;
	}
	
}
