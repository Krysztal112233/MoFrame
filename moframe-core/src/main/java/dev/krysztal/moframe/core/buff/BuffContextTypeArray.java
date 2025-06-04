// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.buff;

import com.google.common.collect.ImmutableList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public final class BuffContextTypeArray implements BuffContextType<List<BuffContextType<?>>> {

    protected static final BuffContextTypeArray EMPTY = new BuffContextTypeArray(ImmutableList.of());

    @Getter
    private final List<BuffContextType<?>> value;
}
