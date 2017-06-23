package gdx.farmgame;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

/**
 * Created by will on 3/28/17.
 */

public class MapStage extends Stage implements GestureDetector.GestureListener{
    Map map;
    MapScreen screen;
    OrthographicCamera camera;

    public MapStage(MapScreen s){
        super(new ScalingViewport(Scaling.none, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), s.game.batch);
        screen = s;
        if(Gdx.files.internal("map1.txt").exists())
            load();
        else
            map = new Map();


        camera = new OrthographicCamera(10, 10 * (Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));
        camera.setToOrtho(false);
        getViewport().setCamera(camera);
        if(Gdx.app.getType() == Application.ApplicationType.Desktop)
            camera.zoom = .5f;
        else if(Gdx.app.getType() == Application.ApplicationType.Android)
            camera.zoom = .1f;

        camera.translate(-camera.position.x, -camera.position.y);
        s.game.batch.setProjectionMatrix(camera.combined);
    }

    public void update(float delta){
        map.update(delta);
    }

    public boolean mouseMoved(int screenX, int screenY){

        map.setHoverCell(cellAt(screenX, screenY));

        return false;
    }
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        //Vector3 coords = new Vector3(x, y, 0);
        //camera.unproject(coords);
        //System.out.println("click at x= " +coords.x + " y = " + coords.y);
        //map.player.setDestination(((coords.x/16) + (coords.y/8))/2, ((coords.y/8) - (coords.x/16))/2);
        if(map.placingTileType != null)
            map.placeTile(map.placingTileType, cellAt(x,y));
        if(map.placingObjectType != null)
            map.placeObject(cellAt(x,y));
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        camera.translate(-deltaX*camera.zoom, deltaY*camera.zoom);
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    public Cell cellAt(float screenX, float screenY){
        Vector3 coords = new Vector3(screenX, screenY, 0);
        camera.unproject(coords);
        int cellX = (int)(((coords.x/16) + (coords.y/8))/2);
        int cellY = (int)(((coords.y/8) - (coords.x/16))/2);
        if(cellX < 0 || cellY < 0 || cellX >= map.cells.length || cellY >= map.cells[0].length){
            return map.dummyCell;
        }
        return map.cells[cellX][cellY];
    }

    public void setPlacingTile(Tile.TileType type){
        Vector3 coords = new Vector3(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
        camera.unproject(coords);
        map.placingTileType = type;
    }

    public void setPlacingObject(MapObject.ObjectType type){
        map.placingObjectType = type;
    }

    public void exitBuildMode(){
        map.exitBuildMode();
    }

    public void save(){
        Json json = new Json();
       // MapFile mapFile = new MapFile(map);
        FileHandle saveFile = Gdx.files.local("map1.txt");
        saveFile.writeString(json.toJson(map), false);
    }
    public void load(){
        FileHandle saveFile = Gdx.files.internal("map1.txt");
        Json json = new Json();
        map = json.fromJson(Map.class, saveFile);
        //map = new Map(mapFile);
    }

    public void dispose(){
        super.dispose();
    }


}
