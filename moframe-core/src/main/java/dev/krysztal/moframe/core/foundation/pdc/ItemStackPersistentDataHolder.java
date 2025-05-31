// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.foundation.pdc;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ItemStackPersistentDataHolder implements PersistentDataHolder {

    public static ItemStackPersistentDataHolder of(final ItemStack itemStack) {
        return new ItemStackPersistentDataHolder(new ItemStackPersistentDataContainer(itemStack));
    }

    private final ItemStackPersistentDataContainer itemStackPersistentDataContainer;

    @Override
    public @NotNull PersistentDataContainer getPersistentDataContainer() {
        return itemStackPersistentDataContainer;
    }
}
