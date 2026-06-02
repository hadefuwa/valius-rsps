package com.client.particle;

import java.util.Random;

public class PointSpawnShape implements SpawnShape {

    private Vector point;

    public PointSpawnShape(Vector point) {
        this.point = point;
    }

    @Override
    public Vector getPoint(Random r) {
        return point.clone();
    }
}
