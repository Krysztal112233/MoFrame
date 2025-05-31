// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.foundation.registry;

import java.util.Collection;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;

public interface Registry<T extends Object> {
    Optional<RegistryObject<T>> get(Identifier identifier);

    RegistryObject<T> register(Identifier identifier, T obj);

    @Nullable RegistryObject<T> remove(Identifier identifier);

    Collection<RegistryObject<T>> unmodifiableView();
}
