package foodust.tamagotchi.screen.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import foodust.tamagotchi.screen.game.GameScreen;

public class MainMenuScreen implements Screen {

    private final GameScreen gameScreen;
    OrthographicCamera camera;

    public MainMenuScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        gameScreen.getBatch().setProjectionMatrix(camera.combined);

        gameScreen.getBatch().begin();
        gameScreen.getFont().draw(gameScreen.getBatch(), "hi", 100, 150);
        gameScreen.getFont().draw(gameScreen.getBatch(), "bye", 100, 100);
        gameScreen.getBatch().end();

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
