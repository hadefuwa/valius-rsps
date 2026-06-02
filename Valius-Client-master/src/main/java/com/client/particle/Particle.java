package com.client.particle;

public class Particle {

    private int age;
    private int color;
    private float size;
    private Vector velocity;
    private float alpha;
    private float oldAlpha;
    private boolean dead;
    private int particleDepth;
    private ParticleDefinition def;
    private Vector position;

    public final void tick() {
        if (def != null) {
            ++age;
            if (age >= def.getLifeSpan()) {
                dead = true;
            } else {
                color += def.getColorStep();
                size += def.getSizeStep();
                position.addLocal(velocity);
                velocity.addLocal(def.getVelocityStep());
                alpha += def.getAlphaStep();
                if (def.getGravity() != null) {
                    position.addLocal(def.getGravity());
                }
                if (alpha <= 0f) {
                    alpha = 0.025f;
                }
            }
        }
    }

    public Particle(ParticleDefinition def, Vector position, int definitionID) {
        this(def.getStartColor(), def.getStartSize(), def.getStartVelocity(definitionID).clone(), def.getSpawnedShape().getPoint(ParticleDefinition.RANDOM).addLocal(position), def.getStartAlpha());
        this.def = def;
    }

    public Particle(int color, float size, Vector velocity, Vector position, float alpha) {
        this.age = 0;
        this.dead = false;
        this.def = null;
        this.color = color;
        this.size = size;
        this.velocity = velocity;
        this.position = position;
        this.alpha = alpha;
        this.oldAlpha = alpha;
    }

    public final int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public final float getOldAlpha() {
        return oldAlpha;
    }

    public final int getDepth() {
        return particleDepth;
    }

    public void setDepth(int particleDepth) {
        this.particleDepth = particleDepth;
    }

    public final int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public final float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public final float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public final boolean isDead() {
        return dead;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public final ParticleDefinition getDefinition() {
        return def;
    }

    public final Vector getPosition() {
        return position;
    }
}
