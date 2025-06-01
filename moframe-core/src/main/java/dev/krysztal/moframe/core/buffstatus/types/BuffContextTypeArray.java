// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.buffstatus.types;

import com.google.common.collect.ImmutableList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public final class BuffContextTypeArray implements BuffContextType<List<BuffContextType<?>>> {
    public static BuffContextTypeArray of(List<BuffContextType<?>> list) {
        return new BuffContextTypeArray(ImmutableList.copyOf(list));
    }

    public static BuffContextTypeArray of(BuffContextType<?>... ele) {
        return new BuffContextTypeArray(ImmutableList.copyOf(ele));
    }

    public static BuffContextTypeArray empty() {
        return new BuffContextTypeArray(ImmutableList.of());
    }

    @Getter
    private final List<BuffContextType<?>> value;
}
