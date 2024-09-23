package foodust.tamagotchi.object.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public abstract class BaseCharacter {
    protected String name = "none";
    protected long level = 0;
    protected long hp = 10;
    protected float speed = 0.25f;

    protected Texture texture;
    protected Sprite sprite;
    protected Rectangle rectangle;

    public BaseCharacter(String textureName) {
        this.texture = new Texture(textureName);
        this.sprite = new Sprite(texture);
        this.rectangle = new Rectangle(this.sprite.getX(), this.sprite.getY(), this.sprite.getWidth(), this.sprite.getHeight());
    }

}
