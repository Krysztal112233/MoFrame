// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.foundation.da;

import dev.krysztal.moframe.core.foundation.ExecutorUtils;
import dev.krysztal.moframe.core.foundation.ExecutorUtils.TaskType;
import io.vavr.control.Try;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * AsyncDataAccessor provide async data accessing.
 *
 *
 * This interface required SyncDataAccessor at first, if any class implemented
 * SyncDataAccessor, you can get the abilities of async accessing.
 */
public interface AsynchronousDataAccessor<T> extends SynchronousDataAccessor<T> {

    default public <R> Future<Try<R>> asyncRead(final Function<T, R> f) {
        return ExecutorUtils.getInstance().future(() -> this.read(f).get(), TaskType.IO);
    }

    default public Future<Try<Void>> asyncAction(final Consumer<T> f) {
        return ExecutorUtils.getInstance().future(() -> this.action(f).get(), TaskType.IO);
    }
}
