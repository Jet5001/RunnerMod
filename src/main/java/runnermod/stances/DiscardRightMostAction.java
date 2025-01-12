package runnermod.stances;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardRightMostAction extends AbstractGameAction {

    private DamageInfo info;
    private AbstractPlayer p;
    public DiscardRightMostAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.p = (AbstractPlayer)target;
        this.setValues(target, source, amount);
        this.actionType = ActionType.DISCARD;
    }


    //the effect that executes on the stack when able to
    @Override
    public void update() {
        AbstractCard c;
        AbstractPlayer p = AbstractDungeon.player;
        c = this.p.hand.getTopCard();
        this.p.hand.moveToDiscardPile(c);
        c.triggerOnManualDiscard();
        isDone = true;
    }
}
