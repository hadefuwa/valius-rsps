package valius.model;

import java.util.Map;

import com.google.common.collect.Maps;

public class NamedValueMap {
	
	private Map<String, Object> valueMap = Maps.newConcurrentMap();
	
	public NamedValueMap add(String key, Object value) {
		valueMap.put(key, value);
		return this;
	}
	public <T> T get(String key, T defaultVal){
		if(valueMap.containsKey(key)) {
			Object val = valueMap.get(key);
			if(defaultVal.getClass().isAssignableFrom(val.getClass()))
				return (T) val;
		}
		return defaultVal;
	}
	public void clear() {
		valueMap.clear();
	}

}
