// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//  
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//  
// See the file LICENSE for the full license text.

package dev.krysztal.moframe.core.config;

import com.electronwill.nightconfig.core.serde.annotations.SerdeComment;
import com.electronwill.nightconfig.core.serde.annotations.SerdeDefault;
import io.vavr.Function0;
import io.vavr.control.Try;
import java.util.function.Supplier;
import lombok.Getter;
import one.util.streamex.StreamEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** MoFrameCoreConfig */
public class MoFrameCoreConfig {
    public enum Database {
        H2, Postgres
    }

    class HikariConfig {
        @SerdeDefault(provider = "defaultUserName")
        @Getter
        private final String userName;

        @SerdeDefault(provider = "defaultUrl")
        @Getter
        private final String url;

        transient Supplier<String> defaultUserName = () -> "username";
        transient Supplier<String> defaultUrl = () -> "jdbc:postgres://localhost:5432/moframe";

        HikariConfig() {
            this.userName = this.defaultUserName.get();
            this.url = this.defaultUrl.get();
        }
    }

    class TaskConsumerConfig {
        public enum ConsumeStrategy {
            Overflow, Limited
        }

        @SerdeDefault(provider = "defaultMaxTasks")
        @Getter
        private int maxTasks;

        @SerdeDefault(provider = "defaultConsumeStrategy")
        @SerdeComment("The strategy of task consumer.")
        @SerdeComment("  Avaliable value: Overflow, Limited.")
        @Getter
        private ConsumeStrategy consumeMethod;

        transient Supplier<ConsumeStrategy> defaultConsumeStrategy = () -> ConsumeStrategy.Limited;
        transient Supplier<Integer> defaultMaxTasks = () -> 4096;
    }

    private static final Logger LOGGER = LoggerFactory
            .getLogger("MoFrameCore/" + MoFrameCoreConfig.class.getSimpleName());

    @SerdeDefault(provider = "defaultHikariConfig")
    @Getter
    private HikariConfig hikari;

    transient Supplier<HikariConfig> defaultHikariConfig = () -> new HikariConfig();

    transient Function0<Database> database = Function0.of(() -> {
        final var schema = Try.of(() -> this.hikari.url.split(":")[1]).getOrElse("h2");
        final var supported = StreamEx.of(Database.values()).map(Database::toString)
                .indexOf(it -> it.equalsIgnoreCase(schema)).orElseGet(() -> {
                    LOGGER.warn("invalid url, using H2 as persitence data backend");
                    LOGGER.warn("!!!YOUR DATA WILL NOT BE SAVED AFTER RESTART!!!");
                    return 0;
                });
        return Database.values()[(int) supported];
    });

    MoFrameCoreConfig() {
        this.hikari = this.defaultHikariConfig.get();
    }

    public Database getDatabase() {
        return this.database.apply();
    }
}
