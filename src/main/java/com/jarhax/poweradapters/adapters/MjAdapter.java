package com.jarhax.poweradapters.adapters;

import com.jarhax.poweradapters.InternalBattery;

import buildcraft.api.mj.IMjConnector;
import buildcraft.api.mj.IMjPassiveProvider;
import buildcraft.api.mj.IMjReceiver;
import buildcraft.api.mj.MjAPI;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public class MjAdapter extends IPowerAdapter implements IMjReceiver, IMjPassiveProvider {

    public MjAdapter (InternalBattery battery) {

        super(battery);
    }

    @Override
    public long getExchangeRate () {

        // 1Mj = 100 internal power
        return 100;
    }

    @Override
    public void distributePower (World world, BlockPos pos) {

        for (final EnumFacing facing : EnumFacing.VALUES) {
            if (world.getTileEntity(pos.offset(facing)) != null && world.getTileEntity(pos.offset(facing)).hasCapability(MjAPI.CAP_RECEIVER, facing.getOpposite())) {
                final IMjReceiver storage = world.getTileEntity(pos.offset(facing)).getCapability(MjAPI.CAP_RECEIVER, facing.getOpposite());
                final long power = this.extractPower(0, this.getLocalOutput(), true);
                this.extractPower(0, storage.receivePower(power * MjAPI.ONE_MINECRAFT_JOULE, false) / MjAPI.ONE_MINECRAFT_JOULE, false);
            }
        }
    }

    @Override
    public boolean hasCapability (Capability<?> capability, EnumFacing facing) {

        if (capability == MjAPI.CAP_PASSIVE_PROVIDER || capability == MjAPI.CAP_CONNECTOR || capability == MjAPI.CAP_RECEIVER) {

            return true;
        }
        return false;
    }

    @Override
    public <T> T getCapability (Capability<T> capability, EnumFacing facing) {

        if (capability == MjAPI.CAP_PASSIVE_PROVIDER || capability == MjAPI.CAP_CONNECTOR || capability == MjAPI.CAP_RECEIVER) {

            return (T) this;
        }
        return null;
    }

    @Override
    public boolean canConnect (IMjConnector other) {

        return true;
    }

    @Override
    public long getPowerRequested () {

        return Math.min(this.getLocalInput(), this.getLocalCapacity() - this.getLocalStored());
    }

    @Override
    public long receivePower (long microJoules, boolean simulate) {

        return this.addPower(microJoules, simulate);
    }

    @Override
    public long extractPower (long min, long max, boolean simulate) {

        return this.takePower(max, simulate);
    }
}