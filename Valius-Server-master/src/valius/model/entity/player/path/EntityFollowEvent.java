/**
 * 
 */
package valius.model.entity.player.path;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.model.Location;
import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.pets.PetHandler;
import valius.model.entity.path.PathGenerator;
import valius.model.entity.path.RS317PathFinder;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.CombatAssistant;

/**
 * @author ReverendDread
 * Jun 1, 2019
 */
@AllArgsConstructor
public class EntityFollowEvent implements CycleEvent {

	private final Entity entity;
	@Getter @Setter private Entity following;
	
	@Override
	public void execute(CycleEventContainer container) {
		
		//Block if our movement is blocked.
		if (!this.entity.getMovementQueue().canMove()) {
			return;
		}
		
		//Check if npc is dead
		if (this.following.isNPC()) {
			if (this.following.asNPC().isDead) {
				container.stop();
				return;
			}
		}
		
		//Are we in range of the target?
		boolean inRange = true;
		
		//Are we follwing in combat?
		boolean combatFollow = (entity.isNPC() ? (entity.asNPC().underAttack && entity.asNPC().underAttackBy == following.getIndex()) : (entity.isPlayer() ? entity.asPlayer().underAttackBy2 == following.getIndex() : false));
		
		//The targets location
		final Location targetLocation = this.following.getLocation();
		
		// If we're way too far away from eachother, reset following completely.
		if (!this.entity.getLocation().withinDistance(this.following.getLocation(), 16)) {
			
			//The reset state
			boolean reset = true;
			
			if (this.entity.isNPC()) {
				
				NPC npc = this.entity.asNPC();
				
				//Handle pet walking
				if (PetHandler.isPet(npc.getDefinition().getNpcId())) {
					//Move pet to owner
					Player owner = PlayerHandler.getPlayerByIndex(npc.summonedBy).orElse(null);
					//Stop if owner is null
					if (Objects.isNull(owner)) {
						return;
					}
					//Set npcs new location
					Location location = owner.getLocation();
					npc.setX(location.getX());
					npc.setY(location.getY());
					npc.setHeight(location.getZ());
					return;
				}
				
				//Special exceptions
				switch (npc.getDefinition().getNpcId()) {
				case 3127:
					reset = false;
					break;
				}
				
			}
			
			//Reset because to far from target
			if (reset) {
				
				if (this.entity.isPlayer() && this.following.isPlayer()) {
					this.entity.asPlayer().sendMessage("Unable to find " + this.following.asPlayer().getName() + ".");
				}
				
				//Reset the combat
				if (combatFollow) {
					if (this.entity.isNPC()) {
						NPC npc = this.entity.asNPC();
						npc.underAttackBy = 0;
						npc.lastRandomlySelectedPlayer = 0;
						npc.underAttack = false;
						npc.randomWalk = true;	
					} else if (this.entity.isPlayer()) {
						this.entity.asPlayer().underAttackBy = -1;
						this.entity.asPlayer().underAttackBy2 = -1;
					}
				}
				this.entity.getMovementQueue().reset();
				this.entity.getMovementQueue().resetFollowing();
				stop();
				return;
				
			}
			
		}
		
		//The destination we'll be walking to.
		Location destination = null;
		
		// If we're combat following, make sure to only reset movement once we are in attack range.
		if (combatFollow) {
			if (!CombatAssistant.canReach(this.entity, this.following)) {
				inRange = false;
			}
		} else {
			// If two players are following eachother, make them "dance" with
			// each other by marking the destination as their old position.
			if (this.entity.isPlayer() && this.following.isPlayer()) {
				if (this.following.getMovementQueue().isFollowing(this.entity)) {
					destination = this.following.asPlayer().getPreviousLocation();
					inRange = false;
				}
			}
			
			// If we aren't close to the target or we are in a diagonal block, flag as not
			// in range..
			// This will cause our character to continue following the target.
			if (inRange) {
				if (!this.entity.getLocation().withinDistance(targetLocation, this.following.getSize())
						|| this.entity.getLocation().equals(targetLocation)
						|| this.entity.getSize() == 1 && this.following.getSize() == 1 
								&& RS317PathFinder.isInDiagonalBlock(this.entity.getLocation(), targetLocation)) {
					inRange = false;
				}
			}
			
		}
		
		if (this.following.isPlayer()) {
			if (this.entity.isPlayer())
				this.entity.asPlayer().faceUpdate(this.following.isPlayer() ? this.entity.asPlayer().followId + 32768 : this.entity.asPlayer().followId);
			else 
				this.entity.asNPC().faceEntity(this.following);
		}
		
		if (inRange) {
			this.entity.getMovementQueue().reset();
			return;
		}
		
		if (destination == null) {		
			if (!combatFollow/* || character.isNpc() */) {
				destination = PathGenerator.getBasicPath(this.entity, targetLocation);
			} else {
				destination = PathGenerator.getCombatPath(this.entity, targetLocation, this.following.getSize());
			}
		}
		
		if (destination != null) {
			RS317PathFinder.findPath(this.entity, destination.getX(), destination.getY(), false, 16, 16);
		}
		
	}

}
