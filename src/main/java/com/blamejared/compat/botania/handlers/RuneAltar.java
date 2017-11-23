package com.blamejared.compat.botania.handlers;

import static com.blamejared.mtlib.helpers.InputHelper.toIItemStack;
import static com.blamejared.mtlib.helpers.InputHelper.toObjects;
import static com.blamejared.mtlib.helpers.InputHelper.toStack;
import static com.blamejared.mtlib.helpers.StackHelper.matches;

import java.util.LinkedList;
import java.util.List;

import com.blamejared.compat.botania.Botania;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseListAddition;
import com.blamejared.mtlib.utils.BaseListRemoval;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeRuneAltar;

@ZenClass("mods.botania.RuneAltar")
@ModOnly("botania")
@ZenRegister
public class RuneAltar {

    protected static final String name = "Botania Rune Altar";

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient[] input, int mana) {
        CraftTweakerAPI.apply(new Add(new RecipeRuneAltar(toStack(output), mana, toObjects(input))));
    }

    private static class Add extends BaseListAddition<RecipeRuneAltar> {
        public Add(RecipeRuneAltar recipe) {
            super(RuneAltar.name, BotaniaAPI.runeAltarRecipes);

            recipes.add(recipe);
        }

        @Override
        public String getRecipeInfo(RecipeRuneAltar recipe) {
            return LogHelper.getStackDescription(recipe.getOutput());
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void removeRecipe(IIngredient output) {
    	Botania.LATE.add(() -> {
        // Get list of existing recipes, matching with parameter
        List<RecipeRuneAltar> recipes = new LinkedList<RecipeRuneAltar>();

        for (RecipeRuneAltar r : BotaniaAPI.runeAltarRecipes) {
            if (r != null && r.getOutput() != null && matches(output, toIItemStack(r.getOutput()))) {
                recipes.add(r);
            }
        }

        // Check if we found the recipes and apply the action
        if (!recipes.isEmpty()) {
            CraftTweakerAPI.apply(new Remove(recipes));
        } else {
            LogHelper.logWarning(String.format("No %s Recipe found for %s. Command ignored!", RuneAltar.name, output.toString()));
        }});
    }

    private static class Remove extends BaseListRemoval<RecipeRuneAltar> {
        public Remove(List<RecipeRuneAltar> recipes) {
            super(RuneAltar.name, BotaniaAPI.runeAltarRecipes, recipes);
        }

        @Override
        public String getRecipeInfo(RecipeRuneAltar recipe) {
            return LogHelper.getStackDescription(recipe.getOutput());
        }
    }
}
