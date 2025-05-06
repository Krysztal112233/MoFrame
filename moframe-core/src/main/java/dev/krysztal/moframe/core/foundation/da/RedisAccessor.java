// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.foundation.da;

import dev.krysztal.moframe.core.config.MoFrameConfig;
import dev.krysztal.moframe.core.config.MoFrameCoreConfig.JedisConfig;
import io.vavr.control.Try;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisAccessor implements AsynchronousDataAccessor<Jedis> {
    @Getter
    private static RedisAccessor instance;

    @Deprecated
    public static void _load() {
        instance = new RedisAccessor(MoFrameConfig.getInstance().getCore().getJedisConfig());
    }

    private final JedisPool jedisPool;

    private RedisAccessor(final JedisConfig jedisConfig) {
        this.jedisPool = new JedisPool(jedisConfig.getHost(), jedisConfig.getPort());
    }

    @Override
    public <R> Try<R> read(final Function<Jedis, R> f) {
        return Try.of(() -> {
            try (var jedis = this.jedisPool.getResource()) {
                return f.apply(jedis);
            }
        });
    }

    @Override
    public Try<Void> action(final Consumer<Jedis> f) {

        return Try.of(() -> {
            f.accept(this.jedisPool.getResource());
            return null;
        });

    }
}
