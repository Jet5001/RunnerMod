package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class Recouperate extends BaseCard {
    public static final String ID = makeID(Recouperate.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            -1
    );

    private static int BLOCK = 4;
    private static int BLOCK_UPG = 3;
    public Recouperate()
    {
        super(ID,info);
        setBlock(BLOCK,BLOCK_UPG);
    }

    //called when the card is played and performs the actions for the card
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < energyOnUse; i++) {
            addToBot(new GainBlockAction(p,block));
        }
        addToBot(new ApplyPowerAction(p,p, new EnergizedBluePower(p,energyOnUse)));
        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
    }
}
