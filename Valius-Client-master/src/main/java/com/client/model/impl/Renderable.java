package com.client.model.impl;

import com.client.collection.Cacheable;
import com.client.model.Model;
import com.client.model.VertexNormal;

public class Renderable extends Cacheable {

    public void renderAtPoint(int rotation, int sineYCurve, int cosineYCurve, int sineXCurve, int cosineXCurve, int distanceX, int distanceZ,
                              int distanceY, long uid) {
        Model model = getRotatedModel();
        if (model != null) {
            modelHeight = model.modelHeight;
            model.renderAtPoint(rotation, sineYCurve, cosineYCurve, sineXCurve, cosineXCurve, distanceX, distanceZ, distanceY, uid);
        }
    }

    public Model getRotatedModel() {
        return null;
    }

    public Renderable() {
        modelHeight = 1000;
    }

    public VertexNormal normals[];
    public int modelHeight;
}
