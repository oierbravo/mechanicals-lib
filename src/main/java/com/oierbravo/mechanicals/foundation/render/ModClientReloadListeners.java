package com.oierbravo.mechanicals.foundation.render;

import com.oierbravo.mechanicals.Mechanicals;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

/**
 * Clears {@link PartialBlockRenderer}'s per-block sprite cache whenever client resources reload, so the
 * cached atlas UVs don't survive an atlas re-stitch (resource pack swap / F3+T) and draw a stale texture.
 */
@EventBusSubscriber(modid = Mechanicals.MODID, value = Dist.CLIENT)
public final class ModClientReloadListeners {

    private ModClientReloadListeners() {}

    @net.neoforged.bus.api.SubscribeEvent
    public static void onRegisterReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener((ResourceManagerReloadListener) (ResourceManager rm) -> PartialBlockRenderer.clearCache());
    }
}
