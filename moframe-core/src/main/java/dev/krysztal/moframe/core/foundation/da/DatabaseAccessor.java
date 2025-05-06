// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.foundation.da;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.pool.HikariPool;
import dev.krysztal.moframe.core.config.MoFrameConfig;
import dev.krysztal.moframe.core.config.MoFrameCoreConfig.HikariPoolConfig;
import io.vavr.control.Try;
import java.sql.Connection;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.Getter;

public class DatabaseAccessor implements AsynchronousDataAccessor<Connection> {
    @Getter
    private static DatabaseAccessor instance;

    @Deprecated
    public static void _load() {
        instance = new DatabaseAccessor(MoFrameConfig.getInstance().getCore().getHikari());
    }

    private static HikariPool instantHikariPool(final HikariPoolConfig hikariPoolConfig) {
        final var config = new HikariConfig();
        config.setJdbcUrl(hikariPoolConfig.getUrl());
        config.setUsername(hikariPoolConfig.getUserName());
        config.setPassword(hikariPoolConfig.getPwd());

        return new HikariPool(config);
    }

    private final HikariPool hikariPool;

    private DatabaseAccessor(final HikariPoolConfig hikariPoolConfig) {
        this.hikariPool = instantHikariPool(hikariPoolConfig);
    }

    @Override
    public <R> Try<R> read(final Function<Connection, R> f) {
        return Try.of(() -> f.apply(this.hikariPool.getConnection()));
    }

    @Override
    public Try<Void> action(Consumer<Connection> f) {
        return Try.of(() -> {
            f.accept(this.hikariPool.getConnection());
            return null;
        });
    }

}
