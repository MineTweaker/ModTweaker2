package com.blamejared.compat.botania.lexicon;

import com.blamejared.compat.botania.BotaniaHelper;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import net.minecraft.util.ResourceLocation;
import vazkii.botania.api.lexicon.LexiconCategory;

public class SetCategoryIcon implements IAction {
	
	LexiconCategory category;
	ResourceLocation oldIcon;
	ResourceLocation newIcon;
	final String name;

    public SetCategoryIcon(String name, String icon) {
        this.name=name;
        this.newIcon=new ResourceLocation(icon);
    }

    @Override
	public void apply() {
    	category = BotaniaHelper.findCatagory(name);
		if (category == null) {
			CraftTweakerAPI.getLogger().logError("Cannot find lexicon category " + name);
			return;
		}
    	oldIcon=category.getIcon();
    	category.setIcon(newIcon);
	}
	
	@Override
	public String describe() {
		if(category == null) return "Cannot find lexicon category " + name;
        return "Setting Lexicon Category icon: " + category.getUnlocalizedName();
	}

}
