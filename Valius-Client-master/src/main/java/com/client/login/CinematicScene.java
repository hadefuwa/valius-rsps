package com.client.login;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.client.Client;
import com.client.cache.graphics.Sprite;
import com.client.cache.provider.Resource;
import com.client.camera.Camera;
import com.client.camera.CameraFrame;
import com.client.camera.CameraMove;
import com.client.config.Configuration;
import com.client.draw.raster.Raster;
import com.client.draw.raster.Rasterizer3D;
import com.client.map.MapRegion;
import com.client.map.SceneGraph;
import com.client.map.clipping.CollisionMap;
import com.client.utilities.MapRegionData;
import com.client.utilities.Vector3;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.Getter;
import lombok.Setter;

public class CinematicScene {
	private final Client client;
	
	public CinematicScene(Client client) {
		this.client = client;

		blackWindow = new Sprite(Client.currentGameWidth, Client.currentGameHeight);
		Arrays.fill(blackWindow.myPixels, (255 << 24 ) + 0x050505);
		this.fadeToBlack.addListener((observable, oldVal, newVal) -> {
			if(newVal.doubleValue() >= 100) {
				setNextScene();
				resetMapData();
				this.prepareLoginScene();
			}
			if(newVal.doubleValue() <= 0) {
				resetSceneGraph();
				setNextCamera();
				this.fadeToBlack.set(-1);
			}
		});
		this.setupCamera();
		this.setupWorldMap();
		//randomizeMaps();
		
		setNextScene();
		setNextCamera();
		resetMapData();
		resetSceneGraph();
	}
	

	public void randomizeMaps() {
		long seed = System.nanoTime();
		List<Vector3> positions = Lists.newArrayList(mapPositions);
		List<CameraMove> cameras = Lists.newArrayList(mapCameraMoves);
		Collections.shuffle(positions, new Random(seed));
		Collections.shuffle(cameras, new Random(seed));
		
		mapPositions.clear();
		mapPositions.addAll(positions);
		
		mapCameraMoves.clear();
		mapCameraMoves.addAll(cameras);
	}
	private void setupWorldMap() {
		
		//mapPositions.add(Vector3.of(3050, 9950, 0));
		Vector3[] vecs = {
				//Vector3.of(3050, 9950, 0),
				Vector3.of(3676, 3219, 0),
				Vector3.of(2273, 5343, 0),/*
				Vector3.of(3079, 3776, 0),
				Vector3.of(2229, 3099, 0),
				Vector3.of(1309, 3616, 0),
				Vector3.of(1600, 3952, 0),
				Vector3.of(1634, 3676, 0),
				Vector3.of(1750, 3597, 0),
				Vector3.of(1415, 3520, 0),*/
				};
		for(int i = 0;i<vecs.length;i++) {
			mapPositions.add(vecs[i]);
		}
	}
	
	private void setupCamera() {
	
		for(int i = 0;i<1;i++) {
			CameraMove otherCameraMove = new CameraMove();
			otherCameraMove
			.startInformation(Vector3.of(1000, 2550, -3181), 1500, 1)
			.add(CameraFrame
					.builder()
					.targetLocation(Vector3.of(1400, 2550, -2000))
					.rotation(1500)
					.tilt(101)
					.frames(250)
					.build()
			)
			.add(CameraFrame
					.builder()
					.targetLocation(Vector3.of(1800, 2550, -1600))
					.rotation(1500)
					.tilt(101)
					.frames(240)
					.build()
			)
			.add(CameraFrame
					.builder()
					.targetLocation(Vector3.of(2816, 2550, -1200))
					.rotation(1500)
					.tilt(101)
					.frames(245)
					.build()
			)
			.add(CameraFrame
					.builder()
					.targetLocation(Vector3.of(3216, 2550, -1100))
					.rotation(1500)
					.tilt(50)
					.frames(200)
					.moveScene(true)
					.build()
			)
			.add(CameraFrame
					.builder()
					.targetLocation(Vector3.of(3616, 2550, -1000))
					.rotation(1500)
					.tilt(20)
					.frames(175)
					.build()
			)
			.add(CameraFrame
					.builder()
					.targetLocation(Vector3.of(3980, 2550, -1000))
					.rotation(1500)
					.tilt(0)
					.frames(170)
					.build()
			)
//			.add(CameraFrame
//					.builder()
//					.targetLocation(Vector3.of(2816, 1800, -840))
//					.rotation(0)
//					.tilt(51)
//					.frames(100)
//					.build()
//			)
//			.add(CameraFrame
//					.builder()
//					.targetLocation(Vector3.of(2816, 1600, -1200))
//					.rotation(0)
//					.tilt(101)
//					.frames(652)
//					.build()
//			)
//			.add(CameraFrame
//					.builder()
//					.targetLocation(Vector3.of(2816, 1200, -1200))
//					.rotation(2047)
//					.tilt(101)
//					.frames(1956)
//					.build()
//			)
//			.add(CameraFrame
//					.builder()
//					.targetLocation(Vector3.of(2816, 3192, -1700))
//					.rotation(2047)
//					.tilt(81)
//					.frames(327)
//					.moveScene(true)
//					.build()
//			)
//			
//			.add(CameraFrame
//					.builder()
//					.targetLocation(Vector3.of(2816, 287, -3181))
//					.rotation(1024)
//					.tilt(81)
//					.frames(652)
//					.build()
//			)
//			
//			.add(CameraFrame
//					.builder()
//					.targetLocation(Vector3.of(2816, 3192, -2100))
//					.rotation(1024)
//					.tilt(81)
//					.frames(652)
//					.moveScene(true)
//					.build()
//			)
			
			;
	
			this.mapCameraMoves.add(otherCameraMove);
		}
		
		//this.mapCameraMoves.add(homeCameraMove);
	}
	
