package runnermod.stances;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import org.lwjgl.Sys;

import java.security.cert.TrustAnchor;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

public class ChangeRunnerStanceAction extends AbstractGameAction {

    private static Dictionary<String,String> comboLookup = new Hashtable<>();

    AbstractStance finalStance;
    AbstractStance previousStance;
    boolean changeStance = true;
    int[] durabilities = new int[3];
    String newStanceID;

    //Only use for single stance
    public ChangeRunnerStanceAction(String stanceID, int durability)
    {
        newStanceID = stanceID;
        this.durabilities[0] = durability;
        //Combo table to reference previous stance and new stance to see what you get
        comboLookup.put("AgilityBrute","Accel");
        comboLookup.put("BruteAgility","Accel");
        previousStance = AbstractDungeon.player.stance;

        if (!(previousStance instanceof RunnerStance))
        {
            finalStance = makeStance(stanceID);
        }
        else
        {
            String newID = "";
            if (((RunnerStance) previousStance).durabilityDictionary.keys().toString().contains(stanceID))
            {
                newID = "same";
            }
            else
            {
                newID = comboLookup.get(previousStance.ID + stanceID);
                if (newID == null)
                {
                    newID = stanceID;
                }
            }
            System.out.println("NEW STANCE ID: " +newID );
            if (newID == "same")
            {
                changeStance = false;
                durabilities[0] = Math.max(durabilities[0], ((RunnerStance) previousStance).durabilityDictionary.get(stanceID));
                ((RunnerStance) previousStance).durabilityDictionary.put(stanceID,durabilities[0]);
                previousStance.updateDescription();
            }
            else
            {
                finalStance = makeStance(newID);
                System.out.println("New Stance : " + finalStance.ID);
            }
        }

    }

    //only use when adding combo stance
    ChangeRunnerStanceAction(String[] stanceIDs, int[] durabilitys)
    {
        this.durabilities = durabilitys;
        comboLookup.put("Brute","Brute");
        comboLookup.put("BruteBrute","Brute");
        AbstractStance previousStance = AbstractDungeon.player.stance;

        if (!(previousStance instanceof RunnerStance))
        {
            changeStance = true;
            finalStance = makeStance(comboLookup.get(stanceIDs[0]));
        }
        else
        {

            String stringNewID = "";
        }

    }

    AbstractStance makeStance(String stanceID)
    {
        switch (stanceID)
        {
            case "Brute":
                return new BruteStance(new String[]{"Brute"},new int[]{durabilities[0]});
            case "Agility":
                return new AgilityStance(new String[]{"Agility"},new int[]{durabilities[0]});
            case "Accel":

                if (newStanceID == "Brute")
                {
                    durabilities[1] = durabilities[0];
                    durabilities[0]=0;
                }
                for (String id: Collections.list(((RunnerStance) previousStance).durabilityDictionary.keys())) {
                    if (id == "Agility")
                    {
                        durabilities[0] = Math.max(durabilities[0], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                    if (id == "Brute")
                    {
                        durabilities[1] = Math.max(durabilities[1], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                }
                return new AccelStance(new String[]{"Agility", "Brute"},new int[]{durabilities[0], durabilities[1]});
            default:
                return new NeutralStance();
        }

    }


    @Override
    public void update() {
        if (changeStance)
        {
            AbstractDungeon.actionManager.addToTop(new ChangeStanceAction(finalStance));
        }
        this.isDone = true;
    }
}
