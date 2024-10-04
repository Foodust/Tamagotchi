package foodust.tamagotchi.object.character.main;

import foodust.tamagotchi.object.character.BaseCharacter;

public class MainCharacter extends BaseCharacter {

    public MainCharacter(String textureName, float x, float y, float width, float height, float sizeWidth, float sizeHeight) {
        super(textureName, x, y, width, height, sizeWidth, sizeHeight);
        this.speed = 4f;
    }
}
