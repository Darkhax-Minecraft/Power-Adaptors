package com.jarhax.poweradapters.adapters;

import cofh.redstoneflux.api.IEnergyReceiver;
import com.jarhax.poweradapters.InternalBattery;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.*;

public class RFAdapter extends IPowerAdapter{
	
	public RFAdapter(InternalBattery battery) {
		
		super(battery);
	}

	@Override
	public long getExchangeRate() {

		// Tesla = 25 internal power
		return 25;
	}
    
    @Override
    public void distributePower(World world, BlockPos pos) {
        for(EnumFacing facing : EnumFacing.VALUES) {
            if(world.getTileEntity(pos.offset(facing))!=null && world.getTileEntity(pos.offset(facing)) instanceof IEnergyReceiver){
                IEnergyReceiver rec = (IEnergyReceiver) world.getTileEntity(pos.offset(facing));
                takePower(rec.receiveEnergy(facing, (int) takePower(getLocalOutput(), true), false), false);
    
            }
        }
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return false;
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return null;
    }
    
}