package valius.model.entity.player;

/*
 * Standardizes in-game announcements.
 * 
 * @author Patrity
 * 
 *NEWS = horn sprite [news] in blue
 *LOOT = Coins sprite [loot] in red
 *EVENT = red skull sprite [Event] in red
 *DONATION = GE icon [donation] in blue
 *NONE = use only if necessary, adds no sprite or color, do this yourself.
 * 
 * cr10 - blue question mark
 * cr18 - coins
 * cr20 - horn
 * cr17 - medal
 */

public class GlobalMessages {

	public static void send(String message, MessageType type) {

		String sprite = type.getSprite();
		String prefix = type.getPrefix();
		PlayerHandler.executeGlobalMessage(sprite + prefix + message);
	}

	public enum MessageType {
		NEWS("@cr20@", "[@yel@<shad>News</shad>@bla@]: @yel@<shad>"), 
		LOOT("@cr18@", "[@pur@Loot@bla@]: @pur@"),
		PET("@cr18@", "[@pur@PET@bla@]: @pur@"),
		COMPLETIONIST("@cr20@", "[@pur@<shad>Completionist</shad>@bla@]: @pur@<shad>"),
		EVENT("@cr19@", "[@red@<shad>Event</shad>@bla@]: @red@<shad>"), 
		EVENT_BOSS("@cr22@", "[@red@<shad>Event Boss</shad>@bla@]: @red@<shad>"), 
		DONATION("@cr9@", "[@gre@<shad>Donation</shad>@bla@]: @gre@<shad>"),
		WILDERNESS_BOSS("@cr22@", "[@red@<shad>Event Boss@bla@]: @red@<shad>"),
		NONE("", ""),
		;
		private MessageType(String sprite, String prefix) {
			this.sprite = sprite;
			this.prefix = prefix;
		}

		private String sprite;

		private String getSprite() {
			return sprite;
		}

		private String prefix;

		private String getPrefix() {
			return prefix;
		}
	}
}
