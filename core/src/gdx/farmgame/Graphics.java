package gdx.farmgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by will on 5/11/17.
 */

public class Graphics {
    public enum TextureId{
        DIRT, TILLED_SOIL, SPROUT, TURNIP, STALK, POTATO, APPLE_TREE, HIVE_TREE, WATERMELON,
        GRASS, NULL, TALL_GRASS, FLOWER, B4, B5, B6, B7, B8,
        ROAD, C1, C2, C3, C4, PATH, PATH_CORNER, C7, C8,
        WATER0, WATER1, WATER2, WATER3, WATER4, WATER5, WATER6, WATER7, WATER8,
        FLOOR, WALL, WINDOW1, WINDOW2, E4, E5, E6, E7, E8,
        CHICK, CHICKEN, ROOSTER, PIG, PIGLET, COW, COW_GRAZING, CALF, CRAB,
        PERSON1,PERSON2,PERSON3,PERSON4,PERSON5,PERSON6, PERSON7, BUNNY, PERSON9,
        CHAR1, CHAR2,CHAR3,CHAR4,CHAR5,CHAR6,CHAR7, H7, H8,
        I0,I1,I2,I3,I4,I5,Ii6,I7,I8,
        WHEELBARROW, SIGN, BED, BARREL, SIGN_POST, TABLE, RAIL, J7, J8,
        HEATER, AC, HOLE



    }
    Texture spriteSheet;
    TextureRegion[] textures;

    public Graphics(){
        spriteSheet = new Texture(Gdx.files.internal("tiles.png"));
        textures= new TextureRegion[400];
        int index = 0;
        for(int y = 0; y < 20; y++) {
            for (int x = 0; x < 9; x++) {
                textures[index] = new TextureRegion(spriteSheet, 32*x, 32*y, 32, 32);
                index++;
            }
        }
    }

    public void draw(SpriteBatch b, IsoActor a){
        b.setColor(a.color);
        b.draw(textures[a.textureId.ordinal()], a.worldX, a.worldY);
        b.setColor(1,1,1,1);
    }

    public TextureRegion getTextureRegion(TextureId id){
        if(id == null || id.ordinal()<0 || id.ordinal()>=textures.length) return textures[0];
        return textures[id.ordinal()];
    }


}

