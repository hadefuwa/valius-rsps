package com.client.model;

import com.client.Client;
import com.client.cache.animation.Frame;
import com.client.cache.animation.FrameBase;
import com.client.cache.provider.ResourceProvider;
import com.client.draw.raster.Raster;
import com.client.draw.raster.Rasterizer3D;
import com.client.io.Buffer;
import com.client.map.SceneGraph;
import com.client.model.impl.Renderable;
import com.client.particle.Particle;
import com.client.particle.ParticleAttachment;
import com.client.particle.ParticleDefinition;
import com.client.particle.Vector;
import com.client.utilities.DataUtils;
import com.google.common.io.Files;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedInputStream;
import java.io.File;

public class Model extends Renderable {

    public static void nullLoader() {
        aClass21Array1661 = null;
        aBooleanArray1663 = null;
        aBooleanArray1664 = null;
        projected_vertex_y = null;
        anIntArray1667 = null;
        camera_vertex_y = null;
        camera_vertex_x = null;
        camera_vertex_z = null;
        anIntArray1671 = null;
        anIntArrayArray1672 = null;
        anIntArray1673 = null;
        anIntArrayArray1674 = null;
        anIntArray1675 = null;
        anIntArray1676 = null;
        anIntArray1677 = null;
        SINE = null;
        COSINE = null;
        hsl2rgb = null;
        lightDecay = null;
    }

    public void convertTexturesTo317(short[] textureIds, int[] texa, int[] texb, int[] texc, byte[] texture_coordinates) {
        int set = 0;
        int set2 = 0;
        int max = 50;
        if (textureIds != null) {
            textured_face_a = new int[numberOfTriangleFaces];
            textured_face_b = new int[numberOfTriangleFaces];
            textured_face_c = new int[numberOfTriangleFaces];


            for (int i = 0; i < numberOfTriangleFaces; i++) {
                if (textureIds[i] == -1 && face_render_type[i] == 2) {
                    face_color[i] = 65535;
                    face_render_type[i] = 0;
                }
                if (textureIds[i] >= max || textureIds[i] < 0 || textureIds[i] == 39) {
                    face_render_type[i] = 0;
                    continue;
                }
                face_render_type[i] = 2 + set2;
                set2 += 4;
                int a = face_a[i];
                int b = face_b[i];
                int c = face_c[i];
                face_color[i] = textureIds[i];


                int texture_type = -1;
                if (texture_coordinates != null) {
                    texture_type = texture_coordinates[i] & 0xff;
                    if (texture_type != 0xff)
                        if (texa[texture_type] >= camera_vertex_x.length || texb[texture_type] >= camera_vertex_y.length
                                || texc[texture_type] >= camera_vertex_z.length)
                            texture_type = -1;
                }
                if (texture_type == 0xff)
                    texture_type = -1;


                textured_face_a[set] = texture_type == -1 ? a : texa[texture_type];
                textured_face_b[set] = texture_type == -1 ? b : texb[texture_type];
                textured_face_c[set++] = texture_type == -1 ? c : texc[texture_type];


            }
            numberOfTexturesFaces = set;
        }
    }

    public void convertTexturesTo317(short[] textureIds, int[] texa, int[] texb, int[] texc, boolean osrs,
                                     byte[] texture_coordinates) {
        int set = 0;
        int set2 = 0;
        int max = Rasterizer3D.textureAmount;
        if (textureIds != null) {
            textured_face_a = new int[numberOfTriangleFaces];
            textured_face_b = new int[numberOfTriangleFaces];
            textured_face_c = new int[numberOfTriangleFaces];

            for (int i = 0; i < numberOfTriangleFaces; i++) {
                if (textureIds[i] == -1 && face_render_type[i] == 2) {
                    face_render_type[i] = 0;
                }
                if (textureIds[i] >= max || textureIds[i] < 0 || textureIds[i] == 39) {
                    face_render_type[i] = 0;
                    continue;
                }
                face_render_type[i] = 2 + set2;
                set2 += 4;
                int a = face_a[i];
                int b = face_b[i];
                int c = face_c[i];
                face_color[i] = textureIds[i];

                int texture_type = -1;
                if (texture_coordinates != null) {
                    texture_type = texture_coordinates[i] & 0xff;
                    if (texture_type != 0xff)
                        if (texa[texture_type] >= camera_vertex_x.length || texb[texture_type] >= camera_vertex_y.length
                                || texc[texture_type] >= camera_vertex_z.length)
                            texture_type = -1;
                }
                if (texture_type == 0xff)
                    texture_type = -1;

                textured_face_a[set] = texture_type == -1 ? a : texa[texture_type];
                textured_face_b[set] = texture_type == -1 ? b : texb[texture_type];
                textured_face_c[set++] = texture_type == -1 ? c : texc[texture_type];

            }
            numberOfTexturesFaces = set;
        }
    }

    public void method476(int i, int j) {
        for (int k = 0; k < numberOfTriangleFaces; k++)
            if (face_color[k] == i)
                face_color[k] = j;

    }

    public void replaceColor(int i, int j, int tex) {
        if (tex == -1) {
            for (int k = 0; k < numberOfTriangleFaces; k++)
                if (face_color[k] == i)
                    face_color[k] = j;
        } else {
            numberOfTexturesFaces = numberOfTriangleFaces;
            int set2 = 0;
            if (face_render_type == null)
                face_render_type = new int[numberOfTriangleFaces];
            if (face_color == null)
                face_color = new int[numberOfTriangleFaces];
            textured_face_a = new int[numberOfTriangleFaces];
            textured_face_b = new int[numberOfTriangleFaces];
            textured_face_c = new int[numberOfTriangleFaces];
            for (int i3 = 0; i3 < numberOfTriangleFaces; i3++) {
                if (face_color[i3] != 0) {
                    face_color[i3] = tex;
                    face_render_type[i3] = 3 + set2;
                    set2 += 4;
                    textured_face_a[i3] = face_a[i3];
                    textured_face_b[i3] = face_b[i3];
                    textured_face_c[i3] = face_c[i3];
                }
            }
        }
    }


