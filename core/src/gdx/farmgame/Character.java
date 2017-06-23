package gdx.farmgame;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

/**
 * Created by will on 5/12/17.
 */

public class Character extends IsoActor{
    int homeX, homeY;
    String name;
    Graphics.TextureId shirt;
    Graphics.TextureId pants;
    Graphics.TextureId frames[];
    float frametime = .5f;
    float timeOnCurrentFrame = 0;
    int index = 0;
    transient Map map;


    public Character(){
        super();
        frames = new Graphics.TextureId[6];
        frames[0] = Graphics.TextureId.CHAR1;
        frames[1] = Graphics.TextureId.CHAR2;
        frames[2] = Graphics.TextureId.CHAR3;
        frames[3] = Graphics.TextureId.CHAR4;
        frames[4] = Graphics.TextureId.CHAR5;
        frames[5] = Graphics.TextureId.CHAR6;
        textureId = frames[index];

        homeX = 0;
        homeY = 0;
        name = "Gumby";
    }

    public void act(float delta){
        super.act(delta);
        timeOnCurrentFrame += delta;
        if(timeOnCurrentFrame >= frametime){
            advanceFrame();
            timeOnCurrentFrame-=frametime;
        }
    }

    public void advanceFrame(){
        if(index >= frames.length-1)
            index = 0;
        else
            index++;
        textureId = frames[index];
    }

}
