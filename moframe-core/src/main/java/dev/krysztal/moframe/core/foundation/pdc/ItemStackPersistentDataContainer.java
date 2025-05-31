// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.foundation.pdc;

import io.papermc.paper.persistence.PersistentDataContainerView;
import java.io.IOException;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemStackPersistentDataContainer implements PersistentDataContainer {
    public static ItemStackPersistentDataContainer of(final ItemStack itemStack) {
        return new ItemStackPersistentDataContainer(itemStack);
    }

    private final ItemStack itemStack;

    @Override
    public <P, C> boolean has(final NamespacedKey key, final PersistentDataType<P, C> type) {
        return this.getUnmodifiableView().has(key, type);
    }

    @Override
    public boolean has(final NamespacedKey key) {
        return this.getUnmodifiableView().has(key);
    }

    @Override
    public <P, C> @Nullable C get(final NamespacedKey key, final PersistentDataType<P, C> type) {
        return this.getUnmodifiableView().get(key, type);
    }

    @Override
    public <P, C> C getOrDefault(final NamespacedKey key, final PersistentDataType<P, C> type, final C defaultValue) {
        return this.getUnmodifiableView().getOrDefault(key, type, defaultValue);
    }

    @Override
    public Set<NamespacedKey> getKeys() {
        return this.getUnmodifiableView().getKeys();
    }

    @Override
    public boolean isEmpty() {
        return this.getUnmodifiableView().isEmpty();
    }

    @Override
    public void copyTo(final PersistentDataContainer other, final boolean replace) {
        this.getUnmodifiableView().copyTo(other, replace);
    }

    @Override
    public PersistentDataAdapterContext getAdapterContext() {
        return this.getUnmodifiableView().getAdapterContext();
    }

    @Override
    public byte[] serializeToBytes() throws IOException {
        return this.getUnmodifiableView().serializeToBytes();
    }

    @Override
    public <P, C> void set(@NotNull final NamespacedKey key, @NotNull final PersistentDataType<P, C> type,
            @NotNull final C value) {
        this.itemStack.editPersistentDataContainer(c -> c.set(key, type, value));
    }

    @Override
    public void remove(@NotNull final NamespacedKey key) {
        this.itemStack.editPersistentDataContainer(c -> remove(key));
    }

    @Override
    public void readFromBytes(final byte @NotNull [] bytes, final boolean clear) throws IOException {
        this.itemStack.editPersistentDataContainer(c -> {
            try {
                c.readFromBytes(bytes);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        });
    }

    private PersistentDataContainerView getUnmodifiableView() {
        return this.itemStack.getPersistentDataContainer();
    }
}
