package me.profelements.dynatech.items.electric;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.profelements.dynatech.DynaTech;
import me.profelements.dynatech.items.electric.abstracts.AMachine;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.exoticgarden.items.ExoticGardenFruit;
import io.github.thebusybiscuit.slimefun4.api.items.ItemSetting;

public class SeedPlucker extends AMachine {

    private ItemSetting<Boolean> exoticGardenIntegration = new ItemSetting<Boolean>("exotic-garden-integration", true);

    public SeedPlucker(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        addItemSetting(exoticGardenIntegration);
    }

    @Override
    protected void registerDefaultRecipes() {
        registerRecipe(10, new ItemStack[] {new ItemStack(Material.WHEAT)}, new ItemStack[] {new ItemStack(Material.WHEAT_SEEDS)});
        registerRecipe(10, new ItemStack[] {new ItemStack(Material.BEETROOT)}, new ItemStack[] {new ItemStack(Material.BEETROOT_SEEDS)});
        registerRecipe(10, new ItemStack[] {new ItemStack(Material.PUMPKIN)}, new ItemStack[] {new ItemStack(Material.PUMPKIN_SEEDS)});
        registerRecipe(10, new ItemStack[] {new ItemStack(Material.MELON)}, new ItemStack[] {new ItemStack(Material.MELON_SEEDS)});
    }
    
    @Override
    public MachineRecipe findNextRecipe(BlockMenu inv) {
        if (DynaTech.isExoticGardenInstalled() && exoticGardenIntegration.getValue()) {
            for (int inputSlot : getInputSlots()) {
                ItemStack item = inv.getItemInSlot(inputSlot);
                if (item != null && SlimefunItem.getByItem(item) != null) {
                    SlimefunItem sfItem = SlimefunItem.getByItem(item);
                    
                    if (sfItem instanceof ExoticGardenFruit) {

                        if (SlimefunItem.getByID(sfItem.getId().concat("_BUSH")) != null) {
                            inv.consumeItem(inputSlot);
                            return new MachineRecipe(10, new ItemStack[] {sfItem.getItem()}, new ItemStack[] {SlimefunItem.getByID(sfItem.getId().concat("_BUSH")).getItem()});
                        } else if (SlimefunItem.getByID(sfItem.getId().concat("_SAPLING")) != null) {
                            inv.consumeItem(inputSlot);
                            return new MachineRecipe(10, new ItemStack[] {sfItem.getItem()}, new ItemStack[] {SlimefunItem.getByID(sfItem.getId().concat("_SAPLING")).getItem()});
                        } else if (SlimefunItem.getByID(sfItem.getId().concat("_PLANT")) != null) {
                            inv.consumeItem(inputSlot);
                            return new MachineRecipe(10, new ItemStack[] {sfItem.getItem()}, new ItemStack[] {SlimefunItem.getByID(sfItem.getId().concat("_PLANT")).getItem()});
                        }
                        
                        
                    }
                }
            }
        }
        return super.findNextRecipe(inv);
        
    } 

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.HOPPER);
    }

    @Override
    public String getMachineIdentifier() {
        return "SEED_PLUCKER";
    }



}
