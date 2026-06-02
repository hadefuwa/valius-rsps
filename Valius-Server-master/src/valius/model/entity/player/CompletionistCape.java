package valius.model.entity.player;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class CompletionistCape {
	
	private static final int UNTRIMMED_CAPE = 33019;
	private static final int TRIMMED_CAPE = 33020;
	
	@Getter
	private static final int[] DEFAULT_COLOURS = {65214, 65200, 65186, 62995};
	
	private final Player player;
	
	@Getter @Setter
	private int[] overrides;

	public void setColours(int detailTop, int backgroundTop, int detailBottom, int backgroundBottom) {
		overrides = new int[] {detailTop, backgroundTop, detailBottom, backgroundBottom};
	}
	
	public boolean wearingCape() {
		return player.getItems().isWearingAnyItem(UNTRIMMED_CAPE, TRIMMED_CAPE);
	}

	public boolean coloursNotDefault() {
		return !Arrays.equals(overrides, DEFAULT_COLOURS);
	}

	public void forEach(Consumer<Integer> consumer) {
		IntStream.of(DEFAULT_COLOURS).forEach(consumer::accept);
		IntStream.of(overrides).forEach(consumer::accept);
	}
	
	public void sendColours() {
		if(overrides == null)
			return;
		player.getOutStream().writePacketHeader(66);

		IntStream.of(overrides).forEach(player.getOutStream()::writeDWord);
	}
	@Override
	public String toString() {
		return IntStream.of(overrides).mapToObj(String::valueOf).collect(Collectors.joining("\t"));
	}
}
