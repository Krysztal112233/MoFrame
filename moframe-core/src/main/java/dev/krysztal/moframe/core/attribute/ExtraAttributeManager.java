// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.attribute;

import dev.krysztal.moframe.core.foundation.pdc.ItemStackPersistentDataHolder;
import io.vavr.control.Option;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataHolder;

public class ExtraAttributeManager {
    public static ExtraAttributeManager of(final PersistentDataHolder pdh) {
        return new ExtraAttributeManager(pdh);
    }

    public static ExtraAttributeManager of(final ItemStack itemStack) {
        return new ExtraAttributeManager(ItemStackPersistentDataHolder.of(itemStack));
    }

    private final PersistentDataHolder pdh;

    private ExtraAttributeManager(final PersistentDataHolder pdh) {
        this.pdh = pdh;
    }

    public <P, C> Option<C> read(final ExtraAttribute<P, C> attribute) {
        return Option.of(this.pdh.getPersistentDataContainer().get(attribute.getNamespacedKey(), attribute.getPdt()));
    }

    public <P, C> void write(final ExtraAttribute<P, C> attribute, final C data) {
        this.pdh.getPersistentDataContainer().set(attribute.getNamespacedKey(), attribute.getPdt(), data);
    }

    public <P, C> void initial(final ExtraAttribute<P, C> attribute) {
        attribute.getDefaultValue().peek(data -> {
            if (this.read(attribute).isEmpty()) {
                this.write(attribute, data);
            }
        });
    }
}
