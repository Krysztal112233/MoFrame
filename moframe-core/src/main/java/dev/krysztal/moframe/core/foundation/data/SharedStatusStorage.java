// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.foundation.data;

import com.google.common.reflect.TypeToken;
import dev.krysztal.moframe.core.foundation.SGson;
import dev.krysztal.moframe.core.foundation.da.RedisAccessor;
import io.vavr.control.Try;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.OptionalLong;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

public class SharedStatusStorage implements StatusStorage {
    @Getter
    private static SharedStatusStorage instance;

    public static void _load() {
        instance = new SharedStatusStorage();
    }

    private SharedStatusStorage() {
    }

    @Override
    public <T> TimedRecord<T> put(final String key, final T obj, @Nullable final long expire) {
        final var timedRecord = new TimedRecord<T>(obj, System.currentTimeMillis(), OptionalLong.of(expire));

        RedisAccessor.getInstance().asyncAction(jedis -> {
            OptionalLong.of(expire).ifPresentOrElse(
                    (ex) -> jedis.setex(key, ex, SGson.getInstance().getGson().toJson(timedRecord)),
                    () -> jedis.set(key, SGson.getInstance().getGson().toJson(timedRecord)));
        });

        return timedRecord;
    }

    @Override
    public <T> Try<TimedRecord<T>> read(final String key, final Class<T> type) {
        return RedisAccessor.getInstance().read(jedis -> {
            final String json = Optional.of(jedis.get(key)).get();
            final var gson = SGson.getInstance().getGson();
            final Type typeToken = new TypeToken<TimedRecord<T>>() {
            }.getType();

            return gson.fromJson(json, typeToken);
        });
    }

    @Override
    public boolean remove(final String key) {
        RedisAccessor.getInstance().action(jedis -> jedis.del(key));
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<String> keys() {
        return RedisAccessor.getInstance().read(jedis -> jedis.keys("*")).getOrElse(Collections.EMPTY_SET);
    }

    @Override
    public boolean exist(final String key) {
        return RedisAccessor.getInstance().read(jedis -> jedis.exists(key)).getOrElse(false);
    }
}
