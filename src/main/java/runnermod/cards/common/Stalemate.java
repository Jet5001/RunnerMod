package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class Stalemate extends BaseCard {
    public static final String ID = makeID(Stalemate.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );


    private static int BLOCK = 7;
    private static int BLOCK_UPG = 3;

    public Stalemate()
    {
        super(ID,info);
        setBlock(BLOCK,BLOCK_UPG);
        tags.add(RunnerCharacter.Enums.NEON);
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
        addToBot(new GainBlockAction(m,5));
    }



}
