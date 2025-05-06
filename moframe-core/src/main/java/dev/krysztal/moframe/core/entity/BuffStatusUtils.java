// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.entity;

import dev.krysztal.moframe.core.Plugin;
import dev.krysztal.moframe.core.buffstatus.BuffStatus;
import dev.krysztal.moframe.core.foundation.data.InMemoryStatusStorage;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import one.util.streamex.StreamEx;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

public class BuffStatusUtils {
    public static BuffStatusUtils of(final Entity entity) {
        return new BuffStatusUtils(entity);
    }

    public static Optional<BuffStatusUtils> of(final UUID uuid) {
        return Optional.ofNullable(Bukkit.getEntity(uuid)).map(BuffStatusUtils::new);
    }

    private BuffStatusUtils(final Entity entity) {
    }

    public Future<Set<String>> status() {
        final var future = new CompletableFuture<Set<String>>();
        Bukkit.getScheduler().runTaskAsynchronously(Plugin.getPluginInstance(), () -> {
            final var result = StreamEx.of(InMemoryStatusStorage.getInstance().keys()).parallel()
                    .filter(BuffStatus::isStatus).toSet();
            future.complete(result);
        });
        return future;
    }
}
