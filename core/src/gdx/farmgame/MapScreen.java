package gdx.farmgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

/**
 * Created by will on 3/26/17.
 */

public class MapScreen extends ScreenAdapter{
    final MyGdxGame game;
    MapStage mapStage;
    MapRenderer mapRenderer;
    Graphics graphics;
    MapScreenUI uiStage;
    InputMultiplexer multiplexer;

    public MapScreen(MyGdxGame g){
        super();
        game = g;
        graphics = g.graphics;


        mapStage = new MapStage(this);
        uiStage = new MapScreenUI(mapStage, game);


        multiplexer = new InputMultiplexer();

        multiplexer.addProcessor(uiStage);
        multiplexer.addProcessor(mapStage);
        multiplexer.addProcessor(new GestureDetector(mapStage));

        mapRenderer = new MapRenderer(mapStage, mapStage.map, graphics);
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void render(float delta){
        mapStage.camera.update();
        mapStage.update(delta);
        game.batch.setProjectionMatrix(mapStage.camera.combined);
        mapRenderer.render();
        game.batch.setProjectionMatrix(uiStage.camera.combined);
        uiStage.draw();
        uiStage.act(delta);
    }

    public void resize(int width, int height){
        mapStage.getViewport().setScreenSize(width, height);
        uiStage.getViewport().setScreenSize(width, height);
    }

    public void dispose(){
        mapStage.dispose();
    }

    //public Map loadMap(String fileName){
        //Json json = new Json();
        //String mapString = Gdx.files.internal(fileName).;

    //}
}
