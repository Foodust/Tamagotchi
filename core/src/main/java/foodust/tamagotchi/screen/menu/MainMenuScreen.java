package foodust.tamagotchi.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import foodust.tamagotchi.game.Tamagotchi;
import foodust.tamagotchi.manager.object.ObjectManager;
import foodust.tamagotchi.module.Modules;
import foodust.tamagotchi.module.text.TextClickable;
import foodust.tamagotchi.screen.game.GameScreen;

public class MainMenuScreen implements Screen {

    private final Tamagotchi tamagotchi;
    private final OrthographicCamera camera;
    private final TextClickable startText;
    private final TextClickable quitText;


    public MainMenuScreen(Tamagotchi tamagotchi) {
        this.tamagotchi = tamagotchi;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        this.startText = new TextClickable(tamagotchi.getFont(), "Game Start", 100, 150, camera);
        this.quitText = new TextClickable(tamagotchi.getFont(), "bye", 100, 100, camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        tamagotchi.getBatch().setProjectionMatrix(camera.combined);

        tamagotchi.getBatch().begin();
        startText.draw(tamagotchi.getBatch());
        quitText.draw(tamagotchi.getBatch());
        tamagotchi.getBatch().end();

        if (startText.isClicked()) {
            tamagotchi.setScreen(new GameScreen(tamagotchi));
            dispose();
        } else if (quitText.isClicked()) {
            Gdx.app.exit();
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
