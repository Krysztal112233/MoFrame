// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//  
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//  
// See the file LICENSE for the full license text.

package dev.krysztal.moframe.core.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.serde.ObjectDeserializer;
import com.electronwill.nightconfig.core.serde.annotations.SerdeDefault;
import java.util.function.Supplier;
import lombok.Getter;

/** MoFrameConfig */
public class MoFrameConfig {
    private static MoFrameConfig instance;

    public static MoFrameConfig getInstance() {
        return instance;
    }

    @Deprecated
    public static void _loading(final String path) {
        final var builder = CommentedFileConfig.builder(path).build();
        MoFrameConfig.instance = ObjectDeserializer.standard().deserializeFields(builder, MoFrameConfig::new);
    }

    public static MoFrameConfig defaultConfig() {
        return new MoFrameConfig();
    }

    @SerdeDefault(provider = "defaultCore")
    @Getter
    private final MoFrameCoreConfig core;

    transient Supplier<MoFrameCoreConfig> defaultCore = () -> new MoFrameCoreConfig();

    transient Supplier<Boolean> defaultExternalMvnRepositoy = () -> false;

    private MoFrameConfig() {
        this.core = this.defaultCore.get();
    }
}
