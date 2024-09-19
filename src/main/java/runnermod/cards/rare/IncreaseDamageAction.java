package runnermod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import runnermod.cards.tempcards.Decoy;

import java.util.UUID;

public class IncreaseDamageAction extends AbstractGameAction {

    private int increaseAmount;
    private UUID uuid;
    IncreaseDamageAction(UUID targetUUID, int increaseAmount)
    {
        this.increaseAmount = increaseAmount;
        this.uuid = targetUUID;
    }


    //the effect that executes on the stack when able to
    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (!c.uuid.equals(this.uuid))
                continue;
            c.misc += this.increaseAmount;
            c.applyPowers();
            c.baseDamage = c.misc;
            c.isDamageModified = false;
        }
        for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            c.misc += this.increaseAmount;
            c.applyPowers();
            c.baseDamage = c.misc;
        }
        this.isDone = true;

    }
}
