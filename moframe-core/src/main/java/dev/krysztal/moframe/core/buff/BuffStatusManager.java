// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.buff;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Entity;

public enum BuffStatusManager {
    INSTANCE;

    public static BuffStatusStorage of(final Entity entity) {
        return INSTANCE.managed.computeIfAbsent(entity.getUniqueId(), key -> new BuffStatusStorage());
    }

    public static BuffStatusStorage of(final UUID uuid) {
        return INSTANCE.managed.computeIfAbsent(uuid, key -> new BuffStatusStorage());
    }

    private final ConcurrentHashMap<UUID, BuffStatusStorage> managed = new ConcurrentHashMap<>();
}
