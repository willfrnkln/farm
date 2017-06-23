package gdx.farmgame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.GroupLayout;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.table;

/**
 * Created by will on 5/14/17.
 */

public class MapScreenUI extends Stage { //only for screen-relative UI components. UI components on the map itself can be added and drawn with draw()

    public class ObjectBuildButton extends Table{
        public MapObject.ObjectType  type;
        public ObjectBuildButton(MapObject.ObjectType objectType){
            type = objectType;
            Image image= new Image(graphics.getTextureRegion(MapObject.getTextureId(type)));
            Label label = new Label("Str: 6\n Def: 20\n Att: 15", skin);
            add(image).size(64,64);
            add(label).expand();
            setTouchable(Touchable.enabled);
            addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y) {
                    selectedObjectBuildButton.setColor(1f,1f,1f,1f);
                    selectedObjectBuildButton = MapScreenUI.ObjectBuildButton.this;
                    selectedObjectBuildButton.setColor(.5f,.5f,.5f,.5f);
                }
            });
        }
    }
    public class TileBuildButton extends Table{
        public Tile.TileType type;
        public TileBuildButton(Tile.TileType tileType){
            type = tileType;
            Image image= new Image(graphics.getTextureRegion(Tile.getTextureId(type)));
            Label label = new Label("Str: 6\n Def: 20\n Att: 15", skin);
            add(image).size(64,64);
            add(label).expand();
            setTouchable(Touchable.enabled);
            addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y) {
                    selectedTileBuildButton.setColor(1f,1f,1f,1f);
                    selectedTileBuildButton = MapScreenUI.TileBuildButton.this;
                    selectedTileBuildButton.setColor(.5f,.5f,.5f,.5f);
                }
            });
        }
    }

    MyGdxGame game;
    MapStage mapStage;
    Graphics graphics;
    Skin skin;
    OrthographicCamera camera;

    InputListener outsideInputListener;


    Window buildWindow;
    TileBuildButton selectedTileBuildButton;
    ObjectBuildButton selectedObjectBuildButton;
    TextButton stopBuildButton;

    TextButton saveButton;

    public MapScreenUI(MapStage m, MyGdxGame g){

        super(new ScalingViewport(Scaling.none,Gdx.graphics.getWidth(),Gdx.graphics.getHeight()), g.batch);
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        getViewport().setCamera(camera);
        game = g;
        mapStage = m;
        graphics = g.graphics;
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        initBuildWindow();
        addActor(buildWindow);
        TextButton button = new TextButton("press", skin);
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            button.setSize(Gdx.graphics.getWidth()*.15f, Gdx.graphics.getWidth()*.07f);
        button.addListener(new ChangeListener(){
            public void changed(ChangeEvent e, Actor a){
                buildWindow.setVisible(true);
            }

        });
        addActor(button);
        stopBuildButton = new TextButton("Done", skin);
        stopBuildButton.setPosition(Gdx.graphics.getWidth() - stopBuildButton.getWidth(), 0);
        stopBuildButton.addListener(new ChangeListener(){
            public void changed(ChangeEvent e, Actor a){
                mapStage.exitBuildMode();
                stopBuildButton.setVisible(false);
            }
        });
        addActor(stopBuildButton);

        saveButton = new TextButton("Save", skin);
        saveButton.addListener(new ChangeListener(){
            public void changed(ChangeEvent e, Actor a){
                mapStage.save();
            }
        });
        addActor(saveButton);
    }

    public void draw(){
        game.batch.setProjectionMatrix(camera.projection);
        super.draw();
    }


    private void initBuildWindow(){
        buildWindow = new Window("Build", skin);
        //set touch outside window to close window
        outsideInputListener = new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (x < 0 || x > buildWindow.getWidth() || y < 0 || y > buildWindow.getHeight()){
                    buildWindow.setVisible(false);
                    return true;
                }
                return false;
            }
        };
        buildWindow.setModal(true);
        buildWindow.addListener(outsideInputListener);
        buildWindow.setVisible(false);
        //adjust scale for different screen sizes
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            buildWindow.scaleBy(4);
        else
            buildWindow.setSize(Gdx.graphics.getWidth()*.3f, Gdx.graphics.getHeight() *.5f);
        //Set up tab buttons
        HorizontalGroup tabGroup = new HorizontalGroup();
        final TextButton tab1 = new TextButton("Tiles", skin, "toggle");
        final TextButton tab2 = new TextButton("Objects", skin, "toggle");
        final TextButton tab3 = new TextButton("Tab3", skin, "toggle");
        tabGroup.addActor(tab1);
        tabGroup.addActor(tab2);
        tabGroup.addActor(tab3);
        buildWindow.add(tabGroup);
        buildWindow.row();

        //set up tile tab
        final Table tileScrollTable = new Table();
        tileScrollTable.pad(10).defaults().expandX().space(4);
        final ScrollPane tileScrollPane = new ScrollPane(tileScrollTable,skin);
        tileScrollPane.setScrollingDisabled(true, false);

        for(Tile.TileType type : Tile.TileType.values()){
            tileScrollTable.add(new TileBuildButton(type)).left().expand().fill();
            tileScrollTable.row();
        }
        selectedTileBuildButton = (TileBuildButton) tileScrollTable.getCells().get(0).getActor();
        //set up object tab
        final Table objectScrollTable = new Table();
        objectScrollTable.pad(10).defaults().expandX().space(4);
        final ScrollPane objectScrollPane = new ScrollPane(objectScrollTable,skin);
        objectScrollPane.setScrollingDisabled(true, false);

        for(MapObject.ObjectType type : MapObject.ObjectType.values()){
            objectScrollTable.add(new ObjectBuildButton(type)).left().expand().fill();
            objectScrollTable.row();
        }
        selectedObjectBuildButton = (ObjectBuildButton) objectScrollTable.getCells().get(0).getActor();

        Stack tabs = new Stack();
        tabs.add(objectScrollPane);
        tabs.add(tileScrollPane);
        buildWindow.add(tabs).expand().fill();
        buildWindow.row();

        Table buildButtons = new Table();
        TextButton buildSelect = new TextButton("Select", skin);
        buildSelect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(tab1.isChecked()) {
                    mapStage.setPlacingTile(selectedTileBuildButton.type);
                    mapStage.setPlacingObject(null);
                }
                else if(tab2.isChecked()) {
                    mapStage.setPlacingObject(selectedObjectBuildButton.type);
                    mapStage.setPlacingTile(null);
                }
                stopBuildButton.setVisible(true);

                buildWindow.setVisible(false);
            }
        });
        TextButton buildClose = new TextButton("Close", skin);
        buildClose.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buildWindow.setVisible(false);
            }
        });
        buildButtons.add(buildSelect);
        buildButtons.add(buildClose);
        buildWindow.add(buildButtons).expand();

        //setup tab input listener stuff
        ChangeListener tab_listener = new ChangeListener(){
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                tileScrollPane.setVisible(tab1.isChecked());
                objectScrollPane.setVisible(tab2.isChecked());
            }
        };
        tab1.addListener(tab_listener);
        tab2.addListener(tab_listener);
        ButtonGroup tabButtons = new ButtonGroup();
        tabButtons.setMinCheckCount(1);
        tabButtons.setMaxCheckCount(1);
        tabButtons.add(tab1);
        tabButtons.add(tab2);

    }
}
