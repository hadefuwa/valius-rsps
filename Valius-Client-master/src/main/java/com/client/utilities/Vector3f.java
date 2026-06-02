package com.client.utilities;

/**
 * Holds a 3-tuple vector.
 * 
 * @author ReverendDread
 * Sep 5, 2018
 */
public class Vector3f {

	/**
	 * The position of the vector.
	 */
	public float x, y, z;
	
	/**
	 * Constructs a Vector3f from the desired coordinates.
	 * @param x
	 * 			the x coordinate.
	 * @param y
	 * 			the y coordinate.
	 * @param z
	 * 			the z coordinate.
	 */
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Constructs a Vector3f from the desired Vector3f.
	 * @param vector
	 * 			the Vector3f containing the initialization x y z data
	 */
	public Vector3f(Vector3f vector) {
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
	}
	
	/**
	 * Constructs a Vector3f from the desired coordinates.
	 * @param coordinates
	 * 			the array of length 3 containing xyz in order			
	 */
	public Vector3f(float[] coordinates) {
		this.x = coordinates[0];
		this.y = coordinates[1];
		this.z = coordinates[2];
	}
	
	/**
	 * Translates a vector.
	 * @param x
	 * 			x The translation in x
	 * @param y
	 * 			y The translation in y
	 * @param z
	 * 			z The translation in z
	 * @return
	 * 			the translated vector.
	 */
	public Vector3f translate(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	/**
	 * Sets the vector.
	 * @param x
	 * 			the x coordinate.
	 * @param y
	 * 			the y coordinate.
	 * @param z
	 * 			the z coordinate.
	 */
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static Vector3f of(float x, float y, float z) {
		return new Vector3f(x, y, z);
	}
}
