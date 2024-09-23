package foodust.tamagotchi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import foodust.tamagotchi.manager.object.ObjectManager;
import foodust.tamagotchi.module.InputModule;
import foodust.tamagotchi.module.Modules;
import foodust.tamagotchi.module.image.SpriteModule;
import foodust.tamagotchi.module.image.TextureModule;
import foodust.tamagotchi.module.sound.SoundModule;
import foodust.tamagotchi.object.character.main.MainCharacter;
import foodust.tamagotchi.object.object.BaseObject;
import foodust.tamagotchi.object.object.drop.DropObject;

import java.util.List;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private final Modules modules = new Modules();
    private Vector2 touchPos;

    MainCharacter mainCharacter;

    Texture backgroundTexture;

    Sound dropSound;
    Music music;

    FitViewport viewport;

    float dropTimer;

    @Override
    public void create() {
        backgroundTexture = modules.getTextureModule().makeTexture("background.png");

        dropSound = modules.getSoundModule().makeSound("drop.mp3");
        music = modules.getSoundModule().makeMusic("music.mp3");

        mainCharacter = new MainCharacter("bucket.png");

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        touchPos = new Vector2();

        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void pause() {
    }

    private void input() {
        if (modules.getInputModule().getKeyBoardInput(Input.Keys.RIGHT)) {
            mainCharacter.getSprite().translateX(mainCharacter.getSpeed() * Gdx.graphics.getDeltaTime());
        } else if (modules.getInputModule().getKeyBoardInput(Input.Keys.LEFT)) {
            mainCharacter.getSprite().translateX(-mainCharacter.getSpeed() * Gdx.graphics.getDeltaTime());
        }
        if (modules.getInputModule().getTouch()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);
            mainCharacter.getSprite().setCenterX(touchPos.x);
        }
    }

    private void logic() {
        float worldHeight = viewport.getWorldHeight();
        float worldWidth = viewport.getWorldWidth();
        mainCharacter.getSprite().setX(MathUtils.clamp(mainCharacter.getSprite().getX(), 0, worldWidth - mainCharacter.getSprite().getWidth()));
        mainCharacter.getRectangle().set(mainCharacter.getSprite().getX(), mainCharacter.getSprite().getY(), mainCharacter.getSprite().getWidth(), mainCharacter.getSprite().getHeight());

        float deltaTime = Gdx.graphics.getDeltaTime();
        List<BaseObject> objects = ObjectManager.getInstance().getObjects();
        for (int i = objects.size() - 1; i >= 0; i--) {
            BaseObject object = objects.get(i);
            Sprite objectSprite = object.getSprite();
            objectSprite.translateY(-2f * deltaTime);
            object.getRectangle().set(objectSprite.getX(), objectSprite.getY(), objectSprite.getWidth(), objectSprite.getHeight());
            if (objectSprite.getY() < -objectSprite.getHeight()) {
                ObjectManager.getInstance().getObjects().remove(object);
            } else if (mainCharacter.getRectangle().overlaps(object.getRectangle())) {
                ObjectManager.getInstance().getObjects().remove(object);
                dropSound.play();
            }
        }

        dropTimer += deltaTime;
        if (dropTimer > 1f) {
            createDroplet();
            dropTimer = 0;
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();


        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        mainCharacter.getSprite().draw(spriteBatch);

        ObjectManager.getInstance().getObjects().forEach(object -> object.getSprite().draw(spriteBatch));

        spriteBatch.end();
    }


    private void createDroplet() {
        DropObject dropObject = new DropObject("drop.png");
        Sprite dropSprite = dropObject.getSprite();
        float dropWidth = 1;
        float dropHeight = 1;
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        dropSprite.setSize(dropWidth, dropHeight);
        dropSprite.setX(MathUtils.random(0f, worldWidth - dropWidth));
        dropSprite.setY(worldHeight);
        ObjectManager.getInstance().getObjects().add(dropObject);
    }
}
