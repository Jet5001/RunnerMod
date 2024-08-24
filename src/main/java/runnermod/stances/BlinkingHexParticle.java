package runnermod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BlinkingHexParticle extends AbstractGameEffect {
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float dur_div2;
    private float dvy;
    private float dvx;
    private Texture img;

    public BlinkingHexParticle(Color color) {
        //this.scale = MathUtils.random(1.0F, 1.5F);
        this();
        this.color = new Color(color.r, color.g, color.b, 0);
    }

    public BlinkingHexParticle() {
        this.scale = Settings.scale;
        //this.scale = MathUtils.random(1.0F, 1.5F);
        this.color = new Color(1,1,1,0);
        this.startingDuration = this.scale + 0.8F;
        this.duration = this.startingDuration;
        this.scale *= Settings.scale;
        this.dur_div2 = this.duration / 2.0F;
        this.color = new Color(MathUtils.random(0.8F, 1.0F), MathUtils.random(0.5F, 0.7F), MathUtils.random(0.8F, 1.0F), 0.0F);
        this.x = AbstractDungeon.player.hb.cX + MathUtils.random(-AbstractDungeon.player.hb.width / 2.0F - 50.0F * Settings.scale, AbstractDungeon.player.hb.width / 2.0F + 50.0F * Settings.scale);
        this.y = AbstractDungeon.player.hb.cY + MathUtils.random(-AbstractDungeon.player.hb.height / 2.0F + 10.0F * Settings.scale, AbstractDungeon.player.hb.height / 2.0F - 20.0F * Settings.scale);
        this.renderBehind = MathUtils.randomBoolean();
        this.rotation = MathUtils.random(12.0F, 6.0F);
        if (this.x > AbstractDungeon.player.hb.cX) {
            this.rotation = -this.rotation;
        }
        this.img = ImageMaster.loadImage("runnermod/images/particles/HexParticle_smol.png");
    }

    public void update() {
        if (this.duration > this.dur_div2) {
            this.color.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - this.dur_div2) / this.dur_div2);
        } else {
            this.color.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / this.dur_div2);
        }

        if (this.duration > this.startingDuration * 0.85F) {
            this.vY = 12.0F * Settings.scale;
        } else if (this.duration > this.startingDuration * 0.8F) {
            this.vY = 8.0F * Settings.scale;
        } else if (this.duration > this.startingDuration * 0.75F) {
            this.vY = 4.0F * Settings.scale;
        } else if (this.duration > this.startingDuration * 0.7F) {
            this.vY = 3.0F * Settings.scale;
        } else if (this.duration > this.startingDuration * 0.65F) {

        } else if (this.duration > this.startingDuration * 0.6F) {

        } else if (this.duration > this.startingDuration * 0.55F) {

        } else if (this.duration > this.startingDuration * 0.38F) {

        } else if (this.duration > this.startingDuration * 0.3F) {

        } else if (this.duration > this.startingDuration * 0.25F) {
            this.vY = 3.0F * Settings.scale;

        } else if (this.duration > this.startingDuration * 0.2F) {
            this.vY = 4.0F * Settings.scale;

        } else if (this.duration > this.startingDuration * 0.15F) {
            this.vY = 8.0F * Settings.scale;

        } else {
            this.vY = 12.0F * Settings.scale;

        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.x, this.y + this.vY, 0, 0, 32.0F, 32f, this.scale, this.scale , this.rotation, 0, 0, 64, 54, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
