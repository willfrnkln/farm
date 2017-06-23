package gdx.farmgame;

/**
 * Created by will on 5/16/17.
 */

public class Tile extends IsoActor{
    public enum TileType{
        DIRT, STREET, FLOWER, WATER, GRASS, FLOOR, RAIL
    }
    public static final int SINGLE = 1;
    public static final int ROW = 2;
    public static final int AREA = 3;

    TileType type;

    public Tile(){
        super();
    }

    public Tile(TileType tileType, Cell c){
        super(getTextureId(tileType), c);
        type = tileType;
    }

    public Graphics.TextureId getTextureId(){
        return getTextureId(type);
    }
    public static Graphics.TextureId getTextureId(Tile.TileType tileType){
        switch(tileType) {
            case DIRT:
                return Graphics.TextureId.DIRT;
            case STREET:
                return Graphics.TextureId.ROAD;
            case FLOWER:
                return Graphics.TextureId.FLOWER;
            case GRASS:
                 return Graphics.TextureId.GRASS;
            case WATER:
                return Graphics.TextureId.WATER6;
            case FLOOR:
                return Graphics.TextureId.FLOOR;
        }
        return Graphics.TextureId.CHICKEN;
    }

    public static int getPlacementType(TileType tileType){
        switch(tileType) {
            case DIRT:
                return AREA;
            case FLOOR:
                return AREA;
            case STREET:
                return ROW;
            case FLOWER:
                return SINGLE;
        }
        return SINGLE;
    }

    public void updateType(TileType newType){
        type = newType;
        textureId = getTextureId(type);
    }

    public void updateTexture(){
        switch(type) {
            case WATER:
                break;
        }
    }
}
