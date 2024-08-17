package runnermod.stances;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.stances.AbstractStance;
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

    public void reduceDurability(int amount)
    {
        for (String id: Collections.list(durabilityDictionary.keys())) {
            durabilityDictionary.put(id, durabilityDictionary.get(id)-amount);
        }
        //update description as it doesn't like to update for some reason
        //this.description = "";
        //for (String id: Collections.list(durabilityDictionary.keys())) {
        //    this.description += id + " : " + durabilityDictionary.get(id) + " cards left";
        //}
        updateDescription();
    }




    @Override
    public void updateDescription() {
        //Overwritten in the actual stances... Use this for updating descriptions
    }
}
