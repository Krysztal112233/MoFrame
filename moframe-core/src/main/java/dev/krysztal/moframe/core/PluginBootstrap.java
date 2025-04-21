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
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginBootstrap implements io.papermc.paper.plugin.bootstrap.PluginBootstrap {
    private static final Logger LOGGER = LoggerFactory
            .getLogger("MoFrameCore/" + PluginBootstrap.class.getSimpleName());

    @Override
    public void bootstrap(final BootstrapContext context) {
        this.initializingConfig(context);
    }

    @SuppressWarnings("deprecation")
    void initializingConfig(final BootstrapContext context) {
        final var filePath = context.getDataDirectory().resolve("config.toml").normalize();
        boolean exist = false;
        LOGGER.info("checking configuration file exist: %s", exist = filePath.toFile().exists());

        if (!exist) {
            final var config = CommentedConfig.inMemory();
            ObjectSerializer.builder().build().serializeFields(MoFrameConfig.defaultConfig(), config);
            new TomlWriter().write(config, filePath.toFile(), WritingMode.REPLACE);
            LOGGER.warn("writed default configuration, please modify it for your server.");
        }

        MoFrameConfig._loading(filePath.toString());
        LOGGER.info("loaded configuration from: %s", filePath.toString());
    }
}
