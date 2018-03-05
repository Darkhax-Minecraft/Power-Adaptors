package com.jarhax.poweradapters;

import net.minecraft.nbt.NBTTagCompound;

public class InternalBattery {

    public static final String TAG_STORED = "Stored";
    public static final String TAG_CAPACITY = "Capacity";
    public static final String TAG_INPUT = "Input";
    public static final String TAG_OUTPUT = "Output";
    public static final String TAG_BATTERY = "InternalBattery";

    private long stored;

    private long capacity;

    private long input;

    private long output;

    public InternalBattery (NBTTagCompound tag) {

        this.read(tag);
    }

    public InternalBattery (InternalBattery original) {

        this(original.getStored(), original.getCapacity(), original.getInput(), original.getOutput());
    }

    public InternalBattery (InternalBattery original, long capacity, long input, long output) {

        this(original.getStored(), capacity, input, output);
    }

    public InternalBattery (long capacity, long input, long output) {

        this(0, capacity, input, output);
    }

    public InternalBattery (long stored, long capacity, long input, long output) {

        this.stored = stored;
        this.capacity = capacity;
        this.input = input;
        this.output = output;
    }

    public void write (NBTTagCompound tag) {

        final NBTTagCompound data = new NBTTagCompound();
        data.setLong(TAG_STORED, this.getStored());
        data.setLong(TAG_CAPACITY, this.getCapacity());
        data.setLong(TAG_INPUT, this.getInput());
        data.setLong(TAG_OUTPUT, this.getOutput());
        tag.setTag(TAG_BATTERY, data);
    }

    public void read (NBTTagCompound tag) {

        final NBTTagCompound data = tag.getCompoundTag(TAG_BATTERY);
        this.setStored(data.getLong(TAG_STORED));
        this.setCapacity(data.getLong(TAG_CAPACITY));
        this.setInput(data.getLong(TAG_INPUT));
        this.setInput(data.getLong(TAG_OUTPUT));
    }

    public void takePower (long power) {

        this.setStored(Math.max(0, this.getStored() - power));
    }

    public void addPower (long power) {

        this.setStored(Math.min(this.getCapacity(), this.getStored() + power));
    }

    public long getStored () {

        return this.stored;
    }

    public void setStored (long stored) {

        this.stored = stored;
    }

    public long getCapacity () {

        return this.capacity;
    }

    public void setCapacity (long capacity) {

        this.capacity = capacity;
    }

    public long getInput () {

        return this.input;
    }

    public void setInput (long input) {

        this.input = input;
    }

    public long getOutput () {

        return this.output;
    }

    public void setOutput (long output) {

        this.output = output;
    }
}
