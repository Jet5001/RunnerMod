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
        AbstractCard toDiscard;
        toDiscard = AbstractDungeon.player.hand.getRandomCard(new Random());
        boolean nonVirus = false;
        for (int i = 0; i < AbstractDungeon.player.hand.size()-1; i++) {
            if(!(AbstractDungeon.player.hand.group.get(i) instanceof Virus))
            {
                nonVirus = true;
            }
        }
        if(!nonVirus)
        {
            this.isDone = true;
            return;
        }
        if (toDiscard instanceof Virus)
        {
            return;
        }

        AbstractDungeon.player.hand.moveToDiscardPile(toDiscard);
        this.isDone = true;
    }
}
