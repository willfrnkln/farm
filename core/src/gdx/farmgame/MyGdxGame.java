package gdx.farmgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;

import static com.badlogic.gdx.Gdx.input;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class MyGdxGame extends Game {
	TextureRegion texture;
	OrthographicCamera cam;
	SpriteBatch batch;
    MapScreen screen;
    Graphics graphics;


	@Override public void create() {
		texture = new TextureRegion(new Texture(Gdx.files.internal("road.png")));

		batch = new SpriteBatch();
        graphics = new Graphics();
		cam = new OrthographicCamera(10, 10 * (Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));

        screen = new MapScreen(this);
        setScreen(screen);
	}

	@Override public void render() {
        Gdx.gl.glClearColor(.3f, .5f, .6f, 1);
		Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        screen.render(Gdx.graphics.getDeltaTime());
	}


	@Override
	public void dispose () {
		batch.dispose();
        screen.dispose();
	}

}
