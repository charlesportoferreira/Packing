/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author charleshenriqueportoferreira
 */
public class Presente {

    private final int id;
    private int x;
    private int y;
    private final int dimX;
    private final int dimY;
    private final int dimZ;
    private int z;
    int xMax;
    int yMax;
    int zMax;
    List<int[]> cubos;
    int t;

    public Presente(int id, int dimX, int dimY, int dimZ) {
        this.id = id;
        this.dimX = dimX;
        this.dimY = dimY;
        this.dimZ = dimZ;
        cubos = new ArrayList<>(dimX * dimY * dimZ);
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return dimX;
    }

    public int getY() {
        return dimY;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getZ() {
        return dimZ;
    }

    public int getAreaXY() {
        return x * y;
    }

    public int getAreaXZ() {
        return x * z;
    }

    public int getAreaYZ() {
        return y * x;
    }

    public void setVerticeInicial(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        criaCubos();
    }

    private void criaCubos() {
        int auxZ = z > 1 ? dimZ - 1 + z : dimZ;
        int auxX = x > 1 ? dimX - 1 + x : dimX;
        int auxY = y > 1 ? dimY - 1 + y : dimY;
        for (int i = x; i <= auxX; i++) {
            for (int j = y; j <= auxY; j++) {
                for (int k = z; k <= auxZ; k++) {
                    addCoordenada(new int[]{i, j, k});
                    t++;
                }
            }
        }
        xMax = auxX;
        yMax = auxY;
        zMax = auxZ;
    }

    private void addCoordenada(int[] cubo) {
        this.cubos.add(cubo);
        //System.out.println(t + Arrays.toString(cubos.get(t)));
    }

    public List<int[]> getVertices() {
        List<int[]> vertices = new ArrayList<>(24);
        vertices.add(new int[]{xMax, yMax, zMax});
        vertices.add(new int[]{x, y, z});
        vertices.add(new int[]{xMax, y, z});
        vertices.add(new int[]{x, yMax, z});
        vertices.add(new int[]{xMax, yMax, z});
        vertices.add(new int[]{xMax, y, zMax});
        vertices.add(new int[]{x, y, zMax});
        vertices.add(new int[]{x, yMax, zMax});
        return vertices;
    }

    @Override
    public String toString() {
        return "Presente{" + "cubos=" + cubos.toString() + '}';
    }

    
}
