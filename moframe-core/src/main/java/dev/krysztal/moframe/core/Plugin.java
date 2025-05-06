// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core;

import dev.krysztal.moframe.core.foundation.ExecutorUtils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

    @Getter
    private static JavaPlugin pluginInstance;

    @Override
    public void onLoad() {
        pluginInstance = this;
    }

    @Override
    public void onEnable() {

    }

    @Override
    @SuppressWarnings("deprecation")
    public void onDisable() {
        ExecutorUtils._unload();
    }
}
