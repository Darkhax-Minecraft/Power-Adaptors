package com.jarhax.poweradapters;

import javax.annotation.Nullable;

import buildcraft.api.mj.IMjReceiver;
import buildcraft.api.mj.MjAPI;
import net.darkhax.bookshelf.block.tileentity.TileEntityBasic;
import net.darkhax.bookshelf.block.tileentity.TileEntityBasicTickable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityMJ extends TileEntityBasicTickable {

	@Override
	public void writeNBT(NBTTagCompound dataTag) {
		
	}

	@Override
	public void readNBT(NBTTagCompound dataTag) {
		
	}
	
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
    	
    	if (capability == MjAPI.CAP_PASSIVE_PROVIDER) {
    		
    		return true;
    	}
    	
    	return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

    	if (capability == MjAPI.CAP_PASSIVE_PROVIDER) {
    		
    		return (T) new MJProducer();
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
					
					System.out.println("hi");
					reciever.receivePower(this.getCapability(MjAPI.CAP_PASSIVE_PROVIDER, dir).extractPower(0, 100, false), false);
					return;
				}
			}
		}
	}
}