    public void read525Model(byte abyte0[], int modelID) {
        Buffer nc1 = new Buffer(abyte0);
        Buffer nc2 = new Buffer(abyte0);
        Buffer nc3 = new Buffer(abyte0);
        Buffer nc4 = new Buffer(abyte0);
        Buffer nc5 = new Buffer(abyte0);
        Buffer nc6 = new Buffer(abyte0);
        Buffer nc7 = new Buffer(abyte0);
        nc1.position = abyte0.length - 23;
        int numVertices = nc1.readShort();
        int numTriangles = nc1.readShort();
        
        numVertices = ModelHeader.kll(numVertices);
        numTriangles = ModelHeader.kll455(numTriangles);
        numVertices = ModelHeader.err(numVertices);
        numTriangles = ModelHeader.err(numTriangles);

        int numTexTriangles = nc1.readUnsignedByte();
        ModelHeader ModelDef_1 = aClass21Array1661[modelID] = new ModelHeader();

        ModelDef_1.aByteArray368 = abyte0;
        ModelDef_1.anInt369 = numVertices;
        ModelDef_1.anInt370 = numTriangles;
        ModelDef_1.anInt371 = numTexTriangles;
        int l1 = nc1.readUnsignedByte();
        boolean bool = (0x1 & l1 ^ 0xffffffff) == -2;
        int i2 = nc1.readUnsignedByte();
        int j2 = nc1.readUnsignedByte();
        int k2 = nc1.readUnsignedByte();
        int l2 = nc1.readUnsignedByte();
        int i3 = nc1.readUnsignedByte();
        int j3 = nc1.readShort();
        int k3 = nc1.readShort();
        int l3 = nc1.readShort();
        int i4 = nc1.readShort();
        int j4 = nc1.readShort();
        int k4 = 0;
        int l4 = 0;
        int i5 = 0;
        byte[] x = null;
        byte[] O = null;
        byte[] J = null;
        byte[] F = null;
        byte[] cb = null;
        byte[] gb = null;
        byte[] lb = null;
        int[] kb = null;
        int[] y = null;
        int[] N = null;
        short[] D = null;
        int[] triangleColours2 = new int[numTriangles];
        if (numTexTriangles > 0) {
            O = new byte[numTexTriangles];
            nc1.position = 0;
            for (int j5 = 0; j5 < numTexTriangles; j5++) {
                byte byte0 = O[j5] = nc1.readSignedByte();
                if (byte0 == 0) {
                    k4++;
                }
                if (byte0 >= 1 && byte0 <= 3) {
                    l4++;
                }
                if (byte0 == 2) {
                    i5++;
                }
            }
        }
        int k5 = numTexTriangles;
        int l5 = k5;
        k5 += numVertices;
        int i6 = k5;
        if (l1 == 1) {
            k5 += numTriangles;
        }
        int j6 = k5;
        k5 += numTriangles;
        int k6 = k5;
        if (i2 == 255) {
            k5 += numTriangles;
        }
        int l6 = k5;
        if (k2 == 1) {
            k5 += numTriangles;
        }
        int i7 = k5;
        if (i3 == 1) {
            k5 += numVertices;
        }
        int j7 = k5;
        if (j2 == 1) {
            k5 += numTriangles;
        }
        int k7 = k5;
        k5 += i4;
        int l7 = k5;
        if (l2 == 1) {
            k5 += numTriangles * 2;
        }
        int i8 = k5;
        k5 += j4;
        int j8 = k5;
        k5 += numTriangles * 2;
        int k8 = k5;
        k5 += j3;
        int l8 = k5;
        k5 += k3;
        int i9 = k5;
        k5 += l3;
        int j9 = k5;
        k5 += k4 * 6;
        int k9 = k5;
        k5 += l4 * 6;
        int l9 = k5;
        k5 += l4 * 6;
        int i10 = k5;
        k5 += l4;
        int j10 = k5;
        k5 += l4;
        int k10 = k5;
        k5 += l4 + i5 * 2;
        int[] vertexX = new int[numVertices];
        int[] vertexY = new int[numVertices];
        int[] vertexZ = new int[numVertices];
        int[] facePoint1 = new int[numTriangles];
        int[] facePoint2 = new int[numTriangles];
        int[] facePoint3 = new int[numTriangles];
        verticesParticle = new int[numVertices];
        vertexBones = new int[numVertices];
        face_render_type = new int[numTriangles];
        face_render_priorities = new int[numTriangles];
        face_alpha = new int[numTriangles];
        anIntArray1656 = new int[numTriangles];
        if (i3 == 1) {
            vertexBones = new int[numVertices];
        }
        if (bool) {
            face_render_type = new int[numTriangles];
        }
        if (i2 == 255) {
            face_render_priorities = new int[numTriangles];
        } else {
        }
        if (j2 == 1) {
            face_alpha = new int[numTriangles];
        }
        if (k2 == 1) {
            anIntArray1656 = new int[numTriangles];
        }
        if (l2 == 1) {
            D = new short[numTriangles];
        }
        if (l2 == 1 && numTexTriangles > 0) {
            x = new byte[numTriangles];
        }
        triangleColours2 = new int[numTriangles];
        int[] texTrianglesPoint1 = null;
        int[] texTrianglesPoint2 = null;
        int[] texTrianglesPoint3 = null;
        if (numTexTriangles > 0) {
            texTrianglesPoint1 = new int[numTexTriangles];
            texTrianglesPoint2 = new int[numTexTriangles];
            texTrianglesPoint3 = new int[numTexTriangles];
            if (l4 > 0) {
                kb = new int[l4];
                N = new int[l4];
                y = new int[l4];
                gb = new byte[l4];
                lb = new byte[l4];
                F = new byte[l4];
            }
            if (i5 > 0) {
                cb = new byte[i5];
                J = new byte[i5];
            }
        }
        nc1.position = l5;
        nc2.position = k8;
        nc3.position = l8;
        nc4.position = i9;
        nc5.position = i7;
        int l10 = 0;
        int i11 = 0;
        int j11 = 0;
        for (int k11 = 0; k11 < numVertices; k11++) {
            int l11 = nc1.readUnsignedByte();
            int j12 = 0;
            if ((l11 & 1) != 0) {
                j12 = nc2.method421();
            }
            int l12 = 0;
            if ((l11 & 2) != 0) {
                l12 = nc3.method421();
            }
            int j13 = 0;
            if ((l11 & 4) != 0) {
                j13 = nc4.method421();
            }
            vertexX[k11] = l10 + j12;
            vertexY[k11] = i11 + l12;
            vertexZ[k11] = j11 + j13;
            l10 = vertexX[k11];
            i11 = vertexY[k11];
            j11 = vertexZ[k11];
            if (vertexBones != null) {
                vertexBones[k11] = nc5.readUnsignedByte();
            }
        }
        nc1.position = j8;
        nc2.position = i6;
        nc3.position = k6;
        nc4.position = j7;
        nc5.position = l6;
        nc6.position = l7;
        nc7.position = i8;
        for (int i12 = 0; i12 < numTriangles; i12++) {
            triangleColours2[i12] = nc1.readShort();
            if (l1 == 1) {
                face_render_type[i12] = nc2.readSignedByte();
                if (face_render_type[i12] == 2) {
                    triangleColours2[i12] = 65535;
                }
                face_render_type[i12] = 0;
            }
            if (i2 == 255) {
                face_render_priorities[i12] = nc3.readSignedByte();
            }
            if (j2 == 1) {
                face_alpha[i12] = nc4.readSignedByte();
                if (face_alpha[i12] < 0) {
                    face_alpha[i12] = 256 + face_alpha[i12];
                }
            }
            if (k2 == 1) {
                anIntArray1656[i12] = nc5.readUnsignedByte();
            }
            if (l2 == 1) {
                D[i12] = (short) (nc6.readShort() - 1);
            }
            if (x != null) {
                if (D[i12] != -1) {
                    x[i12] = (byte) (nc7.readUnsignedByte() - 1);
                } else {
                    x[i12] = -1;
                }
            }
        }
        // /fix's triangle issue, but fucked up - no need, loading all 474-
        // models
        /*
         * try { for(int i12 = 0; i12 < numberOfTriangleFaces; i12++) {
		 * triangleColours2[i12] = nc1.readUnsignedWord(); if(l1 == 1){
		 * face_render_type[i12] = nc2.readSignedByte(); } if(i2 == 255){
		 * face_render_priorities[i12] = nc3.readSignedByte(); } if(j2 == 1){
		 * anIntArray1639[i12] = nc4.readSignedByte(); if(anIntArray1639[i12] <
		 * 0) anIntArray1639[i12] = (256+anIntArray1639[i12]); } if(k2 == 1)
		 * anIntArray1656[i12] = nc5.readUnsignedByte(); if(l2 == 1) D[i12] =
		 * (short)(nc6.readUnsignedWord() - 1); if(x != null) if(D[i12] != -1)
		 * x[i12] = (byte)(nc7.readUnsignedByte() -1); else x[i12] = -1; } }
		 * catch (Exception ex) { }
		 */
        nc1.position = k7;
        nc2.position = j6;
        int k12 = 0;
        int i13 = 0;
        int k13 = 0;
        int l13 = 0;
        for (int i14 = 0; i14 < numTriangles; i14++) {
            int j14 = nc2.readUnsignedByte();
            if (j14 == 1) {
                k12 = nc1.method421() + l13;
                l13 = k12;
                i13 = nc1.method421() + l13;
                l13 = i13;
                k13 = nc1.method421() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
            if (j14 == 2) {
                i13 = k13;
                k13 = nc1.method421() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
            if (j14 == 3) {
                k12 = k13;
                k13 = nc1.method421() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
            if (j14 == 4) {
                int l14 = k12;
                k12 = i13;
                i13 = l14;
                k13 = nc1.method421() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
        }
        nc1.position = j9;
        nc2.position = k9;
        nc3.position = l9;
        nc4.position = i10;
        nc5.position = j10;
        nc6.position = k10;
        for (int k14 = 0; k14 < numTexTriangles; k14++) {
            int i15 = O[k14] & 0xff;
            if (i15 == 0) {
                texTrianglesPoint1[k14] = nc1.readShort();
                texTrianglesPoint2[k14] = nc1.readShort();
                texTrianglesPoint3[k14] = nc1.readShort();
            }
            if (i15 == 1) {
                texTrianglesPoint1[k14] = nc2.readShort();
                texTrianglesPoint2[k14] = nc2.readShort();
                texTrianglesPoint3[k14] = nc2.readShort();
                kb[k14] = nc3.readShort();
                N[k14] = nc3.readShort();
                y[k14] = nc3.readShort();
                gb[k14] = nc4.readSignedByte();
                lb[k14] = nc5.readSignedByte();
                F[k14] = nc6.readSignedByte();
            }
            if (i15 == 2) {
                texTrianglesPoint1[k14] = nc2.readShort();
                texTrianglesPoint2[k14] = nc2.readShort();
                texTrianglesPoint3[k14] = nc2.readShort();
                kb[k14] = nc3.readShort();
                N[k14] = nc3.readShort();
                y[k14] = nc3.readShort();
                gb[k14] = nc4.readSignedByte();
                lb[k14] = nc5.readSignedByte();
                F[k14] = nc6.readSignedByte();
                cb[k14] = nc6.readSignedByte();
                J[k14] = nc6.readSignedByte();
            }
            if (i15 == 3) {
                texTrianglesPoint1[k14] = nc2.readShort();
                texTrianglesPoint2[k14] = nc2.readShort();
                texTrianglesPoint3[k14] = nc2.readShort();
                kb[k14] = nc3.readShort();
                N[k14] = nc3.readShort();
                y[k14] = nc3.readShort();
                gb[k14] = nc4.readSignedByte();
                lb[k14] = nc5.readSignedByte();
                F[k14] = nc6.readSignedByte();
            }
        }
        if (i2 != 255) {
            for (int i12 = 0; i12 < numTriangles; i12++) {
                face_render_priorities[i12] = i2;
            }
        }
        face_color = triangleColours2;
        this.numVertices = numVertices;
        this.numberOfTriangleFaces = numTriangles;
        this.vertexX = vertexX;
        this.vertexY = vertexY;
        this.vertexZ = vertexZ;
        face_a = facePoint1;
        face_b = facePoint2;
        face_c = facePoint3;
        convertTexturesTo317(D, texTrianglesPoint1, texTrianglesPoint2, texTrianglesPoint3, false, x);
    }

    public static final byte[] read(String location) {
        try {
            File read = new File(location);
            int size = (int) read.length();
            byte data[] = new byte[size];
            DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(location)));
            input.readFully(data, 0, size);
            input.close();
            return data;
        } catch (Exception exception) {
        }
        return null;
    }

    public Model(int model) {
        if (model == 0) {
            return;
        }
        byte[] data = aClass21Array1661[model].aByteArray368;
        //data = read(signlink.findcachedir() + "/Raw/" + model + ".dat");
        if (data[data.length - 1] == -1 && data[data.length - 2] == -1) {
            read622Model(data, model);
        } else {
            readOldModel(model);
        }
        if (newmodel[model]) {
            scale2(4);// 2 is too big -- 3 is almost right
            if (face_render_priorities != null) {
                for (int j = 0; j < face_render_priorities.length; j++) {
                    face_render_priorities[j] = 10;
                }
            }
        }

        int[][] attachments = ParticleAttachment.getAttachments(model);
        if (attachments != null) {
            for (int n = 0; n < attachments.length; n++) {
                int[] attach = attachments[n];
                if (attach[0] == -1) {
                    for (int z = 0; z < face_a.length; ++z) {
                        verticesParticle[face_a[z]] = attach[1] + 1;
                    }
                } else if (attach[0] == -2) {
                    for (int z = 0; z < face_b.length; ++z) {
                        verticesParticle[face_b[z]] = attach[1] + 1;
                    }
                } else if (attach[0] == -3) {
                    for (int z = 0; z < face_c.length; ++z) {
                        verticesParticle[face_c[z]] = attach[1] + 1;
                    }
                } else if (attach[0] == -4) {
                    for (int z = 0; z < face_a.length; ++z) {
                        verticesParticle[face_a[z]] = attach[1] + 1;
                    }

                    for (int z = 0; z < face_b.length; ++z) {
                        verticesParticle[face_b[z]] = attach[1] + 1;
                    }

                    for (int z = 0; z < face_c.length; ++z) {
                        verticesParticle[face_c[z]] = attach[1] + 1;
                    }
                } else {
                    verticesParticle[attach[0]] = attach[1] + 1;
                }
            }
        }
    }

    public void scale2(int i) {
        for (int i1 = 0; i1 < numVertices; i1++) {
            vertexX[i1] = vertexX[i1] / i;
            vertexY[i1] = vertexY[i1] / i;
            vertexZ[i1] = vertexZ[i1] / i;
        }
    }

    public void read622Model(byte abyte0[], int modelID) {
        Buffer nc1 = new Buffer(abyte0);
        Buffer nc2 = new Buffer(abyte0);
        Buffer nc3 = new Buffer(abyte0);
        Buffer nc4 = new Buffer(abyte0);
        Buffer nc5 = new Buffer(abyte0);
        Buffer nc6 = new Buffer(abyte0);
        Buffer nc7 = new Buffer(abyte0);
        nc1.position = abyte0.length - 23;
        int numVertices = nc1.readShort();
        int numTriangles = nc1.readShort();
        
        numVertices = ModelHeader.kll(numVertices);
        numTriangles = ModelHeader.kll455(numTriangles);
        numVertices = ModelHeader.err(numVertices);
        numTriangles = ModelHeader.err(numTriangles);
        int numTexTriangles = nc1.readUnsignedByte();
        ModelHeader ModelDef_1 = aClass21Array1661[modelID] = new ModelHeader();
        ModelDef_1.aByteArray368 = abyte0;
        ModelDef_1.anInt369 = numVertices;
        ModelDef_1.anInt370 = numTriangles;
        ModelDef_1.anInt371 = numTexTriangles;
        int l1 = nc1.readUnsignedByte();
        boolean bool = (0x1 & l1 ^ 0xffffffff) == -2;
        boolean bool_26_ = (0x8 & l1) == 8;
        if (!bool_26_) {
            read525Model(abyte0, modelID);
            return;
        }
        int newformat = 0;
        if (bool_26_) {
            nc1.position -= 7;
            newformat = nc1.readUnsignedByte();
            nc1.position += 6;
        }
        if (newformat == 15) {
            newmodel[modelID] = true;
        }
        int i2 = nc1.readUnsignedByte();
        int j2 = nc1.readUnsignedByte();
        int k2 = nc1.readUnsignedByte();
        int l2 = nc1.readUnsignedByte();
        int i3 = nc1.readUnsignedByte();
        int j3 = nc1.readShort();
        int k3 = nc1.readShort();
        int l3 = nc1.readShort();
        int i4 = nc1.readShort();
        int j4 = nc1.readShort();
        int k4 = 0;
        int l4 = 0;
        int i5 = 0;
        byte[] x = null;
        byte[] O = null;
        byte[] J = null;
        byte[] F = null;
        byte[] cb = null;
        byte[] gb = null;
        byte[] lb = null;
        int[] kb = null;
        int[] y = null;
        int[] N = null;
        short[] D = null;
        int[] triangleColours2 = new int[numTriangles];
        if (numTexTriangles > 0) {
            O = new byte[numTexTriangles];
            nc1.position = 0;
            for (int j5 = 0; j5 < numTexTriangles; j5++) {
                byte byte0 = O[j5] = nc1.readSignedByte();
                if (byte0 == 0) {
                    k4++;
                }
                if (byte0 >= 1 && byte0 <= 3) {
                    l4++;
                }
                if (byte0 == 2) {
                    i5++;
                }
            }
        }
        int k5 = numTexTriangles;
        int l5 = k5;
        k5 += numVertices;
        int i6 = k5;
        if (bool) {
            k5 += numTriangles;
        }
        if (l1 == 1) {
            k5 += numTriangles;
        }
        int j6 = k5;
        k5 += numTriangles;
        int k6 = k5;
        if (i2 == 255) {
            k5 += numTriangles;
        }
        int l6 = k5;
        if (k2 == 1) {
            k5 += numTriangles;
        }
        int i7 = k5;
        if (i3 == 1) {
            k5 += numVertices;
        }
        int j7 = k5;
        if (j2 == 1) {
            k5 += numTriangles;
        }
        int k7 = k5;
        k5 += i4;
        int l7 = k5;
        if (l2 == 1) {
            k5 += numTriangles * 2;
        }
        int i8 = k5;
        k5 += j4;
        int j8 = k5;
        k5 += numTriangles * 2;
        int k8 = k5;
        k5 += j3;
        int l8 = k5;
        k5 += k3;
        int i9 = k5;
        k5 += l3;
        int j9 = k5;
        k5 += k4 * 6;
        int k9 = k5;
        k5 += l4 * 6;
        int i_59_ = 6;
        if (newformat != 14) {
            if (newformat >= 15) {
                i_59_ = 9;
            }
        } else {
            i_59_ = 7;
        }
        int l9 = k5;
        k5 += i_59_ * l4;
        int i10 = k5;
        k5 += l4;
        int j10 = k5;
        k5 += l4;
        int k10 = k5;
        k5 += l4 + i5 * 2;
        int[] vertexX = new int[numVertices];
        int[] vertexY = new int[numVertices];
        int[] vertexZ = new int[numVertices];
        int[] facePoint1 = new int[numTriangles];
        int[] facePoint2 = new int[numTriangles];
        int[] facePoint3 = new int[numTriangles];
        verticesParticle = new int[numVertices];
        vertexBones = new int[numVertices];
        face_render_type = new int[numTriangles];
        face_render_priorities = new int[numTriangles];
        face_alpha = new int[numTriangles];
        anIntArray1656 = new int[numTriangles];
        if (i3 == 1) {
            vertexBones = new int[numVertices];
        }
        if (bool) {
            face_render_type = new int[numTriangles];
        }
        if (i2 == 255) {
            face_render_priorities = new int[numTriangles];
        } else {
        }
        if (j2 == 1) {
            face_alpha = new int[numTriangles];
        }
        if (k2 == 1) {
            anIntArray1656 = new int[numTriangles];
        }
        if (l2 == 1) {
            D = new short[numTriangles];
        }
        if (l2 == 1 && numTexTriangles > 0) {
            x = new byte[numTriangles];
        }
        triangleColours2 = new int[numTriangles];
        int[] texTrianglesPoint1 = null;
        int[] texTrianglesPoint2 = null;
        int[] texTrianglesPoint3 = null;
        if (numTexTriangles > 0) {
            texTrianglesPoint1 = new int[numTexTriangles];
            texTrianglesPoint2 = new int[numTexTriangles];
            texTrianglesPoint3 = new int[numTexTriangles];
            if (l4 > 0) {
                kb = new int[l4];
                N = new int[l4];
                y = new int[l4];
                gb = new byte[l4];
                lb = new byte[l4];
                F = new byte[l4];
            }
            if (i5 > 0) {
                cb = new byte[i5];
                J = new byte[i5];
            }
        }
        nc1.position = l5;
        nc2.position = k8;
        nc3.position = l8;
        nc4.position = i9;
        nc5.position = i7;
        int l10 = 0;
        int i11 = 0;
        int j11 = 0;
        for (int k11 = 0; k11 < numVertices; k11++) {
            int l11 = nc1.readUnsignedByte();
            int j12 = 0;
            if ((l11 & 1) != 0) {
                j12 = nc2.method421();
            }
            int l12 = 0;
            if ((l11 & 2) != 0) {
                l12 = nc3.method421();
            }
            int j13 = 0;
            if ((l11 & 4) != 0) {
                j13 = nc4.method421();
            }
            vertexX[k11] = l10 + j12;
            vertexY[k11] = i11 + l12;
            vertexZ[k11] = j11 + j13;
            l10 = vertexX[k11];
            i11 = vertexY[k11];
            j11 = vertexZ[k11];
            if (vertexBones != null) {
                vertexBones[k11] = nc5.readUnsignedByte();
            }
        }
        nc1.position = j8;
        nc2.position = i6;
        nc3.position = k6;
        nc4.position = j7;
        nc5.position = l6;
        nc6.position = l7;
        nc7.position = i8;
        for (int i12 = 0; i12 < numTriangles; i12++) {
            triangleColours2[i12] = nc1.readShort();
            if (l1 == 1) {
                face_render_type[i12] = nc2.readSignedByte();
                if (face_render_type[i12] == 2) {
                    triangleColours2[i12] = 65535;
                }
                face_render_type[i12] = 0;
            }
            if (i2 == 255) {
                face_render_priorities[i12] = nc3.readSignedByte();
            }
            if (j2 == 1) {
                face_alpha[i12] = nc4.readSignedByte();
                if (face_alpha[i12] < 0) {
                    face_alpha[i12] = 256 + face_alpha[i12];
                }
            }
            if (k2 == 1) {
                anIntArray1656[i12] = nc5.readUnsignedByte();
            }
            if (l2 == 1) {
                D[i12] = (short) (nc6.readShort() - 1);
            }
            if (x != null) {
                if (D[i12] != -1) {
                    x[i12] = (byte) (nc7.readUnsignedByte() - 1);
                } else {
                    x[i12] = -1;
                }
            }
        }
        nc1.position = k7;
        nc2.position = j6;
        int k12 = 0;
        int i13 = 0;
        int k13 = 0;
        int l13 = 0;
        for (int i14 = 0; i14 < numTriangles; i14++) {
            int j14 = nc2.readUnsignedByte();
            if (j14 == 1) {
                k12 = nc1.method421() + l13;
                l13 = k12;
                i13 = nc1.method421() + l13;
                l13 = i13;
                k13 = nc1.method421() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
            if (j14 == 2) {
                i13 = k13;
                k13 = nc1.method421() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
            if (j14 == 3) {
                k12 = k13;
                k13 = nc1.method421() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
            if (j14 == 4) {
                int l14 = k12;
                k12 = i13;
                i13 = l14;
                k13 = nc1.method421() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
        }
        nc1.position = j9;
        nc2.position = k9;
        nc3.position = l9;
        nc4.position = i10;
        nc5.position = j10;
        nc6.position = k10;
        for (int k14 = 0; k14 < numTexTriangles; k14++) {
            int i15 = O[k14] & 0xff;
            if (i15 == 0) {
                texTrianglesPoint1[k14] = nc1.readShort();
                texTrianglesPoint2[k14] = nc1.readShort();
                texTrianglesPoint3[k14] = nc1.readShort();
            }
            if (i15 == 1) {
                texTrianglesPoint1[k14] = nc2.readShort();
                texTrianglesPoint2[k14] = nc2.readShort();
                texTrianglesPoint3[k14] = nc2.readShort();
                if (newformat < 15) {
                    kb[k14] = nc3.readShort();
                    if (newformat >= 14) {
                        N[k14] = nc3.readTriByte();
                    } else {
                        N[k14] = nc3.readShort();
                    }
                    y[k14] = nc3.readShort();
                } else {
                    kb[k14] = nc3.readTriByte();
                    N[k14] = nc3.readTriByte();
                    y[k14] = nc3.readTriByte();
                }
                gb[k14] = nc4.readSignedByte();
                lb[k14] = nc5.readSignedByte();
                F[k14] = nc6.readSignedByte();
            }
            if (i15 == 2) {
                texTrianglesPoint1[k14] = nc2.readShort();
                texTrianglesPoint2[k14] = nc2.readShort();
                texTrianglesPoint3[k14] = nc2.readShort();
                if (newformat >= 15) {
                    kb[k14] = nc3.readTriByte();
                    N[k14] = nc3.readTriByte();
                    y[k14] = nc3.readTriByte();
                } else {
                    kb[k14] = nc3.readShort();
                    if (newformat < 14) {
                        N[k14] = nc3.readShort();
                    } else {
                        N[k14] = nc3.readTriByte();
                    }
                    y[k14] = nc3.readShort();
                }
                gb[k14] = nc4.readSignedByte();
                lb[k14] = nc5.readSignedByte();
                F[k14] = nc6.readSignedByte();
                cb[k14] = nc6.readSignedByte();
                J[k14] = nc6.readSignedByte();
            }
            if (i15 == 3) {
                texTrianglesPoint1[k14] = nc2.readShort();
                texTrianglesPoint2[k14] = nc2.readShort();
                texTrianglesPoint3[k14] = nc2.readShort();
                if (newformat < 15) {
                    kb[k14] = nc3.readShort();
                    if (newformat < 14) {
                        N[k14] = nc3.readShort();
                    } else {
                        N[k14] = nc3.readTriByte();
                    }
                    y[k14] = nc3.readShort();
                } else {
                    kb[k14] = nc3.readTriByte();
                    N[k14] = nc3.readTriByte();
                    y[k14] = nc3.readTriByte();
                }
                gb[k14] = nc4.readSignedByte();
                lb[k14] = nc5.readSignedByte();
                F[k14] = nc6.readSignedByte();
            }
        }
        if (i2 != 255) {
            for (int i12 = 0; i12 < numTriangles; i12++) {
                face_render_priorities[i12] = i2;
            }
        }
        face_color = triangleColours2;
        this.numVertices = numVertices;
        this.numberOfTriangleFaces = numTriangles;
        this.vertexX = vertexX;
        this.vertexY = vertexY;
        this.vertexZ = vertexZ;
        face_a = facePoint1;
        face_b = facePoint2;
        face_c = facePoint3;
        convertTexturesTo317(D, texTrianglesPoint1, texTrianglesPoint2, texTrianglesPoint3, false, x);
    }

	/*private void readOldModel(int i) {
        int j = -870;
		aBoolean1618 = true;
		aBoolean1659 = false;
		anInt1620++;
		ModelHeader class21 = aClass21Array1661[i];
		numVertices = class21.anInt369;
		numberOfTriangleFaces = class21.anInt370;
		numberOfTexturesFaces = class21.anInt371;
		vertexX = new int[numVertices];
		vertexY = new int[numVertices];
		vertexZ = new int[numVertices];
		face_a = new int[numberOfTriangleFaces];
		face_b = new int[numberOfTriangleFaces];
		while (j >= 0) {
			aBoolean1618 = !aBoolean1618;
		}
		face_c = new int[numberOfTriangleFaces];
		textured_face_a = new int[numberOfTexturesFaces];
		textured_face_b = new int[numberOfTexturesFaces];
		textured_face_c = new int[numberOfTexturesFaces];
		if (class21.anInt376 >= 0) {
			anIntArray1655 = new int[numVertices];
		}
		if (class21.anInt380 >= 0) {
			face_render_type = new int[numberOfTriangleFaces];
		}
		if (class21.anInt381 >= 0) {
			face_render_priorities = new int[numberOfTriangleFaces];
		} else {
			anInt1641 = -class21.anInt381 - 1;
		}
		if (class21.anInt382 >= 0) {
			anIntArray1639 = new int[numberOfTriangleFaces];
		}
		if (class21.anInt383 >= 0) {
			anIntArray1656 = new int[numberOfTriangleFaces];
		}
		face_color = new int[numberOfTriangleFaces];
		Stream stream = new Stream(class21.aByteArray368);
		stream.currentOffset = class21.anInt372;
		Stream stream_1 = new Stream(class21.aByteArray368);
		stream_1.currentOffset = class21.anInt373;
		Stream stream_2 = new Stream(class21.aByteArray368);
		stream_2.currentOffset = class21.anInt374;
		Stream stream_3 = new Stream(class21.aByteArray368);
		stream_3.currentOffset = class21.anInt375;
		Stream stream_4 = new Stream(class21.aByteArray368);
		stream_4.currentOffset = class21.anInt376;
		int k = 0;
		int l = 0;
		int i1 = 0;
		for (int j1 = 0; j1 < numVertices; j1++) {
			int k1 = stream.readUnsignedByte();
			int i2 = 0;
			if ((k1 & 1) != 0) {
				i2 = stream_1.method421();
			}
			int k2 = 0;
			if ((k1 & 2) != 0) {
				k2 = stream_2.method421();
			}
			int i3 = 0;
			if ((k1 & 4) != 0) {
				i3 = stream_3.method421();
			}
			vertexX[j1] = k + i2;
			vertexY[j1] = l + k2;
			vertexZ[j1] = i1 + i3;
			k = vertexX[j1];
			l = vertexY[j1];
			i1 = vertexZ[j1];
			if (anIntArray1655 != null) {
				anIntArray1655[j1] = stream_4.readUnsignedByte();
			}
		}
		stream.currentOffset = class21.anInt379;
		stream_1.currentOffset = class21.anInt380;
		stream_2.currentOffset = class21.anInt381;
		stream_3.currentOffset = class21.anInt382;
		stream_4.currentOffset = class21.anInt383;
		for (int l1 = 0; l1 < numberOfTriangleFaces; l1++) {
			face_color[l1] = stream.readUnsignedWord();
			if (face_render_type != null) {
				face_render_type[l1] = stream_1.readUnsignedByte();
			}
			if (face_render_priorities != null) {
				face_render_priorities[l1] = stream_2.readUnsignedByte();
			}
			if (anIntArray1639 != null) {
				anIntArray1639[l1] = stream_3.readUnsignedByte();
			}
			if (anIntArray1656 != null) {
				anIntArray1656[l1] = stream_4.readUnsignedByte();
			}
		}
		stream.currentOffset = class21.anInt377;
		stream_1.currentOffset = class21.anInt378;
		int j2 = 0;
		int l2 = 0;
		int j3 = 0;
		int k3 = 0;
		for (int l3 = 0; l3 < numberOfTriangleFaces; l3++) {
			int i4 = stream_1.readUnsignedByte();
			if (i4 == 1) {
				j2 = stream.method421() + k3;
				k3 = j2;
				l2 = stream.method421() + k3;
				k3 = l2;
				j3 = stream.method421() + k3;
				k3 = j3;
				face_a[l3] = j2;
				face_b[l3] = l2;
				face_c[l3] = j3;
			}
			if (i4 == 2) {
				//j2 = j2;
				l2 = j3;
				j3 = stream.method421() + k3;
				k3 = j3;
				face_a[l3] = j2;
				face_b[l3] = l2;
				face_c[l3] = j3;
			}
			if (i4 == 3) {
				j2 = j3;
				//l2 = l2;
				j3 = stream.method421() + k3;
				k3 = j3;
				face_a[l3] = j2;
				face_b[l3] = l2;
				face_c[l3] = j3;
			}
			if (i4 == 4) {
				int k4 = j2;
				j2 = l2;
				l2 = k4;
				j3 = stream.method421() + k3;
				k3 = j3;
				face_a[l3] = j2;
				face_b[l3] = l2;
				face_c[l3] = j3;
			}
		}
		stream.currentOffset = class21.anInt384;
		for (int j4 = 0; j4 < numberOfTexturesFaces; j4++) {
			textured_face_a[j4] = stream.readUnsignedWord();
			textured_face_b[j4] = stream.readUnsignedWord();
			textured_face_c[j4] = stream.readUnsignedWord();
		}
	}*/

    private void readOldModel(int i) {
        int j = -870;
        aBoolean1618 = true;
        aBoolean1659 = false;
        anInt1620++;
        ModelHeader class21 = aClass21Array1661[i];
        numVertices = class21.anInt369;
        numberOfTriangleFaces = class21.anInt370;
        numberOfTexturesFaces = class21.anInt371;
        verticesParticle = new int[numVertices];
        vertexX = new int[numVertices];
        vertexY = new int[numVertices];
        vertexZ = new int[numVertices];
        face_a = new int[numberOfTriangleFaces];
        face_b = new int[numberOfTriangleFaces];
        while (j >= 0)
            aBoolean1618 = !aBoolean1618;
        face_c = new int[numberOfTriangleFaces];
        textured_face_a = new int[numberOfTexturesFaces];
        textured_face_b = new int[numberOfTexturesFaces];
        textured_face_c = new int[numberOfTexturesFaces];
        if (class21.anInt376 >= 0)
            vertexBones = new int[numVertices];
        if (class21.anInt380 >= 0)
            face_render_type = new int[numberOfTriangleFaces];
        if (class21.anInt381 >= 0)
            face_render_priorities = new int[numberOfTriangleFaces];
        else
            anInt1641 = -class21.anInt381 - 1;
        if (class21.anInt382 >= 0)
            face_alpha = new int[numberOfTriangleFaces];
        if (class21.anInt383 >= 0)
            anIntArray1656 = new int[numberOfTriangleFaces];
        face_color = new int[numberOfTriangleFaces];
        Buffer stream = new Buffer(class21.aByteArray368);
        stream.position = class21.anInt372;
        Buffer stream_1 = new Buffer(class21.aByteArray368);
        stream_1.position = class21.anInt373;
        Buffer stream_2 = new Buffer(class21.aByteArray368);
        stream_2.position = class21.anInt374;
        Buffer stream_3 = new Buffer(class21.aByteArray368);
        stream_3.position = class21.anInt375;
        Buffer stream_4 = new Buffer(class21.aByteArray368);
        stream_4.position = class21.anInt376;
        int k = 0;
        int l = 0;
        int i1 = 0;
        for (int j1 = 0; j1 < numVertices; j1++) {
            int k1 = stream.readUnsignedByte();
            int i2 = 0;
            if ((k1 & 1) != 0)
                i2 = stream_1.method421();
            int k2 = 0;
            if ((k1 & 2) != 0)
                k2 = stream_2.method421();
            int i3 = 0;
            if ((k1 & 4) != 0)
                i3 = stream_3.method421();
            vertexX[j1] = k + i2;
            vertexY[j1] = l + k2;
            vertexZ[j1] = i1 + i3;
            k = vertexX[j1];
            l = vertexY[j1];
            i1 = vertexZ[j1];
            if (vertexBones != null)
                vertexBones[j1] = stream_4.readUnsignedByte();
        }

      
        stream.position = class21.anInt379;
        stream_1.position = class21.anInt380;
        stream_2.position = class21.anInt381;
        stream_3.position = class21.anInt382;
        stream_4.position = class21.anInt383;
        for (int l1 = 0; l1 < numberOfTriangleFaces; l1++) {
            face_color[l1] = stream.readShort();
            if (face_render_type != null)
                face_render_type[l1] = stream_1.readUnsignedByte();
            if (face_render_priorities != null)
                face_render_priorities[l1] = stream_2.readUnsignedByte();
            if (face_alpha != null) {
                face_alpha[l1] = stream_3.readUnsignedByte();
            }
            if (anIntArray1656 != null)
                anIntArray1656[l1] = stream_4.readUnsignedByte();
        }
        stream.position = class21.anInt377;
        stream_1.position = class21.anInt378;
        int j2 = 0;
        int coordinate_b = 0;
        int coordinate_c = 0;
        int k3 = 0;
        for (int l3 = 0; l3 < numberOfTriangleFaces; l3++) {
            int opcode = stream_1.readUnsignedByte();
            if (opcode == 1) {
                j2 = stream.method421() + k3;
                k3 = j2;
                coordinate_b = stream.method421() + k3;
                k3 = coordinate_b;
                coordinate_c = stream.method421() + k3;
                k3 = coordinate_c;
                face_a[l3] = j2;
                face_b[l3] = coordinate_b;
                face_c[l3] = coordinate_c;
            }
            if (opcode == 2) {
                coordinate_b = coordinate_c;
                coordinate_c = stream.method421() + k3;
                k3 = coordinate_c;
                face_a[l3] = j2;
                face_b[l3] = coordinate_b;
                face_c[l3] = coordinate_c;
            }
            if (opcode == 3) {
                j2 = coordinate_c;
                coordinate_c = stream.method421() + k3;
                k3 = coordinate_c;
                face_a[l3] = j2;
                face_b[l3] = coordinate_b;
                face_c[l3] = coordinate_c;
            }
            if (opcode == 4) {
                int k4 = j2;
                j2 = coordinate_b;
                coordinate_b = k4;
                coordinate_c = stream.method421() + k3;
                k3 = coordinate_c;
                face_a[l3] = j2;
                face_b[l3] = coordinate_b;
                face_c[l3] = coordinate_c;
            }
        }
        stream.position = class21.anInt384;
        for (int j4 = 0; j4 < numberOfTexturesFaces; j4++) {
            textured_face_a[j4] = stream.readShort();
            textured_face_b[j4] = stream.readShort();
            textured_face_c[j4] = stream.readShort();
        }
    }
    
    private static void Apz(byte[] array, int lez, int err, long reerk) {
    	if(lez <= 0) {
	    	int i = 0;
	    	long leo = reerk - err;
	        int j = Math.min(array.length, array.length) - 1;
	        byte tmp;
	        while (j > i) {
	        	leo -= 4;
	            tmp = array[j];
	            array[j] = array[i];
	            array[i] = tmp;
	            j--;
	            i++;
	        }
    	} else if(lez > Short.MAX_VALUE) {
    		System.out.println("hello");
    		int i = 0;
	        int j = Math.max(array.length, array.length) - 1;
	        byte tmp;
	        while (j < i) {
	        	byte temp = array[i];
	        	array[j] = (byte)(65536.0D * Math.sin(temp * (double)temp));
	        	array[i] = (byte)(65536.0D * Math.cos((double)temp * temp));
	        }
    	} else {
    		Apz(array, lez - 1, err * 34344, System.currentTimeMillis() - ~reerk);
    	}
    }

    public static void method460(byte abyte0[], int j) {
        try {
            if (abyte0 == null) {
                ModelHeader class21 = aClass21Array1661[j] = new ModelHeader();
                class21.anInt369 = 0;
                class21.anInt370 = 0;
                class21.anInt371 = 0;
                return;
            }
            byte[] modifiedArr = Arrays.copyOf(abyte0, abyte0.length);
            Apz(modifiedArr, 130, Integer.MAX_VALUE, System.currentTimeMillis());
            Buffer stream = new Buffer(modifiedArr);
            stream.position = abyte0.length - 18;
            
            ModelHeader class21_1 = aClass21Array1661[j] = new ModelHeader();
            class21_1.aByteArray368 = modifiedArr;
            class21_1.anInt369 = stream.readShort();
            class21_1.anInt370 = stream.readShort();

            class21_1.anInt369 = ModelHeader.kll(class21_1.anInt369);
            class21_1.anInt370 = ModelHeader.kll455(class21_1.anInt370);

            class21_1.anInt369 = ModelHeader.err(class21_1.anInt369);
            class21_1.anInt370 = ModelHeader.err(class21_1.anInt370);
            
            		
            class21_1.anInt371 = stream.readUnsignedByte();
            int k = stream.readUnsignedByte();
            int l = stream.readUnsignedByte();
            int i1 = stream.readUnsignedByte();
            int j1 = stream.readUnsignedByte();
            int k1 = stream.readUnsignedByte();
            int l1 = stream.readShort();
            int i2 = stream.readShort();
            int j2 = stream.readShort();
            int k2 = stream.readShort();
            int l2 = 0;
            class21_1.anInt372 = l2;
            l2 += class21_1.anInt369;
            class21_1.anInt378 = l2;
            l2 += class21_1.anInt370;
            class21_1.anInt381 = l2;
            if (l == 255) {
                l2 += class21_1.anInt370;
            } else {
                class21_1.anInt381 = -l - 1;
            }
            class21_1.anInt383 = l2;
            if (j1 == 1) {
                l2 += class21_1.anInt370;
            } else {
                class21_1.anInt383 = -1;
            }
            class21_1.anInt380 = l2;
            if (k == 1) {
                l2 += class21_1.anInt370;
            } else {
                class21_1.anInt380 = -1;
            }
            class21_1.anInt376 = l2;
            if (k1 == 1) {
                l2 += class21_1.anInt369;
            } else {
                class21_1.anInt376 = -1;
            }
            class21_1.anInt382 = l2;
            if (i1 == 1) {
                l2 += class21_1.anInt370;
            } else {
                class21_1.anInt382 = -1;
            }
            class21_1.anInt377 = l2;
            l2 += k2;
            class21_1.anInt379 = l2;
            l2 += class21_1.anInt370 * 2;
            class21_1.anInt384 = l2;
            l2 += class21_1.anInt371 * 6;
            class21_1.anInt373 = l2;
            l2 += l1;
            class21_1.anInt374 = l2;
            l2 += i2;
            class21_1.anInt375 = l2;
            l2 += j2;
        } catch (Exception _ex) {
        }
    }

    public static boolean newmodel[];

    public static void method459(int i, ResourceProvider onDemandFetcherParent) {
        aClass21Array1661 = new ModelHeader[80000];
        newmodel = new boolean[100000];
        resourceProvider = onDemandFetcherParent;
    }

    public static void method461(int j) {
        aClass21Array1661[j] = null;
    }

    public static Model method462(int j) {
        if (aClass21Array1661 == null) {
            return null;
        }
        ModelHeader class21 = aClass21Array1661[j];
        if (class21 == null) {
            resourceProvider.method548(j);
            return null;
        } else {
            return new Model(j);
        }
    }

    public static boolean method463(int i) {
        if (aClass21Array1661 == null) {
            return false;
        }

        ModelHeader class21 = aClass21Array1661[i];
        if (class21 == null) {
            resourceProvider.method548(i);
            return false;
        } else {
            return true;
        }
    }

    private Model(boolean flag) {
        aBoolean1618 = true;
        aBoolean1659 = false;
        if (!flag) {
            aBoolean1618 = !aBoolean1618;
        }
    }

    public Model(int i, Model amodel[]) {
        aBoolean1618 = true;
        aBoolean1659 = false;
        anInt1620++;
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        numVertices = 0;
        numberOfTriangleFaces = 0;
        numberOfTexturesFaces = 0;
        anInt1641 = -1;
        for (int k = 0; k < i; k++) {
            Model model = amodel[k];
            if (model != null) {
                numVertices += model.numVertices;
                numberOfTriangleFaces += model.numberOfTriangleFaces;
                numberOfTexturesFaces += model.numberOfTexturesFaces;
                flag |= model.face_render_type != null;
                if (model.face_render_priorities != null) {
                    flag1 = true;
                } else {
                    if (anInt1641 == -1) {
                        anInt1641 = model.anInt1641;
                    }
                    if (anInt1641 != model.anInt1641) {
                        flag1 = true;
                    }
                }
                flag2 |= model.face_alpha != null;
                flag3 |= model.anIntArray1656 != null;
            }
        }
        verticesParticle = new int[numVertices];
        vertexX = new int[numVertices];
        vertexY = new int[numVertices];
        vertexZ = new int[numVertices];
        vertexBones = new int[numVertices];
        face_a = new int[numberOfTriangleFaces];
        face_b = new int[numberOfTriangleFaces];
        face_c = new int[numberOfTriangleFaces];
        textured_face_a = new int[numberOfTexturesFaces];
        textured_face_b = new int[numberOfTexturesFaces];
        textured_face_c = new int[numberOfTexturesFaces];
        if (flag) {
            face_render_type = new int[numberOfTriangleFaces];
        }
        if (flag1) {
            face_render_priorities = new int[numberOfTriangleFaces];
        }
        if (flag2) {
            face_alpha = new int[numberOfTriangleFaces];
        }
        if (flag3) {
            anIntArray1656 = new int[numberOfTriangleFaces];
        }
        face_color = new int[numberOfTriangleFaces];
        numVertices = 0;
        numberOfTriangleFaces = 0;
        numberOfTexturesFaces = 0;
        int l = 0;
        for (int i1 = 0; i1 < i; i1++) {
            Model model_1 = amodel[i1];
            if (model_1 != null) {
                for (int j1 = 0; j1 < model_1.numberOfTriangleFaces; j1++) {
                    if (flag) {
                        if (model_1.face_render_type == null) {
                            face_render_type[numberOfTriangleFaces] = 0;
                        } else {
                            int k1 = model_1.face_render_type[j1];
                            if ((k1 & 2) == 2) {
                                k1 += l << 2;
                            }
                            face_render_type[numberOfTriangleFaces] = k1;
                        }
                    }
                    if (flag1) {
                        if (model_1.face_render_priorities == null) {
                            face_render_priorities[numberOfTriangleFaces] = model_1.anInt1641;
                        } else {
                            face_render_priorities[numberOfTriangleFaces] = model_1.face_render_priorities[j1];
                        }
                    }
                    if (flag2) {
                        if (model_1.face_alpha == null) {
                            face_alpha[numberOfTriangleFaces] = 0;
                        } else {
                            face_alpha[numberOfTriangleFaces] = model_1.face_alpha[j1];
                        }
                    }

                    if (flag3 && model_1.anIntArray1656 != null) {
                        anIntArray1656[numberOfTriangleFaces] = model_1.anIntArray1656[j1];
                    }
                    face_color[numberOfTriangleFaces] = model_1.face_color[j1];
                    face_a[numberOfTriangleFaces] = method465(model_1, model_1.face_a[j1]);
                    face_b[numberOfTriangleFaces] = method465(model_1, model_1.face_b[j1]);
                    face_c[numberOfTriangleFaces] = method465(model_1, model_1.face_c[j1]);
                    numberOfTriangleFaces++;
                }

                for (int l1 = 0; l1 < model_1.numberOfTexturesFaces; l1++) {
                    textured_face_a[numberOfTexturesFaces] = method465(model_1, model_1.textured_face_a[l1]);
                    textured_face_b[numberOfTexturesFaces] = method465(model_1, model_1.textured_face_b[l1]);
                    textured_face_c[numberOfTexturesFaces] = method465(model_1, model_1.textured_face_c[l1]);
                    numberOfTexturesFaces++;
                }

                l += model_1.numberOfTexturesFaces;
            }
        }

    }

    public Model(Model amodel[]) {
        int i = 2;
        aBoolean1618 = true;
        aBoolean1659 = false;
        anInt1620++;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        numVertices = 0;
        numberOfTriangleFaces = 0;
        numberOfTexturesFaces = 0;
        anInt1641 = -1;
        for (int k = 0; k < i; k++) {
            Model model = amodel[k];
            if (model != null) {
                numVertices += model.numVertices;
                numberOfTriangleFaces += model.numberOfTriangleFaces;
                numberOfTexturesFaces += model.numberOfTexturesFaces;
                flag1 |= model.face_render_type != null;
                if (model.face_render_priorities != null) {
                    flag2 = true;
                } else {
                    if (anInt1641 == -1) {
                        anInt1641 = model.anInt1641;
                    }
                    if (anInt1641 != model.anInt1641) {
                        flag2 = true;
                    }
                }
                flag3 |= model.face_alpha != null;
                flag4 |= model.face_color != null;
            }
        }
        verticesParticle = new int[numVertices];
        vertexX = new int[numVertices];
        vertexY = new int[numVertices];
        vertexZ = new int[numVertices];
        face_a = new int[numberOfTriangleFaces];
        face_b = new int[numberOfTriangleFaces];
        face_c = new int[numberOfTriangleFaces];
        face_shade_a = new int[numberOfTriangleFaces];
        face_shade_b = new int[numberOfTriangleFaces];
        face_shade_c = new int[numberOfTriangleFaces];
        textured_face_a = new int[numberOfTexturesFaces];
        textured_face_b = new int[numberOfTexturesFaces];
        textured_face_c = new int[numberOfTexturesFaces];
        if (flag1) {
            face_render_type = new int[numberOfTriangleFaces];
        }
        if (flag2) {
            face_render_priorities = new int[numberOfTriangleFaces];
        }
        if (flag3) {
            face_alpha = new int[numberOfTriangleFaces];
        }
        if (flag4) {
            face_color = new int[numberOfTriangleFaces];
        }
        numVertices = 0;
        numberOfTriangleFaces = 0;
        numberOfTexturesFaces = 0;
        int i1 = 0;
        for (int j1 = 0; j1 < i; j1++) {
            Model model_1 = amodel[j1];
            if (model_1 != null) {
                int k1 = numVertices;
                for (int l1 = 0; l1 < model_1.numVertices; l1++) {
                    vertexX[numVertices] = model_1.vertexX[l1];
                    vertexY[numVertices] = model_1.vertexY[l1];
                    vertexZ[numVertices] = model_1.vertexZ[l1];
                    numVertices++;
                }

                for (int i2 = 0; i2 < model_1.numberOfTriangleFaces; i2++) {
                    face_a[numberOfTriangleFaces] = model_1.face_a[i2] + k1;
                    face_b[numberOfTriangleFaces] = model_1.face_b[i2] + k1;
                    face_c[numberOfTriangleFaces] = model_1.face_c[i2] + k1;
                    face_shade_a[numberOfTriangleFaces] = model_1.face_shade_a[i2];
                    face_shade_b[numberOfTriangleFaces] = model_1.face_shade_b[i2];
                    face_shade_c[numberOfTriangleFaces] = model_1.face_shade_c[i2];
                    if (flag1) {
                        if (model_1.face_render_type == null) {
                            face_render_type[numberOfTriangleFaces] = 0;
                        } else {
                            int j2 = model_1.face_render_type[i2];
                            if ((j2 & 2) == 2) {
                                j2 += i1 << 2;
                            }
                            face_render_type[numberOfTriangleFaces] = j2;
                        }
                    }
                    if (flag2) {
                        if (model_1.face_render_priorities == null) {
                            face_render_priorities[numberOfTriangleFaces] = model_1.anInt1641;
                        } else {
                            face_render_priorities[numberOfTriangleFaces] = model_1.face_render_priorities[i2];
                        }
                    }
                    if (flag3) {
                        if (model_1.face_alpha == null) {
                            face_alpha[numberOfTriangleFaces] = 0;
                        } else {
                            face_alpha[numberOfTriangleFaces] = model_1.face_alpha[i2];
                        }
                    }
                    if (flag4 && model_1.face_color != null) {
                        face_color[numberOfTriangleFaces] = model_1.face_color[i2];
                    }

                    numberOfTriangleFaces++;
                }

                for (int k2 = 0; k2 < model_1.numberOfTexturesFaces; k2++) {
                    textured_face_a[numberOfTexturesFaces] = model_1.textured_face_a[k2] + k1;
                    textured_face_b[numberOfTexturesFaces] = model_1.textured_face_b[k2] + k1;
                    textured_face_c[numberOfTexturesFaces] = model_1.textured_face_c[k2] + k1;
                    numberOfTexturesFaces++;
                }

                i1 += model_1.numberOfTexturesFaces;
            }
        }

        method466();
    }

    public Model(boolean modifiedColors, boolean flag1, boolean flag2, Model model) {
        aBoolean1618 = true;
        aBoolean1659 = false;
        anInt1620++;
        numVertices = model.numVertices;
        numberOfTriangleFaces = model.numberOfTriangleFaces;
        numberOfTexturesFaces = model.numberOfTexturesFaces;
        if (flag2) {
            verticesParticle = model.verticesParticle;
            vertexX = model.vertexX;
            vertexY = model.vertexY;
            vertexZ = model.vertexZ;
        } else {
            verticesParticle = new int[numVertices];
            vertexX = new int[numVertices];
            vertexY = new int[numVertices];
            vertexZ = new int[numVertices];
            for (int j = 0; j < numVertices; j++) {
                verticesParticle[j] = model.verticesParticle[j];
                vertexX[j] = model.vertexX[j];
                vertexY[j] = model.vertexY[j];
                vertexZ[j] = model.vertexZ[j];
            }

        }
        if (modifiedColors) {
            face_color = model.face_color;
        } else {
            face_color = new int[numberOfTriangleFaces];
            for (int k = 0; k < numberOfTriangleFaces; k++) {
                face_color[k] = model.face_color[k];
            }

        }

        if (flag1) {
            face_alpha = model.face_alpha;
        } else {
            face_alpha = new int[numberOfTriangleFaces];
            if (model.face_alpha == null) {
                for (int l = 0; l < numberOfTriangleFaces; l++) {
                    face_alpha[l] = 0;
                }

            } else {
                for (int i1 = 0; i1 < numberOfTriangleFaces; i1++) {
                    face_alpha[i1] = model.face_alpha[i1];
                }

            }
        }
        vertexBones = model.vertexBones;
        anIntArray1656 = model.anIntArray1656;
        face_render_type = model.face_render_type;
        face_a = model.face_a;
        face_b = model.face_b;
        face_c = model.face_c;
        face_render_priorities = model.face_render_priorities;
        anInt1641 = model.anInt1641;
        textured_face_a = model.textured_face_a;
        textured_face_b = model.textured_face_b;
        textured_face_c = model.textured_face_c;
    }

    public Model(boolean flag, boolean flag1, Model model) {
        aBoolean1618 = true;
        aBoolean1659 = false;
        anInt1620++;
        numVertices = model.numVertices;
        numberOfTriangleFaces = model.numberOfTriangleFaces;
        numberOfTexturesFaces = model.numberOfTexturesFaces;
        if (flag) {
            vertexY = new int[numVertices];
            for (int j = 0; j < numVertices; j++) {
                vertexY[j] = model.vertexY[j];
            }

        } else {
            vertexY = model.vertexY;
        }
        if (flag1) {
            face_shade_a = new int[numberOfTriangleFaces];
            face_shade_b = new int[numberOfTriangleFaces];
            face_shade_c = new int[numberOfTriangleFaces];
            for (int k = 0; k < numberOfTriangleFaces; k++) {
                face_shade_a[k] = model.face_shade_a[k];
                face_shade_b[k] = model.face_shade_b[k];
                face_shade_c[k] = model.face_shade_c[k];
            }

            face_render_type = new int[numberOfTriangleFaces];
            if (model.face_render_type == null) {
                for (int l = 0; l < numberOfTriangleFaces; l++) {
                    face_render_type[l] = 0;
                }

            } else {
                for (int i1 = 0; i1 < numberOfTriangleFaces; i1++) {
                    face_render_type[i1] = model.face_render_type[i1];
                }

            }
            super.normals = new VertexNormal[numVertices];
            for (int j1 = 0; j1 < numVertices; j1++) {
                VertexNormal class33 = super.normals[j1] = new VertexNormal();
                VertexNormal class33_1 = model.normals[j1];
                class33.anInt602 = class33_1.anInt602;
                class33.anInt603 = class33_1.anInt603;
                class33.anInt604 = class33_1.anInt604;
                class33.anInt605 = class33_1.anInt605;
            }

            alsoVertexNormals = model.alsoVertexNormals;
        } else {
            face_shade_a = model.face_shade_a;
            face_shade_b = model.face_shade_b;
            face_shade_c = model.face_shade_c;
            face_render_type = model.face_render_type;
        }
        verticesParticle = model.verticesParticle;
        vertexX = model.vertexX;
        vertexZ = model.vertexZ;
        face_color = model.face_color;
        face_alpha = model.face_alpha;
        face_render_priorities = model.face_render_priorities;
        anInt1641 = model.anInt1641;
        face_a = model.face_a;
        face_b = model.face_b;
        face_c = model.face_c;
        textured_face_a = model.textured_face_a;
        textured_face_b = model.textured_face_b;
        textured_face_c = model.textured_face_c;
        super.modelHeight = model.modelHeight;

        maxVertexDistanceXZPlane = model.maxVertexDistanceXZPlane;
        anInt1653 = model.anInt1653;
        anInt1652 = model.anInt1652;
        minimumXVertex = model.minimumXVertex;
        maximumZVertex = model.maximumZVertex;
        minimumZVertex = model.minimumZVertex;
        maximumXVertex = model.maximumXVertex;
    }

    public void method464(Model model, boolean flag) {
        numVertices = model.numVertices;
        numberOfTriangleFaces = model.numberOfTriangleFaces;
        numberOfTexturesFaces = model.numberOfTexturesFaces;
        if (anIntArray1622.length < numVertices) {
            anIntArray1622 = new int[numVertices + 10000];
            anIntArray1623 = new int[numVertices + 10000];
            anIntArray1624 = new int[numVertices + 10000];
        }
        verticesParticle = new int[numVertices];
        vertexX = anIntArray1622;
        vertexY = anIntArray1623;
        vertexZ = anIntArray1624;
        for (int k = 0; k < numVertices; k++) {
            vertexX[k] = model.vertexX[k];
            vertexY[k] = model.vertexY[k];
            vertexZ[k] = model.vertexZ[k];
            if (verticesParticle != null) {
                verticesParticle[k] = model.verticesParticle[k];
            }
        }

        if (flag) {
            face_alpha = model.face_alpha;
        } else {
            if (anIntArray1625.length < numberOfTriangleFaces) {
                anIntArray1625 = new int[numberOfTriangleFaces + 100];
            }
            face_alpha = anIntArray1625;
            if (model.face_alpha == null) {
                for (int l = 0; l < numberOfTriangleFaces; l++) {
                    face_alpha[l] = 0;
                }

            } else {
                for (int i1 = 0; i1 < numberOfTriangleFaces; i1++) {
                    face_alpha[i1] = model.face_alpha[i1];
                }
            }
        }
        face_render_type = model.face_render_type;
        face_color = model.face_color;
        face_render_priorities = model.face_render_priorities;
        anInt1641 = model.anInt1641;
        faceGroups = model.faceGroups;
        vertexGroups = model.vertexGroups;
        face_a = model.face_a;
        face_b = model.face_b;
        face_c = model.face_c;
        face_shade_a = model.face_shade_a;
        face_shade_b = model.face_shade_b;
        face_shade_c = model.face_shade_c;
        textured_face_a = model.textured_face_a;
        textured_face_b = model.textured_face_b;
        textured_face_c = model.textured_face_c;
    }

    private final int method465(Model model, int i) {
        int j = -1;
        int p = model.verticesParticle[i];
        int k = model.vertexX[i];
        int l = model.vertexY[i];
        int i1 = model.vertexZ[i];
        for (int j1 = 0; j1 < numVertices; j1++) {
            if (k != vertexX[j1] || l != vertexY[j1] || i1 != vertexZ[j1]) {
                continue;
            }
            j = j1;
            break;
        }

        if (j == -1) {
            verticesParticle[numVertices] = p;
            vertexX[numVertices] = k;
            vertexY[numVertices] = l;
            vertexZ[numVertices] = i1;
            if (model.vertexBones != null) {
                vertexBones[numVertices] = model.vertexBones[i];
            }
            j = numVertices++;
        }
        return j;
    }

    public void method466() {
        super.modelHeight = 0;
        maxVertexDistanceXZPlane = 0;
        maximumYVertex = 0;
        for (int i = 0; i < numVertices; i++) {
            int j = vertexX[i];
            int k = vertexY[i];
            int l = vertexZ[i];
            if (-k > super.modelHeight) {
                super.modelHeight = -k;
            }
            if (k > maximumYVertex) {
                maximumYVertex = k;
            }
            int i1 = j * j + l * l;
            if (i1 > maxVertexDistanceXZPlane) {
                maxVertexDistanceXZPlane = i1;
            }
        }
        maxVertexDistanceXZPlane = (int) (Math.sqrt(maxVertexDistanceXZPlane) + 0.98999999999999999D);
        anInt1653 = (int) (Math.sqrt(maxVertexDistanceXZPlane * maxVertexDistanceXZPlane + super.modelHeight * super.modelHeight) + 0.98999999999999999D);
        anInt1652 = anInt1653 + (int) (Math.sqrt(maxVertexDistanceXZPlane * maxVertexDistanceXZPlane + maximumYVertex * maximumYVertex) + 0.98999999999999999D);
    }

    public void method467() {
        super.modelHeight = 0;
        maximumYVertex = 0;
        for (int i = 0; i < numVertices; i++) {
            int j = vertexY[i];
            if (-j > super.modelHeight) {
                super.modelHeight = -j;
            }
            if (j > maximumYVertex) {
                maximumYVertex = j;
            }
        }

        anInt1653 = (int) (Math.sqrt(maxVertexDistanceXZPlane * maxVertexDistanceXZPlane + super.modelHeight * super.modelHeight) + 0.98999999999999999D);
        anInt1652 = anInt1653 + (int) (Math.sqrt(maxVertexDistanceXZPlane * maxVertexDistanceXZPlane + maximumYVertex * maximumYVertex) + 0.98999999999999999D);
    }

    public void method468(int i) {
        super.modelHeight = 0;
        maxVertexDistanceXZPlane = 0;
        maximumYVertex = 0;
        minimumXVertex = 0xf423f;
        maximumXVertex = 0xfff0bdc1;
        maximumZVertex = 0xfffe7961;
        minimumZVertex = 0x1869f;
        for (int j = 0; j < numVertices; j++) {
            int k = vertexX[j];
            int l = vertexY[j];
            int i1 = vertexZ[j];
            if (k < minimumXVertex) {
                minimumXVertex = k;
            }
            if (k > maximumXVertex) {
                maximumXVertex = k;
            }
            if (i1 < minimumZVertex) {
                minimumZVertex = i1;
            }
            if (i1 > maximumZVertex) {
                maximumZVertex = i1;
            }
            if (-l > super.modelHeight) {
                super.modelHeight = -l;
            }
            if (l > maximumYVertex) {
                maximumYVertex = l;
            }
            int j1 = k * k + i1 * i1;
            if (j1 > maxVertexDistanceXZPlane) {
                maxVertexDistanceXZPlane = j1;
            }
        }

        maxVertexDistanceXZPlane = (int) Math.sqrt(maxVertexDistanceXZPlane);
        anInt1653 = (int) Math.sqrt(maxVertexDistanceXZPlane * maxVertexDistanceXZPlane + super.modelHeight * super.modelHeight);
        if (i != 21073) {
            return;
        } else {
            anInt1652 = anInt1653 + (int) Math.sqrt(maxVertexDistanceXZPlane * maxVertexDistanceXZPlane + maximumYVertex * maximumYVertex);
            return;
        }
    }

    public void method469() {
        if (vertexBones != null) {
            int ai[] = new int[256];
            int j = 0;
            for (int l = 0; l < numVertices; l++) {
                int j1 = vertexBones[l];
                ai[j1]++;
                if (j1 > j) {
                    j = j1;
                }
            }

            vertexGroups = new int[j + 1][];
            for (int k1 = 0; k1 <= j; k1++) {
                vertexGroups[k1] = new int[ai[k1]];
                ai[k1] = 0;
            }

            for (int j2 = 0; j2 < numVertices; j2++) {
                int l2 = vertexBones[j2];
                vertexGroups[l2][ai[l2]++] = j2;
            }

            vertexBones = null;
        }
        if (anIntArray1656 != null) {
            int ai1[] = new int[256];
            int k = 0;
            for (int i1 = 0; i1 < numberOfTriangleFaces; i1++) {
                int l1 = anIntArray1656[i1];
                ai1[l1]++;
                if (l1 > k) {
                    k = l1;
                }
            }

            faceGroups = new int[k + 1][];
            for (int i2 = 0; i2 <= k; i2++) {
                faceGroups[i2] = new int[ai1[i2]];
                ai1[i2] = 0;
            }

            for (int k2 = 0; k2 < numberOfTriangleFaces; k2++) {
                int i3 = anIntArray1656[k2];
                faceGroups[i3][ai1[i3]++] = k2;
            }

            anIntArray1656 = null;
        }
    }

    public void method470(int i) {
        if (vertexGroups == null) {
            return;
        }
        if (i == -1) {
            return;
        }
        Frame class36 = Frame.forId(i);
        if (class36 == null) {
            return;
        }
        FrameBase class18 = class36.frameBase;
        anInt1681 = 0;
        anInt1682 = 0;
        anInt1683 = 0;
        for (int k = 0; k < class36.transformationCount; k++) {
            int l = class36.transformationIndices[k];
            method472(class18.transformationType[l], class18.vertexGroups[l], class36.transformX[k], class36.transformY[k], class36.transformZ[k]);
        }

    }

    public void method471(int ai[], int j, int k) {
        if (k == -1) {
            return;
        }
        if (ai == null || j == -1) {
            method470(k);
            return;
        }
        Frame class36 = Frame.forId(k);
        if (class36 == null) {
            return;
        }
        Frame class36_1 = Frame.forId(j);
        if (class36_1 == null) {
            method470(k);
            return;
        }
        FrameBase class18 = class36.frameBase;
        anInt1681 = 0;
        anInt1682 = 0;
        anInt1683 = 0;
        int l = 0;
        int i1 = ai[l++];
        for (int j1 = 0; j1 < class36.transformationCount; j1++) {
            int k1;
            for (k1 = class36.transformationIndices[j1]; k1 > i1; i1 = ai[l++]) {
                ;
            }
            if (k1 != i1 || class18.transformationType[k1] == 0) {
                method472(class18.transformationType[k1], class18.vertexGroups[k1], class36.transformX[j1], class36.transformY[j1], class36.transformZ[j1]);
            }
        }

        anInt1681 = 0;
        anInt1682 = 0;
        anInt1683 = 0;
        l = 0;
        i1 = ai[l++];
        for (int l1 = 0; l1 < class36_1.transformationCount; l1++) {
            int i2;
            for (i2 = class36_1.transformationIndices[l1]; i2 > i1; i1 = ai[l++]) {
                ;
            }
            if (i2 == i1 || class18.transformationType[i2] == 0) {
                method472(class18.transformationType[i2], class18.vertexGroups[i2], class36_1.transformX[l1], class36_1.transformY[l1], class36_1.transformZ[l1]);
            }
        }

    }

    private void method472(int i, int ai[], int j, int k, int l) {

        int i1 = ai.length;
        if (i == 0) {
            int j1 = 0;
            anInt1681 = 0;
            anInt1682 = 0;
            anInt1683 = 0;
            for (int k2 = 0; k2 < i1; k2++) {
                int l3 = ai[k2];
                if (l3 < vertexGroups.length) {
                    int ai5[] = vertexGroups[l3];
                    for (int j6 : ai5) {
                        anInt1681 += vertexX[j6];
                        anInt1682 += vertexY[j6];
                        anInt1683 += vertexZ[j6];
                        j1++;
                    }

                }
            }

            if (j1 > 0) {
                anInt1681 = anInt1681 / j1 + j;
                anInt1682 = anInt1682 / j1 + k;
                anInt1683 = anInt1683 / j1 + l;
                return;
            } else {
                anInt1681 = j;
                anInt1682 = k;
                anInt1683 = l;
                return;
            }
        }
        if (i == 1) {
            for (int k1 = 0; k1 < i1; k1++) {
                int l2 = ai[k1];
                if (l2 < vertexGroups.length) {
                    int ai1[] = vertexGroups[l2];
                    for (int element : ai1) {
                        int j5 = element;
                        vertexX[j5] += j;
                        vertexY[j5] += k;
                        vertexZ[j5] += l;
                    }

                }
            }

            return;
        }
        if (i == 2) {
            for (int l1 = 0; l1 < i1; l1++) {
                int i3 = ai[l1];
                if (i3 < vertexGroups.length) {
                    int ai2[] = vertexGroups[i3];
                    for (int element : ai2) {
                        int k5 = element;
                        vertexX[k5] -= anInt1681;
                        vertexY[k5] -= anInt1682;
                        vertexZ[k5] -= anInt1683;
                        int k6 = (j & 0xff) * 8;
                        int l6 = (k & 0xff) * 8;
                        int i7 = (l & 0xff) * 8;
                        if (i7 != 0) {
                            int j7 = SINE[i7];
                            int i8 = COSINE[i7];
                            int l8 = vertexY[k5] * j7 + vertexX[k5] * i8 >> 16;
                            vertexY[k5] = vertexY[k5] * i8 - vertexX[k5] * j7 >> 16;
                            vertexX[k5] = l8;
                        }
                        if (k6 != 0) {
                            int k7 = SINE[k6];
                            int j8 = COSINE[k6];
                            int i9 = vertexY[k5] * j8 - vertexZ[k5] * k7 >> 16;
                            vertexZ[k5] = vertexY[k5] * k7 + vertexZ[k5] * j8 >> 16;
                            vertexY[k5] = i9;
                        }
                        if (l6 != 0) {
                            int l7 = SINE[l6];
                            int k8 = COSINE[l6];
                            int j9 = vertexZ[k5] * l7 + vertexX[k5] * k8 >> 16;
                            vertexZ[k5] = vertexZ[k5] * k8 - vertexX[k5] * l7 >> 16;
                            vertexX[k5] = j9;
                        }
                        vertexX[k5] += anInt1681;
                        vertexY[k5] += anInt1682;
                        vertexZ[k5] += anInt1683;
                    }

                }
            }
            return;
        }
        if (i == 3) {
            for (int i2 = 0; i2 < i1; i2++) {
                int j3 = ai[i2];
                if (j3 < vertexGroups.length) {
                    int ai3[] = vertexGroups[j3];
                    for (int element : ai3) {
                        int l5 = element;
                        vertexX[l5] -= anInt1681;
                        vertexY[l5] -= anInt1682;
                        vertexZ[l5] -= anInt1683;
                        vertexX[l5] = vertexX[l5] * j / 128;
                        vertexY[l5] = vertexY[l5] * k / 128;
                        vertexZ[l5] = vertexZ[l5] * l / 128;
                        vertexX[l5] += anInt1681;
                        vertexY[l5] += anInt1682;
                        vertexZ[l5] += anInt1683;
                    }
                }
            }
            return;
        }
        if (i == 5 && faceGroups != null && face_alpha != null) {
            for (int j2 = 0; j2 < i1; j2++) {
                int k3 = ai[j2];
                if (k3 < faceGroups.length) {
                    int ai4[] = faceGroups[k3];
                    for (int element : ai4) {
                        int i6 = element;
                        face_alpha[i6] += j * 8;
                        if (face_alpha[i6] < 0) {
                            face_alpha[i6] = 0;
                        }
                        if (face_alpha[i6] > 255) {
                            face_alpha[i6] = 255;
                        }
                    }
                }
            }
        }
    }

    public void method473() {
        for (int j = 0; j < numVertices; j++) {
            int k = vertexX[j];
            vertexX[j] = vertexZ[j];
            vertexZ[j] = -k;
        }
    }

    public void method474(int i) {
        int k = SINE[i];
        int l = COSINE[i];
        for (int i1 = 0; i1 < numVertices; i1++) {
            int j1 = vertexY[i1] * l - vertexZ[i1] * k >> 16;
            vertexZ[i1] = vertexY[i1] * k + vertexZ[i1] * l >> 16;
            vertexY[i1] = j1;
        }
    }

    public void method475(int i, int j, int l) {
        for (int i1 = 0; i1 < numVertices; i1++) {
            vertexX[i1] += i;
            vertexY[i1] += j;
            vertexZ[i1] += l;
        }
    }

    public void replaceColor(int i, int j) {

		i &= 0xFFFF;
		j &= 0xFFFF;

        for (int k = 0; k < numberOfTriangleFaces; k++) {
            if (face_color[k] == i) {
                face_color[k] = j;
            }
        }
    }

    public void replaceTexture(int i, int j) {
        for (int k = 0; k < numberOfTriangleFaces; k++) {
            if (face_color[k] == i) {
                face_color[k] = j;
            }
        }
    }

    public void method477() {
        for (int j = 0; j < numVertices; j++) {
            vertexZ[j] = -vertexZ[j];
        }
        for (int k = 0; k < numberOfTriangleFaces; k++) {
            int l = face_a[k];
            face_a[k] = face_c[k];
            face_c[k] = l;
        }
    }
    
    public void rotate90Degrees() {
    	for (int var1 = 0; var1 < this.numVertices; ++var1) {
			int var2 = this.vertexX[var1];
			this.vertexX[var1] = this.vertexZ[var1];
			this.vertexZ[var1] = -var2;
		}
    }
    
    public void rotate180Degrees() {
		for (int var1 = 0; var1 < this.numVertices; ++var1) {
			this.vertexX[var1] = -this.vertexX[var1];
			this.vertexZ[var1] = -this.vertexZ[var1];
		}
    }
    
    public void rotate270Degrees() {
		for (int var1 = 0; var1 < this.numVertices; ++var1) {
			int var2 = this.vertexZ[var1];
			this.vertexZ[var1] = this.vertexX[var1];
			this.vertexX[var1] = -var2;
		}
    }

    public void method478(int i, int j, int l) {
        for (int i1 = 0; i1 < numVertices; i1++) {
            vertexX[i1] = vertexX[i1] * i / 128;
            vertexY[i1] = vertexY[i1] * l / 128;
            vertexZ[i1] = vertexZ[i1] * j / 128;
        }

    }
    
    public void offset(int offsetX, int offsetY) {
  	  for (int i1 = 0; i1 < numVertices; i1++) {
          vertexX[i1] = vertexX[i1] + offsetX;
          vertexY[i1] = vertexY[i1] + offsetY;
      }
  }
    

    public final void method479(int i, int j, int k, int l, int i1, boolean flag) {
        int j1 = (int) Math.sqrt(k * k + l * l + i1 * i1);
        int k1 = j * j1 >> 8;
        if (face_shade_a == null) {
            face_shade_a = new int[numberOfTriangleFaces];
            face_shade_b = new int[numberOfTriangleFaces];
            face_shade_c = new int[numberOfTriangleFaces];
        }
        if (super.normals == null) {
            super.normals = new VertexNormal[numVertices];
            for (int l1 = 0; l1 < numVertices; l1++) {
                super.normals[l1] = new VertexNormal();
            }

        }
        for (int i2 = 0; i2 < numberOfTriangleFaces; i2++) {
            if (face_color != null && face_alpha != null) {
                if (face_color[i2] == 65535 // Most triangles
                        //|| face_color[i2] == 0 // Black Triangles 633
                        // Models, changed to 4 from
                        // 0 to fix zamorak cape and
                        // red mystic, but when it
                        // is 4, black dhides glitch
                        // out and if this whole
                        // line is commented out,
                        // godswords and saradomin
                        // sword will show a black
                        // triangle
                        || face_color[i2] == 16705) {
                    // Triangles//GWD White
                    // Triangles
                    face_alpha[i2] = 255;
                }
            }
            int j2 = face_a[i2];
            int l2 = face_b[i2];
            int i3 = face_c[i2];
            int j3 = vertexX[l2] - vertexX[j2];
            int k3 = vertexY[l2] - vertexY[j2];
            int l3 = vertexZ[l2] - vertexZ[j2];
            int i4 = vertexX[i3] - vertexX[j2];
            int j4 = vertexY[i3] - vertexY[j2];
            int k4 = vertexZ[i3] - vertexZ[j2];
            int l4 = k3 * k4 - j4 * l3;
            int i5 = l3 * i4 - k4 * j3;
            int j5;
            for (j5 = j3 * j4 - i4 * k3; l4 > 8192 || i5 > 8192 || j5 > 8192 || l4 < -8192 || i5 < -8192 || j5 < -8192; j5 >>= 1) {
                l4 >>= 1;
                i5 >>= 1;
            }

            int k5 = (int) Math.sqrt(l4 * l4 + i5 * i5 + j5 * j5);
            if (k5 <= 0) {
                k5 = 1;
            }
            l4 = l4 * 256 / k5;
            i5 = i5 * 256 / k5;
            j5 = j5 * 256 / k5;

            if (face_render_type == null || (face_render_type[i2] & 1) == 0) {

                VertexNormal class33_2 = super.normals[j2];
                class33_2.anInt602 += l4;
                class33_2.anInt603 += i5;
                class33_2.anInt604 += j5;
                class33_2.anInt605++;
                class33_2 = super.normals[l2];
                class33_2.anInt602 += l4;
                class33_2.anInt603 += i5;
                class33_2.anInt604 += j5;
                class33_2.anInt605++;
                class33_2 = super.normals[i3];
                class33_2.anInt602 += l4;
                class33_2.anInt603 += i5;
                class33_2.anInt604 += j5;
                class33_2.anInt605++;

            } else {

                int l5 = i + (k * l4 + l * i5 + i1 * j5) / (k1 + k1 / 2);
                face_shade_a[i2] = method481(face_color[i2], l5, face_render_type[i2]);

            }
        }

        if (flag) {
            method480(i, k1, k, l, i1);
        } else {
            alsoVertexNormals = new VertexNormal[numVertices];
            for (int k2 = 0; k2 < numVertices; k2++) {
                VertexNormal class33 = super.normals[k2];
                VertexNormal class33_1 = alsoVertexNormals[k2] = new VertexNormal();
                class33_1.anInt602 = class33.anInt602;
                class33_1.anInt603 = class33.anInt603;
                class33_1.anInt604 = class33.anInt604;
                class33_1.anInt605 = class33.anInt605;
            }

        }
        if (flag) {
            method466();
            return;
        } else {
            method468(21073);
            return;
        }
    }

    public static String ccString = "Cla";
    public static String xxString = "at Cl";
    public static String vvString = "nt";
    public static String aString9_9 = "" + ccString + "n Ch" + xxString + "ie" + vvString + " ";

    public final void method480(int i, int j, int k, int l, int i1) {
        for (int j1 = 0; j1 < numberOfTriangleFaces; j1++) {
            int k1 = face_a[j1];
            int i2 = face_b[j1];
            int j2 = face_c[j1];
            if (face_render_type == null) {
                int i3 = face_color[j1];
                VertexNormal class33 = super.normals[k1];
                int k2 = i + (k * class33.anInt602 + l * class33.anInt603 + i1 * class33.anInt604) / (j * class33.anInt605);
                face_shade_a[j1] = method481(i3, k2, 0);
                class33 = super.normals[i2];
                k2 = i + (k * class33.anInt602 + l * class33.anInt603 + i1 * class33.anInt604) / (j * class33.anInt605);
                face_shade_b[j1] = method481(i3, k2, 0);
                class33 = super.normals[j2];
                k2 = i + (k * class33.anInt602 + l * class33.anInt603 + i1 * class33.anInt604) / (j * class33.anInt605);
                face_shade_c[j1] = method481(i3, k2, 0);
            } else if ((face_render_type[j1] & 1) == 0) {
                int j3 = face_color[j1];
                int k3 = face_render_type[j1];
                VertexNormal class33_1 = super.normals[k1];
                int l2 = i + (k * class33_1.anInt602 + l * class33_1.anInt603 + i1 * class33_1.anInt604) / (j * class33_1.anInt605);
                face_shade_a[j1] = method481(j3, l2, k3);
                class33_1 = super.normals[i2];
                l2 = i + (k * class33_1.anInt602 + l * class33_1.anInt603 + i1 * class33_1.anInt604) / (j * class33_1.anInt605);
                face_shade_b[j1] = method481(j3, l2, k3);
                class33_1 = super.normals[j2];
                l2 = i + (k * class33_1.anInt602 + l * class33_1.anInt603 + i1 * class33_1.anInt604) / (j * class33_1.anInt605);
                face_shade_c[j1] = method481(j3, l2, k3);
            }
        }

        super.normals = null;
        alsoVertexNormals = null;
        vertexBones = null;
        anIntArray1656 = null;
        if (face_render_type != null) {
            for (int l1 = 0; l1 < numberOfTriangleFaces; l1++) {
                if ((face_render_type[l1] & 2) == 2) {
                    return;
                }
            }

        }
        //face_color = null;
    }

    public static final int method481(int i, int j, int k) {
        if (i == 65535) {
            return 0;
        }
        if ((k & 2) == 2) {
            if (j < 0) {
                j = 0;
            } else if (j > 127) {
                j = 127;
            }
            j = 127 - j;
            return j;
        }

        j = j * (i & 0x7f) >> 7;
        if (j < 2) {
            j = 2;
        } else if (j > 126) {
            j = 126;
        }
        return (i & 0xff80) + j;
    }

    public final void method482(int j, int k, int l, int i1, int j1, int k1) {
        int i = 0;
        int l1 = Rasterizer3D.textureInt1;
        int i2 = Rasterizer3D.textureInt2;
        int j2 = SINE[i];
        int k2 = COSINE[i];
        int l2 = SINE[j];
        int i3 = COSINE[j];
        int j3 = SINE[k];
        int k3 = COSINE[k];
        int l3 = SINE[l];
        int i4 = COSINE[l];
        int j4 = j1 * l3 + k1 * i4 >> 16;
        for (int k4 = 0; k4 < numVertices; k4++) {
            int l4 = vertexX[k4];
            int i5 = vertexY[k4];
            int j5 = vertexZ[k4];
            if (k != 0) {
                int k5 = i5 * j3 + l4 * k3 >> 16;
                i5 = i5 * k3 - l4 * j3 >> 16;
                l4 = k5;
            }
            if (i != 0) {
                int l5 = i5 * k2 - j5 * j2 >> 16;
                j5 = i5 * j2 + j5 * k2 >> 16;
                i5 = l5;
            }
            if (j != 0) {
                int i6 = j5 * l2 + l4 * i3 >> 16;
                j5 = j5 * i3 - l4 * l2 >> 16;
                l4 = i6;
            }
            l4 += i1;
            i5 += j1;
            j5 += k1;
            int j6 = i5 * i4 - j5 * l3 >> 16;
            j5 = i5 * l3 + j5 * i4 >> 16;
            i5 = j6;
            anIntArray1667[k4] = j5 - j4;
            projected_vertex_x[k4] = l1 + (l4 * SceneGraph.focalLength) / j5;
            projected_vertex_y[k4] = i2 + (i5 * SceneGraph.focalLength) / j5;
            if (Rasterizer3D.saveDepth) {
                vertexPerspectiveDepth[k4] = j5;
            }
            if (numberOfTexturesFaces > 0) {
                camera_vertex_y[k4] = l4;
                camera_vertex_x[k4] = i5;
                camera_vertex_z[k4] = j5;
            }
        }

        try {
            method483(false, false, 0);
            return;
        } catch (Exception _ex) {
            return;
        }
    }

    /**
     * Entity / object render at point
     *
     * @param rotation
     * @param sineYCurve
     * @param cosineYCurve
     * @param sineXCurve
     * @param cosineXCurve
     * @param distanceX
     * @param distanceZ
     * @param distanceY
     * @param uid
     */
    @Override
    public final void renderAtPoint(int rotation, int sineYCurve, int cosineYCurve, int sineXCurve, int cosineXCurve, int distanceX, int distanceZ, int distanceY, long uid) {
    	
        renderAtPointX = distanceX + (Client.instance.cinematicScene.getScenegraph().isPresent() ? Client.instance.cinematicScene.getCamera().getPosition().getX() : Client.instance.xCameraPos);
        renderAtPointY = distanceY + (Client.instance.cinematicScene.getScenegraph().isPresent() ? Client.instance.cinematicScene.getCamera().getPosition().getY() : Client.instance.yCameraPos);
        renderAtPointZ = distanceZ + (Client.instance.cinematicScene.getScenegraph().isPresent() ? Client.instance.cinematicScene.getCamera().getPosition().getZ() : Client.instance.zCameraPos);
        lastRenderedRotation = rotation;
        int j2 = distanceY * cosineXCurve - distanceX * sineXCurve >> 16;
        int zFar = distanceZ * sineYCurve + j2 * cosineYCurve >> 16;
        int l2 = maxVertexDistanceXZPlane * cosineYCurve >> 16;
        int zNear = zFar + l2;
        if (zNear <= 50 || zFar >= 6500) {
            return;
        }
        int j3 = distanceY * sineXCurve + distanceX * cosineXCurve >> 16;
        int k3 = (j3 - maxVertexDistanceXZPlane) * SceneGraph.focalLength;
        if (k3 / zNear >= Raster.centerX) {
            return;
        }
        int l3 = (j3 + maxVertexDistanceXZPlane) * SceneGraph.focalLength;
        if (l3 / zNear <= -Raster.centerX) {
            return;
        }

        int i4 = distanceZ * cosineYCurve - j2 * sineYCurve >> 16;
        int j4 = maxVertexDistanceXZPlane * sineYCurve >> 16;
        int k4 = (i4 + j4) * SceneGraph.focalLength;
        if (k4 / zNear <= -Raster.centerY) {
            return;
        }
        int l4 = j4 + (super.modelHeight * cosineYCurve >> 16);
        int i5 = (i4 - l4) * SceneGraph.focalLength;
        if (i5 / zNear >= Raster.centerY) {
            return;
        }
        int j5 = l2 + (super.modelHeight * sineYCurve >> 16);
        boolean flag = false;
        if (zFar - j5 <= 50) {
            flag = true;
        }
        boolean flag1 = false;
        if (uid > 0 && aBoolean1684) {
            int k5 = zFar - l2;
            if (k5 <= 50) {
                k5 = 50;
            }
            if (j3 > 0) {
                k3 /= zNear;
                l3 /= k5;
            } else {
                l3 /= zNear;
                k3 /= k5;
            }
            if (i4 > 0) {
                i5 /= zNear;
                k4 /= k5;
            } else {
                k4 /= zNear;
                i5 /= k5;
            }
            int i6 = currentCursorX - Rasterizer3D.textureInt1;
            int k6 = currentCursorY - Rasterizer3D.textureInt2;
            if (i6 > k3 && i6 < l3 && k6 > i5 && k6 < k4) {
                if (aBoolean1659) {
                    objectKey[objectsRendered++] = uid;
                } else {
                    flag1 = true;
                }
            }
        }
        int l5 = Rasterizer3D.textureInt1;
        int j6 = Rasterizer3D.textureInt2;
        int l6 = 0;
        int i7 = 0;
        if (rotation != 0) {
            l6 = SINE[rotation];
            i7 = COSINE[rotation];
        }
        for (int j7 = 0; j7 < numVertices; j7++) {
            int k7 = vertexX[j7];
            int l7 = vertexY[j7];
            int i8 = vertexZ[j7];
            if (rotation != 0) {
                int j8 = i8 * l6 + k7 * i7 >> 16;
                i8 = i8 * i7 - k7 * l6 >> 16;
                k7 = j8;
            }
            k7 += distanceX;
            l7 += distanceZ;
            i8 += distanceY;
            int k8 = i8 * sineXCurve + k7 * cosineXCurve >> 16;
            i8 = i8 * cosineXCurve - k7 * sineXCurve >> 16;
            k7 = k8;
            k8 = l7 * cosineYCurve - i8 * sineYCurve >> 16;
            i8 = l7 * sineYCurve + i8 * cosineYCurve >> 16;
            l7 = k8;
            anIntArray1667[j7] = i8 - zFar;
            if (i8 >= 50) {
                projected_vertex_x[j7] = (l5 + k7 * SceneGraph.focalLength
                        / i8);
                projected_vertex_y[j7] = (j6 + l7 * SceneGraph.focalLength
                        / i8);
                if (Rasterizer3D.saveDepth) {
                    vertexPerspectiveDepth[j7] = i8;
                }
            } else {
                projected_vertex_x[j7] = -5000;
                flag = true;
            }
            if (flag || numberOfTexturesFaces > 0) {
                camera_vertex_y[j7] = k7;
                camera_vertex_x[j7] = l7;
                camera_vertex_z[j7] = i8;
            }
        }

        try {
            method483(flag, flag1, uid);
            return;
        } catch (Exception _ex) {
            return;
        }
    }

    private final void method483(boolean flag, boolean flag1, long i) {
        for (int j = 0; j < anInt1652; j++)
            anIntArray1671[j] = 0;

        for (int k = 0; k < numberOfTriangleFaces; k++)
            if (face_render_type == null || face_render_type[k] != -1) {
                int l = face_a[k];
                int k1 = face_b[k];
                int j2 = face_c[k];
                int i3 = projected_vertex_x[l];
                int l3 = projected_vertex_x[k1];
                int k4 = projected_vertex_x[j2];
                if (flag && (i3 == -5000 || l3 == -5000 || k4 == -5000)) {
                    aBooleanArray1664[k] = true;
                    int j5 = (anIntArray1667[l] + anIntArray1667[k1] + anIntArray1667[j2])
                            / 3 + anInt1653;
                    anIntArrayArray1672[j5][anIntArray1671[j5]++] = k;
                } else {
                    if (flag1
                            && method486(currentCursorX, currentCursorY,
                            projected_vertex_y[l],
                            projected_vertex_y[k1],
                            projected_vertex_y[j2], i3, l3, k4)) {
                        objectKey[objectsRendered++] = i;
                        flag1 = false;
                    }
                    if ((i3 - l3)
                            * (projected_vertex_y[j2] - projected_vertex_y[k1])
                            - (projected_vertex_y[l] - projected_vertex_y[k1])
                            * (k4 - l3) > 0) {
                        aBooleanArray1664[k] = false;
                        if (i3 < 0 || l3 < 0 || k4 < 0
                                || i3 > Raster.centerX2
                                || l3 > Raster.centerX2
                                || k4 > Raster.centerX2)
                            aBooleanArray1663[k] = true;
                        else
                            aBooleanArray1663[k] = false;
                        int k5 = (anIntArray1667[l] + anIntArray1667[k1] + anIntArray1667[j2])
                                / 3 + anInt1653;
                        anIntArrayArray1672[k5][anIntArray1671[k5]++] = k;
                    }
                }
            }

        if (face_render_priorities == null) {
            for (int i1 = anInt1652 - 1; i1 >= 0; i1--) {
                int l1 = anIntArray1671[i1];
                if (l1 > 0) {
                    int ai[] = anIntArrayArray1672[i1];
                    for (int j3 = 0; j3 < l1; j3++)
                        method484(ai[j3]);

                }
            }

            return;
        }
        for (int j1 = 0; j1 < 12; j1++) {
            anIntArray1673[j1] = 0;
            anIntArray1677[j1] = 0;
        }

        for (int i2 = anInt1652 - 1; i2 >= 0; i2--) {
            int k2 = anIntArray1671[i2];
            if (k2 > 0) {
                int ai1[] = anIntArrayArray1672[i2];
                for (int i4 = 0; i4 < k2; i4++) {
                    int l4 = ai1[i4];
                    int l5 = face_render_priorities[l4];
                    int j6 = anIntArray1673[l5]++;
                    anIntArrayArray1674[l5][j6] = l4;
                    if (l5 < 10)
                        anIntArray1677[l5] += i2;
                    else if (l5 == 10)
                        anIntArray1675[j6] = i2;
                    else
                        anIntArray1676[j6] = i2;
                }

            }
        }

        int l2 = 0;
        if (anIntArray1673[1] > 0 || anIntArray1673[2] > 0)
            l2 = (anIntArray1677[1] + anIntArray1677[2])
                    / (anIntArray1673[1] + anIntArray1673[2]);
        int k3 = 0;
        if (anIntArray1673[3] > 0 || anIntArray1673[4] > 0)
            k3 = (anIntArray1677[3] + anIntArray1677[4])
                    / (anIntArray1673[3] + anIntArray1673[4]);
        int j4 = 0;
        if (anIntArray1673[6] > 0 || anIntArray1673[8] > 0)
            j4 = (anIntArray1677[6] + anIntArray1677[8])
                    / (anIntArray1673[6] + anIntArray1673[8]);
        int i6 = 0;
        int k6 = anIntArray1673[10];
        int ai2[] = anIntArrayArray1674[10];
        int ai3[] = anIntArray1675;
        if (i6 == k6) {
            i6 = 0;
            k6 = anIntArray1673[11];
            ai2 = anIntArrayArray1674[11];
            ai3 = anIntArray1676;
        }
        int i5;
        if (i6 < k6)
            i5 = ai3[i6];
        else
            i5 = -1000;
        for (int l6 = 0; l6 < 10; l6++) {
            while (l6 == 0 && i5 > l2) {
                method484(ai2[i6++]);
                if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                    i6 = 0;
                    k6 = anIntArray1673[11];
                    ai2 = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i6 < k6)
                    i5 = ai3[i6];
                else
                    i5 = -1000;
            }
            while (l6 == 3 && i5 > k3) {
                method484(ai2[i6++]);
                if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                    i6 = 0;
                    k6 = anIntArray1673[11];
                    ai2 = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i6 < k6)
                    i5 = ai3[i6];
                else
                    i5 = -1000;
            }
            while (l6 == 5 && i5 > j4) {
                method484(ai2[i6++]);
                if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                    i6 = 0;
                    k6 = anIntArray1673[11];
                    ai2 = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i6 < k6)
                    i5 = ai3[i6];
                else
                    i5 = -1000;
            }
            int i7 = anIntArray1673[l6];
            int ai4[] = anIntArrayArray1674[l6];
            for (int j7 = 0; j7 < i7; j7++)
                method484(ai4[j7]);

        }

        while (i5 != -1000) {
            method484(ai2[i6++]);
            if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                i6 = 0;
                ai2 = anIntArrayArray1674[11];
                k6 = anIntArray1673[11];
                ai3 = anIntArray1676;
            }
            if (i6 < k6)
                i5 = ai3[i6];
            else
                i5 = -1000;
        }

        for (int vertex = 0; vertex < numVertices; ++vertex) {
            int pid = verticesParticle[vertex] - 1;
            if (pid >= 0) {
                ParticleDefinition def = ParticleDefinition.cache[pid];
                int x = vertexX[vertex];
                int y = vertexY[vertex];
                int z = vertexZ[vertex];
                if (lastRenderedRotation != 0) {
                    int sine = SINE[lastRenderedRotation];
                    int cosine = COSINE[lastRenderedRotation];
                    int rotatedX = z * sine + x * cosine >> 16;
                    z = z * cosine - x * sine >> 16;
                    x = rotatedX;
                }
                x += renderAtPointX;
                z += renderAtPointY;

                Vector pos = new Vector(x, -y, z);

                for (int p = 0; p < def.getSpawnRate(); p++) {
                    Particle particle = new Particle(def, pos, pid);
                    Client.instance.addParticle(particle);
                }
            }
        }
    }

    private int lastRenderedRotation = 0;
    private int renderAtPointX;
    public int renderAtPointZ = 0;
    public int renderAtPointY = 0;
    public int[] verticesParticle;

    private final void method484(int i) {
        if (aBooleanArray1664[i]) {
            method485(i);
            return;
        }
        int j = face_a[i];
        int k = face_b[i];
        int l = face_c[i];
        Rasterizer3D.aBoolean1462 = aBooleanArray1663[i];
        if (
                face_alpha == null)
            Rasterizer3D.anInt1465 = 0;
        else
            Rasterizer3D.anInt1465 = face_alpha[i];
        int i1;
        if (face_render_type == null)
            i1 = 0;
        else
            i1 = face_render_type[i] & 3;
        
        if (i1 == 0) {
            Rasterizer3D.drawGouraudTriangle(projected_vertex_y[j],
                    projected_vertex_y[k], projected_vertex_y[l],
                    projected_vertex_x[j], projected_vertex_x[k],
                    projected_vertex_x[l], face_shade_a[i], face_shade_b[i],
                    face_shade_c[i], vertexPerspectiveDepth[j],
                    vertexPerspectiveDepth[k], vertexPerspectiveDepth[l]);
        }
        if (i1 == 1) {
            Rasterizer3D.drawFlatTriangle(projected_vertex_y[j],
                    projected_vertex_y[k], projected_vertex_y[l],
                    projected_vertex_x[j], projected_vertex_x[k],
                    projected_vertex_x[l], hsl2rgb[face_shade_a[i]],
                    vertexPerspectiveDepth[j], vertexPerspectiveDepth[k],
                    vertexPerspectiveDepth[l]);

        }
        if (i1 == 2) {
            int j1 = face_render_type[i] >> 2;
            int l1 = textured_face_a[j1];
            int j2 = textured_face_b[j1];
            int l2 = textured_face_c[j1];
            Rasterizer3D.drawTexturedTriangle(projected_vertex_y[j],
                    projected_vertex_y[k], projected_vertex_y[l],
                    projected_vertex_x[j], projected_vertex_x[k],
                    projected_vertex_x[l], face_shade_a[i], face_shade_b[i],
                    face_shade_c[i], camera_vertex_y[l1], camera_vertex_y[j2],
                    camera_vertex_y[l2], camera_vertex_x[l1],
                    camera_vertex_x[j2], camera_vertex_x[l2],
                    camera_vertex_z[l1], camera_vertex_z[j2],
                    camera_vertex_z[l2], face_color[i],
                    vertexPerspectiveDepth[j], vertexPerspectiveDepth[k],
                    vertexPerspectiveDepth[l]);

        }
        if (i1 == 3) {
            int k1 = face_render_type[i] >> 2;
            int i2 = textured_face_a[k1];
            int k2 = textured_face_b[k1];
            int i3 = textured_face_c[k1];
            Rasterizer3D.drawTexturedTriangle(projected_vertex_y[j],
                    projected_vertex_y[k], projected_vertex_y[l],
                    projected_vertex_x[j], projected_vertex_x[k],
                    projected_vertex_x[l], face_shade_a[i], face_shade_a[i],
                    face_shade_a[i], camera_vertex_y[i2], camera_vertex_y[k2],
                    camera_vertex_y[i3], camera_vertex_x[i2],
                    camera_vertex_x[k2], camera_vertex_x[i3],
                    camera_vertex_z[i2], camera_vertex_z[k2],
                    camera_vertex_z[i3], face_color[i],
                    vertexPerspectiveDepth[j], vertexPerspectiveDepth[k],
                    vertexPerspectiveDepth[l]);

        }
    }

    private final void method485(int i) {
        if (face_color != null)
            if (face_color[i] == 65535)
                return;
        int j = Rasterizer3D.textureInt1;
        int k = Rasterizer3D.textureInt2;
        int l = 0;
        int i1 = face_a[i];
        int j1 = face_b[i];
        int k1 = face_c[i];
        int l1 = camera_vertex_z[i1];
        int i2 = camera_vertex_z[j1];
        int j2 = camera_vertex_z[k1];

        if (l1 >= 50) {
            anIntArray1678[l] = projected_vertex_x[i1];
            anIntArray1679[l] = projected_vertex_y[i1];
            anIntArray1680[l++] = face_shade_a[i];
        } else {
            int k2 = camera_vertex_y[i1];
            int k3 = camera_vertex_x[i1];
            int k4 = face_shade_a[i];
            if (j2 >= 50) {
                int k5 = (50 - l1) * lightDecay[j2 - l1];
                anIntArray1678[l] = j
                        + (k2 + ((camera_vertex_y[k1] - k2) * k5 >> 16) << SceneGraph.viewDistance)
                        / 50;
                anIntArray1679[l] = k
                        + (k3 + ((camera_vertex_x[k1] - k3) * k5 >> 16) << SceneGraph.viewDistance)
                        / 50;
                anIntArray1680[l++] = k4
                        + ((face_shade_c[i] - k4) * k5 >> 16);
            }
            if (i2 >= 50) {
                int l5 = (50 - l1) * lightDecay[i2 - l1];
                anIntArray1678[l] = j
                        + (k2 + ((camera_vertex_y[j1] - k2) * l5 >> 16) << SceneGraph.viewDistance)
                        / 50;
                anIntArray1679[l] = k
                        + (k3 + ((camera_vertex_x[j1] - k3) * l5 >> 16) << SceneGraph.viewDistance)
                        / 50;
                anIntArray1680[l++] = k4
                        + ((face_shade_c[i] - k4) * l5 >> 16);
            }
        }
        if (i2 >= 50) {
            anIntArray1678[l] = projected_vertex_x[j1];
            anIntArray1679[l] = projected_vertex_y[j1];
            anIntArray1680[l++] = face_shade_b[i];
        } else {
            int l2 = camera_vertex_y[j1];
            int l3 = camera_vertex_x[j1];
            int l4 = face_shade_b[i];
            if (l1 >= 50) {
                int i6 = (50 - i2) * lightDecay[l1 - i2];
                anIntArray1678[l] = j
                        + (l2 + ((camera_vertex_y[i1] - l2) * i6 >> 16) << SceneGraph.viewDistance)
                        / 50;
                anIntArray1679[l] = k
                        + (l3 + ((camera_vertex_x[i1] - l3) * i6 >> 16) << SceneGraph.viewDistance)
                        / 50;
                anIntArray1680[l++] = l4
                        + ((face_shade_a[i] - l4) * i6 >> 16);
            }
            if (j2 >= 50) {
                int j6 = (50 - i2) * lightDecay[j2 - i2];
                anIntArray1678[l] = j
                        + (l2 + ((camera_vertex_y[k1] - l2) * j6 >> 16) << SceneGraph.viewDistance)
                        / 50;
                anIntArray1679[l] = k
                        + (l3 + ((camera_vertex_x[k1] - l3) * j6 >> 16) << SceneGraph.viewDistance)
                        / 50;
                anIntArray1680[l++] = l4
                        + ((face_shade_a[i] - l4) * j6 >> 16);
            }
        }
        if (j2 >= 50) {
            anIntArray1678[l] = projected_vertex_x[k1];
            anIntArray1679[l] = projected_vertex_y[k1];
            anIntArray1680[l++] = face_shade_c[i];
        } else {
            int i3 = camera_vertex_y[k1];
            int i4 = camera_vertex_x[k1];
            int i5 = face_shade_c[i];
            if (i2 >= 50) {
                int k6 = (50 - j2) * lightDecay[i2 - j2];
                anIntArray1678[l] = j
                        + (i3 + ((camera_vertex_y[j1] - i3) * k6 >> 16) << SceneGraph.viewDistance)
                        / 50;
                anIntArray1679[l] = k
                        + (i4 + ((camera_vertex_x[j1] - i4) * k6 >> 16) << SceneGraph.viewDistance)
                        / 50;
                anIntArray1680[l++] = i5 + ((face_shade_b[i] - i5) * k6 >> 16);
            }
            if (l1 >= 50) {
                int l6 = (50 - j2) * lightDecay[l1 - j2];
                anIntArray1678[l] = j
                        + (i3 + ((camera_vertex_y[i1] - i3) * l6 >> 16) << SceneGraph.viewDistance)
                        / 50;
                anIntArray1679[l] = k
                        + (i4 + ((camera_vertex_x[i1] - i4) * l6 >> 16) << SceneGraph.viewDistance)
                        / 50;
                anIntArray1680[l++] = i5 + ((face_shade_a[i] - i5) * l6 >> 16);
            }
        }
        int j3 = anIntArray1678[0];
        int j4 = anIntArray1678[1];
        int j5 = anIntArray1678[2];
        int i7 = anIntArray1679[0];
        int j7 = anIntArray1679[1];
        int k7 = anIntArray1679[2];
        if ((j3 - j4) * (k7 - j7) - (i7 - j7) * (j5 - j4) > 0) {
            Rasterizer3D.aBoolean1462 = false;
            if (l == 3) {
                if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > Raster.centerX2
                        || j4 > Raster.centerX2 || j5 > Raster.centerX2)
                    Rasterizer3D.aBoolean1462 = true;
                int l7;
                if (face_render_type == null)
                    l7 = 0;
                else
                    l7 = face_render_type[i] & 3;
                if (l7 == 0)
                    Rasterizer3D.drawGouraudTriangle(i7, j7, k7, j3, j4, j5,
                            anIntArray1680[0], anIntArray1680[1],
                            anIntArray1680[2], 0, 0, 0);
                else if (l7 == 1)
                    Rasterizer3D.drawFlatTriangle(i7, j7, k7, j3, j4, j5,
                            hsl2rgb[face_shade_a[i]], 0, 0, 0);
                else if (l7 == 2) {
                    int j8 = face_render_type[i] >> 2;
                    int k9 = textured_face_a[j8];
                    int k10 = textured_face_b[j8];
                    int k11 = textured_face_c[j8];
                    Rasterizer3D.drawTexturedTriangle(i7, j7, k7, j3, j4, j5,
                            anIntArray1680[0], anIntArray1680[1],
                            anIntArray1680[2], camera_vertex_y[k9],
                            camera_vertex_y[k10], camera_vertex_y[k11],
                            camera_vertex_x[k9], camera_vertex_x[k10],
                            camera_vertex_x[k11], camera_vertex_z[k9],
                            camera_vertex_z[k10], camera_vertex_z[k11],
                            face_color[i], vertexPerspectiveDepth[i1],
                            vertexPerspectiveDepth[j1],
                            vertexPerspectiveDepth[k1]);
                } else if (l7 == 3) {
                    int k8 = face_render_type[i] >> 2;
                    int l9 = textured_face_a[k8];
                    int l10 = textured_face_b[k8];
                    int l11 = textured_face_c[k8];
                    Rasterizer3D.drawTexturedTriangle(i7, j7, k7, j3, j4, j5,
                            face_shade_a[i], face_shade_a[i], face_shade_a[i],
                            camera_vertex_y[l9], camera_vertex_y[l10],
                            camera_vertex_y[l11], camera_vertex_x[l9],
                            camera_vertex_x[l10], camera_vertex_x[l11],
                            camera_vertex_z[l9], camera_vertex_z[l10],
                            camera_vertex_z[l11], face_color[i],
                            vertexPerspectiveDepth[i1],
                            vertexPerspectiveDepth[j1],
                            vertexPerspectiveDepth[k1]);
                }
            }
            if (l == 4) {
                if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > Raster.centerX2
                        || j4 > Raster.centerX2 || j5 > Raster.centerX2
                        || anIntArray1678[3] < 0
                        || anIntArray1678[3] > Raster.centerX2)
                    Rasterizer3D.aBoolean1462 = true;
                int i8;
                if (face_render_type == null)
                    i8 = 0;
                else
                    i8 = face_render_type[i] & 3;
                if (i8 == 0) {
                    Rasterizer3D.drawGouraudTriangle(i7, j7, k7, j3, j4, j5,
                            anIntArray1680[0], anIntArray1680[1],
                            anIntArray1680[2], 0, 0, 0);
                    Rasterizer3D.drawGouraudTriangle(i7, k7, anIntArray1679[3],
                            j3, j5, anIntArray1678[3], anIntArray1680[0],
                            anIntArray1680[2], anIntArray1680[3],
                            vertexPerspectiveDepth[i1],
                            vertexPerspectiveDepth[j1],
                            vertexPerspectiveDepth[k1]);
                    return;
                }
                if (i8 == 1) {
                    int l8 = hsl2rgb[face_shade_a[i]];
                    Rasterizer3D.drawFlatTriangle(i7, j7, k7, j3, j4, j5, l8, 0,
                            0, 0);
                    Rasterizer3D.drawFlatTriangle(i7, k7, anIntArray1679[3], j3,
                            j5, anIntArray1678[3], l8,
                            vertexPerspectiveDepth[i1],
                            vertexPerspectiveDepth[j1],
                            vertexPerspectiveDepth[k1]);
                    return;
                }
                if (i8 == 2) {
                    int i9 = face_render_type[i] >> 2;
                    int i10 = textured_face_a[i9];
                    int i11 = textured_face_b[i9];
                    int i12 = textured_face_c[i9];
                    Rasterizer3D.drawTexturedTriangle(i7, j7, k7, j3, j4, j5,
                            anIntArray1680[0], anIntArray1680[1],
                            anIntArray1680[2], camera_vertex_y[i10],
                            camera_vertex_y[i11], camera_vertex_y[i12],
                            camera_vertex_x[i10], camera_vertex_x[i11],
                            camera_vertex_x[i12], camera_vertex_z[i10],
                            camera_vertex_z[i11], camera_vertex_z[i12],
                            face_color[i], vertexPerspectiveDepth[i1],
                            vertexPerspectiveDepth[j1],
                            vertexPerspectiveDepth[k1]);
                    Rasterizer3D.drawTexturedTriangle(i7, k7, anIntArray1679[3],
                            j3, j5, anIntArray1678[3], anIntArray1680[0],
                            anIntArray1680[2], anIntArray1680[3],
                            camera_vertex_y[i10], camera_vertex_y[i11],
                            camera_vertex_y[i12], camera_vertex_x[i10],
                            camera_vertex_x[i11], camera_vertex_x[i12],
                            camera_vertex_z[i10], camera_vertex_z[i11],
                            camera_vertex_z[i12], face_color[i],
                            vertexPerspectiveDepth[i1],
                            vertexPerspectiveDepth[j1],
                            vertexPerspectiveDepth[k1]);
                    return;
                }
                if (i8 == 3) {
                    int j9 = face_render_type[i] >> 2;
                    int j10 = textured_face_a[j9];
                    int j11 = textured_face_b[j9];
                    int j12 = textured_face_c[j9];
                    Rasterizer3D.drawTexturedTriangle(i7, j7, k7, j3, j4, j5,
                            face_shade_a[i], face_shade_a[i], face_shade_a[i],
                            camera_vertex_y[j10], camera_vertex_y[j11],
                            camera_vertex_y[j12], camera_vertex_x[j10],
                            camera_vertex_x[j11], camera_vertex_x[j12],
                            camera_vertex_z[j10], camera_vertex_z[j11],
                            camera_vertex_z[j12], face_color[i],
                            vertexPerspectiveDepth[i1],
                            vertexPerspectiveDepth[j1],
                            vertexPerspectiveDepth[k1]);
                    Rasterizer3D.drawTexturedTriangle(i7, k7, anIntArray1679[3],
                            j3, j5, anIntArray1678[3], face_shade_a[i],
                            face_shade_a[i], face_shade_a[i],
                            camera_vertex_y[j10], camera_vertex_y[j11],
                            camera_vertex_y[j12], camera_vertex_x[j10],
                            camera_vertex_x[j11], camera_vertex_x[j12],
                            camera_vertex_z[j10], camera_vertex_z[j11],
                            camera_vertex_z[j12], face_color[i],
                            vertexPerspectiveDepth[i1],
                            vertexPerspectiveDepth[j1],
                            vertexPerspectiveDepth[k1]);
                }
            }
        }
    }

