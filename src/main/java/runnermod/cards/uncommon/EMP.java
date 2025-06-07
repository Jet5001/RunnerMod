package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class EMP extends BaseCard {
    public static final String ID = makeID(EMP.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            2
    );

    private static int BLOCK = 14;
    private static int BLOCK_UPG = 3;
    public EMP()
    {
        super(ID,info);
        setBlock(BLOCK,BLOCK_UPG);
    }


    //called when the card is played and performs the actions for the card
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int blockAmount =0;
        for (AbstractMonster mo:AbstractDungeon.getMonsters().monsters) {
            blockAmount += mo.currentBlock;
            addToBot(new RemoveAllBlockAction(mo,p));
        }
        addToBot(new GainBlockAction(p,this.block+blockAmount));
    }
}
