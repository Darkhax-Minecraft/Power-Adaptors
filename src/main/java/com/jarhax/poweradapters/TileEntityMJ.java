package com.jarhax.poweradapters;


import buildcraft.api.mj.*;
import cofh.redstoneflux.api.*;
import com.jarhax.poweradapters.adapters.*;
import net.darkhax.bookshelf.block.tileentity.TileEntityBasicTickable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Optional;

import java.util.*;
import java.util.function.Consumer;

@Optional.Interface(iface = "cofh.redstoneflux.api.IEnergyReceiver", modid = "redstoneflux")
@Optional.Interface(iface = "cofh.redstoneflux.api.IEnergyProvider", modid = "redstoneflux")
public class TileEntityMJ extends TileEntityBasicTickable implements IEnergyReceiver, IEnergyProvider {
    
    private InternalBattery battery = new InternalBattery(5000, 500, 500);
    private List<IPowerAdapter> adapters = new ArrayList<>();
    private IPowerAdapter mjAdapter;
    private IPowerAdapter teslaAdapter;
    private IPowerAdapter rfAdapter;
    private IPowerAdapter feAdapter;
    
    
    public TileEntityMJ() {
        if(PowerAdapters.loadedMj) {
            mjAdapter = new MjAdapter(battery);
            adapters.add(mjAdapter);
        }
        if(PowerAdapters.loadedTesla) {
            teslaAdapter = new TeslaAdapter(battery);
            adapters.add(teslaAdapter);
        }
        if(PowerAdapters.loadedRf) {
            rfAdapter= new RFAdapter(battery);
            adapters.add(rfAdapter);
        }
        feAdapter = new FEAdapter(battery);
        adapters.add(feAdapter);
    }
    
    @Override
    public void writeNBT(NBTTagCompound dataTag) {
        
        battery.write(dataTag);
    }
    
    @Override
    public void readNBT(NBTTagCompound dataTag) {
        
        battery.read(dataTag);
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        for(IPowerAdapter adapter : adapters) {
            if(adapter.hasCapability(capability, facing)){
                return true;
            }
        }
        
       
        
        return super.hasCapability(capability, facing);
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
    
        for(IPowerAdapter adapter : adapters) {
            T cap = adapter.getCapability(capability, facing);
            if(cap !=null){
                return cap;
            }
        }
        
        return super.getCapability(capability, facing);
    }
    
    @Override
    public void onEntityUpdate() {
        
        if(!world.isRemote){
            adapters.forEach(iPowerAdapter -> iPowerAdapter.distributePower(world, pos));
        }
        
    }
    
    @Override
    @Optional.Method(modid = "redstoneflux")
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
        return (int) rfAdapter.addPower(maxReceive, simulate);
    }
    
    @Override
    @Optional.Method(modid = "redstoneflux")
    public int getEnergyStored(EnumFacing from) {
        return (int) rfAdapter.getLocalStored();
    }
    
    @Override
    @Optional.Method(modid = "redstoneflux")
    public int getMaxEnergyStored(EnumFacing from) {
        return (int) rfAdapter.getLocalCapacity();
    }
    
    @Override
    @Optional.Method(modid = "redstoneflux")
    public boolean canConnectEnergy(EnumFacing from) {
        return true;
    }
    
    @Override
    @Optional.Method(modid = "redstoneflux")
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
        return (int) rfAdapter.takePower(maxExtract, simulate);
    }
}
