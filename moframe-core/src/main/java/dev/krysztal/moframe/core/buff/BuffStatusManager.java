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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Entity;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BuffStatusManager {
    public static BuffStatusManager of(final Entity entity) {
        return new BuffStatusManager(entity.getUniqueId());
    }

    public static BuffStatusManager of(final UUID uuid) {
        return new BuffStatusManager(uuid);
    }

    @Getter
    private final UUID uuid;

}
