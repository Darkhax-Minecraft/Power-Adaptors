package com.jarhax.poweradapters;

import net.minecraft.nbt.NBTTagCompound;

public class InternalBattery {

	private long stored;
	
	private long capacity;
	
	private long input;
	
	private long output;
	
	public InternalBattery(NBTTagCompound tag) {
		
		read(tag);
	}
	
	public InternalBattery(InternalBattery original) {
		
		this(original.getStored(), original.getCapacity(), original.getInput(), original.getOutput());
	}
	
	public InternalBattery(InternalBattery original, long capacity, long input, long output) {
		
		this(original.getStored(), capacity, input, output);
	}
	
	public InternalBattery(long capacity, long input, long output) {
		
		this(0, capacity, input, output);
	}
	
	public InternalBattery(long stored, long capacity, long input, long output) {
		
		this.stored = stored;
		this.capacity = capacity;
		this.input = input;
		this.output = output;
	}
	
	public void write(NBTTagCompound tag) {
		
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("Stored", this.getStored());
		data.setLong("Capacity", this.getCapacity());
		data.setLong("Input", this.getInput());
		data.setLong("Output", this.getOutput());
		tag.setTag("InternalBattery", data);
	}
	
	public void read(NBTTagCompound tag) {
		
		NBTTagCompound data = tag.getCompoundTag("InternalBatter");
		this.setStored(data.getLong("Stored"));
		this.setCapacity(data.getLong("Capacity"));
		this.setInput(data.getLong("Input"));
		this.setInput(data.getLong("Ouput"));
	}

	public void addPower(long power) {
		
		this.setStored(Math.min(this.getCapacity(), this.getStored() + power));
	}
	
	public long getStored() {
		return stored;
	}

	public void setStored(long stored) {
		this.stored = stored;
	}

	public long getCapacity() {
		return capacity;
	}

	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}

	public long getInput() {
		return input;
	}

	public void setInput(long input) {
		this.input = input;
	}

	public long getOutput() {
		return output;
	}

	public void setOutput(long output) {
		this.output = output;
	}
}
