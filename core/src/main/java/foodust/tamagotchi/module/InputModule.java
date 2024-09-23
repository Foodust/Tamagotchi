package foodust.tamagotchi.module;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class InputModule {

    public InputModule() {
    }

    public Boolean getKeyBoardInput(Integer number) {
        return Gdx.input.isKeyPressed(number);
    }

    public Boolean getTouch(){
        return Gdx.input.isTouched();
    }
}
