// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.foundation.da;

import io.vavr.control.Try;
import java.util.function.Consumer;
import java.util.function.Function;

public interface SynchronousDataAccessor<T> {

    <R> Try<R> read(Function<T, R> f);

    Try<Void> action(Consumer<T> f);
}
