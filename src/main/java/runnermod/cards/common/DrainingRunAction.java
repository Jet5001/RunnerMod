package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import runnermod.powers.VitalityPower;

public class DrainingRunAction extends AbstractGameAction {

    private DamageInfo info;
    DrainingRunAction(AbstractCreature target, DamageInfo info)
    {
        this.info = info;
        setValues(target,info);
        this.actionType = ActionType.DAMAGE;
    }


    //the effect that executes on the stack when able to
    @Override
    public void update() {
        if (target != null)
        {
            int previousHealth = target.currentHealth;
            target.damage(info);
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_VERTICAL, false));
            if (target.currentHealth < previousHealth)
            {
                //add to top to execute next
                addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player, new VitalityPower(AbstractDungeon.player, previousHealth - target.currentHealth)));
            }
        }
        //mark as completed so it doesn't repeat every frame and can be removed from the buffer
        this.isDone = true;
    }
}
