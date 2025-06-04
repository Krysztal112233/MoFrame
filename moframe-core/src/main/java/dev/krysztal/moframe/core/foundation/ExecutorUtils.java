// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.foundation;

import io.vavr.control.Try;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutorUtils {

    public enum TaskType {
        IO, Compute
    }

    private static final Logger LOGGER = LoggerFactory.getLogger("MoFrameCore/" + ExecutorUtils.class.getSimpleName());

    @Getter
    private static ExecutorUtils instance;

    @Deprecated
    public static void _load() {
        ExecutorUtils.instance = new ExecutorUtils();
    }

    @Deprecated
    public static void _unload() {
        LOGGER.info("waiting for `IO` executor shutdown...");
        instance.io.shutdown();
        LOGGER.info("waiting for `Compute` executor shutdown");
        instance.compute.shutdown();
    }

    private final ExecutorService compute = Executors.newWorkStealingPool(32);
    private final ExecutorService io = Executors.newVirtualThreadPerTaskExecutor();

    public void submit(final Supplier<Void> task) {
        this.submit(task, TaskType.Compute);
    }

    public void submit(final Supplier<Void> task, final TaskType taskType) {
        LOGGER.debug("submit task with TaskType(%s)", taskType.toString());
        switch (taskType) {
            case Compute -> this.compute.submit(() -> task.get());
            default -> this.compute.submit(() -> task.get());
        }
    }

    public <R> Future<Try<R>> future(final Supplier<R> future) {
        return this.future(future, TaskType.Compute);
    }

    public <R> Future<Try<R>> future(final Supplier<R> future, final TaskType taskType) {
        LOGGER.debug("submit future with TaskType(%s)", taskType.toString());
        return switch (taskType) {
            case IO -> this.io.submit(() -> Try.of(() -> future.get()));
            default -> this.compute.submit(() -> Try.of(() -> future.get()));
        };
    }
}
