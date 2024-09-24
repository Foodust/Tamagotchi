package foodust.tamagotchi.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import foodust.tamagotchi.screen.menu.MainMenuScreen;
import lombok.Getter;

@Getter
public class Tamagotchi extends Game {

    private SpriteBatch batch;


    @Override
    public void create() {
        batch = new SpriteBatch();
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
