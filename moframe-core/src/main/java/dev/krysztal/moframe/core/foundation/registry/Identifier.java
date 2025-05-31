// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.foundation.registry;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Identifier {
    @Getter
    private final String namespace;

    @Getter
    private final String key;

    public static Identifier of(final String identifier) {
        final var result = identifier.split("$");
        return result.length >= 2 ? new Identifier(result[0], result[1]) : new Identifier("MoFrame", result[0]);
    }

    public static Identifier of(final String namespace, final String key) {
        return new Identifier(namespace, key);
    }

}
