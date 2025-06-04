// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.electronwill.nightconfig.core.serde.ObjectSerializer;
import com.electronwill.nightconfig.toml.TomlWriter;
import dev.krysztal.moframe.core.config.MoFrameConfig;
import dev.krysztal.moframe.core.foundation.ExecutorUtils;
import dev.krysztal.moframe.core.foundation.da.DatabaseAccessor;
import dev.krysztal.moframe.core.foundation.da.RedisAccessor;
import io.vavr.control.Try;
import java.nio.file.Files;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Plugin extends JavaPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger("MoFrameCore/" + Plugin.class.getSimpleName());

    @Getter
    private static JavaPlugin pluginInstance;

    @Override
    public void onLoad() {
        pluginInstance = this;
        this.saveDefaultConfig();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onDisable() {
        ExecutorUtils._unload();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void saveDefaultConfig() {
        final var pluginDataDirectory = this.getDataPath().toAbsolutePath();
        if (!Files.exists(pluginDataDirectory)) {
            Try.of(() -> Files.createDirectories(pluginDataDirectory));
        }

        final var filePath = pluginDataDirectory.resolve("config.toml").normalize();
        final boolean exist = filePath.toFile().exists();
        LOGGER.info("checking configuration file exist: {}", exist);

        if (!exist) {
            final var config = CommentedConfig.inMemory();
            ObjectSerializer.builder().build().serializeFields(MoFrameConfig.defaultConfig(), config);
            new TomlWriter().write(config, filePath.toFile(), WritingMode.REPLACE_ATOMIC);
            LOGGER.warn("wrote default configuration, please modify it for your server.");
        }

        MoFrameConfig._load(filePath.toString());
        LOGGER.info("loaded configuration from: {}", filePath.toString());
    }

    @SuppressWarnings("deprecation")
    void initSingleInstance() {
        ExecutorUtils._load();
        DatabaseAccessor._load();
        RedisAccessor._load();
    }
}
