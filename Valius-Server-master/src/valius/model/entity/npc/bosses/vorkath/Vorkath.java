package valius.model.entity.npc.bosses.vorkath;
import java.util.HashMap;
import java.util.Random;

import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.npc.NPC;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Hitmark;
import valius.world.World;
import valius.world.objects.GlobalObject;

public class Vorkath {
	/*
	 * How much toxic clouds hurt player for 
	 */
	public static final int venomDamage = 10;
	
	
	/*
	 * How much Vorkaths range attack hurts for
	 */
	public static final int rangeProjectileDamage = 26;
	
	
	/*
	 * How much Vorkaths spitfireball attack hurts for
	 */
	public static final int spitFireDamage = 13;
	
	
	/*
	 * If Vorkath crab has spawned or not
	 */
	public boolean crabSpawn = false;//if the evil crab has spawned or not
	
	
	/*
	 * The type of attack Vorkath is attempting to execute
	 */
	public int attackType = 0;
	
	
	/*
	 * How many quick fireballs vorkath shoots before the venom stage ends
	 */
	public int spitFireCharges = 20;
	
	
	/*
	 * If the venom stage is active or not/event starts when vorkath is below 50% health
	 */
	public boolean venomStage = false;

	
	/*
	 * if the player completed the venom stage or not
	 */
	public boolean venomStageCompleted = false;
	
	
	/*
	 * All toxic cloud locations
	 */
	HashMap <Integer,Integer> poisonLocations = new HashMap<Integer,Integer>();
	
	
	/*
	 * if vorkath is in the middle of executing an attack
	 */
	public boolean attacking = false;//this stops vorkath from sending another attack before previous attack executes
	
	
	
	/* VORKATHS DIFFERENT ATTACKS
	 * 
	 * 
	 * attackStyle = 0   <-----regular dragon fire (Magic)
	 * attackStyle = 1   <-----green dragon fire (Magic)(Poisons player)
	 * attackStyle = 2   <-----blue dragon fire (Magic)
	 * attackStyle = 3   <-----white dragon fire (Magic)(Freezes player & spawns kamakazee crab)
	 * attackStyle = 4    <-----pink dragon fire (Magic)(Turns off players prayer)
	 * attackStyle = 5   <-----spike ball (range/instant damage)
	 * * attackStyle = 6   <-----flying giant dragon fire ball (magic)(1 hits you if you are standing in same spot when casted)
	* attackStyle = 7   <-----spit fire (fire/non dodgeable)(covers map with venom pools, and machine guns fire balls)
	* Standing on green puddles heals boss
	 */
	
	

	
	private Player player; //player in instance
	

	public static int[] lootCoordinates = { 2268, 4061 };//where the loot spawns for player when vorkath dies
	
	/*
	 * This gets the players X&Y and shoots the giant fire ball and small spitfire balls at that location even if player moves
	 */
	public int playerX;
	public int playerY;
	
	
	/*
	 * Constructor for Vorkath
	 */
public Vorkath(Player p){
		this.player = p;
	}

	
/*
 * Checks if player is inVorkath and if he is standing on the left or right of vorkath
 * -If player is on left of vorkath the crab spawns on right
 * -If player is on right of vorkath the crab spawns on left
 */
	public static boolean inVorkath(Player player) {
		return (player.getX() > 2255 && player.getX() < 2288 && player.getY() > 4053 && player.getY() < 4083);
	}
	public static boolean leftOfVorkath(Player player) {
		return (player.getX() < 2272);
	}
	public static boolean rightOfVorkath(Player player) {
		return (player.getX() >= 2272 );
	}
	public static boolean middleOfVorkath(Player player) {
		return (player.getY() >= 4060 && player.getY() <= 4069);
	}
	
	
	/*
	 * resets variables when instance is done or reset
	 */
	public void refreshInstance() {
		attacking = false;
		setAttackType(0);
		setCrabSpawn(false);
		setVenomStage(false);
		setVenomStageCompleted(false);
		setSpitFireCharges(20);
		poisonLocations.clear();
		playerX = 0;
		playerY = 0;
	}

	/*
	 *starts Vorkath battle
	 */
	public void poke(Player player, NPC npc) {
		refreshInstance();
		if(player.getHeight() == 0) {//if player is not in a instance
			player.sendMessage("Vorkath isn't interested in fighting right now... try rejoining the instance.");
			return;
		}
		npc.requestTransform(8027);
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				npc.requestTransform(8028);
			}

