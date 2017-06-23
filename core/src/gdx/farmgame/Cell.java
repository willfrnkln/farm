package gdx.farmgame;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

/**
 * Created by will on 6/21/17.
 */

public class Cell{
    int x =-1, y=-1;
    Tile tile;
    Tile.TileType previewTile;
    MapObject.ObjectType previewObjectType;
    MapObject object;
    ArrayList<IsoActor> actors;

    public boolean neighborTextureChanged = false; //set to true when surrounding tiles are changed

    float worldX, worldY; //drawing coordinates
    public Cell(){
        actors = new ArrayList<IsoActor>();
        object = new MapObject(this);
        tile = new Tile();
    }
    public Cell(int xx, int yy){
        this();
        x=xx;
        y=yy;
        worldX = 16*(x-y) - 16;
        worldY = 8*(x+y);
    }
    public Cell(int xx, int yy, Tile.TileType type){
        this(xx,yy);
        tile = new Tile(type, this);

    }

    public void addActor(IsoActor a){
        actors.add(a);
        a.setCell(this);
    }

    public void addObject(MapObject.ObjectType objectType){

        object.type = objectType;
        System.out.println("Object type set to " + objectType);
    }

    public void update(){
        if(neighborTextureChanged){
            tile.updateTexture();
        }
        for(IsoActor a: actors){
            a.act(Gdx.graphics.getDeltaTime());
        }
    }

    public String toString(){
        return "Cell x = " + x + " y = " + y;
    }
}