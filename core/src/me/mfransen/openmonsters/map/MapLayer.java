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
    }
}
