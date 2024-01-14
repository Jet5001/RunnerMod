package runnermod.cards.starter;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class Defend_Runner extends BaseCard {
    public static final String ID = makeID(Defend_Runner.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            1
    );

    //Card Stats
    private static final int BLOCK = 5;
    private static final int BLOCK_UPG = 3;
    public Defend_Runner()
    {
        super(ID,info);
        setBlock(BLOCK, BLOCK_UPG);
        this.tags.add(AbstractCard.CardTags.STARTER_DEFEND);
    }


    //Not applying vulnerable before damage for some reason?
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,p,this.block));
    }
}
