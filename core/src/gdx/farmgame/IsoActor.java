package gdx.farmgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by will on 5/8/17.
 */

public class IsoActor {
    transient Cell cell;
    protected int cellX, cellY; //locations relative to cell
    protected float worldX, worldY;//coordinates for drawing, input detection
    Graphics.TextureId textureId;
    transient Color color = new Color(1,1,1,1);

    float timeSinceLastAction =0;

    public IsoActor(){
        textureId = Graphics.TextureId.CHICKEN;
    }

    public IsoActor(Cell c){
        cell = c;
        setPosition(0, 0);
    }

    public IsoActor(Graphics.TextureId id, Cell c){
        textureId = id;
        cell = c;
        setPosition(0, 0);
    }

    public void setPosition(int x, int y){
        cellX = x;
        cellY = y;
        worldX = cell.worldX + cellX;
        worldY = cell.worldY + cellY;
    }
    public void setX(int x){
        cellX = x;
        worldX = cell.worldX + cellX;
    }

    public void setY(int y){
        cellY = y;
        worldY = cell.worldY + cellY;
    }

    public void setCell(Cell c){
        cell = c;
        worldX = cell.worldX + cellX;
        worldY = cell.worldY + cellY;
    }

    public void act(float delta){
    }

    public void setColor(float r, float g, float b, float a){
        color.set(r,g,b,a);
    }
}