    public void prepareLoginScene() {
    	if(client.onDemandFetcher == null) {
    		System.out.println("ODM NULL");
    		return;
    	}
    	if(!scenegraph.isPresent()) {
			try {
				if(this.regions.isEmpty()) {
					startResourceProvider();
					for(int x = 0;x<1;x++) {
						for(int y = 0;y<1;y++) {
							int newX = (worldX + (64 * x)) / 64;
							int newY = (worldY + (64 * y)) / 64;
							MapRegionData region = MapRegionData.builder()
													.regionX(newX)
													.regionY(newY)
													.landscape(client.onDemandFetcher.getMapFile(0, newY, newX))
													.objects(client.onDemandFetcher.getMapFile(1, newY, newX))
													.build();
							if(region.getLandscape() != -1 && region.getObjects() != -1) {
								System.out.println("Requesting");
								region.requestFiles(client.onDemandFetcher);
								regions.add(region);
							}
						}
					}
				} else if(allMapsProvided()) {
					scenegraph = this.loadBackgroundMap();
					

			    	System.out.println("PREP2");
				}
				
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
    }
   
    public Optional<SceneGraph> loadBackgroundMap() {
		boolean modelsPreloaded = regions.stream().allMatch(region -> MapRegion.method189(0, region.getObjectsData(), 0));
		if(!modelsPreloaded)
			return Optional.empty();
		
		
		
		client.updateGameScreen();
		byte[][][] tileFlags = new byte[4][104][104];
		int[][][] groundArray = new int[4][105][105];
		CollisionMap[] collisionMaps = new CollisionMap[4];
		for (int j = 0; j < 4; j++) {
			collisionMaps[j] = new CollisionMap(104, 104);

			collisionMaps[j].setDefault();
		}

		MapRegion objectManager = new MapRegion(tileFlags, groundArray, 64, 64);
		SceneGraph sceneGraph = new SceneGraph(groundArray, 64, 64);
		SceneGraph.focalLength = 512;
		SceneGraph.viewDistance = 10;
		sceneGraph.method275(0);
		int baseX = (this.worldX / 64) - 0;
		int baseY = (this.worldY / 64) - 0;
		this.regions.stream().forEach(region -> {
			int offsetX = (region.getRegionX() - baseX) * 64;
			int offsetY = (region.getRegionY() - baseY) * 64;
			System.out.println("REGION offsetX " + offsetX + ", offsetY " + offsetY);
			objectManager.decodeLandscapeFile(region.getLandscapeData(), offsetY, offsetX, region.getRegionX() * 64, region.getRegionY() * 64, collisionMaps);
			objectManager.decodeObjectFile(offsetX, collisionMaps, offsetY, sceneGraph, region.getObjectsData());
			
		});
		objectManager.createRegionScene(collisionMaps, sceneGraph);
		SceneGraph.setRenderDistance(35);
		return Optional.ofNullable(sceneGraph);
	}

    
    public void render() {
    	
    	if(scenegraph.isPresent()) {
			Rasterizer3D.method364();
			MapRegion.currentHeight = 0;
			scenegraph.get().anInt442 = 0;
			SceneGraph.focalLength = Rasterizer3D.width;
			Raster.setAllPixelsToZero();
			try {
				cameraMove.apply(this);
				Camera camera = getCamera();
				scenegraph.get().renderScene(camera.getPosition().getX(), camera.getPosition().getY(), camera.getRotation(), camera.getPosition().getZ(), worldZ, camera.getTilt(), true);

                Rasterizer3D.drawFog(0x050505, (SceneGraph.renderDistance * 100), (SceneGraph.renderDistance * 100) + 250 + 1000);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
			if(fadeFromBlack.get() > 0.0) {
				fadeFromBlack.set( fadeFromBlack.get() - (fadeFromBlack.get() < 93 ? 0.5 : 0.5));
				blackWindow.drawAdvancedTransparentSprite(0, 0, (int) Math.ceil(fadeFromBlack.get()));
			} else if(fadeToBlack.get() > 0) {
				fadeToBlack.set(fadeToBlack.get() - 0.5);
				blackWindow.drawAdvancedTransparentSprite(0, 0, 100 - (int) Math.ceil(fadeToBlack.get()));
			}
		} else {
			blackWindow.drawAdvancedTransparentSprite(0, 0, (int) Math.ceil(fadeFromBlack.get()));
			prepareLoginScene();
		}
    }

	

	private void setNextScene() {
		if(worldX != 0 && worldY != 0)
			mapPositions.offer(Vector3.of(worldX, worldY, 0));
		
		Vector3 nextMap = mapPositions.poll();
		if(nextMap != null) {
			worldX = nextMap.getX();
			worldY = nextMap.getY();
			worldZ = nextMap.getZ();
		}
		
		
	}
	
	private void setNextCamera() {
		if(cameraMove != null)
			mapCameraMoves.offer(cameraMove);
		CameraMove nextMove = mapCameraMoves.poll();
		if(nextMove != null) {
			nextMove.reset();
			cameraMove = nextMove;
		}
	}
	
	private void startResourceProvider() {
		Thread t = new Thread(() ->  {
			while(!Client.gameLoaded) {
				client.processLoginOnDemandQueue();
				try {
					//System.out.println("Sleeping");
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.start();
	}
	
	public void proceedToNextScene() {
		fadeToBlack.set(100);
	}
	
	public void moveScene(int x, int y) {
		this.worldX = x;
		this.worldY = y;
		resetSceneGraph();
	}

	public void resetMapData() {
		regions.clear();
	}
	
	public void resetSceneGraph() {
		this.scenegraph = Optional.empty();
		this.fadeFromBlack.set(100);
	}

	@Getter
    private Optional<SceneGraph> scenegraph = Optional.empty();
    
    public void provideMap(Resource resource) {
    	System.out.println("PROVIDED MAP");
    	regions.stream()
    	.filter(region -> region.getLandscape() == resource.ID)
    	.forEach(region -> region.setLandscapeData(resource.buffer));
    	regions.stream()
    	.filter(region -> region.getObjects() == resource.ID)
    	.forEach(region -> region.setObjectsData(resource.buffer, client.onDemandFetcher));
    }
    
    private boolean allMapsProvided() {
    	return regions.stream().allMatch(region -> region.getObjectsData() != null && region.getLandscapeData() != null);
    }
    private List<MapRegionData> regions = Lists.newArrayList();
    
    
    private int worldX = 0, worldY = 0, worldZ = 0;
    
	@Getter
	@Setter
	private CameraMove cameraMove;
	@Getter
	private DoubleProperty fadeFromBlack = new SimpleDoubleProperty(100.0);
	@Getter
	private DoubleProperty fadeToBlack = new SimpleDoubleProperty(-1);
	
	private ConcurrentLinkedQueue<Vector3> mapPositions = Queues.newConcurrentLinkedQueue();
	private ConcurrentLinkedQueue<CameraMove> mapCameraMoves = Queues.newConcurrentLinkedQueue();

	private Sprite blackWindow;

	public Camera getCamera() {
		return cameraMove.getCamera();
	}

	public void resetFade() {
		this.fadeFromBlack.set(100);
	}

	public void resizeFade() {
		blackWindow = new Sprite(Client.currentGameWidth, Client.currentGameHeight);
		Arrays.fill(blackWindow.myPixels, (255 << 24 ) + 0x050505);
	}

}
