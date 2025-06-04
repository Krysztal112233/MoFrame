// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.buff;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public sealed interface BuffContextType<T>
        permits BuffContextTypeDouble, BuffContextTypeInteger, BuffContextTypeString, BuffContextTypeBoolean,
        BuffContextTypeNull, BuffContextTypeObject, BuffContextTypeArray, BuffContextTypeLong, BuffContextTypeUUID {

    T getValue();

    public static BuffContextTypeLong of(final Long value) {
        return new BuffContextTypeLong(value);
    }

    public static BuffContextTypeInteger of(final Integer value) {
        return new BuffContextTypeInteger(value);
    }

    public static BuffContextTypeString of(final String value) {
        return new BuffContextTypeString(value);
    }

    public static BuffContextTypeDouble of(final Double value) {
        return new BuffContextTypeDouble(value);
    }

    public static BuffContextTypeBoolean of(final Boolean value) {
        return new BuffContextTypeBoolean(value);
    }

    public static BuffContextTypeObject of(final String key, final BuffContextType<?> value) {
        return new BuffContextTypeObject(ImmutableMap.of(key, value));
    }

    public static BuffContextTypeObject of(final Map<String, BuffContextType<?>> map) {
        return new BuffContextTypeObject(ImmutableMap.copyOf(map));
    }

    public static BuffContextTypeArray of(final List<BuffContextType<?>> list) {
        return new BuffContextTypeArray(ImmutableList.copyOf(list));
    }

    public static BuffContextTypeArray of(final BuffContextType<?>... ele) {
        return new BuffContextTypeArray(ImmutableList.copyOf(ele));
    }

    public static BuffContextTypeUUID of(final UUID value) {
        return new BuffContextTypeUUID(value);
    }

    public static BuffContextTypeNull of() {
        return BuffContextTypeNull.INSTANCE;
    }

    public static BuffContextTypeNull empty() {
        return BuffContextTypeNull.INSTANCE;
    }

    public static BuffContextTypeObject emptyObject() {
        return BuffContextTypeObject.EMPTY;
    }

    public static BuffContextTypeArray emptyArray() {
        return BuffContextTypeArray.EMPTY;
    }
}
