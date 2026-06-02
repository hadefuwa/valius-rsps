package valius.model.entity.player.combat.melee;

import valius.model.entity.npc.animations.BlockAnimation;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.magic.MagicData;
import valius.model.items.ItemAssistant;

public class MeleeData {

	public static void resetPlayerAttack(Player c) {
		c.usingMagic = false;
		c.npcIndex = 0;
		c.faceUpdate(0);
		c.playerIndex = 0;
		c.getPA().resetFollow();
		return;
	}

	public static boolean usingHally(Player c) {
		switch (c.playerEquipment[c.playerWeapon]) {
		case 3190:
		case 3192:
		case 3194:
		case 3196:
		case 3198:
		case 2054:
		case 3202:
		case 3204:
		case 13092:
			return true;

		default:
			return false;
		}
	}

	public static void getPlayerAnimIndex(Player c, String weaponName) {
		c.playerStandIndex = 0x328;
		c.playerTurnIndex = 0x337;
		c.playerWalkIndex = 0x333;
		c.playerTurn180Index = 0x334;
		c.playerTurn90CWIndex = 0x335;
		c.playerTurn90CCWIndex = 0x336;
		c.playerRunIndex = 0x338;
		
//	if (c.playerEquipment[c.playerCape] == 33719) {
//		c.startAnimation(1500);
//		c.playerTurnIndex = 1501;
//		c.playerTurn180Index = 1501;
//		c.playerTurn90CCWIndex = 1501;
//		c.playerTurn90CWIndex = 1501;
//		c.playerWalkIndex = 1501;
//		c.playerRunIndex = 1851;
//		c.playerStandIndex = 1501;
//		return;
//	}
	
	if (c.playerEquipment[c.playerRing] == 33743) {
		c.npcId2 = 3847;
		c.isNpc = true;
		c.updateRequired = true;
		c.morphed = false;
		c.setAppearanceUpdateRequired(true);
	}
	
	else if (c.playerEquipment[c.playerRing] == 33744) {
		c.npcId2 = 3848;
		c.isNpc = true;
		c.updateRequired = true;
		c.morphed = false;
		c.setAppearanceUpdateRequired(true);
	}
	
	else if (c.playerEquipment[c.playerRing] != 33743 || c.playerEquipment[c.playerRing] != 33744) {
		c.npcId2 = -1;
		c.isNpc = false;
		c.updateRequired = true;
		c.morphed = false;
		c.setAppearanceUpdateRequired(true);
	}
	
		if (weaponName.contains("hunting knife")) {
			c.playerStandIndex = 7329;
			c.playerWalkIndex = 7327;
			c.playerRunIndex = 7327;
			return;
		}
		
		 if (c.playerEquipment[c.playerWeapon] == 22545 || c.playerEquipment[c.playerWeapon] == 22542
				 || c.playerEquipment[c.playerWeapon] == 33780 || c.playerEquipment[c.playerWeapon] == 33779 || c.playerEquipment[c.playerWeapon] == 33778) {//chainmace
	            c.playerStandIndex = 244;
	            c.playerWalkIndex = 247;
	            c.playerRunIndex = 248;
	            
	            return;
	        }
		
		if (weaponName.contains("bulwark")) {
			c.playerStandIndex = 7508;
			c.playerWalkIndex = 7510;
			c.playerRunIndex = 7509;
			return;
		}
		
		if (weaponName.contains("elder maul") || weaponName.contains("anger battleaxe") || weaponName.contains("chaotic maul")) {
			c.playerStandIndex = 7518;
			c.playerWalkIndex = 7520;
			c.playerRunIndex = 7519;
			return;
		}
		
		if (weaponName.contains("ballista")) {
			c.playerStandIndex = 7220;
			c.playerWalkIndex = 7223;
			c.playerRunIndex = 7221;
			return;
		}
		if (weaponName.contains("clueless")) {
			c.playerStandIndex = 7271;
			c.playerWalkIndex = 7272;
			c.playerRunIndex = 7273;
			return;
		}
		if (weaponName.contains("ale of the gods")) {
			c.playerStandIndex = 3040;
			c.playerWalkIndex = 3039;
			c.playerRunIndex = 3039;
			return;
		}
		if (weaponName.contains("easter basket")) {
			c.playerStandIndex = 1836;
			c.playerWalkIndex = 1836;
			c.playerRunIndex = 1836;
			return;
		}
		if (weaponName.contains("casket")) {
			c.playerRunIndex = 7274;
			return;
		}
		if (c.playerEquipment[c.playerWeapon] == 33111) {
			c.playerStandIndex = 2065;
			c.playerWalkIndex = 2064;
			c.playerRunIndex = 2064;
			return;
		}
		
		if (c.playerEquipment[c.playerWeapon] == 33480) {
			c.playerStandIndex = 2148;
			c.playerWalkIndex = 2148;
			c.playerRunIndex = 2148;
			c.playerTurnIndex = 2148;
			c.playerTurn180Index = 2148;
			c.playerTurn90CWIndex = 2148;
			c.playerTurn90CCWIndex = 2148;
			return;
		}
		
		if (weaponName.contains("halberd") || weaponName.contains("balloon") || weaponName.contains("scythe of vitur") || weaponName.contains("lance") || weaponName.contains("hasta") || weaponName.contains("spear") || weaponName.contains("guthan") || weaponName.contains("sceptre")) {
			c.playerStandIndex = 809;
			c.playerWalkIndex = 1146;
			c.playerRunIndex = 1210;
			return;
		}
		if (weaponName.contains("sled")) {
			c.playerStandIndex = 1461;
			c.playerWalkIndex = 1468;
			c.playerRunIndex = 1467;
			return;
		}
		if (weaponName.contains("valius longwor")) {
			c.playerStandIndex = 2065;
			c.playerWalkIndex = 2064;
			c.playerRunIndex = 2064;
			return;
		}
		if (weaponName.contains("dharok")  || weaponName.contains("anger mace")) {
			c.playerStandIndex = 0x811;
			c.playerWalkIndex = 2064;
			return;
		}
		if (weaponName.contains("ahrim")) {
			c.playerStandIndex = 809;
			c.playerWalkIndex = 1146;
			c.playerRunIndex = 1210;
			return;
		}
		if (weaponName.contains("verac")) {
			c.playerStandIndex = 1832;
			c.playerWalkIndex = 1830;
			c.playerRunIndex = 1831;
			return;
		}
		if (weaponName.contains("wand") || weaponName.contains("staff") || weaponName.equals("infernal staff") || weaponName.equals("infernal_staff") || weaponName.contains("trident")) {
			c.playerStandIndex = 809;
			c.playerRunIndex = 1210;
			c.playerWalkIndex = 1146;
			return;
		}
		if (weaponName.contains("banner")) {
			c.playerStandIndex = 1421;
			c.playerWalkIndex = 1422;
			c.playerRunIndex = 1427;
			return;
		}
		if (weaponName.contains("karil")) {
			c.playerStandIndex = 2074;
			c.playerWalkIndex = 2076;
			c.playerRunIndex = 2077;
			return;
		}
		if (weaponName.contains("2h sword") || weaponName.contains("godsword") || weaponName.contains("of_valius") || weaponName.contains("of valius") || weaponName.contains("saradomin sw") || weaponName.contains("saradomin's bless") || weaponName.contains("large spade")) {
			if (c.playerEquipment[c.playerWeapon] != 7158) {
				c.playerStandIndex = 7053;
				c.playerWalkIndex = 7052;
				c.playerRunIndex = 7043;
				c.playerTurnIndex = 7049;
				c.playerTurn180Index = 7047;
				c.playerTurn90CWIndex = 7047;
				c.playerTurn90CCWIndex = 7048;
				return;
			}
		}
		if (weaponName.contains("bow") || weaponName.startsWith("craw")) {
			c.playerStandIndex = 808;
			c.playerWalkIndex = 819;
			c.playerRunIndex = 824;
			return;
		}
		
		if (weaponName.contains("zamorakian")) {
			c.playerStandIndex = 1662;
			c.playerWalkIndex = 1663;
			c.playerRunIndex = 1664;
			return;
		}

		switch (c.playerEquipment[c.playerWeapon]) {
		case 7158:
			c.playerStandIndex = 2065;
			c.playerWalkIndex = 2064;
			break;
		case 4151:
		case 33526:
		case 12773:
		case 12774:
		case 12006:
			c.playerWalkIndex = 1660;
			c.playerRunIndex = 1661;
			break;
		case 8004:
		case 7960:
			c.playerStandIndex = 2065;
			c.playerWalkIndex = 2064;
			break;
		case 6528:
		case 20756:
			c.playerStandIndex = 0x811;
			c.playerWalkIndex = 2064;
			c.playerRunIndex = 1664;
			break;
		case 12848:
		case 4153:
		case 13263:
			c.playerStandIndex = 1662;
			c.playerWalkIndex = 1663;
			c.playerRunIndex = 1664;
			break;
		case 10887:
			c.playerStandIndex = 5869;
			c.playerWalkIndex = 5867;
			c.playerRunIndex = 5868;
			break;
		case 11802:
		case 11804:
		case 11838:
		case 12809:
		case 11806:
		case 11808:
			c.playerStandIndex = 7053;
			c.playerWalkIndex = 7052;
			c.playerRunIndex = 7043;
			c.playerTurnIndex = 7049;
			c.playerTurn180Index = 7052;
			c.playerTurn90CWIndex = 7052;
			c.playerTurn90CCWIndex = 7052;
			break;
		case 1305:
			c.playerStandIndex = 809;
			break;
			
		default:
			c.playerStandIndex = 0x328;
			c.playerTurnIndex = 0x337;
			c.playerWalkIndex = 0x333;
			c.playerTurn180Index = 0x334;
			c.playerTurn90CWIndex = 0x335;
			c.playerTurn90CCWIndex = 0x336;
			c.playerRunIndex = 0x338;
			break;
		}
	}

