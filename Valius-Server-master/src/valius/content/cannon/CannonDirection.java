package valius.content.cannon;

import lombok.Getter;
import valius.model.Location;

@Getter
public enum CannonDirection {

	  NORTH(0, 515),
      NORTH_EAST(1, 516),
      EAST(2, 517),
      SOUTH_EAST(3, 518),
      SOUTH(4, 519),
      SOUTH_WEST(5, 520),
      WEST(6, 521),
      NORTH_WEST(7, 514);
	
     private CannonDirection(int direction, int animationId) {
         this.direction = direction;
         this.animationId = animationId;
     }
     

     public static CannonDirection forId(int direction) {
         for (CannonDirection facingState : CannonDirection.values()) {
             if (facingState.getDirection() == direction) {
                 return facingState;
             }
         }
         return CannonDirection.NORTH;
     }
     
     public CannonDirection next() {
    	return forId(direction + 1); 
     }
     
     public boolean validArea(Location center, Location location) {
    	 switch (this) {
         case NORTH:
            return (location.getY() > center.getY() && location.getX() >= center.getX() - 1 && location.getX() <= center.getX() + 1);
         case NORTH_EAST:
             return (location.getX() >= center.getX() + 1 && location.getY() >= center.getY() + 1);
         case EAST:
             return (location.getX() > center.getX() && location.getY() >= center.getY() - 1 && location.getY() <= center.getY() + 1);
         case SOUTH_EAST:
             return (location.getY() <= center.getY() - 1 && location.getX() >= center.getX() + 1);
         case SOUTH:
             return (location.getY() < center.getY() && location.getX() >= center.getX() - 1 && location.getX() <= center.getX() + 1);
         case SOUTH_WEST:
             return (location.getX() <= center.getX() - 1 && location.getY() <= center.getY() - 1);
         case WEST:
             return (location.getX() < center.getX() && location.getY() >= center.getY() - 1 && location.getY() <= center.getY() + 1);
         case NORTH_WEST:
             return (location.getX() <= center.getX() - 1 && location.getY() >= center.getY() + 1);
         default:   	 
        	 return false;
    	 }
     }

	 private int direction;
     private int animationId;

}
