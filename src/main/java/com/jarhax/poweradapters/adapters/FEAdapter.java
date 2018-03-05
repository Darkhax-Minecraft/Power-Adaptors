package com.jarhax.poweradapters.adapters;

import com.jarhax.poweradapters.InternalBattery;
import com.jarhax.poweradapters.adapters.caps.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.*;

public class FEAdapter extends IPowerAdapter {
    private EnergyStorageAdapter container;
    
	public FEAdapter(InternalBattery battery) {
		
		super(battery);
		container = new EnergyStorageAdapter((int) battery.getCapacity(), this);
	}

	@Override
	public long getExchangeRate() {

		// Tesla = 25 internal power
		return 25;
	}
    
    @Override
    public void distributePower(World world, BlockPos pos) {
        for(EnumFacing facing : EnumFacing.VALUES) {
            if(world.getTileEntity(pos.offset(facing))!=null && world.getTileEntity(pos.offset(facing)).hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())){
                IEnergyStorage storage = world.getTileEntity(pos.offset(facing)).getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
                container.extractEnergy(storage.receiveEnergy(container.extractEnergy((int) getLocalOutput(), true), false), false);
            }
        }
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY ) {
            return true;
        }
        return false;
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY ) {
            return (T) container;
        }
        return null;
    }
}