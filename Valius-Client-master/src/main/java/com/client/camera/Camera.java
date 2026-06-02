package com.client.camera;

import com.client.utilities.Vector3;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Camera {

	private Vector3 position;
	public double rotation, tilt;
	
	public Camera copy() {
		// TODO Auto-generated method stub
		return Camera
				.builder()
				.position(this.position)
				.tilt(this.tilt)
				.rotation(this.rotation)
				.build();
	}
	
	public int getRotation() {
		return (int) Math.ceil(rotation);
	}
	
	public int getTilt() {
		return (int) Math.ceil(tilt);
	}
	
}
