package com.oierbravo.mechanicals.foundation.blockEntity.behaviour;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

public class DynamicCycleBehavior extends BlockEntityBehaviour {

	private int cycleTime;
	public static final BehaviourType<DynamicCycleBehavior> TYPE = new BehaviourType<>();
	public DynamicCycleBehaviorSpecifics specifics;
	private int prevRunningTicks;
	private int runningTicks;
	private boolean running;
	private boolean finished;



	public <T extends SmartBlockEntity & DynamicCycleBehaviorSpecifics> DynamicCycleBehavior(T te) {
		super(te);
		this.specifics = te;
	}

	@Override
	public void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
		running = compound.getBoolean("Running");
		finished = compound.getBoolean("Finished");
		prevRunningTicks = runningTicks = compound.getInt("Ticks");
		cycleTime = compound.getInt("CycleTime");
		super.read(compound,registries, clientPacket);
	}

	@Override
	public void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
		compound.putBoolean("Running", running);
		compound.putBoolean("Finished", finished);
		compound.putInt("Ticks", runningTicks);
		compound.putInt("CycleTime", cycleTime);
		super.write(compound, registries, clientPacket);
	}

	public void start() {
		running = true;
		prevRunningTicks = 0;
		runningTicks = 0;
		cycleTime = specifics.getProcessingTime();
		specifics.cycleStart();
		blockEntity.sendData();

	}

	public void stop() {
		running = false;
		prevRunningTicks = 0;
		runningTicks = 0;
		cycleTime = 1;
		specifics.cycleStop();
		blockEntity.sendData();

	}

	@Override
	public BehaviourType<?> getType() {
		return TYPE;
	}

	@Override
	public void tick() {
		super.tick();

		Level level = getWorld();
		if (!running || level == null) {
			if (level != null && !level.isClientSide) {

				if (specifics.getKineticSpeed() == 0)
					return;

				if (specifics.tryProcess( true))
					start();
			}
			return;
		}


		if (level.isClientSide && runningTicks == -cycleTime) {
			prevRunningTicks = cycleTime;
			return;
		}

		if (runningTicks >= cycleTime && specifics.getKineticSpeed() != 0) {
			apply();
			specifics.playCompletionSound();
			if (!level.isClientSide)
				blockEntity.sendData();
		}

		if (!level.isClientSide && runningTicks >= cycleTime) {
			finished = true;
			running = false;
			specifics.onOperationCompleted();
			blockEntity.sendData();
			return;
		}

		prevRunningTicks = runningTicks;
		runningTicks += getRunningTickSpeed();
		if (level.isClientSide){
			specifics.playRunningSound();
			specifics.showParticles();
		}
		if (prevRunningTicks < cycleTime && runningTicks >= cycleTime) {
			runningTicks = cycleTime;
			// Pause the ticks until a packet is received
			if (level.isClientSide && !blockEntity.isVirtual()){
				runningTicks = -(cycleTime);
			}
		}
	}

	public float getProgress(float partialTicks){
		if (!running)
			return 0;
		int runningTicks = Math.abs(this.runningTicks);
		float ticks = Mth.lerp(partialTicks, prevRunningTicks, runningTicks);
		return ticks/ cycleTime * 100;
	}


	protected void apply() {
		Level level = getWorld();

		if (level.isClientSide)
			return;

		if (specifics.tryProcess(false))
			blockEntity.sendData();
	}

	public int getRunningTickSpeed() {
		float speed = specifics.getKineticSpeed();
		if (speed == 0)
			return 0;
		return (int) Mth.lerp(Mth.clamp(Math.abs(speed) / 512f, 0, 1), 1, 60);
	}
	public boolean isRunning(){
		return running;
	}
	public int getTotalProgressPercent() {
		return Mth.clamp(runningTicks * 100 / cycleTime, 0,100);
	}
	public int getCycleTime(){
		return cycleTime;
	}

	public int getPrevRunningTicks() {
		return prevRunningTicks;
	}
	public int getRunningTicks() {
		return runningTicks;
	}

	public int getProgressPercent() {
		if(!running)
			return 0;
		return Mth.clamp(prevRunningTicks * 100 / (getCycleTime()), 0,100);
	}
	public float getProgressPercentFloat() {
		if(!running)
			return 0;
		return (float) prevRunningTicks / getCycleTime();
	}

	public float getProcessingRemainingPercentFloat() {
		if(!running)
			return 1;
		return 1 - (float) (getCycleTime() - prevRunningTicks) / getCycleTime();
	}

	public interface DynamicCycleBehaviorSpecifics {
		default void cycleStart(){};
		default void cycleStop(){};

		default void onOperationCompleted(){};

		default void playRunningSound(){};
		default void showParticles(){};
		default void playCompletionSound(){};
		float getKineticSpeed();
		boolean tryProcess(boolean simulate);
		int getProcessingTime();
	}
}