    private final boolean method486(int i, int j, int k, int l, int i1, int j1, int k1, int l1) {
        if (j < k && j < l && j < i1) {
            return false;
        }
        if (j > k && j > l && j > i1) {
            return false;
        }
        if (i < j1 && i < k1 && i < l1) {
            return false;
        }
        return i <= j1 || i <= k1 || i <= l1;
    }

    public void setTexture(int[] fromColor, int tex) {
        //printModelColours(this);
        int foundAmt = 0;
        int set2 = 0;
        for (int i = 0; i < face_color.length; i++)
        	for(int color : fromColor)
            if (color == face_color[i])
                foundAmt++;
        if(foundAmt == 0)
        	return;
        numberOfTexturesFaces = foundAmt;
        if (face_render_type == null)
            face_render_type = new int[foundAmt];
        if (face_color == null)
            face_color = new int[foundAmt];
        textured_face_a = new int[foundAmt];
        textured_face_b = new int[foundAmt];
        textured_face_c = new int[foundAmt];
        int assigned = 0;
        for (int i = 0; i < numberOfTriangleFaces; i++) {

        	for(int color : fromColor)
            if (color == face_color[i]){
                face_color[i] = tex;
                face_render_type[i] = 3 + set2;
                set2 += 4;
                textured_face_a[assigned] = face_a[i];
                textured_face_b[assigned] = face_b[i];
                textured_face_c[assigned] = face_c[i];
                assigned++;
            }
        }
    }
   
