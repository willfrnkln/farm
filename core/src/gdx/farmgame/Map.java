package gdx.farmgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by will on 5/5/17.
 */

public class Map {

    Cell[][] cells;
    ArrayList<IsoActor> actors;
    ArrayList<MapObject> objects;
    ArrayList<Character> characters;

    Character player;
    int MAP_WIDTH = 25;
    int MAP_HEIGHT = 25;

    //variables for input and building

    transient final Tile dummyTile; //for array out of bound tile requests
    transient final Cell dummyCell;
    transient Cell hoverCell;

    transient Tile.TileType placingTileType; //type of tile player wants to build on the map
    transient Cell anchor1;

    transient MapObject.ObjectType placingObjectType = null;

    public Map(){
        dummyTile = new Tile();
        dummyCell = new Cell();
        hoverCell = dummyCell;
        placingTileType = null;
        anchor1 = dummyCell;
        cells = new Cell[MAP_WIDTH][MAP_HEIGHT];

        actors = new ArrayList<IsoActor>();
        objects= new ArrayList<MapObject>();
        characters = new ArrayList<Character>();
        for(int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[0].length;  y++) {
                cells[x][y] = new Cell(x,y, Tile.TileType.STREET);
            }
        }
        Graphics.TextureId[] animation = {Graphics.TextureId.PERSON1, Graphics.TextureId.PERSON4,};
        player = new Character();
        player.map = this;
        cells[4][5].addActor(player);
    }

    public Map(MapFile file){
        this();
        MAP_WIDTH = file.mapWidth;
        MAP_HEIGHT = file.mapHeight;
        cells = new Cell[MAP_WIDTH][MAP_HEIGHT];
        /*for(int x = 0; x < MAP_WIDTH; x++) {
            for (int y = 0; y < MAP_HEIGHT;  y++) {
                cells[x][y] = new Cell(x, y, file.tileTypes.remove(0));
                cells[x][y].addObject(file.objectTypes.remove(0));
            }
        }
        player = new Character();
        player.map = this;
        cells[4][5].addActor(player);*/

    }

    public void placeTile(Tile.TileType tileType, Cell cell){
        if(Tile.getPlacementType(tileType) == Tile.AREA || Tile.getPlacementType(tileType) == Tile.ROW){
            if(anchor1 == dummyCell)//if the anchor is not yet set, set it
                anchor1 = cell;
            else{//if the anchor is set, place the region
                placeRegion(anchor1, cell);
            }
        }
        else{
            cell.tile.updateType(tileType);
        }
    }

    public void placeObject(Cell cell){
        cell.addObject(placingObjectType);
    }

    public void placeRegion(Cell a1, Cell a2){
        int x1,x2,y1,y2;
        if(a1.x > a2.x)
        {x1=a1.x;x2=a2.x;}
        else{x1=a2.x;x2=a1.x;}
        if(a1.y > a2.y)
        {y1=a1.y;y2=a2.y;}
        else{y1=a2.y;y2=a1.y;}

        for(int j = 0; j<cells.length; j++){
            for(int k = 0; k< cells[0].length; k++){
                if(j<=x1&&j>=x2&&k<=y1&&k>=y2) cells[j][k].tile.type = placingTileType;
                cells[j][k].previewTile = null;
            }
        }
        anchor1 = dummyCell;
    }


    public void insertSorted(IsoActor actor, LinkedList<IsoActor> list){
        int index = 0;
    }

    public void update(float delta){
        for(int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[0].length;  y++) {
                cells[x][y].update();
            }
        }
    }

    public void replaceTile(Tile.TileType type, Tile tile){
        tile.updateType(type);
        //tiles[x][y].recalculateStats;
    }
    public void setHoverCell(Cell cell){
        //return previous hoverCell to normal
        hoverCell.tile.setColor(1,1,1,1);
        hoverCell.previewTile = null;
        hoverCell.previewObjectType = null;

        hoverCell = cell;
        hoverCell.tile.setColor(.5f,.5f,.5f,.5f);
        hoverCell.previewTile = placingTileType;
        hoverCell.previewObjectType = placingObjectType;
        if(anchor1 != dummyCell) {
            updateAnchors(anchor1, hoverCell);
        }
    }

    public void updateAnchors(Cell a1, Cell a2){
        int x1,x2,y1,y2;
        if(a1.x > a2.x)
            {x1=a1.x;x2=a2.x;}
        else{x1=a2.x;x2=a1.x;}
        if(a1.y > a2.y)
            {y1=a1.y;y2=a2.y;}
        else{y1=a2.y;y2=a1.y;}

        for(int j = 0; j<cells.length; j++){
            for(int k = 0; k< cells[0].length; k++){
                if(j<=x1&&j>=x2&&k<=y1&&k>=y2) cells[j][k].previewTile = placingTileType;
                else cells[j][k].previewTile = null;
            }
        }
    }

    public void exitBuildMode(){
        anchor1 = dummyCell;
        placingTileType = null;
        placingObjectType = null;
        for(int x = cells.length-1; x >=0; x--) {
            for (int y = cells[0].length-1; y >= 0; y--) {
                cells[x][y].previewTile = null;
            }
        }
    }
}
