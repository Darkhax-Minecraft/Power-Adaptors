package com.jarhax.poweradapters.adapters;

import com.jarhax.poweradapters.ConfigurationHandler;
import com.jarhax.poweradapters.InternalBattery;

import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public class RFAdapter extends IPowerAdapter {

    public RFAdapter (InternalBattery battery) {

        super(battery);
    }

    @Override
    public long getExchangeRate () {

        // Tesla = 25 internal power
        return ConfigurationHandler.worthRedstoneFlux;
    }

    @Override
    public void distributePower (World world, BlockPos pos) {

        for (final EnumFacing facing : EnumFacing.VALUES) {
            if (world.getTileEntity(pos.offset(facing)) != null && world.getTileEntity(pos.offset(facing)) instanceof IEnergyReceiver) {
                final IEnergyReceiver rec = (IEnergyReceiver) world.getTileEntity(pos.offset(facing));
                this.takePower(rec.receiveEnergy(facing, (int) this.takePower(this.getLocalOutput(), true), false), false);

            }
        }
    }

    @Override
    public boolean hasCapability (Capability<?> capability, EnumFacing facing) {

        return false;
    }

    @Override
    public <T> T getCapability (Capability<T> capability, EnumFacing facing) {

        return null;
    }

}