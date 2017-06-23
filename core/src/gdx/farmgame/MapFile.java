package gdx.farmgame;

import java.util.ArrayList;

/**
 * Created by will on 5/24/17.
 */

public class MapFile {

    int mapHeight, mapWidth;
    ArrayList<Tile.TileType> tileTypes;
    ArrayList<MapObject.ObjectType> objectTypes;
    ArrayList<Character> characters;
    public MapFile(){
        tileTypes = new ArrayList();
        objectTypes = new ArrayList<MapObject.ObjectType>();
        characters = new ArrayList<Character>();
    }

    public MapFile(Map m){
        this();
        mapHeight = m.MAP_HEIGHT;
        mapWidth = m.MAP_WIDTH;
        for(int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight;  y++) {
                tileTypes.add(m.cells[x][y].tile.type);
                objectTypes.add(m.cells[x][y].object.type);
            }
        }
    }
}
