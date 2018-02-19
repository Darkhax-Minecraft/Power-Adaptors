package com.jarhax.poweradapters;

import javax.annotation.Nullable;

import com.jarhax.poweradapters.adapters.MjAdapter;

import buildcraft.api.mj.IMjReceiver;
import buildcraft.api.mj.MjAPI;
import net.darkhax.bookshelf.block.tileentity.TileEntityBasic;
import net.darkhax.bookshelf.block.tileentity.TileEntityBasicTickable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityMJ extends TileEntityBasicTickable {

	private InternalBattery battery = new InternalBattery(5000, 500, 500);
	private MjAdapter mjAdapter = new MjAdapter(battery);
	
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
    	
    	if (capability == MjAPI.CAP_PASSIVE_PROVIDER || capability == MjAPI.CAP_CONNECTOR) {
    		
    		return true;
    	}
    	
    	return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

    	if (capability == MjAPI.CAP_PASSIVE_PROVIDER || capability == MjAPI.CAP_CONNECTOR) {
    		
    		return (T) this.mjAdapter;
    	}
    	
    	return super.getCapability(capability, facing);
    }

	@Override
	public void onEntityUpdate() {

		for (EnumFacing dir : EnumFacing.values()) {
			
			TileEntity tile = world.getTileEntity(this.pos.offset(dir));
			
			if (tile != null && tile.hasCapability(MjAPI.CAP_RECEIVER, dir.getOpposite())) {
				
				IMjReceiver reciever = tile.getCapability(MjAPI.CAP_RECEIVER, dir.getOpposite());
				
				if (reciever.canReceive()) {
					
					reciever.receivePower(this.getCapability(MjAPI.CAP_PASSIVE_PROVIDER, dir).extractPower(0, 100, false), false);
					return;
				}
			}
		}
	}
}