			@Override
			public void stop() {
			}
		}, 7);

	}

	
	/*
	 * Takes player into the Vorkath area
	 */
	public void enterInstance(Player player, int instance) {
		refreshInstance();
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (player.getY() == 4052 && player.getX() != 2272) {
					player.setForceMovement(2272, 4054, 10, 10, "NORTH", 1660);
				}
				if (player.getY() == 4052 && player.getX() == 2272) {
					player.setForceMovement(player.getX(), 4054, 10, 10, "NORTH", 839);
					player.getPA().movePlayer(player.getX(), player.getY(), player.getIndex() * 4);
					World.getWorld().getNpcHandler().spawnNpc(player, 8026, 2272, 4065, player.getIndex() * 4, 0, 750,
							player.antifireDelay > 0 ? 0 : 61, 560, 114, true, false);
					container.stop();
				}
			}

			@Override
			public void stop() {
			}
		}, 1);
	}

	
	/*
	 * Chooses random attack
	 */
	public int getAttack() {
		Random randomAttack = new Random();
		return attackType = randomAttack.nextInt(7);//SPIT FIRE NOT IN RANDOM ATTACKS
	}
	

	
	
	/*
	 * Checks which attack vorkath is doing and executes the effect
	 */
	public void executeAttack(Player p,int type) {//executes the different type of attacks
		if(attacking == false) {
			return;
		}
		if(crabSpawn == true) {//so attacks wont overlap when this stage is active
			return;
		}
		switch(type) {
		case 0://regular dragon fire
			break;
			
		case 1://attack that poisons player
			break;
			
		case 2://blue dragon fire no special effects
			break;
			
		case 3://vorkath attack that spawns crab and freezes player
			if(crabSpawn == false) {
			crabSpawn = true;
			spawnCrab();
			p.freezeTimer = 100;
			}
			break;
			
		case 4://dragon fire attack that resets prayer
			p.getCombat().resetPrayers();
			break;
			
		case 5://VORKATH range hits will hit you no matter what
			p.appendDamage(rangeProjectileDamage, Hitmark.HIT);
			break;
			
		case 6://big fire attack that 1 hits if you dont move
			p.getPA().stillGfx(157, playerX, playerY, 50, 0);
			if(p.getX() == playerX && p.getY() == playerY) {
				p.appendDamage(200, Hitmark.HIT);
			}
			playerX = 0;
			playerY = 0;
			break;
		case 7://spit fire attack that shoots rapid
			poisonLocations.put(playerX, playerY);//adds cloud position
			
			tickVenom();//sees if player is in a toxic cloud, if so damage is applied
			
			p.getPA().stillGfx(157, playerX, playerY, 50, 0);
			
			World.getWorld().getGlobalObjects().add(new GlobalObject(11700, playerX, playerY, player.getHeight(), 0, 10, 30, -1));//poision cloud
			
			if(spitFireCharges <= 0) {//if vorkath has no more spitfires to shoot the venom stage is complete
				venomStageCompleted = true;
				poisonLocations.clear();//after stage is done the venom spots are cleared
			}
			
			spitFireCharges -= 1;//everytime vorkath shoots a bolt the charge number goes down til it hits 0
			
			if(p.getX() == playerX && p.getY() == playerY) {//if player doesnt move they get hit
				p.appendDamage(spitFireDamage, Hitmark.HIT);
				return;
			} 
			
			
			if(venomStageCompleted == false) {//makes it so vorkath doesnt get a new attack while in this phase
				return;
			}
			break;
		}
		attacking = false;
		getAttack();//chooses a new random attack style after stage is complete
		}
	
	
	
	public void spawnCrab() {
		if(middleOfVorkath(player) == true) {//if player is in the middle of the map the crab spawns on Y Axis
			World.getWorld().getNpcHandler().spawnNpc(player, 8062, player.getX(), player.getY()+8, player.getHeight(), 1, 100, 200, 200, 200, true, true);
		return;
		}
		/*
		 * if player is on top or bottom of vorkath crab spawns on X axis
		 */
		if(rightOfVorkath(player) == true) {
			World.getWorld().getNpcHandler().spawnNpc(player, 8062, player.getX()-8, player.getY(), player.getHeight(), 1, 100, 200, 200, 200, true, true);
			} else {
				World.getWorld().getNpcHandler().spawnNpc(player, 8062, player.getX()+8, player.getY(), player.getHeight(), 1, 100, 200, 200, 200, true, true);
			}
	}
	
	
	/*
	 * Everytime vorkath shoots a spitfire attack if player is in a venom cloud he gets ticked for damage
	 */
	public void tickVenom() {//attempts to tick venom on player
	if(poisonLocations.containsKey(player.getX())&& poisonLocations.containsValue(player.getY())) {
		player.appendDamage(venomDamage, Hitmark.POISON);
	}

	}
	
	
	
	
	/*
	 * activates venom stage
	 */
	public void startVenomStage() {//spawns acid
		venomStage = true;
		attackType = 7;
	}
	
	
	
	/*
	 * when player leaves vorkath
	 */
	public void exit(Player player) {
		refreshInstance();
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (player.getY() == 4054 && player.getX() != 2272) {
					player.setForceMovement(2272, 4052, 10, 10, "SOUTH", 1660);
				}
				if (player.getY() == 4054 && player.getX() == 2272) {
					player.setForceMovement(player.getX(), 4052, 10, 10, "SOUTH", 839);
					player.getPA().movePlayer(player.getX(), player.getY(), 0);
					container.stop();
				}
			}

			@Override
			public void stop() {
			}
		}, 1);

	}



	public boolean isCrabSpawn() {
		return crabSpawn;
	}



	public void setCrabSpawn(boolean crabSpawn) {
		this.crabSpawn = crabSpawn;
	}



	public int getAttackType() {
		return attackType;
	}



	public void setAttackType(int attackType) {
		this.attackType = attackType;
	}



	public int getSpitFireCharges() {
		return spitFireCharges;
	}



	public void setSpitFireCharges(int spitFireCharges) {
		this.spitFireCharges = spitFireCharges;
	}



	public boolean isVenomStage() {
		return venomStage;
	}



	public void setVenomStage(boolean venomStage) {
		this.venomStage = venomStage;
	}



	public boolean isVenomStageCompleted() {
		return venomStageCompleted;
	}



	public void setVenomStageCompleted(boolean venomStageCompleted) {
		this.venomStageCompleted = venomStageCompleted;
	}



	public static int getVenomdamage() {
		return venomDamage;
	}

}