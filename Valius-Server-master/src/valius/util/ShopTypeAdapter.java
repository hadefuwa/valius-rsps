package valius.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import valius.model.shops.ShopTab;
import valius.model.shops.ShopItem;

public class ShopTypeAdapter implements JsonSerializer<ShopItem>{

	@Override
	public JsonElement serialize(ShopItem src, Type typeOfSrc, JsonSerializationContext context) {
		 Gson gson = new Gson();
	        JsonObject jObj = (JsonObject)gson.toJsonTree(src);   
	        if(src.getStoredCost() == 0){
	            jObj.remove("cost");
	        }
	        return jObj;
	}

}
