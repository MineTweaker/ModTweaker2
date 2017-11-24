package com.blamejared.compat.botania.lexicon;

import com.blamejared.compat.botania.BotaniaHelper;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import vazkii.botania.api.lexicon.LexiconCategory;

public class SetCategoryPriority implements IAction {
	
	LexiconCategory category;
	int oldPriority;
	int newPriority;
	final String name;

    public SetCategoryPriority(String name, int priority) {
        this.name=name;
        this.newPriority=priority;
    }

    @Override
	public void apply() {
    	category = BotaniaHelper.findCatagory(name);
		if (category == null) {
			CraftTweakerAPI.getLogger().logError("Cannot find lexicon category " + name);
			return;
		}
    	oldPriority=category.getSortingPriority();
    	category.setPriority(newPriority);
	}
	
	@Override
	public String describe() {
        return "Setting Lexicon Category priority: " + category.getUnlocalizedName();
	}
	
}
