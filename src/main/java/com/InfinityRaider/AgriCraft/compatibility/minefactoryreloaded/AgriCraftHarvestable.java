package com.InfinityRaider.AgriCraft.compatibility.minefactoryreloaded;

import com.InfinityRaider.AgriCraft.blocks.BlockCrop;
import com.InfinityRaider.AgriCraft.init.Blocks;
import com.InfinityRaider.AgriCraft.tileentity.TileEntityCrop;
import com.InfinityRaider.AgriCraft.utility.SeedHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AgriCraftHarvestable implements IFactoryHarvestable {
    private BlockCrop crop;
    public AgriCraftHarvestable() {
        this.crop = Blocks.blockCrop;
    }

    @Override
    public Block getPlant () {
        return this.crop;
    }

    @Override
    public HarvestType getHarvestType () {
        return HarvestType.Normal;
    }

    @Override
    public boolean breakBlock () {
        return false;
    }

    @Override
    public boolean canBeHarvested (World world, Map<String, Boolean> harvesterSettings, int x, int y, int z) {
        return this.crop.isMature(world, x, y, z);
    }

    @Override
    public List<ItemStack> getDrops (World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z) {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileEntityCrop) {
            TileEntityCrop crop = (TileEntityCrop) world.getTileEntity(x, y, z);
            if (crop.hasPlant() && crop.isMature()) {
                items.addAll(SeedHelper.getPlantFruits((ItemSeeds) crop.seed, crop.getWorldObj(), crop.xCoord, crop.yCoord, crop.zCoord, crop.gain, crop.seedMeta));
            }
        }
        return items;
    }

    @Override
    public void preHarvest (World world, int x, int y, int z) {
    }

    @Override
    public void postHarvest (World world, int x, int y, int z) {
        world.setBlockMetadataWithNotify(x, y, z, 2, 3);
        world.getTileEntity(x, y, z).markDirty();
    }
}
