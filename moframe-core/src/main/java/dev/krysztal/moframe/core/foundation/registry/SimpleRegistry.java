// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.foundation.registry;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.annotations.Nullable;

public class SimpleRegistry<T> implements Registry<T> {
    private final Map<Identifier, RegistryObject<T>> inner = new Object2ObjectLinkedOpenHashMap();
    private final AtomicBoolean freezed = new AtomicBoolean(false);

    @Override
    public Optional<RegistryObject<T>> get(final Identifier identifier) {
        return Optional.ofNullable(this.inner.get(identifier));
    }

    @Override
    public @Nullable RegistryObject<T> remove(final Identifier identifier) {
        return this.inner.remove(identifier);
    }

    @Override
    public Collection<RegistryObject<T>> unmodifiableView() {
        return Collections.unmodifiableCollection(this.inner.values());
    }

    @Override
    public RegistryObject<T> register(final Identifier identifier, final T obj) {
        final var registryObject = new RegistryObject<>(identifier, obj);
        this.inner.put(identifier, registryObject);
        return registryObject;
    }

    public boolean isFrozen() {
        return this.freezed.get();
    }

    public void used() {
        this.freezed.set(true);
    }
}
