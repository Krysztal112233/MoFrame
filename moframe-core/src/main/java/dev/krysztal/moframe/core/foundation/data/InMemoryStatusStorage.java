// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.foundation.data;

import io.vavr.control.Try;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;

/**
 * InMemoryStatsStorage is fast but will be cleaned after restart.
 */
public final class InMemoryStatusStorage implements StatusStorage {

    @Getter
    private static InMemoryStatusStorage instance;

    public static void _load() {
        instance = new InMemoryStatusStorage();
    }

    private final ConcurrentHashMap<String, TimedRecord<?>> inner;

    private InMemoryStatusStorage() {
        this.inner = new ConcurrentHashMap<>();
    }

    @Override
    public <T> TimedRecord<T> put(final String key, final T obj, final long expire) {
        final var inner = new TimedRecord<T>(obj, System.currentTimeMillis(), OptionalLong.of(expire));
        this.inner.put(key, inner);
        return inner;
    }

    @Override
    public boolean remove(final String key) {
        return Optional.ofNullable(this.inner.remove(key)).isPresent();
    }

    @Override
    public Collection<String> keys() {
        return this.inner.keySet();
    }

    @Override
    public boolean exist(final String key) {
        return this.inner.containsKey(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Try<TimedRecord<T>> read(final String key, final Class<T> type) {
        return Try.of(() -> {
            final TimedRecord<?> record = inner.get(key);
            if (record == null) {
                throw new NoSuchElementException("Key not found: " + key);
            }

            if (!type.isInstance(record.inner())) {
                throw new ClassCastException(
                        "Expected " + type.getName() + " but got " + record.inner().getClass().getName());
            }
            return (TimedRecord<T>) record;
        });
    }
}
