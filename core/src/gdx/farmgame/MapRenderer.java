package gdx.farmgame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;

import java.util.LinkedList;


/**
 * Created by will on 3/28/17.
 */

public class MapRenderer {
    Map map;

    MapStage stage;
    Graphics g;

    public MapRenderer(MapStage s, Map m, Graphics graphics){
        g = graphics;
        stage = s;
        map = m;
    }

    public void render(){
        Cell currentCell;
        stage.screen.game.batch.begin();
        for(int x = map.cells.length-1; x >=0; x--) {
            for (int y = map.cells[0].length-1; y >= 0; y--) {
                currentCell = map.cells[x][y];
                stage.screen.game.batch.setColor(currentCell.tile.color);
                stage.screen.game.batch.draw(
                        g.getTextureRegion(currentCell.tile.getTextureId()),
                        currentCell.tile.worldX,
                        currentCell.tile.worldY );
                stage.screen.game.batch.draw(
                        g.getTextureRegion(MapObject.getTextureId(currentCell.object.type)),
                        currentCell.tile.worldX,
                        currentCell.tile.worldY);
                for(IsoActor a : currentCell.actors) {
                    stage.screen.game.batch.draw(
                            g.getTextureRegion(a.textureId),
                            currentCell.tile.worldX,
                            currentCell.tile.worldY);
                }
                if(currentCell.previewTile != null){
                    stage.screen.game.batch.setColor(1,1,1,.5f);
                    stage.screen.game.batch.draw(
                            g.getTextureRegion(Tile.getTextureId(currentCell.previewTile)),
                            currentCell.tile.worldX,
                            currentCell.tile.worldY);
                    stage.screen.game.batch.setColor(1,1,1,1);
                }
                if(currentCell.previewObjectType != null){
                    stage.screen.game.batch.setColor(1,1,1,.5f);
                    stage.screen.game.batch.draw(
                            g.getTextureRegion(MapObject.getTextureId(currentCell.previewObjectType)),
                            currentCell.tile.worldX,
                            currentCell.tile.worldY);
                    stage.screen.game.batch.setColor(1,1,1,1);
                }
            }
        }
        stage.screen.game.batch.end();
    }

    public void insertionSort(LinkedList<IsoActor> actors){

    }
}
