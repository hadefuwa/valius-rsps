package valius.model.entity.player.commands.all;

import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.world.World;

/**
 * Teleport the player to home.
 *
 * @author Emiel
 */
public class Highrisk extends Command {

    @Override
    public void execute(Player c, String input) {
        if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
            return;
        }
        if (c.inClanWars() || c.inClanWarsSafe()) {
            c.sendMessage("@cr10@You can not teleport from here, speak to the doomsayer to leave.");
            return;
        }
        if (c.inWild()) {
            c.sendMessage("You can't use this command in the wilderness.");
            return;
        }
        c.getPA().spellTeleport(3093, 3495, 8, false);
        c.sendMessage("@red@If you leave the fountain, you are in the wilderness!");
        c.sendMessage("@red@You can not use the protect item prayer or protect items here.");
        c.sendMessage("@red@Type ::home if you want to leave.");
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.of("Teleports you to a instanced edgeville");
    }

}
