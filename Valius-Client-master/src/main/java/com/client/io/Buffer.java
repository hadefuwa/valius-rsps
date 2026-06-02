package com.client.io;

import java.math.BigInteger;

import com.client.collection.Cacheable;
import com.client.collection.DoubleEndedQueue;
import com.client.isaac.ISAACRandomGen;
import com.client.sign.Signlink;

public class Buffer extends Cacheable {

    private static final BigInteger RSA_MODULUS = new BigInteger("113231744792566966668233153140552718806215630012861714609315544614963685486670717622726284444775375044230132523445256175345480009974713841551325089956578052235636990704096826013085472396244300989858839509305267364300906285402924258822250393599022520711952614564075514282600041378609782976087341703499695818663");

    private static final BigInteger RSA_EXPONENT = new BigInteger("65537");

    public static Buffer create() {
        synchronized (nodeList) {
            Buffer stream = null;
            if (anInt1412 > 0) {
                anInt1412--;
                stream = (Buffer) nodeList.popFront();
            }
            if (stream != null) {
                stream.position = 0;
                return stream;
            }
        }
        Buffer buffer = new Buffer();
        buffer.position = 0;
        buffer.payload = new byte[5000];
        return buffer;
    }

    public int getSmart() {
        try {
            // checks current without modifying position
            if (position >= payload.length) {
                return payload[payload.length - 1] & 0xFF;
            }
            int value = payload[position] & 0xFF;

            if (value < 128) {
                return readUnsignedByte();
            } else {
                return readUShortA() - 32768;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return readUShortA() - 32768;
        }
    }

    public static Buffer create(int initialCapacity) {
        synchronized (nodeList) {
            Buffer stream = null;
            if (anInt1412 > 0) {
                anInt1412--;
                stream = (Buffer) nodeList.popFront();
            }
            if (stream != null) {
                stream.position = 0;
                return stream;
            }
        }
        Buffer stream_1 = new Buffer();
        stream_1.position = 0;
        stream_1.payload = new byte[initialCapacity];
        return stream_1;
    }

    public Buffer() {
    }

    public byte[] getData(byte[] buffer2) {
        for (int i = 0; i < buffer2.length; i++)
            buffer2[i] = payload[position++];
        return buffer2;
    }

    public Buffer(byte data[]) {
        payload = data;
        position = 0;
    }

    public void createFrame(int i) {
        payload[position++] = (byte) (i + encryption.getNextKey());
    }

    public void writeWordBigEndian(int i) {
        payload[position++] = (byte) i;
    }

    public final int readTriByte() {
        position += 3;
        return (0xff & payload[position - 3] << 16)
                + (0xff & payload[position - 2] << 8)
                + (0xff & payload[position - 1]);
    }

    public int readShort2() {
        position += 2;
        int i = ((payload[position - 2] & 0xff) << 8)
                + (payload[position - 1] & 0xff);
        if (i > 60000)
            i = -65535 + i;
        return i;

    }

    public int readUSmart2() {
        int baseVal = 0;
        int lastVal = 0;
        while ((lastVal = readUSmart()) == 32767) {
            baseVal += 32767;
        }
        return baseVal + lastVal;
    }

    public String readNewString() {
        int i = position;
        while (payload[position++] != 0)
            ;
        return new String(payload, i, position - i - 1);
    }

    public void writeShort(int i) {
        payload[position++] = (byte) (i >> 8);
        payload[position++] = (byte) i;
    }

    public void method400(int i) {
        payload[position++] = (byte) i;
        payload[position++] = (byte) (i >> 8);
    }

    public void writeDWordBigEndian(int i) {
        payload[position++] = (byte) (i >> 16);
        payload[position++] = (byte) (i >> 8);
        payload[position++] = (byte) i;
    }

    public void writeInt(int i) {
        payload[position++] = (byte) (i >> 24);
        payload[position++] = (byte) (i >> 16);
        payload[position++] = (byte) (i >> 8);
        payload[position++] = (byte) i;
    }

    public void writeLEInt(int j) {
        payload[position++] = (byte) j;
        payload[position++] = (byte) (j >> 8);
        payload[position++] = (byte) (j >> 16);
        payload[position++] = (byte) (j >> 24);
    }

    public void writeQWord(long l) {
        try {
            payload[position++] = (byte) (int) (l >> 56);
            payload[position++] = (byte) (int) (l >> 48);
            payload[position++] = (byte) (int) (l >> 40);
            payload[position++] = (byte) (int) (l >> 32);
            payload[position++] = (byte) (int) (l >> 24);
            payload[position++] = (byte) (int) (l >> 16);
            payload[position++] = (byte) (int) (l >> 8);
            payload[position++] = (byte) (int) l;
        } catch (RuntimeException runtimeexception) {
            Signlink.reporterror("14395, " + 5 + ", " + l + ", "
                    + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    public void writeString(String s) {
        System.arraycopy(s.getBytes(), 0, payload, position, s.length());
        position += s.length();
        payload[position++] = 10;
    }

    public void writeBytes(byte abyte0[], int i, int j) {
        for (int k = j; k < j + i; k++)
            payload[position++] = abyte0[k];
    }

    public void writeByte(int i) {
        payload[position++] = (byte) i;
    }

    public void writeBytes(int i) {
        payload[position - i - 1] = (byte) i;
    }

    public int readUnsignedByte() {
        return payload[position++] & 0xff;
    }

    public byte readSignedByte() {
        return payload[position++];
    }

    public int readShort() {
      
            position += 2;
            return ((payload[position - 2] & 0xff) << 8)
                    + (payload[position - 1] & 0xff);
       
    }

    public int readSignedShort() {
        position += 2;
        int i = ((payload[position - 2] & 0xff) << 8)
                + (payload[position - 1] & 0xff);
        if (i > 32767)
            i -= 0x10000;
        return i;
    }

    public int readUTriByte() {
        position += 3;
        return ((payload[position - 3] & 0xff) << 16)
                + ((payload[position - 2] & 0xff) << 8)
                + (payload[position - 1] & 0xff);
    }

    public int readInt() {
        position += 4;
        return ((payload[position - 4] & 0xff) << 24)
                + ((payload[position - 3] & 0xff) << 16)
                + ((payload[position - 2] & 0xff) << 8)
                + (payload[position - 1] & 0xff);
    }

    public long readLong() {
        long l = readInt() & 0xffffffffL;
        long l1 = readInt() & 0xffffffffL;
        return (l << 32) + l1;
    }

    public String readString() {
        int i = position;
        while (payload[position++] != 10)
            ;
        return new String(payload, i, position - i - 1);
    }

    public byte[] readBytes() {
        int i = position;
        while (payload[position++] != 10)
            ;
        byte abyte0[] = new byte[position - i - 1];
        System.arraycopy(payload, i, abyte0, i - i, position - 1 - i);
        return abyte0;
    }

    public void readBytes(int i, int j, byte abyte0[]) {
        for (int l = j; l < j + i; l++)
            abyte0[l] = payload[position++];
    }

    public void initBitAccess() {
        bitPosition = position * 8;
    }

    public int readBits(int i) {
        int k = bitPosition >> 3;
        int l = 8 - (bitPosition & 7);
        int i1 = 0;
        bitPosition += i;
        for (; i > l; l = 8) {
            i1 += (payload[k++] & anIntArray1409[l]) << i - l;
            i -= l;
        }
        if (i == l)
            i1 += payload[k] & anIntArray1409[l];
        else
            i1 += payload[k] >> l - i & anIntArray1409[i];
        return i1;
    }

    public void finishBitAccess() {
        position = (bitPosition + 7) / 8;
    }

    public int method421() {
            int i = payload[position] & 0xff;
            if (i < 128)
                return readUnsignedByte() - 64;
            else
                return readShort() - 49152;
        
    }

    public int readUSmart() {
        int i = payload[position] & 0xff;
        if (i < 128)
            return readUnsignedByte();
        else
            return readShort() - 32768;
    }
    
	public int readUnsignedIntSmartShortCompat()
	{
		int var1 = 0;

		int var2;
		for (var2 = this.readUSmart(); var2 == 32767; var2 = this.readUSmart())
		{
			var1 += 32767;
		}

		var1 += var2;
		return var1;
	}

    public void doKeys() {
        int i = position;
        position = 0;
        byte abyte0[] = new byte[i];
        readBytes(i, 0, abyte0);
        BigInteger biginteger2 = new BigInteger(abyte0);
        BigInteger biginteger3 = biginteger2.modPow(RSA_EXPONENT, RSA_MODULUS);
        byte abyte1[] = biginteger3.toByteArray();
        position = 0;
        writeWordBigEndian(abyte1.length);
        writeBytes(abyte1, abyte1.length, 0);
    }

    public void writeNegByte(int i) {
        payload[position++] = (byte) (-i);
    }

    public void writeByteS(int j) {
        payload[position++] = (byte) (128 - j);
    }

    public int readUByteA() {
        return payload[position++] - 128 & 0xff;
    }

    public int readNegUByte() {
        return -payload[position++] & 0xff;
    }

    public int readUByteS() {
        return 128 - payload[position++] & 0xff;
    }

    public byte readNegByte() {
        return (byte) (-payload[position++]);
    }

    public byte readByteS() {
        return (byte) (128 - payload[position++]);
    }

    public void writeLEShort(int i) {
        payload[position++] = (byte) i;
        payload[position++] = (byte) (i >> 8);
    }

    public void writeShortA(int j) {
        payload[position++] = (byte) (j >> 8);
        payload[position++] = (byte) (j + 128);
    }

    public void writeLEShortA(int j) {
        payload[position++] = (byte) (j + 128);
        payload[position++] = (byte) (j >> 8);
    }

    public int readLEUShort() {
        position += 2;
        return ((payload[position - 1] & 0xff) << 8)
                + (payload[position - 2] & 0xff);
    }

    public int readUShortA() {
        position += 2;
        return ((payload[position - 2] & 0xff) << 8)
                + (payload[position - 1] - 128 & 0xff);
    }

    public int readLEUShortA() {
        position += 2;
        return ((payload[position - 1] & 0xff) << 8)
                + (payload[position - 2] - 128 & 0xff);
    }

    public int readLEShort() {
        position += 2;
        int j = ((payload[position - 1] & 0xff) << 8)
                + (payload[position - 2] & 0xff);
        if (j > 32767)
            j -= 0x10000;
        return j;
    }

    public int readLEShortA() {
        position += 2;
        int j = ((payload[position - 1] & 0xff) << 8)
                + (payload[position - 2] - 128 & 0xff);
        if (j > 32767)
            j -= 0x10000;
        return j;
    }

    public int readInt2() {
        position += 4;
        return ((payload[position - 2] & 0xff) << 24)
                + ((payload[position - 1] & 0xff) << 16)
                + ((payload[position - 4] & 0xff) << 8)
                + (payload[position - 3] & 0xff);
    }

    public int readIMEInt() {
        position += 4;
        return ((payload[position - 3] & 0xff) << 24)
                + ((payload[position - 4] & 0xff) << 16)
                + ((payload[position - 1] & 0xff) << 8)
                + (payload[position - 2] & 0xff);
    }

    public void method441(int i, byte abyte0[], int j) {
        for (int k = (i + j) - 1; k >= i; k--)
            payload[position++] = (byte) (abyte0[k] + 128);

    }

    public void method442(int i, int offset, byte abyte0[]) {
        for (int k = (offset + i) - 1; k >= offset; k--)
            abyte0[k] = payload[position++];

    }

    public int getLength() {
        return payload.length;
    }

    public byte payload[];
    public int position;
    public int bitPosition;
    public static final int[] anIntArray1409 = {0, 1, 3, 7, 15, 31, 63, 127,
            255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 0x1ffff,
            0x3ffff, 0x7ffff, 0xfffff, 0x1fffff, 0x3fffff, 0x7fffff, 0xffffff,
            0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff, 0x1fffffff, 0x3fffffff,
            0x7fffffff, -1};
    public ISAACRandomGen encryption;
    public static int anInt1412;
    public static final DoubleEndedQueue nodeList = new DoubleEndedQueue();

}
