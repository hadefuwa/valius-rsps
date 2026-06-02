package com.client.camera;

import com.client.utilities.Vector3;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class CameraFrame {
	
	@Setter
	private boolean moveScene;
	
	private Vector3 targetLocation;
	private Vector3 jumpLocation;
	
	@Builder.Default
	public int rotation = -1;
	@Builder.Default
	public int tilt = -1;

	@Builder.Default
	private int frames = 500;
	
	@Setter
	private int start;
	
	public boolean hasFrame(int frameNum) {
		return frameNum >= start && frameNum <= getEnd();
	}

	public int getEnd() {
		return start + frames;
	}

}
