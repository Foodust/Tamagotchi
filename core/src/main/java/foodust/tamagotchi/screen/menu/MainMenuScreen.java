package foodust.tamagotchi.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import foodust.tamagotchi.game.Tamagotchi;

public class MainMenuScreen implements Screen {

    private final Tamagotchi tamagotchi;
    OrthographicCamera camera;

    public MainMenuScreen(Tamagotchi tamagotchi) {
        this.tamagotchi = tamagotchi;
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
        tamagotchi.getBatch().setProjectionMatrix(camera.combined);

        tamagotchi.getBatch().begin();
        tamagotchi.getFont().draw(tamagotchi.getBatch(), "hi", 100, 150);
        tamagotchi.getFont().draw(tamagotchi.getBatch(), "bye", 100, 100);
        tamagotchi.getBatch().end();

        if(Gdx.input.isTouched()){
//            gameScreen.setScreen(new GameScreen(gameScreen));
            dispose();
        }
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
