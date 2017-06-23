package gdx.farmgame;

/**
 * Created by will on 5/12/17.
 */

public class MapObject extends IsoActor {
    public enum ObjectType{
        NULL, WHEELBARROW, SIGN, BED, BARREL, SIGN_POST, TABLE, HEATER, AC,
    }

    ObjectType type;

    public MapObject(){
        super();
        type = ObjectType.NULL;
    }
    public MapObject( Cell c){
        super(getTextureId(ObjectType.NULL), c);
        type = ObjectType.NULL;

    }
    public MapObject(ObjectType objectType, Cell c){
        super(getTextureId(objectType), c);
        type = objectType;
    }


    public Graphics.TextureId getTextureId(){
        return getTextureId(type);
    }
    public static Graphics.TextureId getTextureId(ObjectType objectType) {
        switch (objectType) {
            case WHEELBARROW:
                return Graphics.TextureId.WHEELBARROW;
            case BED:
                return Graphics.TextureId.BED;
            case SIGN:
                return Graphics.TextureId.SIGN;
            case TABLE:
                return Graphics.TextureId.TABLE;
            case BARREL:
                return Graphics.TextureId.BARREL;
            case SIGN_POST:
                return Graphics.TextureId.SIGN_POST;
        }
        return Graphics.TextureId.NULL;
    }
}
