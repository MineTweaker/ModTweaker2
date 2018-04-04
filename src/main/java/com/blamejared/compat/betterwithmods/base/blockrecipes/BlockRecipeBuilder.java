package com.blamejared.compat.betterwithmods.base.blockrecipes;

import betterwithmods.common.registry.block.managers.CraftingManagerBlock;
import betterwithmods.common.registry.block.recipe.BlockIngredient;
import betterwithmods.common.registry.block.recipe.BlockRecipe;
import com.blamejared.ModTweaker;
import com.blamejared.compat.betterwithmods.base.RemoveAll;
import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

public abstract class BlockRecipeBuilder<T extends BlockRecipe> {
    protected BlockIngredient input;
    protected List<ItemStack> outputs;
    private CraftingManagerBlock<T> registry;
    private String name;

    public BlockRecipeBuilder(CraftingManagerBlock<T> registry, String name) {
        this.registry = registry;
        this.name = name;
    }

    protected void addRecipe(T recipe) {
        ModTweaker.LATE_ADDITIONS.add(new BlockRecipeAdd<>(name, registry, recipe));
    }

    public abstract void build();

    public void removeRecipe(IItemStack[] output) {
        ModTweaker.LATE_REMOVALS.add(new BlockRecipeRemove<>(name, registry, output));
    }

    public void _buildRecipe(IIngredient input, IItemStack[] outputs) {
        this.input = new BlockIngredient(CraftTweakerMC.getIngredient(input));
        this.outputs = InputHelper.toNonNullList(CraftTweakerMC.getItemStacks(outputs));

    }

    @ZenMethod
    public void removeAll() {
        ModTweaker.LATE_REMOVALS.add(new RemoveAll<>(name, registry.getRecipes()));
    }

    public String getName() {
        return name;
    }
}