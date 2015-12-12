package me.mfransen.openmonsters.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matt on 12/11/15.
 */
public class Map {
    private Texture palette;
    private int tileSize = 64;
    private int width;
    private int height;
    public List<MapLayer> layers = new ArrayList<MapLayer>();
    private Texture background;
    public Map(Texture palette, int width, int height, int tileSize) {
        this.palette = palette;
        this.tileSize = tileSize;
        this.width = width;
        this.height = height;
        Pixmap pixmap = new Pixmap(width*tileSize,height*tileSize, Pixmap.Format.Alpha);
        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(0,0,width*tileSize,height*tileSize);
        background = new Texture(pixmap);
    }
    public void drawMap(SpriteBatch spriteBatch, int x, int y) {
        spriteBatch.draw(background,x,y);
        for(int i = 0; i < layers.size(); i++) {
            for(int mX = 0; mX < width; mX++) {
                for(int mY = 0; mY < height; mY++) {
                    if(layers.get(i).tiles[mX][mY]>=0)
                        spriteBatch.draw(palette,mX*tileSize+x,mY*tileSize+y, getPaletteX(layers.get(i).tiles[mX][mY]), getPaletteY(layers.get(i).tiles[mX][mY]),tileSize,tileSize);
                }
            }
        }
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    private int getPaletteX(int tile) {
        return (tile%(palette.getWidth()/tileSize))*tileSize;
    }
    private int getPaletteY(int tile) {
        return (tile/(palette.getWidth()/tileSize))*tileSize;
    }
}