	public static int getWepAnim(Player c, String weaponName) {
		if (c.playerEquipment[c.playerWeapon] <= 0) {
			switch (c.fightMode) {
			case 0:
				return 422;
			case 2:
				return 423;
			case 1:
				return 422;
			}
		}
		if (weaponName.contains("bulwark")) {
			return 7511;
		}
		if (weaponName.contains("elder maul")  || weaponName.contains("stunning hammer")  || weaponName.contains("anger battleaxe") || weaponName.contains("chaotic maul")) {
			return 7516;
		}
		if (weaponName.contains("of valius") || weaponName.contains("of_valius")) {
			switch (c.fightMode) {
			case 0:
				return 407;
			case 1:
				return 406;
			case 2:
				return 409;
			case 3:
				return 409;
			}
		}
		
		 if (c.playerEquipment[c.playerWeapon] == 33172) {
			 switch (c.fightMode) {
	            case 0:
	            case 1:
	            case 2:
	            case 3:
	            	return 2068;
	            }
		 }
		
		 if (c.playerEquipment[c.playerWeapon] == 22545 || c.playerEquipment[c.playerWeapon] == 22542
				 || c.playerEquipment[c.playerWeapon] == 33778 || c.playerEquipment[c.playerWeapon] == 33779 || c.playerEquipment[c.playerWeapon] == 33780) {
	            switch (c.fightMode) {
	            case 0:
	            case 1:
	            case 2:
	            case 3:
	            	return 245;
	            }
	        }
		
		if (weaponName.contains("valius longwor") || weaponName.contains("valius_longswor")) {
			switch (c.fightMode) {
			case 0:
				return 2067;
			case 1:
				return 2066;
			case 2:
				return 406;
			case 3:
				return 409;
			}
		}
		if (weaponName.contains("scythe of")) {
			c.gfx100(478);
			return 1203;
		}
		if (weaponName.contains("zamorakian")) {
			return 2080;
		}
		if (weaponName.contains("hunting knife")) {
			return 7328;
		}
		if (weaponName.contains("ballista")) {
			return 7218;
		}
		if (weaponName.contains("toxic blowpipe")) {
			return 5061;
		}
		if (weaponName.contains("warhammer")) {
			return 401;
		}
		if (weaponName.contains("dart")) {
			return c.fightMode == 2 ? 806 : 6600;
		}
		if (weaponName.contains("dragon 2h")) {
			return 407;
		}
		if (weaponName.contains("knife") || weaponName.contains("javelin") || weaponName.contains("thrownaxe")) {
			return 806;
		}
		if (weaponName.contains("cross") && !weaponName.contains("karil") || weaponName.contains("c'bow") && !weaponName.contains("karil")) {
			return 4230;
		}
		if (weaponName.contains("halberd")) {
			return 440;
		}
		if (weaponName.startsWith("dragon dagger")) {
			return 402;
		}
		if (weaponName.contains("byssal dagger")) {
			return c.fightMode == 1 ? 3295 : c.fightMode == 0 || c.fightMode == 2 ? 3297 : 3294;
		}
		if (weaponName.contains("dagger")) {
			return 412;
		}
		if (weaponName.contains("2h sword") || weaponName.contains("godsword") || weaponName.contains("aradomin sword") || weaponName.contains("blessed sword") || weaponName.contains("large spade")) {
			switch (c.fightMode) {
			case 0:// attack
				return 7045;
			case 2:// str
				return 7045;
			case 1:// def
				return 7046;
			case 3:// crush
				return 7046;
			}
		}
		if (weaponName.contains("dharok") || weaponName.contains("anger mace") ) {
			switch (c.fightMode) {
			case 0:// attack
				return 2067;
			case 2:// str
				return 2067;
			case 1:// def
				return 2067;
			case 3:// crush
				return 2066;
			}
		}
		if (weaponName.contains("sword") && !weaponName.contains("training")) {
			return 451;
		}
		if (weaponName.contains("karil")) {
			return 2075;
		}
		if (weaponName.contains("bow") && !weaponName.contains("'bow") && !weaponName.contains("karil")) {
			return 426;
		}
		if (weaponName.contains("'bow") && !weaponName.contains("karil")) {
			return 4230;
		}
		if (weaponName.contains("hasta") || weaponName.contains("spear") || weaponName.contains("lance")) {
			switch(c.fightMode) {
			case 0:
			return 428;
			case 1:
				return 1665;
			case 2:
				return 401;
			case 3:
				return 2081;
			}
		}
		if (weaponName.contains("rapier")) {
			switch(c.fightMode) {
			case 0:
			case 1:
			case 2:
				return 386;
			}
		}
		switch (c.playerEquipment[c.playerWeapon]) { // if you don't want to use strings
			case 9703:
				return 412;
				
			case 33782:
			case 33781:
			case 33783:
				return 426;
				
			case 13263:
				return 3298;
	
			case 6522:
				return 2614;
			case 11959:
			case 10034:
			case 10033:
			case 33466:
				return 2779;
			case 11791:
			case 12904:
				return 440;
			case 8004:
			case 7960:
				return 2075;
			case 12848:
			case 4153: // granite maul
				return 1665;
			case 4726: // guthan
				return 2080;
			case 4747: // torag
				return 0x814;
			case 4710: // ahrim
			case 33346:
				return 406;
			case 4755: // verac
			case 33015://viggora chainmace
			case 33016:
				return 2062;
			case 4734: // karil
				return 2075;
			case 4151:
			case 33526:
			case 12773:
			case 12774:
			case 12006:
				return 1658;
			case 6528:
				return 2661;
			case 10887:
				return 5865;
			default:
				return 451;
		}
	}

