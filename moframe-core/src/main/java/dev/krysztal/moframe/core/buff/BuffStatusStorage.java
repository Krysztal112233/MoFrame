// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.buff;

import dev.krysztal.moframe.core.PluginRegistry;
import dev.krysztal.moframe.core.foundation.registry.Identifier;
import io.vavr.control.Option;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BuffStatusStorage {
    public record StoredBuffContext(long before, BuffContext buffContext) {
        public boolean isPermanet() {
            return before <= 0;
        }

        public boolean isOutdated() {
            return !this.isPermanet() && System.currentTimeMillis() > this.before;
        }
    }

    private final UUID belong;

    private final ConcurrentHashMap<Identifier, Long> time = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Identifier, BuffContext> ctx = new ConcurrentHashMap<>();

    protected BuffStatusStorage(final UUID uuid) {
        this.belong = uuid;
    }

    public Option<StoredBuffContext> get(final Identifier key) {
        final var time = Option.of(this.time.get(key)).getOrElse(0L);
        final var ctx = Option.of(this.ctx.get(key));

        return ctx.map(it -> new StoredBuffContext(time, it));
    }

    public void put(final Identifier key, final BuffContext ctx) {
        this.put(key, ctx, 0);
    }

    public void put(final Identifier key, final BuffContext ctx, final long before) {
        this.ctx.put(key, ctx);
        this.time.put(key, before);
    }

    public void purge(final Identifier key) {
        this.drop(key).peek(s -> {
            PluginRegistry.BUFF_STATUS.get(key).peek(buff -> buff.getObj().onRemove(this.belong, s.buffContext));
        });
    }

    public Option<StoredBuffContext> drop(final Identifier key) {
        final var time = Option.of(this.time.remove(key)).getOrElse(0L);
        final var ctx = Option.of(this.ctx.remove(key));

        return ctx.map(it -> new StoredBuffContext(time, it));
    }

    public boolean isEmpty() {
        return this.ctx.isEmpty();
    }
}
