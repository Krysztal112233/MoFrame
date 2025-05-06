// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.foundation;

import com.google.gson.Gson;
import lombok.Getter;

public class SGson {
    @Getter
    private static SGson instance;

    @Deprecated
    public static void _load() {
        instance = new SGson();
    }

    @Getter
    private final Gson gson;

    private SGson() {
        this.gson = new Gson();
    }
}
