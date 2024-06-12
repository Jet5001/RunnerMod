package runnermod.stances;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import org.lwjgl.Sys;
import runnermod.powers.ScrapArmourPower;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;

public class ChangeRunnerStanceAction extends AbstractGameAction {

    private static Dictionary<String,String> comboLookup = new Hashtable<>();

    AbstractStance finalStance;
    AbstractStance previousStance;
    boolean changeStance = true;
    int[] durabilities = new int[3];
    String newStanceID;
    String stanceID;
    //Only use for single stance
    public ChangeRunnerStanceAction(String stanceID, int durability)
    {
        this.stanceID = stanceID;
        newStanceID = stanceID;
        this.durabilities[0] = durability;
        for (AbstractPower p :AbstractDungeon.player.powers) {
            if (p.getClass() == ScrapArmourPower.class)
            {
                ((ScrapArmourPower) p).onDurabilityGain(durability);
            }
        }
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
        previousStance = AbstractDungeon.player.stance;



    }

    AbstractStance makeStance(String stanceID)
    {
        switch (stanceID)
        {
            case "Wall":
                return new WallStance(new String[]{"Wall"},new int[]{durabilities[0]});
            case "Blades":
                return new BladesStance(new String[]{"Blades"},new int[]{durabilities[0]});
            case "Artifact":
                return new ArtifactStance(new String[]{"Artifact"},new int[]{durabilities[0]});
            case "Overclock":
                return new OverclockStance(new String[]{"Overclock"},new int[]{durabilities[0]});
            case "Accel":
                //shuffle durabilities into correct spots for this combo
                if (newStanceID == "Wall")
                {
                    durabilities[1] = durabilities[0];
                    durabilities[0]=0;
                }
                // get max durability for each part
                for (String id: Collections.list(((RunnerStance) previousStance).durabilityDictionary.keys())) {
                    if (id == "Blades")
                    {
                        durabilities[0] = Math.max(durabilities[0], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                    if (id == "Wall")
                    {
                        durabilities[1] = Math.max(durabilities[1], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                }
                //make new stance and return
                return new AccelStance(new String[]{"Blades", "Wall"},new int[]{durabilities[0], durabilities[1]});
            case "Hack":
                if (newStanceID == "Blades")
                {
                    durabilities[1] = durabilities[0];
                    durabilities[0]=0;
                }
                for (String id: Collections.list(((RunnerStance) previousStance).durabilityDictionary.keys())) {
                    if (id == "Artifact")
                    {
                        durabilities[0] = Math.max(durabilities[0], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                    if (id == "Blades")
                    {
                        durabilities[1] = Math.max(durabilities[1], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                }
                return new HackStance(new String[]{"Artifact", "Blades"},new int[]{durabilities[0], durabilities[1]});
            case "Metal":
                if (newStanceID == "Wall")
                {
                    durabilities[1] = durabilities[0];
                    durabilities[0]=0;
                }
                for (String id: Collections.list(((RunnerStance) previousStance).durabilityDictionary.keys())) {
                    if (id == "Artifact")
                    {
                        durabilities[0] = Math.max(durabilities[0], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                    if (id == "Wall")
                    {
                        durabilities[1] = Math.max(durabilities[1], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                }
                return new MetalStance(new String[]{"Artifact", "Wall"},new int[]{durabilities[0], durabilities[1]});
            case "Tinker":
                if (newStanceID == "Wall")
                {
                    durabilities[1] = durabilities[0];
                    durabilities[0]=0;
                }
                for (String id: Collections.list(((RunnerStance) previousStance).durabilityDictionary.keys())) {
                    if (id == "Overclock")
                    {
                        durabilities[0] = Math.max(durabilities[0], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                    if (id == "Wall")
                    {
                        durabilities[1] = Math.max(durabilities[1], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                }
                return new TinkerStance(new String[]{"Overclock", "Wall"},new int[]{durabilities[0], durabilities[1]});
            case "Cards":
                if (newStanceID == "Overclock")
                {
                    durabilities[1] = durabilities[0];
                    durabilities[0]=0;
                }
                for (String id: Collections.list(((RunnerStance) previousStance).durabilityDictionary.keys())) {
                    if (id == "Artifact")
                    {
                        durabilities[0] = Math.max(durabilities[0], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                    if (id == "Overclock")
                    {
                        durabilities[1] = Math.max(durabilities[1], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                }
                return new CardsStance(new String[]{"Artifact", "Overclock"},new int[]{durabilities[0], durabilities[1]});
            case "Berserker":
                if (newStanceID == "Overclock")
                {
                    durabilities[1] = durabilities[0];
                    durabilities[0]=0;
                }
                for (String id: Collections.list(((RunnerStance) previousStance).durabilityDictionary.keys())) {
                    if (id == "Blades")
                    {
                        durabilities[0] = Math.max(durabilities[0], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                    if (id == "Overclock")
                    {
                        durabilities[1] = Math.max(durabilities[1], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                }
                return new BerserkerStance(new String[]{"Blades", "Overclock"},new int[]{durabilities[0], durabilities[1]});
            default:
                return new NeutralStance();
        }

    }


    @Override
    public void update() {
        //overide stance if not from this mod
        if (previousStance instanceof AKIRAStance)
        {
            this.isDone = true;
            return;
        }
        if (stanceID == "AKIRA")
        {
            finalStance =  new AKIRAStance(new String[]{"AKIRA"}, new int[]{0});
            AbstractDungeon.actionManager.addToTop(new ChangeStanceAction(finalStance));
            this.isDone = true;
            return;
        }
        if (!(previousStance instanceof RunnerStance))
        {
            finalStance = makeStance(stanceID);
        }
        else
        {
            String newID = "";
            //if new stance already part of existing stance then flag as the same
            String components = "";
            for (String id: Collections.list(((RunnerStance) previousStance).durabilityDictionary.keys())) {
                components += id;
            }
            if(stanceID == "Neutral")
            {
                for (String id: Collections.list(((RunnerStance) previousStance).durabilityDictionary.keys())) {
                    if (((RunnerStance) previousStance).durabilityDictionary.get(id) >0)
                    {
                        this.isDone = true;
                        changeStance = false;
                        return;
                    }
                }
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
            //if the same update durabilities
            System.out.println(newID);
            if (newID == "same")
            {
                changeStance = false;
                durabilities[0] = Math.max(durabilities[0], ((RunnerStance) previousStance).durabilityDictionary.get(stanceID));
                ((RunnerStance) previousStance).durabilityDictionary.put(stanceID,durabilities[0]);
                previousStance.updateDescription();
            }
            else
            {
                //if a combo make new stance
                finalStance = makeStance(newID);
                System.out.println("New Stance : " + finalStance.ID);
            }
        }
        if (changeStance)
        {
            AbstractDungeon.actionManager.addToTop(new ChangeStanceAction(finalStance));
        }
        this.isDone = true;
    }
}
