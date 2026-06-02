package valius.net.packet;

import valius.Config;
import valius.model.entity.player.Player;
import valius.model.entity.player.Right;
import valius.net.packet.impl.AttackPlayer;
import valius.net.packet.impl.Bank10;
import valius.net.packet.impl.Bank5;
import valius.net.packet.impl.BankAll;
import valius.net.packet.impl.BankAllButOne;
import valius.net.packet.impl.BankModifiableX;
import valius.net.packet.impl.BankX1;
import valius.net.packet.impl.BankX2;
import valius.net.packet.impl.CapeColour;
import valius.net.packet.impl.ChallengePlayer;
import valius.net.packet.impl.ChangeAppearance;
import valius.net.packet.impl.ChangeRegions;
import valius.net.packet.impl.Chat;
import valius.net.packet.impl.ClickNPC;
import valius.net.packet.impl.ClickObject;
import valius.net.packet.impl.ClickingButtons;
import valius.net.packet.impl.ClickingInGame;
import valius.net.packet.impl.ClickingStuff;
import valius.net.packet.impl.Commands;
import valius.net.packet.impl.Dialogue;
import valius.net.packet.impl.DropItem;
import valius.net.packet.impl.FollowPlayer;
import valius.net.packet.impl.IdleLogout;
import valius.net.packet.impl.InputField;
import valius.net.packet.impl.InviteToGroup;
import valius.net.packet.impl.ItemOnItem;
import valius.net.packet.impl.ItemOnNpc;
import valius.net.packet.impl.ItemOnObject;
import valius.net.packet.impl.ItemOnPlayer;
import valius.net.packet.impl.ItemOptionOneGroundItem;
import valius.net.packet.impl.ItemOptionTwoGroundItem;
import valius.net.packet.impl.KeyEventPacketHandler;
import valius.net.packet.impl.MagicOnFloorItems;
import valius.net.packet.impl.MagicOnItems;
import valius.net.packet.impl.MagicOnObject;
import valius.net.packet.impl.MapRegionChange;
import valius.net.packet.impl.MapRegionFinish;
import valius.net.packet.impl.Moderate;
import valius.net.packet.impl.MouseMovement;
import valius.net.packet.impl.MoveItems;
import valius.net.packet.impl.OperateItem;
import valius.net.packet.impl.PickupItem;
import valius.net.packet.impl.PrivateMessaging;
import valius.net.packet.impl.RemoveItem;
import valius.net.packet.impl.Report;
import valius.net.packet.impl.SelectItemOnInterface;
import valius.net.packet.impl.SilentPacket;
import valius.net.packet.impl.Trade;
import valius.net.packet.impl.Walking;
import valius.net.packet.impl.WearItem;
import valius.net.packet.impl.action.InterfaceAction;
import valius.net.packet.impl.action.JoinChat;
import valius.net.packet.impl.action.ReceiveString;
import valius.net.packet.impl.itemoptions.ItemOptionOne;
import valius.net.packet.impl.itemoptions.ItemOptionThree;
import valius.net.packet.impl.itemoptions.ItemOptionTwo;

public class PacketHandler {

	private static PacketType packetId[] = new PacketType[255];

