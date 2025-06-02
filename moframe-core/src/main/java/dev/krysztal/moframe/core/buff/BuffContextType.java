// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.buff;

import java.util.Map;
import java.util.UUID;

public sealed interface BuffContextType<T>
        permits BuffContextTypeDouble, BuffContextTypeInteger, BuffContextTypeString, BuffContextTypeBoolean,
        BuffContextTypeNull, BuffContextTypeObject, BuffContextTypeArray, BuffContextTypeLong, BuffContextTypeUUID {

    T getValue();

    public static BuffContextTypeLong of(Long value) {
        return new BuffContextTypeLong(value);
    }

    public static BuffContextTypeInteger of(Integer value) {
        return new BuffContextTypeInteger(value);
    }

    public static BuffContextTypeString of(String value) {
        return new BuffContextTypeString(value);
    }

    public static BuffContextTypeDouble of(Double value) {
        return new BuffContextTypeDouble(value);
    }

    public static BuffContextTypeBoolean of(Boolean value) {
        return new BuffContextTypeBoolean(value);
    }

    public static BuffContextTypeObject of(Map<String, BuffContextType<?>> value) {
        return new BuffContextTypeObject(value);
    }

    public static BuffContextTypeUUID of(UUID value) {
        return new BuffContextTypeUUID(value);
    }

    public static BuffContextTypeNull of() {
        return BuffContextTypeNull.INSTANCE;
    }

    public static BuffContextTypeNull empty() {
        return BuffContextTypeNull.INSTANCE;
    }
}
