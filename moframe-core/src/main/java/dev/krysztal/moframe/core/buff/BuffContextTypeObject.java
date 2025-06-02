// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.buff;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public final class BuffContextTypeObject implements BuffContextType<Map<String, BuffContextType<?>>> {
    public static BuffContextTypeObject of(final String key, final BuffContextType<?> value) {
        return new BuffContextTypeObject(ImmutableMap.of(key, value));
    }

    public static BuffContextTypeObject of(Map<String, BuffContextType<?>> map) {
        return new BuffContextTypeObject(ImmutableMap.copyOf(map));
    }

    public static BuffContextTypeObject empty() {
        return new BuffContextTypeObject(ImmutableMap.of());
    }

    @Getter
    private final Map<String, BuffContextType<?>> value;

    public Optional<BuffContextType<?>> get(final String key) {
        return Optional.ofNullable(this.value.get(key));
    }

    public Optional<BuffContextType<?>> visit(final String path) {
        final String[] segments = path.split("\\.");

        // TailRescure optimization
        BuffContextType<?> cursor = this;
        for (final String key : segments) {
            if (!(cursor instanceof final BuffContextTypeObject obj)) {
                return Optional.empty();
            }
            cursor = obj.value.get(key);
            if (cursor == null) {
                return Optional.empty();
            }
        }

        return Optional.ofNullable(cursor);
    }

    public BuffContextTypeObject remove(String path) {
        String[] seg = path.split("\\.");
        if (seg.length == 0) {
            return this;
        }

        List<BuffContextTypeObject> parents = new ArrayList<>();
        List<String> parentKey = new ArrayList<>();

        BuffContextTypeObject cursor = this;
        for (int i = 0; i < seg.length - 1; i++) {
            String k = seg[i];
            BuffContextType<?> next = cursor.value.get(k);
            if (!(next instanceof BuffContextTypeObject child)) {
                return this;
            }
            parents.add(cursor);
            parentKey.add(k);
            cursor = child;
        }

        String lastKey = seg[seg.length - 1];
        if (!cursor.value.containsKey(lastKey)) {
            return this;
        }
        Map<String, BuffContextType<?>> work = new HashMap<>(cursor.value);
        work.remove(lastKey);
        BuffContextTypeObject updated = new BuffContextTypeObject(ImmutableMap.copyOf(work));

        for (int i = parents.size() - 1; i >= 0; i--) {
            BuffContextTypeObject parent = parents.get(i);
            String key = parentKey.get(i);

            Map<String, BuffContextType<?>> tmp = new HashMap<>(parent.value);
            tmp.put(key, updated);
            updated = new BuffContextTypeObject(ImmutableMap.copyOf(tmp));
        }
        return updated;
    }
}
