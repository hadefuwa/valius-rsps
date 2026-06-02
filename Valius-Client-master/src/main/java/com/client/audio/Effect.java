package com.client.audio;

import com.client.io.Buffer;

public final class Effect {

	private Effect() {
		instruments = new Instrument[10];
	}

	public static void unpack(Buffer stream) {
		_output = new byte[0x6baa8];
		output = new Buffer(_output);
		Instrument.initialise();
		do {
			int effect = stream.readShort();
			if (effect == 65535)
				return;
			effects[effect] = new Effect();
			effects[effect].decode(stream);
			effectDelays[effect] = effects[effect].method243();
		} while (true);
	}

	public static Buffer method241(int i, int j) {
		if (effects[j] != null) {
			Effect sounds = effects[j];
			return sounds.encode(i);
		} else {
			return null;
		}
	}

	private void decode(Buffer stream) {
		for (int instrument = 0; instrument < 10; instrument++) {
			int active = stream.readUnsignedByte();
			if (active != 0) {
				stream.position--;
				instruments[instrument] = new Instrument();
				instruments[instrument].decode(stream);
			}
		}
		loopStart = stream.readShort();
		loopEnd = stream.readShort();
	}

	private int method243() {
		int j = 0x98967f;
		for (int k = 0; k < 10; k++)
			if (instruments[k] != null
					&& instruments[k].begin / 20 < j)
				j = instruments[k].begin / 20;

		if (loopStart < loopEnd && loopStart / 20 < j)
			j = loopStart / 20;
		if (j == 0x98967f || j == 0)
			return 0;
		for (int l = 0; l < 10; l++)
			if (instruments[l] != null)
				instruments[l].begin -= j * 20;

		if (loopStart < loopEnd) {
			loopStart -= j * 20;
			loopEnd -= j * 20;
		}
		return j;
	}

	private Buffer encode(int i) {
		int size = mix(i);
		output.position = 0;
		output.writeInt(0x52494646);
		output.writeLEInt(36 + size);
		output.writeInt(0x57415645);
		output.writeInt(0x666d7420);
		output.writeLEInt(16);
		output.method400(1);
		output.method400(1);
		output.writeLEInt(22050);
		output.writeLEInt(22050);
		output.method400(1);
		output.method400(8);
		output.writeInt(0x64617461);
		output.writeLEInt(size);
		output.position += size;
		return output;
	}

	private int mix(int loopCount) {
		int _duration = 0;
		for (int instrument = 0; instrument < 10; instrument++)
			if (instruments[instrument] != null
					&& instruments[instrument].duration
							+ instruments[instrument].begin > _duration)
				_duration = instruments[instrument].duration + instruments[instrument].begin;

		if (_duration == 0)
			return 0;
		int stepCount = (22050 * _duration) / 1000;
		int loopStart = (22050 * this.loopStart) / 1000;
		int loopEnd = (22050 * this.loopEnd) / 1000;
		if (loopStart < 0 || loopStart > stepCount || loopEnd < 0 || loopEnd > stepCount || loopStart >= loopEnd)
			loopCount = 0;
		int length = stepCount + (loopEnd - loopStart) * (loopCount - 1);
		for (int offset = 44; offset < length + 44; offset++)
			_output[offset] = -128;

		for (int instrument = 0; instrument < 10; instrument++)
			if (instruments[instrument] != null) {
				int duration = (instruments[instrument].duration * 22050) / 1000;
				int offset = (instruments[instrument].begin * 22050) / 1000;
				int samples[] = instruments[instrument].synthesize(duration,
						instruments[instrument].duration);
				for (int sample = 0; sample < duration; sample++)
					_output[sample + offset + 44] += (byte) (samples[sample] >> 8);

			}

		if (loopCount > 1) {
			loopStart += 44;
			loopEnd += 44;
			stepCount += 44;
			int offset = (length += 44) - stepCount;
			for (int step = stepCount - 1; step >= loopEnd; step--)
				_output[step + offset] = _output[step];

			for (int loop = 1; loop < loopCount; loop++) {
				int _offset = (loopEnd - loopStart) * loop;
				System.arraycopy(_output, loopStart, _output, loopStart + _offset, loopEnd
						- loopStart);

			}

			length -= 44;
		}
		return length;
	}

	private static final Effect[] effects = new Effect[5000];
	public static final int[] effectDelays = new int[5000];
	private static byte[] _output;
	private static Buffer output;
	private final Instrument[] instruments;
	private int loopStart;
	private int loopEnd;

}
