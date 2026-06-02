package valius.model.entity.instance;

import java.util.List;

import com.google.common.collect.Lists;

public class InstanceManager {

	private static List<Instance> activeInstances = Lists.newCopyOnWriteArrayList();
	
	public static void register(Instance instance) {
		if(!activeInstances.contains(instance)) {
			activeInstances.add(instance);
			instance.initialize();
		}
	}
	

	public static void tick() {
		Lists.newArrayList(activeInstances).stream().forEach(instance -> {
			if(instance.isDestroyed()) {
				activeInstances.remove(instance);
				return;
			}
			if(instance.isAwaitingDestroy()) {
				instance.destroy();
				activeInstances.remove(instance);
				return;
			}
			instance.tick();
		});
	}
}
