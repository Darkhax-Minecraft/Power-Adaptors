package com.jarhax.poweradapters.compat.waila;

import java.util.List;

import com.jarhax.poweradapters.BlockBasicMJ;
import com.jarhax.poweradapters.InternalBattery;
import com.jarhax.poweradapters.TileEntityMJ;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@WailaPlugin
public class WailaPowerAdapterInfo implements IWailaPlugin, IWailaDataProvider {

    @Override
    public List<String> getWailaBody (ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

        if (accessor.getNBTData().hasKey(InternalBattery.TAG_BATTERY)) {

            final InternalBattery battery = new InternalBattery(accessor.getNBTData());
            currenttip.add(String.format("Power %d / %d", battery.getStored(), battery.getCapacity()));
            currenttip.add(String.format("Input: %d", battery.getInput()));
            currenttip.add(String.format("Output: %d", battery.getOutput()));
        }

        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData (EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {

        final TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileEntityMJ) {

            ((TileEntityMJ) tile).getBattery().write(tag);
        }

        return tag;
    }

    @Override
    public void register (IWailaRegistrar registrar) {

        registrar.registerBodyProvider(this, BlockBasicMJ.class);
        registrar.registerNBTProvider(this, BlockBasicMJ.class);
    }
}