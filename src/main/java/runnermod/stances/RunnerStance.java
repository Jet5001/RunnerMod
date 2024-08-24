package runnermod.stances;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import runnermod.character.RunnerCharacter;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;

public abstract class RunnerStance extends AbstractStance {
    public Dictionary<String,Integer> durabilityDictionary;

    RunnerStance(String[] ids, int[] durabilties)
    {
        durabilityDictionary = new Hashtable<>();
        for (int i = 0; i < ids.length; i++) {
            durabilityDictionary.put(ids[i],durabilties[i] );
        }
    }

    RunnerStance(String id, int durabilty)
    {
            durabilityDictionary = new Hashtable<>();
            durabilityDictionary.put(id,durabilty);
    }

    public void onPlayCard(AbstractCard card) {
        if(card.hasTag(RunnerCharacter.Enums.NEON))
        {
            return;
        }
        String durability1 = Collections.list(durabilityDictionary.keys()).get(0);
        String durability2 = null;
        if (Collections.list(durabilityDictionary.keys()).size() >=2)
        {
            durability2 = Collections.list(durabilityDictionary.keys()).get(1);
        }
        if (durability2 != null)
        {
            reduceDurability(1, durability1, durability2);
        }
        else
        {
            reduceDurability(1,durability1);
        }

    }

    public void reduceDurability(int amount) {
        for (String id : Collections.list(durabilityDictionary.keys())) {
            durabilityDictionary.put(id, durabilityDictionary.get(id) - amount);
        }
    }

    public void reduceDurability(int amount, String durability)
    {
        for (String id: Collections.list(durabilityDictionary.keys())) {
            durabilityDictionary.put(id, durabilityDictionary.get(id)-amount);
        }
        if (durabilityDictionary.get(durability).equals(0) || durabilityDictionary.get(durability)<0)
        {
            AbstractDungeon.actionManager.addToTop(new ChangeRunnerStanceAction("Neutral",0));
        }
        updateDescription();
    }

    public void reduceDurability(int amount, String durability1, String durability2)
    {
        for (String id: Collections.list(durabilityDictionary.keys())) {
            durabilityDictionary.put(id, durabilityDictionary.get(id)-amount);
        }

        if (durabilityDictionary.get(durability1).equals(0) || durabilityDictionary.get(durability1) < 0)
        {
            if (durabilityDictionary.get(durability2).equals(0) || durabilityDictionary.get(durability2) < 0)
            {
                if (AbstractDungeon.player instanceof RunnerCharacter)
                {
                    AbstractDungeon.player.img = ((RunnerCharacter) AbstractDungeon.player).baseImg;
                }
                AbstractDungeon.actionManager.addToTop(new ChangeRunnerStanceAction("Neutral",0));
                return;
            }
            if (AbstractDungeon.player instanceof RunnerCharacter)
            {
                AbstractDungeon.player.img = GetStanceImg(durability2);
            }
            AbstractDungeon.actionManager.addToTop(new ChangeStanceAction(MakeStance(durability2,new String[]{durability2}, new int[]{durabilityDictionary.get(durability2)} )));

        }
        if (durabilityDictionary.get(durability2).equals(0) || durabilityDictionary.get(durability2) < 0)
        {
            if (durabilityDictionary.get(durability1).equals(0) || durabilityDictionary.get(durability1) < 0)
            {
                if (AbstractDungeon.player instanceof RunnerCharacter)
                {
                    AbstractDungeon.player.img = ((RunnerCharacter) AbstractDungeon.player).baseImg;
                }
                AbstractDungeon.actionManager.addToTop(new ChangeRunnerStanceAction("Neutral",0));
                return;
            }
            if (AbstractDungeon.player instanceof RunnerCharacter)
            {
                AbstractDungeon.player.img = GetStanceImg(durability1);
            }
            AbstractDungeon.actionManager.addToTop(new ChangeStanceAction(MakeStance(durability1,new String[]{durability1}, new int[]{durabilityDictionary.get(durability1)} )));

        }
        updateDescription();
    }

    public static AbstractStance MakeStance(String StanceID, String[] durabilityIds, int[] durabilities)
    {
        switch (StanceID){
            case "Blades":
                    return new BladesStance(durabilityIds,durabilities);
            case "Overclock":
                return  new OverclockStance(durabilityIds,durabilities);
            case "Wall":
                return new WallStance(durabilityIds,durabilities);
            case "Artifact":
                return new ArtifactStance(durabilityIds,durabilities);
            default:
                return new NeutralStance();
        }

    }

    public static Texture GetStanceImg(String StanceID)
    {
        if(!(AbstractDungeon.player instanceof RunnerCharacter))
        {
            return AbstractDungeon.player.img;
        }
        switch (StanceID){
            case "Blades":
                return ((RunnerCharacter) AbstractDungeon.player).bladesStanceImg;
            case "Overclock":
                return  ((RunnerCharacter) AbstractDungeon.player).overclockerStanceImg;
            case "Wall":
                return ((RunnerCharacter) AbstractDungeon.player).shieldsStanceImg;
            case "Artifact":
                return ((RunnerCharacter) AbstractDungeon.player).firewallStanceImg;
            default:
                return AbstractDungeon.player.img;
        }

    }

    public static String getStanceChangeDescription(String ID)
    {
        if (ID == "Accel")
        {
            return " NL RunnerMod:Enter_Accel";
        }
        if (ID == "Metal")
        {
            return  " NL RunnerMod:Enter_Metal";
        }
        if (ID == "Hack")
        {
            return  " NL RunnerMod:Enter_Hack";
        }
        if (ID == "Tinker")
        {
            return " NL RunnerMod:Enter_Tinker";
        }
        if (ID == "Cards")
        {
            return  " NL RunnerMod:Enter_Blaster";
        }
        if (ID == "Berserker")
        {
            return  " NL RunnerMod:Enter_Berserker";
        }
        return "";
    }


    public static String determineNewStance(String ID)
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



    @Override
    public void updateDescription() {
        //Overwritten in the actual stances... Use this for updating descriptions
    }
}
