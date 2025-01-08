package runnermod;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.Hitbox;

public class DisplayEquipment {
    public Texture texture, inactiveTexture;
    public Hitbox hb;
    public String id;
    public String name;
    public String description;
    public int durability;
    public float x, y;

    public DisplayEquipment(String id, Texture texture, Texture inactiveTexture, Hitbox hb, String name, String description) {
        this.id = id;
        this.texture = texture;
        this.inactiveTexture = inactiveTexture;
        this.hb = hb;
        this.name = name;
        this.description = description;
        this.durability = 0;
    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
