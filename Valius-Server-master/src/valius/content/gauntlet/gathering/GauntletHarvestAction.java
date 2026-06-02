/**
 * 
 */
package valius.content.gauntlet.gathering;
//import javafx.scene.layout.Region;
import valius.clip.WorldObject;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.Event;
import valius.model.entity.player.Player;
import valius.model.items.ItemAssistant;
import valius.model.items.ItemDefinition;
import valius.model.map.ResourceNode;
import valius.util.Misc;
import valius.world.World;
import valius.world.objects.GlobalObject;

/**
 * @author ReverendDread
 * Aug 17, 2019
 */
public class GauntletHarvestAction extends Event<Player> {

	/**
	 * The resource nodes data.
	 */
	private GauntletResourceNodes nodeData;
	
	/**
	 * The resource node being harvested.
	 */
	private ResourceNode node;
	
	/**
	 * @param attachment
	 * @param ticks
	 */
	public GauntletHarvestAction(Player attachment, ResourceNode node, GauntletResourceNodes nodeData) {
		super("gauntlet_harvesting", attachment, nodeData.getTime());
		this.nodeData = nodeData;
		this.node = node;
	}
	
	@Override
	public void update() {
		if (!check()) {
			stop();
			return;
		}
		attachment.startAnimation(nodeData.getAnimation());
	}

	@Override
	public void execute() {
		if (!check()) {
			stop();
			return;
		}
		attachment.sendMessage("You've successfully harvested a " + ItemAssistant.getItemName(nodeData.getItemId()) + ".");
		attachment.getItems().addItem(nodeData.getItemId(), 1);
		attachment.getSkills().addExperience(nodeData.getExperience(), nodeData.getSkill());
		node.deincrementAndGet();
	}
	
	private boolean check() {
		if (attachment == null || attachment.disconnected || attachment.getSession() == null) {
			stop();
			return false;
		}
		if (attachment.getMovementQueue().hasSteps()) {
			return false;
		}
		if (!attachment.isWithinDistance(node.getLocation(), 1)) {
			return false;
		}
		if (nodeData.getToolId() != -1 && !attachment.getItems().hasItemOrEquipped(nodeData.getToolId())) {
			attachment.sendMessage("You don't have the required tool to harvest this.");
			return false;
		}
		if (!attachment.getItems().hasFreeSlots()) {
			attachment.sendMessage("You need at least one free space to harvest this.");
			return false;
		}
		if (!node.hasResources()) {
			attachment.sendMessage("This resource has been depleted.");
			World.getWorld().getGlobalObjects().replace(node, new GlobalObject(nodeData.getDepletedId(), node.getLocation()));
			return false;
		}
		return true;
	}
	
	@Override
	public void stop() {
		super.stop();
		if (attachment == null) {
			return;
		}
		attachment.stopAnimation();
	}

}
