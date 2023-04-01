/*
 * GNU General Public License v3
 *
 * utils, a set of utilities for Paper plugins
 *
 * Copyright (C) 2021-2022 Machine_Maker
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package me.machinemaker.datapacks.testing.extensions;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

public final class NOPLogger extends Logger {

    public static final Logger INSTANCE = new NOPLogger();

    private NOPLogger() {
        super("NO-OP", null);
    }

    @Override
    public void log(final LogRecord record) {
        // NO-OP
    }
}
