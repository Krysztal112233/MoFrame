// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.listener;

import dev.krysztal.moframe.core.attribute.ExtraAttributeManager;
import dev.krysztal.moframe.core.attribute.ExtraAttributes;
import java.util.stream.Stream;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class InitializeDefaultExtraAttribute implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void initialListener(final PlayerJoinEvent event) {
        final var manager = ExtraAttributeManager.of(event.getPlayer());

        Stream.of(ExtraAttributes.ENTITY_PHYSIC_DEF_RATE, ExtraAttributes.ENTITY_MAGIC_DEF_RATE,
                ExtraAttributes.ENTITY_PHYSIC_MULTIPLIER, ExtraAttributes.ENTITY_MAGIC_ATK_MULTIPLIER,
                ExtraAttributes.ENTITY_HEAL_MULTIPLIER).forEach(manager::initial);
    }
}
