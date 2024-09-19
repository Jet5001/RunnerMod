package runnermod.relics;

import basemod.BaseMod;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import runnermod.character.RunnerCharacter;
import runnermod.stances.RunnerStance;

import java.util.ArrayList;
import java.util.List;

import static runnermod.RunnerMod.makeID;

public class FissionCore extends BaseRelic{
    private static final String NAME = "FissionCore";
    public static final String ID = makeID(NAME);
    public static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;
    List<String> enteredStances;
    public FissionCore()
    {
        super(ID,NAME, RunnerCharacter.Enums.CARD_COLOR,RARITY,SOUND);
        enteredStances = new ArrayList<>();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStartPreDraw() {
        enteredStances.clear();
        super.atBattleStartPreDraw();
    }

    @Override
    public void onChangeStance(AbstractStance prevStance, AbstractStance newStance) {
        AbstractPlayer p = AbstractDungeon.player;
        if(!enteredStances.contains(newStance.name))
        {
            enteredStances.add(newStance.name);
            flash();
            addToBot(new ApplyPowerAction(p,p,new StrengthPower(p, 1)));
        }
        super.onChangeStance(prevStance, newStance);
    }
}