	static {
		SilentPacket u = new SilentPacket();
		packetId[3] = u;
		packetId[202] = u;
		packetId[77] = u;
		packetId[86] = u;
		packetId[78] = u;
		packetId[36] = u;
		packetId[226] = u;
		packetId[246] = u;
		packetId[148] = u;
		packetId[183] = u;
		packetId[230] = u;
		packetId[136] = u;
		packetId[189] = u;
		packetId[153] = new InviteToGroup(); //invite
		packetId[152] = u;
		packetId[200] = u;
		packetId[85] = u;
		packetId[165] = u;
		packetId[150] = u;
		packetId[74] = u;
		packetId[34] = u;
		packetId[68] = u;
		packetId[79] = u;
		packetId[140] = u;
		// packetId[18] = u;
		packetId[223] = u;

		packetId[238] = new CapeColour();
		packetId[8] = new Moderate();
		packetId[142] = new InputField();
		packetId[253] = new ItemOptionTwoGroundItem();
		packetId[218] = new Report();
		packetId[40] = new Dialogue();
		packetId[232] = new OperateItem();
		KeyEventPacketHandler ke = new KeyEventPacketHandler();
		packetId[186] = ke;
		ClickObject co = new ClickObject();
		packetId[ClickObject.FIRST_CLICK] = co;
		packetId[ClickObject.SECOND_CLICK] = co;
		packetId[ClickObject.THIRD_CLICK] = co;
		packetId[ClickObject.FOURTH_CLICK] = co;
		packetId[ClickObject.FIFTH_CLICK] = co;
		packetId[57] = new ItemOnNpc();
		ClickNPC cn = new ClickNPC();
		packetId[72] = cn;
		packetId[131] = cn;
		packetId[155] = cn;
		packetId[17] = cn;
		packetId[21] = cn;
		packetId[18] = cn;
		packetId[124] = new SelectItemOnInterface();
		packetId[16] = new ItemOptionTwo();
		packetId[75] = new ItemOptionThree();
		packetId[122] = new ItemOptionOne();
		packetId[241] = new ClickingInGame();
		packetId[4] = new Chat();
		packetId[236] = new PickupItem();
		packetId[87] = new DropItem();
		packetId[185] = new ClickingButtons();
		packetId[130] = new ClickingStuff();
		packetId[103] = new Commands();
		packetId[214] = new MoveItems();
		packetId[237] = new MagicOnItems();
		packetId[181] = new MagicOnFloorItems();
		packetId[202] = new IdleLogout();
		AttackPlayer ap = new AttackPlayer();
		packetId[73] = ap;
		packetId[249] = ap;
		packetId[128] = new ChallengePlayer();
		packetId[39] = new Trade();
		packetId[139] = new FollowPlayer();
		packetId[41] = new WearItem();
		packetId[145] = new RemoveItem();
		packetId[117] = new Bank5();
		packetId[43] = new Bank10();
		packetId[129] = new BankAll();
		packetId[140] = new BankAllButOne();
		packetId[141] = new BankModifiableX();
		packetId[101] = new ChangeAppearance();
		PrivateMessaging pm = new PrivateMessaging();
		packetId[188] = pm;
		packetId[126] = pm;
		packetId[215] = pm;
		packetId[74] = pm;
		packetId[95] = pm;
		packetId[133] = pm;
		packetId[135] = new BankX1();
		packetId[208] = new BankX2();
		Walking w = new Walking();
		packetId[98] = w;
		packetId[164] = w;
		packetId[248] = w;
		packetId[53] = new ItemOnItem();
		packetId[192] = new ItemOnObject();
		packetId[25] = new ItemOptionOneGroundItem();
		@SuppressWarnings("unused")
		ChangeRegions cr = new ChangeRegions();
		packetId[60] = new JoinChat();
		packetId[127] = new ReceiveString();
		packetId[213] = new InterfaceAction();
		packetId[14] = new ItemOnPlayer();
		packetId[121] = new MapRegionFinish();
		packetId[210] = new MapRegionChange();
		packetId[35] = new MagicOnObject();
		MouseMovement ye = new MouseMovement();
		packetId[187] = ye;

	}

	public static void processPacket(Player c, int packetType, int packetSize) {
		PacketType p = packetId[packetType];
		if (p != null && packetType > 0 && packetType < 258 && packetType == c.packetType && packetSize == c.packetSize) {
			if (Config.sendServerPackets && c.getRights().isOrInherits(Right.OWNER)) {
				c.sendMessage("PacketType: " + packetType + ". PacketSize: " + packetSize + ".");
			}
			try {
				p.processPacket(c, packetType, packetSize);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			c.disconnected = true;
			System.out.println(c.playerName + " is sending invalid PacketType: " + packetType + ". PacketSize: " + packetSize);
		}
	}
}