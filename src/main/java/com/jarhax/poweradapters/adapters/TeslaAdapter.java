package com.jarhax.poweradapters.adapters;

import com.jarhax.poweradapters.InternalBattery;
import com.jarhax.poweradapters.adapters.caps.BaseContainerTesla;
import net.darkhax.tesla.api.implementation.BaseTeslaContainer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public class TeslaAdapter extends IPowerAdapter {
    
    private BaseContainerTesla container;
    
    public TeslaAdapter(InternalBattery battery) {
        
        super(battery);
        container = new BaseContainerTesla(this);
    }
    
    @Override
    public long getExchangeRate() {
        
        // Tesla = 25 internal power
        return 25;
    }
    
    @Override
    public void distributePower(World world, BlockPos pos) {
        for(EnumFacing dir : EnumFacing.VALUES) {
            TileEntity tile = world.getTileEntity(pos.offset(dir));
            if(tile != null)
                if(tile.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, dir.getOpposite())) {
                    BaseTeslaContainer cont = (BaseTeslaContainer) tile.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, dir.getOpposite());
                    container.takePower(cont.givePower(container.takePower(container.getOutputRate(), true), false), false);
                }
        }
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_HOLDER) {
            return true;
        }
        return false;
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_HOLDER) {
            return (T) container;
        }
        return null;
    }
    
}