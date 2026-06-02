package valius.model.entity.npc.combat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ScriptSettings {
	
	int[] npcIds() default {};
	String[] npcNames() default {};

}
