package com.jarhax.poweradapters;

import net.darkhax.bookshelf.block.BlockTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBasicMJ extends BlockTileEntity {

	protected BlockBasicMJ() {
		
		super(Material.IRON);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		return new TileEntityMJ();
	}
}
