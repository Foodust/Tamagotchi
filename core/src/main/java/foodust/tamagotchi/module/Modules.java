package foodust.tamagotchi.module;

import foodust.tamagotchi.module.image.SpriteModule;
import foodust.tamagotchi.module.image.TextureModule;
import foodust.tamagotchi.module.sound.SoundModule;
import lombok.Getter;

@Getter
public class Modules {

    private final SpriteModule spriteModule;
    private final InputModule inputModule;
    private final TextureModule textureModule;
    private final SoundModule soundModule;

    public Modules() {
        this.spriteModule = new SpriteModule();
        this.inputModule = new InputModule();
        this.textureModule = new TextureModule();
        this.soundModule = new SoundModule();
    }
}
