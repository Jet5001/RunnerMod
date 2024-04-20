package runnermod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;
import runnermod.character.RunnerCharacter;

import java.util.Collections;

public class ArtifactStance extends RunnerStance {
    public static final String STANCE_ID = "Artifact";

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString("Artifact");
    private static final String baseDescription = "While in this stance you have Artifact NL ";

    private static long sfxId = -1L;
    private int durability;
    private SuperArtifactPower stancePower;
    public ArtifactStance(String[] ids, int[] durabilties) {
        super(ids,durabilties);

        this.ID = "Artifact";
        this.name = "Artifact";
        this.description = "Each time you play a skill deal damage equal to it's cost";
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



    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new WrathParticleEffect());
            }
        }
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.3F, 0.4F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Wrath"));
        }
    }

    @Override
    public void onPlayCard(AbstractCard card) {
        super.onPlayCard(card);
        if (!card.hasTag(RunnerCharacter.Enums.NEON))
        {
            reduceDurability(1);
        }
        if (durabilityDictionary.get("Artifact").equals(0) || durabilityDictionary.get("Artifact")<0)
        {
            AbstractDungeon.actionManager.addToTop(new ChangeRunnerStanceAction("Neutral",0));
        }

    }

    public void updateDescription() {
        this.description = baseDescription;
        for (String id: Collections.list(durabilityDictionary.keys())) {
                this.description += id + " : " + durabilityDictionary.get(id) + " turns left";
        }
    }

    public void onEnterStance() {
        if (sfxId != -1L)
            stopIdleSfx();
        CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_WRATH");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SCARLET, true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Wrath"));
        AbstractCreature player = AbstractDungeon.player;
        //need to get previous artifact amount and replace with a new instance of artifact if
        //greater than one as new super artifact stacks with old regular artifact and could thefore be removed by debuffs
        int previousArtifactAmount = 0;
        for (int i = 0; i < player.powers.size(); i++) {
            if (player.powers.get(i).ID.equals("Artifact"))
            {
                previousArtifactAmount = player.powers.get(i).amount;
            }
        }
        stancePower = new SuperArtifactPower(AbstractDungeon.player,1+ previousArtifactAmount);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, stancePower));
    }

    public void onExitStance() {
        AbstractCreature player = AbstractDungeon.player;
        //need to get previous artifact amount and replace with a new instance of artifact if
        //greater than one as new artifact stacks with old super artifact and could thefore leave character with
        //permanent super artifact
        int previousArtifactAmount = 0;
        for (int i = 0; i < player.powers.size(); i++) {
            if (player.powers.get(i).ID.equals("Artifact"))
            {
                previousArtifactAmount = player.powers.get(i).amount;
            }
        }
        if (previousArtifactAmount >=2)
        {
            int newArtifactAmount = previousArtifactAmount -1;
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player , stancePower));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(player,newArtifactAmount)));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player , stancePower));
        }

        stopIdleSfx();
    }

    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_WRATH", sfxId);
            sfxId = -1L;
        }
    }
}