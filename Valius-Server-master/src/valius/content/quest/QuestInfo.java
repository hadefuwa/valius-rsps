package valius.content.quest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface QuestInfo {

	long questIdentifier();
	String questTabDisplay();
	String[] questInfoLines() default {};
	int amountOfStages();
	
}
