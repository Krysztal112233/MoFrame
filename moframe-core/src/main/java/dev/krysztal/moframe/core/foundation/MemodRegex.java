// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.foundation;

import io.vavr.Function0;
import java.util.regex.Pattern;

public class MemodRegex {
    public static final Function0<Pattern> STATUS_PATTERN = Function0
            .of(() -> Pattern.compile("^core\\.([^.]*)\\.buffstatus\\.([^.]*)", Pattern.CASE_INSENSITIVE));
}
