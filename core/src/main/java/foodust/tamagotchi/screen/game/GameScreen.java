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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen implements Screen {
    private final Modules modules = ObjectManager.getInstance().getModules();
    Tamagotchi game;

    Texture dropImage;
    Texture bucketImage;
    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;
    Rectangle bucket;
    List<Rectangle> raindrops;
    long lastDropTime;
    int dropsGathered;


    public GameScreen(Tamagotchi game) {
        this.game = game;

        // load the images for the droplet and the bucket, 64x64 pixels each

        dropImage = modules.getTextureModule().makeTexture("drop.png");
        bucketImage = modules.getTextureModule().makeTexture("bucket.png");

        // load the drop sound effect and the rain background "music"
        dropSound = modules.getSoundModule().makeSound("drop.mp3");
        rainMusic = modules.getSoundModule().makeMusic("music.mp3");
        rainMusic.setLooping(true);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // create a Rectangle to logically represent the bucket
        bucket = new Rectangle();
        bucket.x = (float) 800 / 2 - (float) 64 / 2; // center the bucket horizontally
        bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        bucket.width = 64;
        bucket.height = 64;

        // create the raindrops array and spawn the first raindrop
        raindrops = new ArrayList<>();
        spawnRaindrop();
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        SpriteBatch batch = game.getBatch();
        batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        batch.begin();
        game.getFont().draw(batch, "Drops Collected: " + dropsGathered, 0, 480);
        batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
        for (Rectangle raindrop : raindrops) {
            batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        batch.end();

        // process user input
        if (modules.getInputModule().getTouch()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.setX(touchPos.x - (float) 64 / 2);
        }
        if (modules.getInputModule().getKeyBoardInput(Input.Keys.LEFT) && bucket.getX() > 0)
            bucket.x -= (int) (200 * Gdx.graphics.getDeltaTime());
        if (modules.getInputModule().getKeyBoardInput(Input.Keys.RIGHT) && bucket.getX() < 800 - 64)
            bucket.x += (int) (200 * Gdx.graphics.getDeltaTime());

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();

        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= (int) (200 * Gdx.graphics.getDeltaTime());
            if (raindrop.getY() + 64 < 0)
                iter.remove();
            if (raindrop.overlaps(bucket)) {
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
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }

}
