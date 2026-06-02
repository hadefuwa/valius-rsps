package valius.cache.definitions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import valius.util.Buffer;

@Slf4j	
public class IdentityKit {

	public static void unpack() throws IOException {
		Buffer stream = new Buffer(Files.readAllBytes(new File("./Data/data/idk.dat").toPath()));
		length = stream.readUnsignedWord();
		if (cache == null)
			cache = new IdentityKit[length];
		for (int j = 0; j < length; j++) {
			if (cache[j] == null)
				cache[j] = new IdentityKit();
			cache[j].id = j;
			cache[j].readValues(stream);
		}
		log.info("Loaded {} IdentityKits", length);
	}

	private void readValues(Buffer stream) {
		do {
			int i = stream.readUnsignedByte();
			if (i == 0)
				return;
			if (i == 1)
				bodyPartId = stream.readUnsignedByte();
			else if (i == 2) {
				int j = stream.readUnsignedByte();
				for (int k = 0; k < j; k++)
					stream.readUnsignedWord();
			} else if (i == 3)
				nonSelectable = true;
			else if (i == 40) {
				int length = stream.readUnsignedByte();
				for(int idx = 0;idx<length;idx++) {
					stream.readUnsignedWord();
					 stream.readUnsignedWord();
				}
			} else if (i == 41) {
				int length = stream.readUnsignedByte();
				for(int idx = 0;idx<length;idx++) {
					stream.readUnsignedWord();
					stream.readUnsignedWord();
				}
			} else if (i >= 60 && i < 70) {
				stream.readUnsignedWord();		
			}
			else
				System.out.println("Error unrecognised IDK config code: " + i);
		} while (true);
	}
	
	private static List<IdentityKit> matchingKit(int bodyPart){
		
		return Stream.of(cache)
				.filter(Objects::nonNull)
				.filter(idk -> idk.bodyPartId == bodyPart)
				.collect(Collectors.toList());
	}
	
	public static boolean isValid(int bodyPart, int id) {
		List<IdentityKit> bodyKits = matchingKit(bodyPart);
		
		return bodyKits.stream().anyMatch(idk -> idk.id == id);
	}
	
	public static Optional<IdentityKit> getDefault(int bodyPart) {
		return matchingKit(bodyPart).stream().findFirst();
	}


	private IdentityKit() {
		bodyPartId = -1;
		nonSelectable = false;
	}

	
	public static int length;
	public static IdentityKit cache[];
	public int id;
	public int bodyPartId;
	public boolean nonSelectable;

}
