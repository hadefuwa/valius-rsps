package com.client.graphics.loaders;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * Decodes an encoded file and maps file names to positions within the file
 * @author James
 *
 */
public class ImageArchive {
	
	private static final Gson GSON = new Gson();
	
	/**
	 * Creates a new instance of ImageArchive and attempts to decode the provided file
	 * @param encodedFile The encoded file as a File
	 * @param encryptionKey
	 * @throws IOException If the file could not be read or decoded
	 */
	public ImageArchive(File encodedFile, String encryptionKey) throws Exception {
		this.fileChannel = FileChannel.open(encodedFile.toPath(), StandardOpenOption.READ);
		ByteBuffer dataBuffer = ByteBuffer.allocate((int) fileChannel.size());
		fileChannel.read(dataBuffer);
		dataBuffer.flip();
		if(encryptionKey.isEmpty())
			decode(dataBuffer);
		else {
			byte[] data = new byte[dataBuffer.limit()];
			dataBuffer.get(data);
			
			byte[] decrypted = decrypt(data, encryptionKey);
			dataBuffer = ByteBuffer.wrap(decrypted);
			decode(dataBuffer);
		}
	}
	
	/**
	 * Attempts to decrypt the files bytes using the provided key
	 * @param in The encrypted bytes
	 * @param keyString The key to decrypt the bytes
	 * @return The decrypted bytes
	 * @throws Exception If there was an error while decrypting
	 */
	private byte[] decrypt(byte[] in, String keyString) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		byte[] key = Arrays.copyOf(sha.digest(keyString.getBytes("UTF-8")), 16);
		SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(in);
	}

	/**
	 * Reads a header json file and maps it into a list of {@link ImageInfo} 
	 * @param buffer The file data as a ByteBuffer
	 * @throws IOException If the file cannot be found
	 */
	private void decode(ByteBuffer buffer) throws IOException {
		this.dataBuffer = buffer;
		int version = buffer.get();//For future use
		int length = buffer.getInt();
		byte[] jsonData = new byte[length];
		buffer.get(jsonData);

		basePosition = buffer.position();
		try(InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(jsonData))){
			spriteData = new ArrayList<>();
			spriteData.addAll(GSON.fromJson(reader, new TypeToken<List<ImageInfo>>() { }.getType()));
			System.out.println("Loaded " + spriteData.size() + " image definitions");
		}
		
	}
	
	/**
	 * Returns either cached data for the given filename, otherwise attempts to load.
	 * @param name
	 * @return The image data as PNG, if it exists, otherwise {@code null}
	 */
	public byte[] readImage(String name) {
		Optional<ImageInfo> found = spriteData
				.stream()
				.filter(encoded -> encoded.fileName.equalsIgnoreCase(name))
				.findFirst();
		if(found.isPresent()) {
			try {
				return dataCache.getOrDefault(found.get(), loadFromDisk(found.get()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Attempts to load the sprite data from the ByteBuffer stored in memory
	 * @param encodedSprite The encoded sprite data
	 * @return The image data as PNG, otherwise null
	 * @throws IOException If there was a problem reading the file from disk
	 */
	private byte[] loadFromDisk(ImageInfo encodedSprite) throws IOException {
		dataBuffer.position(basePosition + encodedSprite.offset);
		byte[] data = new byte[encodedSprite.length];
		dataBuffer.get(data, 0, encodedSprite.length);
		dataCache.put(encodedSprite, data);
		return data;

	}
	
	
	/**
	 * The list of encoded sprite data objects
	 */
	private List<ImageInfo> spriteData;
	
	/**
	 * A map of cached data, limited to a size of 15
	 */
	private LinkedHashMap<ImageInfo, byte[]> dataCache = new LinkedHashMap<ImageInfo, byte[]>() {
	
		private static final long serialVersionUID = -2905070956991408849L;

		@Override
	    protected boolean removeEldestEntry(Map.Entry<ImageInfo, byte[]> eldest) {
	        return size() > 15;
	    }
	};
	
	private int basePosition;

	/**
	 * The file channel for the encoded file
	 */
	private FileChannel fileChannel;
	
	/**
	 * A ByteBuffer wrapping the data contained in the encoded file. Only non-null if LoadType is set to PRELOAD
	 */
	private ByteBuffer dataBuffer;
	
	
	/**
	 * Represents a data object that contains a file name, an offset within the encoded file and a length
	 * @author James
	 */
	private static class ImageInfo {
		private String fileName;
		private int offset;
		private int length;
		
	}
}
