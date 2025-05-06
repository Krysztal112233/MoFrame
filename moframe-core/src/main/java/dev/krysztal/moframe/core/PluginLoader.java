// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core;

import dev.krysztal.moframe.core.config.MoFrameConfig;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginLoader implements io.papermc.paper.plugin.loader.PluginLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger("MoFrameCore/" + PluginLoader.class.getSimpleName());

    @Override
    public void classloader(final PluginClasspathBuilder classpathBuilder) {
        var mvn = new MavenLibraryResolver();

        this.addJdbc(mvn);
    }

    void addJdbc(MavenLibraryResolver mvn) {
        var db = MoFrameConfig.getInstance().getCore().getDatabase();
        LOGGER.info("detected database: %s", db.toString());

        var driver = switch (db) {
            case H2 -> "com.h2database:h2:2.3.232";
            case Postgres -> "org.postgresql:postgresql:42.7.5";
        };

        mvn.addDependency(new Dependency(new DefaultArtifact(driver), null));
    }
}
