// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.attribute;

import dev.krysztal.moframe.core.Plugin;
import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExtraAttribute<P, C> {

    public static <P, C> ExtraAttribute<P, C> of(final NamespacedKey namespacedKey,
            final PersistentDataType<P, C> type) {
        return new ExtraAttribute<>(namespacedKey, type, Option.none());
    }

    public static <P, C> ExtraAttribute<P, C> of(final NamespacedKey namespacedKey, final PersistentDataType<P, C> type,
            final C defaultValue) {
        return new ExtraAttribute<>(namespacedKey, type, Option.of(defaultValue));
    }

    public static <P, C> ExtraAttribute<P, C> of(final String key, final PersistentDataType<P, C> type,
            final C defaultValue) {
        return new ExtraAttribute<P, C>(new NamespacedKey(Plugin.getPluginInstance(), key), type,
                Option.of(defaultValue));
    }

    public static <P, C> ExtraAttribute<P, C> of(final String key, final PersistentDataType<P, C> type) {
        return new ExtraAttribute<P, C>(new NamespacedKey(Plugin.getPluginInstance(), key), type, Option.none());
    }

    @Getter
    private final NamespacedKey namespacedKey;

    @Getter
    private final PersistentDataType<P, C> pdt;

    @Getter
    private final Option<C> defaultValue;
}
