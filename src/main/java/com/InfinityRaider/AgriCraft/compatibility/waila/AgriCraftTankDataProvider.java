package com.InfinityRaider.AgriCraft.compatibility.waila;

import com.InfinityRaider.AgriCraft.blocks.BlockWaterTank;
import com.InfinityRaider.AgriCraft.init.Blocks;
import com.InfinityRaider.AgriCraft.tileentity.TileEntityCustomWood;
import com.InfinityRaider.AgriCraft.tileentity.TileEntityTank;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

import java.util.List;

public class AgriCraftTankDataProvider implements IWailaDataProvider {

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor dataAccessor, IWailaConfigHandler configHandler) {
        Block block = dataAccessor.getBlock();
        TileEntity te = dataAccessor.getTileEntity();
        if(block instanceof BlockWaterTank && te instanceof TileEntityCustomWood) {
            ItemStack stack = new ItemStack(Blocks.blockWaterTank, 1, 0);
            stack.setTagCompound(((TileEntityCustomWood) te).getMaterialTag());
            return stack;
        }
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> list, IWailaDataAccessor dataAccessor, IWailaConfigHandler configHandler) {
        return list;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> list, IWailaDataAccessor dataAccessor, IWailaConfigHandler configHandler) {
        Block block = dataAccessor.getBlock();
        TileEntity te = dataAccessor.getTileEntity();
        if(block!=null && block instanceof BlockWaterTank  && te!=null && te instanceof TileEntityTank) {
            TileEntityTank tank = (TileEntityTank) te;
            //define material
            ItemStack materialStack =tank.getMaterial();
            String material = materialStack.getItem().getItemStackDisplayName(materialStack);
            list.add(StatCollector.translateToLocal("agricraft_tooltip.material")+": "+material);
            //show contents
            TileEntityTank bottomTank = (TileEntityTank) tank.getWorldObj().getTileEntity(tank.xCoord, tank.yCoord-tank.getYPosition(), tank.zCoord);
            int contents = bottomTank.getFluidLevel();
            int capacity = tank.getTotalCapacity();
            list.add(StatCollector.translateToLocal("agricraft_tooltip.waterLevel")+": "+contents+"/"+capacity);
        }
        return list;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> list, IWailaDataAccessor dataAccessor, IWailaConfigHandler configHandler) {
        return list;
    }
}
