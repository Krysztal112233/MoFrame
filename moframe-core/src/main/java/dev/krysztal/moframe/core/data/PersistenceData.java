// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//  
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//  
// See the file LICENSE for the full license text.

package dev.krysztal.moframe.core.data;

import io.vavr.control.Try;
import java.util.Collection;
import java.util.Optional;
import java.util.OptionalLong;
import javax.annotation.Nullable;

/**
 * PersistenceData defined basic.
 */
public interface PersistenceData {

	final public static record TimedRecord<T>(T inner, long start, OptionalLong expire) {
		public boolean isExpired() {
			return expire.isEmpty() ? false : (System.currentTimeMillis() - this.start() >= expire.getAsLong());
		}
	}

	/**
	 * @param key
	 * @param obj
	 * @return the original data which has been replaced.
	 */
	@Nullable <T> TimedRecord<T> put(String key, T obj, @Nullable long expire);

	<T> Optional<TimedRecord<T>> read(String key);

	<T> Try<TimedRecord<T>> readSafety(String key, Class<T> type);

	/**
	 * @param key
	 * @return if element has been removed.
	 */
	boolean remove(String key);

	/**
	 * @return immutable view of keys.
	 */
	Collection<String> keys();

	boolean exist(String key);
}
