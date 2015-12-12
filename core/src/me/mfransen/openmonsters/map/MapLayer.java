package me.mfransen.openmonsters.map;

/**
 * Created by matt on 12/11/15.
 */
public class MapLayer {
    public int[][] tiles;
    public int[][] properties;
    public MapLayer(Map parent) {
        tiles = new int[parent.getWidth()][parent.getHeight()];
        properties = new int[parent.getWidth()][parent.getHeight()];
        for(int x = 0; x < parent.getWidth(); x++) {
            for(int y = 0; y < parent.getHeight(); y++) {
                tiles[x][y] = 0;
            }
        }
    }
}