	public static int getBlockEmote(Player c) {
		String shield = ItemAssistant.getItemName(c.playerEquipment[c.playerShield]).toLowerCase();
		String weapon = ItemAssistant.getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase();
		if (shield.contains("defender"))
			return 4177;
		if (shield.contains("2h") && c.playerEquipment[c.playerWeapon] != 7158)
			return 7050;
		if (shield.contains("book") || (weapon.contains("wand") || (weapon.contains("staff") || weapon.contains("trident"))))
			return 420;
		if (shield.contains("shield"))
			return 1156;
		if (shield.contains("warhammer"))
			return 397;
		if (shield.contains("bulwark"))
			return 7512;
		if (shield.contains("elder maul") || shield.contains("anger battleaxe") || shield.contains("chaotic maul"))
			return 7517;
		switch (c.playerEquipment[c.playerWeapon]) {
		case 1734:
		case 411:
			return 3895;
		case 1724:
			return 3921;
		case 1709:
			return 3909;
		case 1704:
			return 3916;
		case 1699:
			return 3902;
		case 1689:
			return 3890;
		case 4755:
			return 2063;
		case 33015:
			return 2063;
		case 33016:
			return 2063;
		case 14484:
			return 397;
		case 12848:
		case 4153:		
		case 13263:
			return 1666;
		case 13265:
		case 13267:
		case 13269:
		case 13271:
			return 3295;
		case 7158:
		case 33031:
		case 22324:
			return 410;
		case 4151:
		case 33526:
		case 12773:
		case 12774:
		case 12006:
			return 1659;

		case 11802:
		case 11806:
		case 11808:
		case 11804:
		case 11838:
		case 12809:
		case 11730:
			return 7056;
		case -1:
			return 424;
		default:
			return 424;
		}
	}

