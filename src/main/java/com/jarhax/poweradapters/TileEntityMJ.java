package com.jarhax.poweradapters;

import java.util.ArrayList;
import java.util.List;

import com.jarhax.poweradapters.adapters.FEAdapter;
import com.jarhax.poweradapters.adapters.IPowerAdapter;
import com.jarhax.poweradapters.adapters.MjAdapter;
import com.jarhax.poweradapters.adapters.RFAdapter;
import com.jarhax.poweradapters.adapters.TeslaAdapter;

import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import net.darkhax.bookshelf.block.tileentity.TileEntityBasicTickable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "cofh.redstoneflux.api.IEnergyReceiver", modid = "redstoneflux")
@Optional.Interface(iface = "cofh.redstoneflux.api.IEnergyProvider", modid = "redstoneflux")
public class TileEntityMJ extends TileEntityBasicTickable implements IEnergyReceiver, IEnergyProvider {

    private final InternalBattery battery = new InternalBattery(ConfigurationHandler.maxCapacity, ConfigurationHandler.maxInput, ConfigurationHandler.maxOutput);
    private final List<IPowerAdapter> adapters = new ArrayList<>();
    private IPowerAdapter mjAdapter;
    private IPowerAdapter teslaAdapter;
    private IPowerAdapter rfAdapter;
    private final IPowerAdapter feAdapter;

    public TileEntityMJ () {

        if (PowerAdapters.loadedMj) {

            this.initMJ();
        }
        if (PowerAdapters.loadedTesla) {

            this.initTesla();
        }
        if (PowerAdapters.loadedRf) {

            this.initRF();
        }

        this.feAdapter = new FEAdapter(this.battery);
        this.adapters.add(this.feAdapter);
    }

    @Optional.Method(modid = "buildcraftcore")
    private void initMJ () {

        this.mjAdapter = new MjAdapter(this.battery);
        this.adapters.add(this.mjAdapter);
    }

    @Optional.Method(modid = "tesla")
    private void initTesla () {

        this.teslaAdapter = new TeslaAdapter(this.battery);
        this.adapters.add(this.teslaAdapter);
    }

    @Optional.Method(modid = "redstoneflux")
    private void initRF () {

        this.rfAdapter = new RFAdapter(this.battery);
        this.adapters.add(this.rfAdapter);
    }

    @Override
    public void writeNBT (NBTTagCompound dataTag) {

        this.battery.write(dataTag);
    }

    @Override
    public void readNBT (NBTTagCompound dataTag) {

        this.battery.read(dataTag);
    }

    @Override
    public boolean hasCapability (Capability<?> capability, EnumFacing facing) {

        for (final IPowerAdapter adapter : this.adapters) {
            if (adapter.hasCapability(capability, facing)) {
                return true;
            }
        }

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability (Capability<T> capability, EnumFacing facing) {

        for (final IPowerAdapter adapter : this.adapters) {
            final T cap = adapter.getCapability(capability, facing);
            if (cap != null) {
                return cap;
            }
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public void onEntityUpdate () {

        if (!this.world.isRemote) {
            this.adapters.forEach(iPowerAdapter -> iPowerAdapter.distributePower(this.world, this.pos));
        }
    }

    @Override
    @Optional.Method(modid = "redstoneflux")
    public int receiveEnergy (EnumFacing from, int maxReceive, boolean simulate) {

        return (int) this.rfAdapter.addPower(maxReceive, simulate);
    }

    @Override
    @Optional.Method(modid = "redstoneflux")
    public int getEnergyStored (EnumFacing from) {

        return (int) this.rfAdapter.getLocalStored();
    }

    @Override
    @Optional.Method(modid = "redstoneflux")
    public int getMaxEnergyStored (EnumFacing from) {

        return (int) this.rfAdapter.getLocalCapacity();
    }

    @Override
    @Optional.Method(modid = "redstoneflux")
    public boolean canConnectEnergy (EnumFacing from) {

        return true;
    }

    @Override
    @Optional.Method(modid = "redstoneflux")
    public int extractEnergy (EnumFacing from, int maxExtract, boolean simulate) {

        return (int) this.rfAdapter.takePower(maxExtract, simulate);
    }

    public InternalBattery getBattery () {

        return this.battery;
    }
}
