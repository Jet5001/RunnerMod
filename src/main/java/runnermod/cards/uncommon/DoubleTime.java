package runnermod.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.AbstractStance;
import runnermod.cards.BaseCard;
import runnermod.cards.tempcards.Decoy;
import runnermod.character.RunnerCharacter;
import runnermod.stances.AKIRAStance;
import runnermod.stances.ChangeRunnerStanceAction;
import runnermod.stances.RunnerStance;
import runnermod.util.CardStats;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;

public class DoubleTime extends BaseCard {
    public static final String ID = makeID(DoubleTime.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

//    //Card Stats
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC =0;

    public DoubleTime()
    {
        super(ID,info);
        this.setMagic(MAGIC, UPG_MAGIC);
        this.tags.add(RunnerCharacter.Enums.NEON);
        this.cardsToPreview = new Decoy();
    }

    //called when the card is upgraded
    @Override
    public void upgrade()
    {
        super.upgrade();
        if (!this.upgraded)
        {
            upgradeName();
            this.upgraded = true;
        }
    }

    //called when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new ChangeRunnerStanceAction("Overclock",magicNumber));
        //can't be bothered adding another number so I'm just checking
        if (this.upgraded){
            addToBot(new MakeTempCardInHandAction(new Decoy(),2));
        }
        else{
            addToBot(new MakeTempCardInHandAction(new Decoy(), 3));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        rawDescription = "";
        this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        String newStance = determineNewStance("Overclock");
        if (newStance == "Accel")
        {
            this.glowColor = Color.RED;
            rawDescription += " NL RunnerMod:Enter_Accel";
        }
        if (newStance == "Metal")
        {
            this.glowColor = Color.RED;
            rawDescription += " NL RunnerMod:Enter_Metal";
        }
        if (newStance == "Hack")
        {
            this.glowColor = Color.RED;
            rawDescription += " NL RunnerMod:Enter_Hack";
        }
        if (newStance == "Tinker")
        {
            this.glowColor = Color.RED;
            rawDescription += " NL RunnerMod:Enter_Tinker";
        }
        if (newStance == "Cards")
        {
            this.glowColor = Color.RED;
            rawDescription += " NL RunnerMod:Enter_Blaster";
        }
        if (newStance == "Berserker")
        {
            this.glowColor = Color.RED;
            rawDescription += " NL RunnerMod:Enter_Berserker";
        }
        initializeDescription();

    }

    private String determineNewStance(String ID)
    {

        Dictionary<String,String> comboLookup = new Hashtable<>();
        //Combo table to reference previous stance and new stance to see what you get
        comboLookup.put("BladesWall","Accel");
        comboLookup.put("WallBlades","Accel");
        comboLookup.put("BladesArtifact","Hack");
        comboLookup.put("ArtifactBlades","Hack");
        comboLookup.put("WallArtifact","Metal");
        comboLookup.put("ArtifactWall","Metal");
        comboLookup.put("WallOverclock","Tinker");
        comboLookup.put("OverclockWall","Tinker");
        comboLookup.put("ArtifactOverclock","Cards");
        comboLookup.put("OverclockArtifact","Cards");
        comboLookup.put("BladesOverclock", "Berserker");
        comboLookup.put("OverclockBlades", "Berserker");
        AbstractStance previousStance = AbstractDungeon.player.stance;
        String stanceID = ID;
        String newID = "";


        if (previousStance instanceof AKIRAStance)
        {
            return "";
        }

        if (!(previousStance instanceof RunnerStance))
        {
            return ID;
        }
        else
        {

            //if new stance already part of existing stance then flag as the same
            String components = "";
            for (String id: Collections.list(((RunnerStance) previousStance).durabilityDictionary.keys())) {
                components += id;
            }
            System.out.println("Previous stance durabilities: " + ((RunnerStance) previousStance).durabilityDictionary.keys());
            if (components.contains(stanceID))
            {
                newID = "same";
            }
            else
            {
                //get combo name if not the same

                //get previous max durability id to combo with longest lasting part
                String previousMaxDurabilityID = "";
                int tempMaxDurability = -1;
                for (String id: Collections.list(((RunnerStance) previousStance).durabilityDictionary.keys())) {
                    if (((RunnerStance) previousStance).durabilityDictionary.get(id) > tempMaxDurability)
                    {
                        previousMaxDurabilityID=  id;
                    }
                }
                //get combo
                newID = comboLookup.get(previousMaxDurabilityID + stanceID);
                //extra check to be careful (defaults to new stance)
                if (newID == null)
                {
                    newID = stanceID;
                }
            }
        }
        return newID;
    }
}
