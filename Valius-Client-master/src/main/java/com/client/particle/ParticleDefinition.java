package com.client.particle;
import java.util.Random;

import com.client.cache.graphics.Sprite;

public class ParticleDefinition {

    public static final Random RANDOM = new Random(System.currentTimeMillis());

    private Vector gravity;

    private float startSize = 1f;
    private float endSize = 1f;

    public int particleDepth;

    private int startColor = -1;
    private int endColor = -1;

    private Vector startVelocity = Vector.ZERO;
    private Vector endVelocity = Vector.ZERO;

    private SpawnShape spawnShape = new PointSpawnShape(Vector.ZERO);

    private float startAlpha = 1f;
    private float endAlpha = 0.05f;

    private int lifeSpan = 1;
    private int spawnRate = 1;
    private Sprite sprite;
    private Vector velocityStep;
    private int colorStep;
    private float sizeStep;
    private float alphaStep;
 // each definition is a new # (0, 1, 2, 3) //second # in particle attachment.
  //first # is vertex = placement found in datmaker
    public static ParticleDefinition[] cache = new ParticleDefinition[] {
            new ParticleDefinition() { {
            	//unupgraded max cape [0]
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));//change the direction of the particle flow
                setLifeSpan(19);//how long they will continue to stay
                setStartColor(0xFFFFFF);//color (make sure this and the setColorStep uses the same # or the one below)
                setSpawnRate(4);
                setStartSize(0.80f);//how bright the particles are when they start
                setEndSize(0);//how big particles are when they finish
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}//use either this or the same color id from setStartColor
            },
            new ParticleDefinition() { {
            	//Infernal max cape [1]
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0xFF3300);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0xFF3300);}
            },
            new ParticleDefinition() { {
            	//custom shadow sword [2]
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0x440644);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x440644);}
            },
            new ParticleDefinition() { {
            	//Agility master cape [3]
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0xFF0000);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//Attack master cape 4
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0xFFFF00);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//Construction master cape 5
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0xFF8900);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//Cooking master cape 6
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0x990000);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//Crafting master cape 7
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0x663300);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//Defence master cape 8
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0x00FFFF);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//Farming master cape 9
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0x00CC00);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//Firemaking master cape 10
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0xFFA200);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//Fishing master cape 11
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0xC7C707);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//Fletching master cape 12
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0x057042);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//herblore master cape 13
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0xFFFF33);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//hitpoint master cape 14
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0xFFFFFF);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//hunter master cape 15
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0x000000);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//magic master cape 16
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0x0000CC);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//mining master cape 17
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0x66FFFF);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//prayer master cape 18
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0xFFFF00);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//range master cape 19
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0xCC6600);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//runecrafting master cape 20
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0xFFFF33);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//slayer master cape 21
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0x660000);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//smithing master cape 22
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0xB98F06);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//strength master cape 23
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0xCC0000);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//thieving master cape 24
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0x000000);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x660033);}
            },
            new ParticleDefinition() { {
            	//woodcutting master cape 24
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0x006633);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//Abyssal Scythe
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0x006633);
                setSpawnRate(4);
                setStartSize(1f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//Goliath gloves
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 4 / 1, 0));
                setLifeSpan(10);
                setStartColor(0xFF3300);
                setSpawnRate(8);
                setStartSize(0.60f);
                setEndSize(0.10f);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0xFF3300);}
            },
            new ParticleDefinition() { {
            	//white longsword 28
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 8 / 1, 0));
                setLifeSpan(16);
                setStartColor(0x0461F7);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(1);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//swift gloves
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 4 / 1, 0));
                setLifeSpan(10);
                setStartColor(0x4ae805);
                setSpawnRate(8);
                setStartSize(0.60f);
                setEndSize(0.10f);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x4ae805);}
            },
            new ParticleDefinition() { {
            	//spellcaster gloves
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 4 / 1, 0));
                setLifeSpan(10);
                setStartColor(0x0563e8);
                setSpawnRate(8);
                setStartSize(0.60f);
                setEndSize(0.10f);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x0563e8);}
            },
            new ParticleDefinition() { {
            	//custom item npc
                setStartVelocity(new Vector(0, -8, 0));
                setEndVelocity(new Vector(0, -8, 0));
                setGravity(new Vector(0, 2 / 4, 1));
                setLifeSpan(50);
                setStartColor(0x66FFFF);
                setSpawnRate(8);
                setStartSize(1.50f);
                setEndSize(1);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x66FFFF);}
            },
            new ParticleDefinition() { {
            	//tokhaar
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 4 / 1, 0));
                setLifeSpan(15);
                setStartColor(0xFF3300);
                setSpawnRate(6);
                setStartSize(1);
                setEndSize(0.25f);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0xFF3300);}
            },
            new ParticleDefinition() { {
            	//blood helmet 33
                setStartVelocity(new Vector(0, 0, 0));
                setEndVelocity(new Vector(0, 0, 0));
                setGravity(new Vector(0, 1 / 1, 0));
                setLifeSpan(5);
                setStartColor(0x00CC00);
                setSpawnRate(5);
                setStartSize(0.40f);
                setEndSize(0);
                setStartAlpha(0.30f);
                updateSteps();
                setColorStep(0x000000);}
            },
            new ParticleDefinition() { {
            	//Comp cape 34
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0xFFFFFF);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0xFFFFFF);}
            },
            new ParticleDefinition() { {
            	//comp cape t 35
                setStartVelocity(new Vector(0, -2, 0));
                setEndVelocity(new Vector(0, -2, 0));
                setGravity(new Vector(0, 2 / 4, 0));
                setLifeSpan(19);
                setStartColor(0xC7C707);
                setSpawnRate(4);
                setStartSize(0.80f);
                setEndSize(0);
                setStartAlpha(0.095f);
                updateSteps();
                setColorStep(0x000000);}
            },
    };

    public final SpawnShape getSpawnedShape() {
        return spawnShape;
    }

    public final float getStartAlpha() {
        return startAlpha;
    }

    public final void setStartAlpha(float startAlpha) {
        this.startAlpha = startAlpha;
    }

    public final float getAlphaStep() {
        return alphaStep;
    }

    public final Sprite getSprite() {
        return sprite;
    }

    public final void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public final int getSpawnRate() {
        return this.spawnRate;
    }

    public final void setSpawnRate(int spawnRate) {
        this.spawnRate = spawnRate;
    }

    public final void setStartSize(float startSize) {
        this.startSize = startSize;
    }

    public final float getStartSize() {
        return startSize;
    }

    public float getEndSize() {
        return endSize;
    }

    public int getEndColor() {
        return endColor;
    }

    public final void setEndSize(float endSize) {
        this.endSize = endSize;
    }

    public final int getStartColor() {
        return startColor;
    }

    public final void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    public final Vector getStartVelocity(int id) {
        switch (id) {
            default:
                return new Vector(this.startVelocity.getX() + randomWithRange(-1, 1), this.startVelocity.getY() + randomWithRange(0, 0), this.startVelocity.getZ() + randomWithRange(-1, 1));
        }
    }

    public Vector getGravity() {
        return gravity;
    }

    public void setGravity(Vector gravity) {
        this.gravity = gravity;
    }

    public final void setStartVelocity(Vector startVelocity) {
        this.startVelocity = startVelocity;
    }

    public Vector getEndVelocity() {
        return endVelocity;
    }

    public final void setEndVelocity(Vector endVelocity) {
        this.endVelocity = endVelocity;
    }

    public final int getLifeSpan() {
        return lifeSpan;
    }

    public final void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public final void setColorStep(int colorStep) {
        this.colorStep = colorStep;
    }

    public final float getSizeStep() {
        return sizeStep;
    }

    public final Vector getVelocityStep() {
        return velocityStep;
    }

    public final int getColorStep() {
        return colorStep;
    }

    public final void updateSteps() {
        this.sizeStep = (endSize - startSize) / (lifeSpan * 1f);
        this.colorStep = (endColor - startColor) / lifeSpan;
        this.velocityStep = endVelocity.subtract(startVelocity).divide(lifeSpan);
        this.alphaStep = (endAlpha - startAlpha) / lifeSpan;
    }
}
