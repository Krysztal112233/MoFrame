// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.buffstatus.types;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public final class BuffContextTypeObject implements BuffContextType<Map<String, BuffContextType<?>>> {
    public static BuffContextTypeObject of(String key, BuffContextType<?> value) {
        return new BuffContextTypeObject(ImmutableMap.of(key, value));
    }

    public static BuffContextTypeObject empty() {
        return new BuffContextTypeObject(ImmutableMap.of());
    }

    @Getter
    private final Map<String, BuffContextType<?>> value;
}
