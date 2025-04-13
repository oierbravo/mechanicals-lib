package com.oierbravo.mechanicals.foundation.blockEntity.behaviour;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

public class CycleBehavior extends BlockEntityBehaviour {

	private int cycleTime;
	private boolean actuateHalfcycle;
	public static final BehaviourType<CycleBehavior> TYPE = new BehaviourType<>();
	public CycleBehaviourSpecifics specifics;
	private int prevRunningTicks;
	private int runningTicks;
	private boolean running;
	private boolean finished;
	private int cycleDivider;
	private int numCycles;
	private int currentCycle;


	public <T extends SmartBlockEntity & CycleBehaviourSpecifics> CycleBehavior(T te, int pCycle, boolean pActuateHalfCycle) {
		super(te);
		this.specifics = te;
		cycleTime = pCycle;
		actuateHalfcycle = pActuateHalfCycle;
		numCycles = 0;
		cycleDivider = (actuateHalfcycle) ? 2 : 1;
		currentCycle = 0;
	}

	@Override
	public void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
		running = compound.getBoolean("Running");
		finished = compound.getBoolean("Finished");
		prevRunningTicks = runningTicks = compound.getInt("Ticks");
		currentCycle = compound.getInt("CurrentCycle");
		cycleTime = compound.getInt("CycleTime");
		numCycles = compound.getInt("NumCycles");
		super.read(compound,registries, clientPacket);
	}

	@Override
	public void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
		compound.putBoolean("Running", running);
		compound.putBoolean("Finished", finished);
		compound.putInt("Ticks", runningTicks);
		compound.putInt("CurrentCycle", currentCycle);
		compound.putInt("CycleTime", cycleTime);
		compound.putInt("NumCycles", numCycles);
		super.write(compound, registries, clientPacket);
	}

	public void start() {
		running = true;
		prevRunningTicks = 0;
		runningTicks = 0;
		currentCycle = 0;
		numCycles = specifics.getCycles();
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


		if (level.isClientSide && runningTicks == -cycleTime / cycleDivider) {
			prevRunningTicks = cycleTime / cycleDivider;
			return;
		}

		if (runningTicks == cycleTime / cycleDivider && specifics.getKineticSpeed() != 0) {
			apply();
			if (!level.isClientSide)
				blockEntity.sendData();
		}

		if (!level.isClientSide && runningTicks > cycleTime) {
			specifics.onCycleCompleted();
			currentCycle++;
			if(currentCycle == numCycles){
				finished = true;
				running = false;
				specifics.onOperationCompletd();
				specifics.playCompletionSound();
			}
			blockEntity.sendData();
			return;
		}

		prevRunningTicks = runningTicks;
		runningTicks += getRunningTickSpeed();

		if (level.isClientSide){
			specifics.playSound();
			specifics.showParticles();
		}

		if (prevRunningTicks < cycleTime / cycleDivider && runningTicks >= cycleTime / cycleDivider) {
			runningTicks = cycleTime / cycleDivider;
			// Pause the ticks until a packet is received
			if (level.isClientSide && !blockEntity.isVirtual())
				runningTicks = -(cycleTime / cycleDivider);
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
		return Mth.clamp(prevRunningTicks * 100 / (cycleTime /cycleDivider) * numCycles, 0,100);
	}
	public int getCycleProgressPercent() {
		return Mth.clamp(prevRunningTicks * 100 / (cycleTime /cycleDivider), 0,100);
	}
	public int getCycleTime(){
		return cycleTime;
	}
	public int getCurrentCycle(){
		return currentCycle;
	}

	public int getPrevRunningTicks() {
		return prevRunningTicks;
	}
	public int getRunningTicks() {
		return runningTicks;
	}
	public interface CycleBehaviourSpecifics {

		default void onCycleCompleted(){};
		default void onOperationCompletd(){};

		default void playSound(){};
		default void showParticles(){};
		default void playCompletionSound(){};

		int getCycles();
		float getKineticSpeed();
		boolean tryProcess(boolean simulate);
	}

}
