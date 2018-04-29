package com.jarhax.poweradapters.adapters;

import com.jarhax.poweradapters.InternalBattery;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public abstract class IPowerAdapter {

    private final InternalBattery battery;

    public IPowerAdapter (InternalBattery battery) {

        this.battery = battery;
    }

    abstract long getExchangeRate ();

    public long getLocalCapacity () {

        return this.battery.getCapacity() / this.getExchangeRate();
    }

    public long getLocalStored () {

        return this.battery.getStored() / this.getExchangeRate();
    }

    public long getLocalInput () {

        return this.battery.getInput() / this.getExchangeRate();
    }

    public long getLocalOutput () {

        return this.battery.getOutput() / this.getExchangeRate();
    }

    public long takePower (long requested, boolean simulated) {

        final long removedPower = Math.min(this.getLocalStored(), Math.min(this.getLocalOutput(), requested));

        if (!simulated) {

            this.battery.takePower(removedPower*getExchangeRate());
        }

        return removedPower;
    }

    public long addPower (long power, boolean simulated) {

        long acceptedPower = Math.min(this.getLocalInput(), power);
        
        if (this.battery.getStored() == this.battery.getCapacity()) {
            
            acceptedPower = 0;
        }
        
        if (!simulated) {

            this.battery.addPower(acceptedPower*getExchangeRate());
        }

        return acceptedPower;
    }

    public InternalBattery getInternalBattery () {

        return this.battery;
    }

    public abstract void distributePower (World world, BlockPos pos);

    public abstract boolean hasCapability (Capability<?> capability, EnumFacing facing);

    public abstract <T> T getCapability (Capability<T> capability, EnumFacing facing);
}
