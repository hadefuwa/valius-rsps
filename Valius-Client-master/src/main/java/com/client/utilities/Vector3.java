package com.client.utilities;

import com.google.common.primitives.Ints;

import lombok.EqualsAndHashCode;

/**
 * Holds a 3-tuple vector.
 * 
 * @author ReverendDread
 * Sep 5, 2018
 */

@EqualsAndHashCode
public class Vector3 {

	/**
	 * The position of the vector.
	 */
	public double x, y, z;
	
	/**
	 * Constructs a Vector3f from the desired coordinates.
	 * @param x
	 * 			the x coordinate.
	 * @param y
	 * 			the y coordinate.
	 * @param z
	 * 			the z coordinate.
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Constructs a Vector3f from the desired Vector3f.
	 * @param vector
	 * 			the Vector3f containing the initialization x y z data
	 */
	public Vector3(Vector3 vector) {
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
	}
	
	/**
	 * Constructs a Vector3f from the desired coordinates.
	 * @param coordinates
	 * 			the array of length 3 containing xyz in order			
	 */
	public Vector3(double[] coordinates) {
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
	public Vector3 translate(double x, double y, double z) {
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
	public void set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static Vector3 of(double x, double y, double z) {
		return new Vector3(x, y, z);
	}


	public Vector3 add(Vector3 toAdd) {
		return Vector3.of(this.x + toAdd.x, this.y + toAdd.y, this.z + toAdd.z);
	}

	public static Vector3 parse(String str) {
		String[] parts = str.split(",");
		if(parts.length == 3) {
			return Vector3.of(Ints.tryParse(parts[0]), Ints.tryParse(parts[1]), Ints.tryParse(parts[2]));
		}
		return Vector3.of(0, 0, 0);
	}
	
	@Override
	public String toString() {
		return x + ", " + y + ", " + z;
	}
	
	public int getX() {
		return (int) Math.ceil(x);
	}
	
	public int getY() {
		return (int) Math.ceil(y);
	}
	
	public int getZ() {
		return (int) Math.ceil(z);
	}
	
}
