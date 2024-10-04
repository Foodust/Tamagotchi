package foodust.tamagotchi.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import foodust.tamagotchi.screen.menu.MainMenuScreen;
import lombok.Getter;

@Getter
public class Tamagotchi extends Game {

    private SpriteBatch batch;
    private BitmapFont font;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
    }


    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
