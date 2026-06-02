package valius.model.entity.player.commands.all;

import java.util.Objects;
import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.world.World;

public class Close extends Command {

	@Override
	public void execute(Player c, String input) {
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("You cannot close the random event..");
			return;
		}
		if (c.isDead || c.getHealth().getAmount() <= 0) {
			c.sendMessage("You are dead, you cannot do this.");
			return;
		}
		if (!Objects.equals(c.rottenPotatoOption, "")) {
			c.rottenPotatoOption = "";
		}
		if (c.getCurrentCombination().isPresent()) {
			c.setCurrentCombination(Optional.empty());
		}
		if (c.getPA().viewingOtherBank) {
			c.getPA().resetOtherBank();
		}
		if (c.getTutorial().isActive()) {
			c.getTutorial().refresh();
			return;
		}
		if (c.isStuck) {
			c.isStuck = false;
			c.sendMessage("@red@You've disrupted stuck command, you will no longer be moved home.");
			return;
		}
		if (c.getBankPin().requiresUnlock()) {
			c.getBankPin().open(2);
			return;
		}
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
			c.sendMessage("You must end your current session to do this.");
			return;
		}
		if (c.canChangeAppearance) {
			c.canChangeAppearance = false;
		}
		
		if (System.currentTimeMillis() - c.lastCloseOfInterface < 4000) {
			c.sendMessage("Wait a couple of seconds before attempting to do this again.");
			return;
		}
		c.lastCloseOfInterface = System.currentTimeMillis();
		c.getPA().closeAllWindows();
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Closes any open interfaces");
	}
}
