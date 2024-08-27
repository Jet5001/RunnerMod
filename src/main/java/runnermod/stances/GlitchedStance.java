package runnermod.stances;

import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;
import runnermod.character.RunnerCharacter;

public class GlitchedStance extends RunnerStance {

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString("Glitched");
    private static final String baseDescription = "You take double damage from attacks and your attacks deal half damage NL Play less than 3 cards in a turn to solve";
    private static long sfxId = -1L;

    private AbstractStance previousStance;
    GlitchedStance(String[] ids, int[] durabilties, AbstractStance previousStance)
    {
        super(ids,durabilties);
        this.previousStance = previousStance;
        this.ID = "Glitched";
        this.name = "Glitched";
        this.description = baseDescription;
    }

    @Override
    public void onEndOfTurn() {
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() < 3)
        {
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(previousStance));
        }
        super.onEndOfTurn();
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL)
            return damage * 2.0F;
        return damage;
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL)
            return damage * 0.5F;
        return damage;
    }

    @Override
    public void updateDescription() {

    }

    public void onEnterStance() {
        if (sfxId != -1L)
            stopIdleSfx();
        CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_WRATH");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SCARLET, true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Wrath"));
    }

    public void updateAnimation() {
        //Main Particle Effect
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.05F;
                float x = AbstractDungeon.player.hb.cX + MathUtils.random(-AbstractDungeon.player.hb.width / 2.0F - 30.0F * Settings.scale, AbstractDungeon.player.hb.width / 2.0F + 30.0F * Settings.scale);
                float y = AbstractDungeon.player.hb.cY + MathUtils.random(-AbstractDungeon.player.hb.height / 2.0F -10.0F * Settings.scale, AbstractDungeon.player.hb.height / 2.0F + 10.0F * Settings.scale);
                AbstractGameEffect glitchParticle = new VfxBuilder(ImageMaster.GRAB_COIN,x, y ,0.6f)
                        //.scale()
                        .setColor(Color.PURPLE)
                        .moveX(x,x+10f)
                        .moveY(y,y+30f)
                        .fadeIn(0.3f)
                        .fadeOut(0.3f)
                        .rotate(MathUtils.random(0f,1f))
                        .build();
                AbstractDungeon.effectsQueue.add(glitchParticle);
            }
        }
        //Stance Aura Effect (Background fog)
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.3F, 0.4F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Divinity"));
        }
    }

    public void onExitStance() {
        stopIdleSfx();
    }

    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_WRATH", sfxId);
            sfxId = -1L;
        }
    }
}