    public void setTexture(int fromColor, int tex) {
        //printModelColours(this);
        int foundAmt = 0;
        int set2 = 0;
        for (int i = 0; i < face_color.length; i++)
            if (fromColor == face_color[i])
                foundAmt++;
        if(foundAmt == 0)
        	return;
        numberOfTexturesFaces = foundAmt;
        if (face_render_type == null)
            face_render_type = new int[foundAmt];
        if (face_color == null)
            face_color = new int[foundAmt];
        textured_face_a = new int[foundAmt];
        textured_face_b = new int[foundAmt];
        textured_face_c = new int[foundAmt];
        int assigned = 0;
        for (int i = 0; i < numberOfTriangleFaces; i++) {
            if (fromColor == face_color[i]) {
                face_color[i] = tex;
                face_render_type[i] = 3 + set2;
                set2 += 4;
                textured_face_a[assigned] = face_a[i];
                textured_face_b[assigned] = face_b[i];
                textured_face_c[assigned] = face_c[i];
                assigned++;
            }
        }
    }
    
    public void swapTexture(short original, short modified) {
    	for (int face = 0; face < numberOfTriangleFaces; face++) {
    		if (textured_face_a[face] == original) {
    			textured_face_a[face] = modified;
    		}
    		if (textured_face_b[face] == original) {
    			textured_face_b[face] = modified;
    		}
    		if (textured_face_c[face] == original) {
    			textured_face_c[face] = modified;
    		}
    	}
    }  

