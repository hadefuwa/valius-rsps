package com.client.camera;

import java.util.List;
import java.util.Optional;

import com.client.login.CinematicScene;
import com.client.utilities.Vector3;
import com.google.common.collect.Lists;

import lombok.Getter;

public class CameraMove {
	
	private Thread applyThread;
	private int currentFrameNumber;

	private int maximumFrame;
	
	@Getter
	private Camera camera, baseCamera, defaultCamera;
	private CameraFrame currentFrame;
	private List<CameraFrame> frames = Lists.newArrayList();
	
	private void getNextFrame(int frameNum) {
		Optional<CameraFrame> frameOpt = 
				frames
				.stream()
				.filter(frame -> frame.hasFrame(frameNum))
				.findFirst();
		if(frameOpt.isPresent()) {
			baseCamera = camera.copy();
			currentFrame = frameOpt.get();
		}
	}
	
	public void apply(CinematicScene scene) {
		if(currentFrameNumber > maximumFrame)
			currentFrameNumber = 0;
		currentFrameNumber++;
		if(currentFrame == null || !currentFrame.hasFrame(currentFrameNumber))
			getNextFrame(currentFrameNumber);
		
		if(currentFrame == null)
			return;
		
		float subframePosition = (float) ((currentFrame.getEnd() * 1.0f) - (currentFrameNumber * 1.0f));
		float percentage = 1.0f - ((subframePosition) / (currentFrame.getFrames() * 1.0f));

		if(currentFrame.isMoveScene()) {
			scene.proceedToNextScene();
		}
		if(currentFrame.getJumpLocation() != null && currentFrame.getJumpLocation() != baseCamera.getPosition()) {
			camera.setPosition(currentFrame.getJumpLocation());
		}
		//System.out.println(currentFrameNumber + " / " + percentage);
		if(currentFrame.getRotation() != -1 && currentFrame.rotation != baseCamera.rotation) {
			double rotation = ((baseCamera.rotation + ((currentFrame.getRotation() - baseCamera.rotation) * percentage)));
			if(rotation >= 2048)
				rotation -= 2048;
			camera.setRotation(rotation);
		}
		
		if(currentFrame.getTargetLocation() != null && currentFrame.getTargetLocation() != baseCamera.getPosition()) {
				double x = ((baseCamera.getPosition().x + ((currentFrame.getTargetLocation().x - baseCamera.getPosition().x)) * percentage));
				double y = ((baseCamera.getPosition().y + ((currentFrame.getTargetLocation().y - baseCamera.getPosition().y)) * percentage));
				double z = ((baseCamera.getPosition().z + ((currentFrame.getTargetLocation().z - baseCamera.getPosition().z)) * percentage));
				camera.setPosition(Vector3.of(x, y, z));
		}
		
		
		if(currentFrame.getTilt() != -1 && currentFrame.tilt != baseCamera.tilt) {

				double tilt = ((baseCamera.tilt +  ((currentFrame.tilt - baseCamera.tilt) * percentage)));
				if(tilt < 128)
					tilt = 128;
				else if(tilt > 384)
					tilt = 384;
				
				camera.setTilt( tilt);
			
			
		}
	}
	
	public CameraMove startInformation(Vector3 startingPosition, int startingRotation, int startingTilt) {

		baseCamera = Camera
				.builder()
				.position(startingPosition)
				.tilt(startingTilt)
				.rotation(startingRotation)
				.build();
		camera = baseCamera.copy();
		defaultCamera = baseCamera.copy();
		
		
		return this;
	}
	
	public CameraMove add(CameraFrame frame) {
		frame.setStart(maximumFrame);
		maximumFrame += frame.getFrames();
		frames.add(frame);
		return this;
	}
	
	public CameraMove close() {
		
			CameraFrame endFrame = 
					CameraFrame
					.builder()
					.targetLocation(defaultCamera.getPosition())
					.frames(1000)
					.rotation(defaultCamera.getRotation())
					.tilt(defaultCamera.getTilt())
					.build();
			CameraFrame last = frames.get(frames.size() - 1);
			last.setMoveScene(true);
			add(endFrame);
		
		return this;
	}

	public void reset() {
		this.baseCamera = defaultCamera.copy();
		this.camera = baseCamera.copy();
		this.currentFrame = null;
		this.currentFrameNumber = 1;
	}

}