	public static int getAttackDelay(Player c, String s) {
		if (c.usingMagic) {
			if (c.spellId == 52 || c.spellId == 53) {
				return 4;
			}
			switch (MagicData.MAGIC_SPELLS[c.spellId][0]) {
			case 12871: // ice blitz
			case 13023: // shadow barrage
			case 12891: // ice barrage
				return 5;

			default:
				return 5;
			}
		}
		if (c.playerEquipment[c.playerWeapon] == -1)
			return 4;// unarmed
		switch (c.playerEquipment[c.playerWeapon]) {
		case 12926:
			return c.playerIndex > 0 ? 4 : 3;
		case 12765:
		case 12766:
		case 12767:
		case 12768:
		case 11235:
			return 9;
		case 12424:
		case 11838:
		case 12809:
			return 4;
		case 6528:
		case 19478:
		case 19481:
			return 7;
		case 10033:
		case 10034:
		case 21012:
		case 33117:
			return 5;
		case 9703:
			return 5;
		}
		if (s.endsWith("greataxe"))
			return 7;
		else if (s.equals("torags hammers"))
			return 5;
		else if (s.equals("barrelchest anchor"))
			return 7;
		else if (s.equals("guthans warspear"))
			return 5;
		else if (s.equals("veracs flail"))
			return 5;
		else if (s.equals("ahrims staff"))
			return 6;
		else if (s.contains("staff")) {
			if (s.contains("zamarok") || s.contains("guthix") || s.contains("saradomian") || s.contains("slayer") || s.contains("ancient") || s.contains("trident"))
				return 4;
			else
				return 5;
		} else if (s.contains("bow")) {
			if (s.contains("composite") || s.equals("seercull"))
				return 5;
			else if (s.contains("aril"))
				return 4;
			else if (s.contains("Ogre"))
				return 8;
			else if (s.contains("short") || s.contains("hunt") || s.contains("sword") || s.startsWith("craw"))
				return 4;
			else if (s.contains("long") || s.contains("crystal bo"))
				return 6;
			else if (s.contains("'bow") && !s.contains("armadyl"))
				return 6;
			return 5;
		} else if (s.contains("dagger"))
			return 4;
		else if (s.contains("godsword") || s.contains("2h"))
			return 6;
		else if (s.contains("longsword") || s.contains("elder maul") || s.contains("chaotic maul"))
			return 5;
		else if (s.contains("sword") || s.contains("bulwark"))
			return 4;
		else if (s.contains("scimitar") || s.contains("of the dead"))
			return 4;
		else if (s.contains("mace"))
			return 5;
		else if (s.contains("battleaxe"))
			return 6;
		else if (s.contains("pickaxe"))
			return 5;
		else if (s.contains("thrownaxe"))
			return 5;
		else if (s.contains("axe"))
			return 5;
		else if (s.contains("warhammer"))
			return 6;
		else if (s.contains("2h"))
			return 7;
		else if (s.contains("spear"))
			return 5;
		else if (s.contains("claw"))
			return 4;
		else if (s.contains("halberd"))
			return 7;
		else if (s.equals("granite maul"))
			return 7;
		else if (s.equals("toktz-xil-ak")) // sword
			return 4;
		else if (s.equals("tzhaar-ket-em")) // mace
			return 5;
		else if (s.equals("tzhaar-ket-om")) // maul
			return 7;
		else if (s.equals("toktz-xil-ek")) // knife
			return 4;
		else if (s.equals("toktz-xil-ul")) // rings
			return 4;
		else if (s.equals("toktz-mej-tal")) // staff
			return 6;
		else if (s.contains("whip") || s.contains("tentacle") || s.contains("abyssal bludgeon"))
			return 4;
		else if (s.contains("rapier") || s.contains("saeldor"))
			return 3;
		else if (s.contains("dart"))
			return 3;
		else if (s.contains("knife"))
			return 3;
		else if (s.contains("anger"))
			return 2;
		else if (s.contains("infernal bow") || s.contains("infernal longsword"))
			return 2;
		else if (s.contains("javelin"))
			return 6;
		else if (s.contains("hasta")) {
			if (s.contains("zamorakian")) {
				return 4;
			}
			return 5;
		}
		return 5;
	}

	public static int getHitDelay(Player c, int i, String weaponName) {
		if (c.usingMagic) {
			switch (MagicData.MAGIC_SPELLS[c.spellId][0]) {
			case 12891:
				return 4;
			case 12871:
				return 6;
			default:
				return 4;
			}
		}
		if (weaponName.contains("dart")) {
			return 3;
		}
		if (weaponName.contains("knife") || weaponName.contains("javelin") || weaponName.contains("thrownaxe")) {
			return 3;
		}
		if (weaponName.contains("cross") || weaponName.contains("c'bow")) {
			return 5;
		}
		if (weaponName.contains("ballista")) {
			return 5;
		}
		if (weaponName.contains("bow") && !c.dbowSpec || weaponName.startsWith("craw")) {
			return 4;
		} else if (c.dbowSpec) {
			return 4;
		}

		switch (c.playerEquipment[c.playerWeapon]) {
		case 33531:
		case 33536:
			return 4;
		case 6522: // Toktz-xil-ul
			return 3;
		case 10887:
			return 3;
		case 10034:
		case 10033:
			return 3;
		default:
			return 2;
		}
	}

	public static int npcDefenceAnim(int i) {
		return BlockAnimation.handleEmote(i);
	}
}