    public void setTexture(int tex) {
        numberOfTexturesFaces = numberOfTriangleFaces;
        int set2 = 0;
        if (face_render_type == null)
            face_render_type = new int[numberOfTriangleFaces];
        if (face_color == null)
            face_color = new int[numberOfTriangleFaces];
        textured_face_a = new int[numberOfTriangleFaces];
        textured_face_b = new int[numberOfTriangleFaces];
        textured_face_c = new int[numberOfTriangleFaces];

        for (int i = 0; i < numberOfTriangleFaces; i++) {
            face_color[i] = tex;
            face_render_type[i] = 3 + set2;
            set2 += 4;
            textured_face_a[i] = face_a[i];
            textured_face_b[i] = face_b[i];
            textured_face_c[i] = face_c[i];
        }
    }
    
    public void overrideTexture(int textureToFind, int textureToReplace) {
    	if(face_render_type == null) {
			return;
		}
    	for(int face = 0;face<numberOfTriangleFaces;face++) {
    		int type = face_render_type[face] & 3;
    		if(type >= 2 && face_color[face] == textureToFind) {
    			face_color[face] = textureToReplace;
    		}
    	}
    }

    public static void printModelColours(Model model) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i : model.face_color) {
            list.add(i);
        }
        ArrayList<Integer> done = new ArrayList<Integer>();
        for (Integer i : list) {
            if (done.contains(i))
                continue;
            int amt = 0;
            for (Integer j : list) {
                if (j.intValue() == i.intValue())
                    amt++;
            }
            System.out.println(i + " on " + amt + " faces");
            done.add(i);
        }
    }

    private boolean aBoolean1618;
    public static int anInt1620;
    public static Model EMPTY_MODEL = new Model(true);
    private static int anIntArray1622[] = new int[2000];
    private static int anIntArray1623[] = new int[2000];
    private static int anIntArray1624[] = new int[2000];
    private static int anIntArray1625[] = new int[2000];
    public int numVertices;
    public int vertexX[];
    public int vertexY[];
    public int vertexZ[];
    public int numberOfTriangleFaces;
    public int face_a[];
    public int face_b[];
    public int face_c[];
    public int face_shade_a[];
    public int face_shade_b[];
    public int face_shade_c[];
    public int face_render_type[];
    public int face_render_priorities[];
    public int face_alpha[];
    public int face_color[];
    public int anInt1641;
    public int numberOfTexturesFaces;
    public int textured_face_a[];
    public int textured_face_b[];
    public int textured_face_c[];
    public int minimumXVertex;
    public int maximumXVertex;
    public int maximumZVertex;
    public int minimumZVertex;
    public int maxVertexDistanceXZPlane;
    public int maximumYVertex;
    public int anInt1652;
    public int anInt1653;
    public int itemDropHeight;
    public int vertexBones[];
    public int anIntArray1656[];
    public int vertexGroups[][];
    public int faceGroups[][];
    public boolean aBoolean1659;
    public VertexNormal alsoVertexNormals[];
    static ModelHeader aClass21Array1661[];
    static ResourceProvider resourceProvider;
    static boolean aBooleanArray1663[] = new boolean[15000];
    static boolean aBooleanArray1664[] = new boolean[15000];
    static int projected_vertex_x[] = new int[15000];
    static int projected_vertex_y[] = new int[15000];
    static int anIntArray1667[] = new int[15000];
    static int camera_vertex_y[] = new int[15000];
    static int camera_vertex_x[] = new int[15000];
    static int camera_vertex_z[] = new int[15000];
    static int vertexPerspectiveDepth[] = new int[20000];
    static int anIntArray1671[] = new int[15000];
    static int anIntArrayArray1672[][] = new int[15000][512];
    static int anIntArray1673[] = new int[12];
    static int anIntArrayArray1674[][] = new int[12][15000];
    static int anIntArray1675[] = new int[15000];
    static int anIntArray1676[] = new int[15000];
    static int anIntArray1677[] = new int[12];
    static int anIntArray1678[] = new int[10];
    static int anIntArray1679[] = new int[10];
    static int anIntArray1680[] = new int[10];
    static int anInt1681;
    static int anInt1682;
    static int anInt1683;
    public static boolean aBoolean1684;
    public static int currentCursorX;
    public static int currentCursorY;
    public static int objectsRendered;
    public static long objectKey[] = new long[15000];
    public static int SINE[];
    public static int COSINE[];
    static int hsl2rgb[];
    static int lightDecay[];

    static {
        SINE = Rasterizer3D.anIntArray1470;
        COSINE = Rasterizer3D.anIntArray1471;
        hsl2rgb = Rasterizer3D.hslToRgb;
        lightDecay = Rasterizer3D.anIntArray1469;
    }
}