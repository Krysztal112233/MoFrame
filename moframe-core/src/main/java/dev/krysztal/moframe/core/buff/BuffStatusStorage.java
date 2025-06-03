// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.buff;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class BuffStatusStorage {
    public record StoredBuffContext(long at, BuffContext buffContext) {
        public boolean isPermanet() {
            return at <= 0;
        }
    }

    private final ConcurrentHashMap<String, Long> time = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, BuffContext> ctx = new ConcurrentHashMap<>();

    public Optional<StoredBuffContext> get(final String key) {
        final var time = Optional.ofNullable(this.time.get(key)).orElse(0L);
        final var ctx = Optional.ofNullable(this.ctx.get(key));

        return ctx.map(it -> new StoredBuffContext(time, it));
    }

    public boolean isEmpty() {
        return this.ctx.isEmpty();
    }
}
