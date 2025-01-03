package runnermod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.DarkShackles;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.BlockReturnPower;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import org.lwjgl.Sys;
import runnermod.powers.FlourishPower;
import runnermod.powers.Hacked;
import runnermod.powers.Overwhelm;

import java.util.*;

public class RandomDebuffAction extends AbstractGameAction {

    List<String> debuffs = new ArrayList<String>(Arrays.asList("Hack", "Shackled", "Mark", "Poison", "Vulnerable", "Weak", "Block Return", "Choked", "Constricted", "Corpse Explosion","Flourish"));
    Random rng = new Random();
    AbstractCreature owner;
    public RandomDebuffAction(AbstractCreature owner, AbstractCreature target)
    {
        this.actionType = ActionType.DEBUFF;
        this.target = target;
        this.owner = owner;
    }
    //the effect that executes on the stack when able to
    @Override
    public void update() {
        if (target != null)
        {
            String debuff = debuffs.get(rng.nextInt(debuffs.size()));
            System.out.println(debuff);
            switch (debuff)
            {
                case "Shackled":
                    addToBot((AbstractGameAction)new ApplyPowerAction(target, AbstractDungeon.player, new StrengthPower(target, -1), 1));
                    if (target != null && !target.hasPower("Artifact"))
                        addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new GainStrengthPower(target, 1), 1));
                    break;
                case "Frail":
                    addToBot(new ApplyPowerAction(target, owner, new FrailPower(target,1,false), 1));
                    break;
                case "Poison":
                    addToBot(new ApplyPowerAction(target, owner, new PoisonPower(target,AbstractDungeon.player,4), 1));
                    break;
                case "Slow":
                    addToBot(new ApplyPowerAction(target, owner, new SlowPower(target,1)));
                    break;
                case "Vulnerable":
                    addToBot(new ApplyPowerAction(target, owner, new VulnerablePower(target,1,false), 1));
                    break;
                case "Weak":
                    addToBot(new ApplyPowerAction(target, owner, new WeakPower(target,1,false), 1));
                    break;
                case "Block Return":
                    addToBot(new ApplyPowerAction(target, owner, new BlockReturnPower(target,1), 1));
                    break;
                case "Choked":
                    addToBot(new ApplyPowerAction(target, owner, new ChokePower(target,1), 1));
                    break;
                case "Constricted":
                    addToBot(new ApplyPowerAction(target, owner, new ConstrictedPower(target,AbstractDungeon.player,1), 1));
                    break;
                case "Corpse Explosion":
                    addToBot(new ApplyPowerAction(target, owner, new CorpseExplosionPower(target), 1));
                    break;
                case "Mark":
                    addToBot(new ApplyPowerAction(target, owner, new MarkPower(target,5), 1));
                    break;
                case "Overwhelm":
                    addToBot(new ApplyPowerAction(target,owner,new Overwhelm(target,1)));
                case "Hack":
                    addToBot(new ApplyPowerAction(target, owner, new Hacked(target,1), 1));
                    break;
                case "Flourish":
                    addToBot(new ApplyPowerAction(target, owner, new FlourishPower(target,2), 1));
                    break;
            }
        }
        //mark as completed so it doesn't repeat every frame and can be removed from the buffer
        this.isDone = true;
    }


}
