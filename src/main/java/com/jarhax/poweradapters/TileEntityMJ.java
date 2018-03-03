package com.jarhax.poweradapters;


import buildcraft.api.mj.*;
import cofh.redstoneflux.api.IEnergyReceiver;
import com.jarhax.poweradapters.adapters.*;
import net.darkhax.bookshelf.block.tileentity.TileEntityBasicTickable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Optional;

import java.util.*;

@Optional.Interface(iface = "cofh.redstoneflux.api.IEnergyReceiver", modid = "redstoneflux")
public class TileEntityMJ extends TileEntityBasicTickable implements IEnergyReceiver {
    
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
        
        for(EnumFacing dir : EnumFacing.values()) {
            
            TileEntity tile = world.getTileEntity(this.pos.offset(dir));
            
            if(tile != null && tile.hasCapability(MjAPI.CAP_RECEIVER, dir.getOpposite())) {
                
                IMjReceiver reciever = tile.getCapability(MjAPI.CAP_RECEIVER, dir.getOpposite());
                
                if(reciever.canReceive()) {
                    
                    reciever.receivePower(this.getCapability(MjAPI.CAP_PASSIVE_PROVIDER, dir).extractPower(0, 100, false), false);
                    return;
                }
            }
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
}
