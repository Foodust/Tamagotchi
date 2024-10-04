package foodust.tamagotchi.screen.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import foodust.tamagotchi.game.Tamagotchi;
import foodust.tamagotchi.manager.object.ObjectManager;
import foodust.tamagotchi.module.Modules;
import foodust.tamagotchi.object.character.main.MainCharacter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen implements Screen {
    private final Modules modules = ObjectManager.getInstance().getModules();
    Tamagotchi tamagotchi;

    MainCharacter mainCharacter;

    Texture dropImage;
    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;
    List<Rectangle> raindrops;
    long lastDropTime;
    int dropsGathered;


    public GameScreen(Tamagotchi tamagotchi) {
        this.tamagotchi = tamagotchi;

        dropImage = modules.getTextureModule().makeTexture("drop.png");
        mainCharacter = new MainCharacter("bucket.png", ObjectManager.X / 2, 32, 64, 64, 1, 1);
        dropSound = modules.getSoundModule().makeSound("drop.mp3");
        rainMusic = modules.getSoundModule().makeMusic("music.mp3", true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, ObjectManager.X, ObjectManager.Y);

        raindrops = new ArrayList<>();
        spawnRaindrop();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();

        SpriteBatch batch = tamagotchi.getBatch();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        tamagotchi.getFont().draw(batch, "Drops Collected: " + dropsGathered, 0, ObjectManager.Y);
        batch.draw(mainCharacter.getTexture(), mainCharacter.getSprite().getX(), mainCharacter.getSprite().getY());

        for (Rectangle raindrop : raindrops) {
            batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        batch.end();

        if (modules.getInputModule().getTouch()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            mainCharacter.getSprite().setX(touchPos.x - 32);
        }

        if (modules.getInputModule().getKeyBoardInput(Input.Keys.LEFT) && mainCharacter.getRectangle().getX() > 0) {
            mainCharacter.getSprite().setX(mainCharacter.getSprite().getX() - mainCharacter.getSpeed() * Gdx.graphics.getDeltaTime());
        }
        if (modules.getInputModule().getKeyBoardInput(Input.Keys.RIGHT) && mainCharacter.getRectangle().getX() < ObjectManager.X - 64) {
            mainCharacter.getSprite().setX(mainCharacter.getSprite().getX() + mainCharacter.getSpeed() * Gdx.graphics.getDeltaTime());
        }
        mainCharacter.updateBounds(mainCharacter.getSprite().getX(), mainCharacter.getSprite().getY() + 32, 64, 64);

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();

        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= (int) (200 * Gdx.graphics.getDeltaTime());
            if (raindrop.getY() + 64 < 0)
                iter.remove();
            if (raindrop.overlaps(mainCharacter.getRectangle())) {
                dropsGathered++;
                dropSound.play();
                iter.remove();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        dropImage.dispose();
        mainCharacter.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, ObjectManager.X - 64);
        raindrop.y = ObjectManager.Y;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }
}
