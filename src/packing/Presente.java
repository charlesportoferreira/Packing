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
    private final int comprimento;
    private final int largura;
    private final int altura;
    private int z;
    int xMax;
    int yMax;
    int zMax;
    List<int[]> cubos;
    int t;

    public Presente(int id, int comprimento, int largura, int altura) {
        this.id = id;
        this.comprimento = comprimento;
        this.largura = largura;
        this.altura = altura;
        //cubos = new ArrayList<>(dimX * dimY * dimZ);
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return comprimento;
    }

    public int getY() {
        return largura;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getZ() {
        return altura;
    }
    
    public int getZMax(){
        return zMax;
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
        if (z == 1) {
            this.z = z;
        } else {
            this.z = z + 1;
        }
        xMax = x + comprimento - 1;
        yMax = y + largura - 1;
        zMax = this.z + altura - 1 ;

    }

    private void criaCubos() {
        int auxZ = z > 1 ? altura - 1 + z : altura;
        int auxX = x > 1 ? comprimento - 1 + x : comprimento;
        int auxY = y > 1 ? largura - 1 + y : largura;
//        for (int i = x; i <= auxX; i++) {
//            for (int j = y; j <= auxY; j++) {
//                for (int k = z; k <= auxZ; k++) {
//                    addCoordenada(new int[]{i, j, k});
//                    t++;
//                }
//            }
//        }
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
        vertices.add(new int[]{x, y, z});
        vertices.add(new int[]{xMax, yMax, z});
        vertices.add(new int[]{xMax, y, z});
        vertices.add(new int[]{x, yMax, z});
        vertices.add(new int[]{xMax, yMax, zMax});
        vertices.add(new int[]{x, y, zMax});
        vertices.add(new int[]{xMax, y, zMax});
        vertices.add(new int[]{x, yMax, zMax});
        return vertices;
    }

    @Override
    public String toString() {
        StringBuilder ver = new StringBuilder();
        List<int[]> vertices = this.getVertices();
            ver.append(this.id);
            ver.append(",");
        for (int[] vertice : vertices) {
            ver.append(vertice[0]);
            ver.append(",");
            ver.append(vertice[1]);
            ver.append(",");
            ver.append(vertice[2]);
            ver.append(",");
        }
        ver.deleteCharAt(ver.length()-1);
        ver.append("\n");
        return ver.toString();
    }

}
