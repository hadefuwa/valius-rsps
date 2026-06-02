package valius.content.falling_stars;

import org.apache.commons.lang3.RandomUtils;

import valius.event.Event;
import valius.model.Location;
import valius.model.entity.player.Player;
import valius.model.entity.player.mode.ModeType;
import valius.model.entity.player.skills.Skill;
import valius.model.entity.player.skills.mining.MiningEvent;
import valius.model.entity.player.skills.mining.Pickaxe;
import valius.util.Misc;

/**
 * Represents a singular event that is executed when a player attempts to mine.
 * 
 * @author Jason MacKeigan
 * @date Feb 18, 2015, 6:17:11 PM
 */
public class StarMineEvent extends Event<Player> {
	
	public static int[] prospectorOutfit = { 12013, 12014, 12015, 12016 };

	/**
	 * The amount of cycles that must pass before the animation is updated
	 */
	private final int ANIMATION_CYCLE_DELAY = 3;

	/**
	 * The value in cycles of the last animation
	 */
	private int lastAnimation;

	/**
	 * The pickaxe being used to mine
	 */
	private final Pickaxe pickaxe;


	/**
	 * The object that we are mning
	 */
	private int objectId;
	
	private int experience;

	/**
	 * The location of the object we're mining
	 */
	private Location location;


	/**
	 * Constructs a new {@link MiningEvent} for a single player
	 * 
	 * @param player the player this is created for
	 * @param objectId the id value of the object being mined from
	 * @param location the location of the object being mined from
	 * @param mineral the mineral being mined
	 * @param pickaxe the pickaxe being used to mine
	 */
	public StarMineEvent(Player attachment, int objectId, int experience, Location location, Pickaxe pickaxe, int time) {
		super("skilling", attachment, time);
		this.objectId = objectId;
		this.experience = experience;
		this.location = location;
		this.pickaxe = pickaxe;
	}

	
	@Override
	public void update() {
		if (attachment == null || attachment.disconnected || attachment.getSession() == null) {
			stop();
			return;
		}
		if (!attachment.getItems().playerHasItem(pickaxe.getItemId()) && !attachment.getItems().isWearingItem(pickaxe.getItemId())) {
			attachment.sendMessage("That is strange! The pickaxe could not be found.");
			stop();
			return;
		}
		if (attachment.getItems().freeSlots() == 0 && !attachment.getItems().playerHasItem(FallingStars.STAR_DUST_ID)) {
			attachment.getDH().sendStatement("You have no more free slots.");
			stop();
			return;
		}
		if (RandomUtils.nextInt(1, 100) == 1 && attachment.getInterfaceEvent().isExecutable()) {
			attachment.getInterfaceEvent().execute();
			stop();
			return;
		}
		if (FallingStars.getActiveStar() == null) {
			stop();
			return;
		}
		if (FallingStars.getActiveStar().getObjectId() != objectId) {
			attachment.sendMessage("The star depletes");
			stop();
			return;
		}
		if (!FallingStars.getActiveStar().getLocation().equals(location)) {
			stop();
			return;
		}
		
		if (super.getElapsedTicks() - lastAnimation > ANIMATION_CYCLE_DELAY) {
			attachment.startAnimation(pickaxe.getAnimation());
			lastAnimation = super.getElapsedTicks();
		}
	}

	@Override
	public void execute() {

		if (attachment == null || attachment.disconnected || attachment.getSession() == null) {
			stop();
			return;
		}
		double osrsExperience = 0;
		double regExperience = 0;
		int pieces = 0;
		for (int i = 0; i < prospectorOutfit.length; i++) {
			if (attachment.getItems().isWearingItem(prospectorOutfit[i])) {
				pieces += 6;
			}
		}
		
		attachment.turnPlayerTo(location.getX(), location.getY());
		
		/**
		 * Experience calculation
		 */
		osrsExperience = experience + (experience / 10 * pieces);
		regExperience = experience + (experience / 10 * pieces);
		
		attachment.getPA().addSkillXP((int) (attachment.getMode().getType().equals(ModeType.OSRS) ? osrsExperience : regExperience), Skill.MINING.getId(), true);
		
		attachment.getItems().addItem(FallingStars.STAR_DUST_ID, 1);
		FallingStars.deplete();
		
		
		if (Misc.random(25) == 0) {
			int sPoints = Misc.random(1, 5);
            attachment.skillPoints += sPoints;
            attachment.sendMessage("@pur@You receive " + sPoints + " Skill Points.");
		}
		
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
