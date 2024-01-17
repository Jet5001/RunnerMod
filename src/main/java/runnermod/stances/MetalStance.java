package runnermod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;
import runnermod.character.RunnerCharacter;

import java.util.Collections;

public class MetalStance extends RunnerStance {
    public static final String STANCE_ID = "Metal";

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString("Metal");
    private static final String baseDescription = "Each time you play a card gain 1 Metalicise NL ";

    private static long sfxId = -1L;
    private int durability;

    public MetalStance(String[] ids, int[] durabilties) {
        super(ids,durabilties);
        this.ID = "Metal";
        this.name = "Metal";
        this.description = baseDescription;
        updateDescription();
    }

//    public float atDamageGive(float damage, DamageInfo.DamageType type) {
//        if (type == DamageInfo.DamageType.NORMAL)
//            return damage * 2.0F;
//        return damage;
//    }
//
//    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
//        if (type == DamageInfo.DamageType.NORMAL)
//            return damage * 2.0F;
//        return damage;
//    }


    @Override
    public void onPlayCard(AbstractCard card) {
        AbstractCreature p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new MetallicizePower(p, 1), 1, true, AbstractGameAction.AttackEffect.NONE));

        super.onPlayCard(card);

        if (!card.hasTag(RunnerCharacter.Enums.NEON))
        {
            reduceDurability(1);
        }

        //sort out new stance as durabilties fade
        if (durabilityDictionary.get("Artifact").equals(0) ||durabilityDictionary.get("Artifact") < 0)
        {
            if (durabilityDictionary.get("Wall").equals(0) ||durabilityDictionary.get("Wall") < 0)
            {
                AbstractDungeon.actionManager.addToTop(new ChangeRunnerStanceAction("Neutral",0));
                return;
            }
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(new WallStance(new String[]{"Wall"}, new int[]{durabilityDictionary.get("Wall")+1} )));

        }
        if (durabilityDictionary.get("Wall").equals(0) ||durabilityDictionary.get("Wall") < 0)
        {
            if (durabilityDictionary.get("Artifact").equals(0) ||durabilityDictionary.get("Artifact") < 0)
            {
                AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Neutral"));
                return;
            }
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(new ArtifactStance(new String[]{"Artifact"}, new int[]{durabilityDictionary.get("Artifact")+1} )));

        }

    }

    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new CalmParticleEffect());
            }
        }
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.3F, 0.4F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Calm"));
        }
    }

    public void updateDescription() {
        this.description = baseDescription;
        for (String id: Collections.list(durabilityDictionary.keys())) {
                this.description += " " + id + " : " + durabilityDictionary.get(id) + " turns left NL ";
        }
    }

    public void onEnterStance() {
        if (sfxId != -1L)
            stopIdleSfx();
        CardCrawlGame.sound.play("STANCE_ENTER_CALM");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_CALM");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.BLUE, true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Calm"));
    }

    public void onExitStance() {
        stopIdleSfx();
    }

    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_CALM", sfxId);
            sfxId = -1L;
        }
    }
}