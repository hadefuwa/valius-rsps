package com.client.utilities;

import com.client.cache.provider.ResourceProvider;
import com.client.map.MapRegion;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MapRegionData {

	private int regionX, regionY, hash;
	private int landscape, objects;
	private byte[] landscapeData, objectsData;
	
	public void requestFiles(ResourceProvider onDemandFetcher) {
		onDemandFetcher.method558(3, objects);
		onDemandFetcher.method558(3, landscape);
		
	}
	
	public void setObjectsData(byte[] objectsData, ResourceProvider rp) {
		this.objectsData = objectsData;
		MapRegion.requestModelPreload(objectsData, rp);
	}
}
