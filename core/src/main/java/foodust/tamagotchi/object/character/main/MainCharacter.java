package foodust.tamagotchi.object.character.main;

import foodust.tamagotchi.object.character.BaseCharacter;

public class MainCharacter extends BaseCharacter {

    public MainCharacter(String textureName) {
        super(textureName);
        this.sprite.setSize(1, 1);
        this.speed = 4f;
    }
}
