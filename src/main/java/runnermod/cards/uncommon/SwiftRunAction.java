package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class SwiftRunAction extends AbstractGameAction {

    private DamageInfo info;
    SwiftRunAction(AbstractCreature target, DamageInfo info)
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
            if (target.currentHealth < previousHealth)
            {
                //add to top to execute next
                addToTop(new DrawCardAction(1));
            }
        }
        //mark as completed so it doesn't repeat every frame and can be removed from the buffer
        this.isDone = true;
    }
}
