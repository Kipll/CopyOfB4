package network;

import java.nio.ByteBuffer;

/**
 * 
 * @author Sam Dowell sxd520
 * Class with static methods for converting between byte arrays and in arrays 
 *
 */
public class ByteConversion {

	
	/**
	 * 
	 * @param bytes byte array you wish to be converted to integer array
	 * @return the bytes as an integer array 
	 */
	public static int[] toInts(byte[] bytes){
		int[] ints = new int[bytes.length / 4];
		byte[] temp = new byte[4]; // byte array for one integer;
		ByteBuffer b = ByteBuffer.wrap(temp);
			for (int i = 0; i < bytes.length; i= i + 4) {
					temp[0] = bytes[i];
					temp[1] = bytes[i + 1];
					temp[2] = bytes[i + 2];
					temp[3] = bytes[i + 3];
					ints[i/4] = b.getInt();
					b.clear();
			}
		return ints;	
	}
			

				
	/**
	 * 
	 * @param ints the integer array you wish to be converted to a byte array
	 * @return the integers as a byte array
	 */
	public static byte[] toBytes(int[] ints){
		byte[] bytes = new byte[ints.length * 4];
		ByteBuffer buf = ByteBuffer.wrap(bytes);
		for (int i = 0; i < ints.length; i++) {
			buf.putInt(i*4,ints[i]);
		}
		return bytes;
	}
}
