package valius.model.entity.player.skills;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerAssistant;
import valius.model.items.Item;


@RequiredArgsConstructor
public class MakeWidget {

	private final Player player;
	private MakeAction[] options = new MakeAction[8];
	
	private int makeAmount = 1;
	
	@Getter
	private int customEnterAmount = -1;

	@Getter
	private boolean settingAmount;
	
	public void setCustomEnterAmount(int amount) {
		if(amount < 1)
			amount = 1;
		else if(amount > 28) {
			amount = 28;
		}
		customEnterAmount = amount;
		player.getPA().sendConfig(20001, amount);
		player.getPA().sendConfig(20000, 4);
		settingAmount = false;
	}
	
	public void send() {
		List<Item> items = Stream.of(options).filter(Objects::nonNull).map(makeAction -> makeAction.getItem()).collect(Collectors.toList());
		PlayerAssistant.sendItems(player, 18830, items, 5);
		player.getPA().sendFrame164(19004);
	}
	
	public MakeWidget set(int itemId, BiConsumer<Player, Integer> onSelected) {
		int index = nextIndex();
		if(index != -1) {
			options[index] = new MakeAction(new Item(itemId), onSelected);
		}
		return this;
	}

	public boolean onButtonClick(int button) {
		
		switch(button) {
		case 18882:
			player.getPA().sendConfig(20000, 1);
			makeAmount = 1;
			return true;
		case 18883:
			player.getPA().sendConfig(20000, 2);
			makeAmount = 5;
			return true;
		case 18884:
			player.getPA().sendConfig(20000, 3);
			makeAmount = 10;
			return true;
		case 18885:
			if(customEnterAmount > 0) {
				player.getPA().sendConfig(20000, 4);
				makeAmount = customEnterAmount;
			} else {
				player.getPA().sendConfig(20001, 0);
				makeAmount = 1;
			}
			return true;
		case 18886:
			player.getOutStream().writePacketHeader(27);
			settingAmount = true;
			return true;
		case 18887:
			player.getPA().sendConfig(20000, 6);
			makeAmount = Integer.MAX_VALUE;
			return true;
		}
		
		if(button >= 18862 && button <= 18866) {
			if(makeAmount > 0) {
				int index = button - 18862;
				runAction(index);
				player.getPA().closeAllWindows();
			}
			return true;
		}
		if(button >= 19062 && button <= 19070) {
			if(makeAmount > 0) {
				int index = button - 19062;
				runAction(index);
				player.getPA().closeAllWindows();
			}
			return true;
		}
		return false;
	}
	
	private void runAction(int index) {
		if(index >= options.length)
			return;
		MakeAction action = options[index];
		if(action != null) {
			action.getConsumer().accept(player, makeAmount);
		}
	}
	
	private int nextIndex() {
		for(int index = 0;index<options.length;index++) {
			if(options[index] == null) {
				return index;
			}
		}
		return -1;
	}
	
	public void reset() {
		settingAmount = false;
		Arrays.fill(options, null);
	}

	public void onLogin() {
		player.getPA().sendConfig(20001, 0);
		player.getPA().sendConfig(20000, 1);
	}
	
	@Data
	private static class MakeAction {
		private final Item item;
		private final BiConsumer<Player, Integer> consumer;
	}
}
