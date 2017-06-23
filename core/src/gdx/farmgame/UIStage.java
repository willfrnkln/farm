package gdx.farmgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by will on 5/10/17.
 */

public class UIStage extends Stage {

    Skin skin;

    Window buildWindow;
    InputListener outsideInputListener;//closes the window on touch input outside window

    public UIStage(MapScreen screen, Viewport v){
        super(v, screen.game.batch);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        outsideInputListener = new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (x < 0 || x > buildWindow.getWidth() || y < 0 || y > buildWindow.getHeight()){
                    buildWindow.remove();
                    return true;
                }
                return false;
            }
        };
        buildWindow = new Window("build", skin);
        buildWindow.setModal(true);
        buildWindow.addListener(outsideInputListener);
        TextButton b = new TextButton("press", skin);
        b.addListener(new ChangeListener(){
            public void changed(ChangeEvent e, Actor a){
                addActor(buildWindow);
            }

        });
        addActor(b);

    }
    public void dispose(){
        skin.dispose();
    }
}
