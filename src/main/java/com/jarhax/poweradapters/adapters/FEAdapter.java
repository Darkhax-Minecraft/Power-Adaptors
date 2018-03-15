package com.jarhax.poweradapters.adapters;

import com.jarhax.poweradapters.ConfigurationHandler;
import com.jarhax.poweradapters.InternalBattery;
import com.jarhax.poweradapters.adapters.caps.EnergyStorageAdapter;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class FEAdapter extends IPowerAdapter {
    private final EnergyStorageAdapter container;

    public FEAdapter (InternalBattery battery) {

        super(battery);
        this.container = new EnergyStorageAdapter((int) battery.getCapacity(), this);
    }

    @Override
    public long getExchangeRate () {

        // Tesla = 25 internal power
        return ConfigurationHandler.worthForgeUnits;
    }

    @Override
    public void distributePower (World world, BlockPos pos) {

        for (final EnumFacing facing : EnumFacing.VALUES) {
            if (world.getTileEntity(pos.offset(facing)) != null && world.getTileEntity(pos.offset(facing)).hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
                final IEnergyStorage storage = world.getTileEntity(pos.offset(facing)).getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
                this.container.extractEnergy(storage.receiveEnergy(this.container.extractEnergy((int) this.getLocalOutput(), true), false), false);
            }
        }
    }

    @Override
    public boolean hasCapability (Capability<?> capability, EnumFacing facing) {

        if (capability == CapabilityEnergy.ENERGY) {
            return true;
        }
        return false;
    }

    @Override
    public <T> T getCapability (Capability<T> capability, EnumFacing facing) {

        if (capability == CapabilityEnergy.ENERGY) {
            return (T) this.container;
        }
        return null;
    }
}