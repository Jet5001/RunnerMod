package runnermod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.BlockReturnPower;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;
import runnermod.powers.FlourishPower;
import runnermod.powers.Hacked;
import runnermod.powers.Overwhelm;

import java.util.*;

public class RandomDebuffAction extends AbstractGameAction {

    List<String> EnemyDebuffs = new ArrayList<String>(Arrays.asList("Hack", "Shackled", "Mark", "Poison", "Vulnerable", "Weak", "Block Return", "Choked", "Constricted", "Corpse Explosion","Flourish"));
    List<String> PlayerDebuffs = new ArrayList<String>(Arrays.asList("Frail","Shackled", "Mark", "Poison", "Vulnerable", "Weak", "Block Return", "Choked", "Constricted", "Corpse Explosion"));
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
            String debuff = "";
            if(target instanceof AbstractPlayer)
            {
                debuff = PlayerDebuffs.get(AbstractDungeon.miscRng.random(PlayerDebuffs.size()-1));
            }
            else
            {
                debuff = EnemyDebuffs.get(AbstractDungeon.miscRng.random(EnemyDebuffs.size()-1));
            }
            System.out.println(debuff);
            switch (debuff)
            {
                case "Shackled":
                    if (target != null && !target.hasPower("Artifact"))
                    {
                        addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new StrengthPower(target, -1), 1));
                        addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new GainStrengthPower(target, 1), 1));
                    }
                    else
                    {
                        return;
                    }

                    break;
                case "Frail":
                    addToBot(new ApplyPowerAction(target, owner, new FrailPower(target,1,false), 1));
                    break;
                case "Poison":
                    if(target instanceof AbstractPlayer)
                    {
                        addToBot(new ApplyPowerAction(target, owner, new PoisonPower(target,AbstractDungeon.player,2), 1));
                    }
                    else
                    {
                        addToBot(new ApplyPowerAction(target, owner, new PoisonPower(target,AbstractDungeon.player,3), 1));
                    }
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
