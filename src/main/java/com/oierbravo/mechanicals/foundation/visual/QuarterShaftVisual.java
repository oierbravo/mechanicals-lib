package com.oierbravo.mechanicals.foundation.visual;

import com.oierbravo.mechanicals.MechanicalPartials;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.core.Direction;

public class QuarterShaftVisual<T extends KineticBlockEntity> extends SingleAxisRotatingVisual<T> {
    public QuarterShaftVisual(VisualizationContext context, T blockEntity, float partialTick, Direction direction) {
        super(context, blockEntity, partialTick, direction, Models.partial(MechanicalPartials.SHAFT_QUARTER, direction.getOpposite()));
    }
    public static <T extends KineticBlockEntity> QuarterShaftVisual<T> bottom(VisualizationContext context, T blockEntity, float partialTick) {
        return new QuarterShaftVisual<>(context, blockEntity, partialTick, Direction.DOWN);
    }
    public static <T extends KineticBlockEntity> QuarterShaftVisual<T> top(VisualizationContext context, T blockEntity, float partialTick) {
        return new QuarterShaftVisual<>(context, blockEntity, partialTick, Direction.UP);
    }
}