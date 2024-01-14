package runnermod.stances;

import com.megacrit.cardcrawl.stances.AbstractStance;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;

public abstract class RunnerStance extends AbstractStance {
    Dictionary<String,Integer> durabilityDictionary;

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

    public void reduceDurability()
    {
        for (String id: Collections.list(durabilityDictionary.keys())) {
            durabilityDictionary.put(id, durabilityDictionary.get(id)-1);
        }
        //update description as it doesn't like to update for some reason
        this.description = "";
        for (String id: Collections.list(durabilityDictionary.keys())) {
            this.description += id + " : " + durabilityDictionary.get(id) + " turns left";
        }
    }




    @Override
    public void updateDescription() {

    }
}
