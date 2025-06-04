// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import java.util.List;
import net.minecraft.util.Tuple;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;

public class PluginLoader implements io.papermc.paper.plugin.loader.PluginLoader {

    @Override
    public void classloader(final PluginClasspathBuilder classpathBuilder) {
        var mvn = new MavenLibraryResolver();

        this.addRepositories(mvn);
        this.addBasic(mvn);
        this.addJdbc(mvn);

        classpathBuilder.addLibrary(mvn);
    }

    void addRepositories(MavenLibraryResolver mvn) {
        List.of(new Tuple<>("ali", "https://maven.aliyun.com/repository/public"),
                new Tuple<>("tencent", "http://mirrors.cloud.tencent.com/nexus/repository/maven-public/")).stream()
                .forEach(repo -> mvn
                        .addRepository(new RemoteRepository.Builder(repo.getA(), "default", repo.getB()).build()));
    }

    void addJdbc(MavenLibraryResolver mvn) {
        List.of("org.postgresql:postgresql:42.7.5", "com.h2database:h2:2.3.232").stream()
                .forEach(pkg -> mvn.addDependency(new Dependency(new DefaultArtifact(pkg), null)));
    }

    void addBasic(MavenLibraryResolver mvn) {
        List.of("com.electronwill.night-config:toml:3.8.1", "com.google.inject:guice:7.0.0",
                "com.zaxxer:HikariCP:6.3.0", "io.vavr:vavr:0.10.6", "one.util:streamex:0.8.3",
                "redis.clients:jedis:5.2.0", "com.h2database:h2:2.3.232").stream()
                .forEach(pkg -> mvn.addDependency(new Dependency(new DefaultArtifact(pkg), null)));
    }

}
