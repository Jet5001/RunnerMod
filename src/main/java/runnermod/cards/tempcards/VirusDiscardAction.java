package runnermod.cards.tempcards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.random.Random;

public class VirusDiscardAction extends AbstractGameAction {

    private DamageInfo info;
    VirusDiscardAction(AbstractCreature target, AbstractCreature source)
    {
        setValues(target,source);
        this.actionType = ActionType.DISCARD;
    }


    //the effect that executes on the stack when able to
    @Override
    public void update() {
        Random rng = new Random();
        AbstractCard toDiscard;
        boolean nonVirus = false;
        for (AbstractCard c:AbstractDungeon.player.hand.group) {
            if (c instanceof Virus)
            {
                nonVirus = true;
            }
        }
        if (!nonVirus)
        {
            this.isDone = true;
            return;
        }
        do {
            toDiscard = AbstractDungeon.player.hand.getRandomCard(rng);
        }while (toDiscard instanceof Virus);
        AbstractDungeon.player.hand.moveToDiscardPile(toDiscard);
        this.isDone = true;
    }
}
