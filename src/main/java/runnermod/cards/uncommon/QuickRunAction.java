package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class QuickRunAction extends AbstractGameAction {

    private DamageInfo info;
    QuickRunAction(AbstractCreature target, DamageInfo info)
    {
        this.info = info;
        setValues(target,info);
        this.actionType = ActionType.DAMAGE;
    }


    @Override
    public void update() {
        if (target != null)
        {
            int previousHealth = target.currentHealth;
            target.damage(info);
            if (target.currentHealth < previousHealth)
            {
                addToTop(new GainEnergyAction(1));
            }
        }
        this.isDone = true;
    }
}
