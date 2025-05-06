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

    public static class HikariPoolConfig {

        public enum Database {
            H2, Postgres
        }

        @SerdeDefault(provider = "defaultUserName")
        @Getter
        private final String userName;

        @SerdeDefault(provider = "defaultUrl")
        @Getter
        private final String url;

        @SerdeDefault(provider = "defaultPwd")
        @Getter
        private final String pwd;

        transient Supplier<String> defaultUserName = () -> "username";
        transient Supplier<String> defaultUrl = () -> "jdbc:postgres://localhost:5432/moframe";
        transient Supplier<String> defaultPwd = () -> "jdbc:postgres://localhost:5432/moframe";

        HikariPoolConfig() {
            this.userName = this.defaultUserName.get();
            this.url = this.defaultUrl.get();
            this.pwd = this.defaultPwd.get();
        }
    }

    public static class TaskConsumerConfig {
        public enum ConsumeStrategy {
            Overflow, Limited
        }

        @SerdeDefault(provider = "defaultMaxTasks")
        @Getter
        private final int maxTasks;

        @SerdeDefault(provider = "defaultConsumeStrategy")
        @SerdeComment("The strategy of task consumer.")
        @SerdeComment("  Avaliable value: Overflow, Limited.")
        @Getter
        private final ConsumeStrategy consumeMethod;

        @SerdeDefault(provider = "defaultTickDurantion")
        @Getter
        private final int tickDurantion;

        transient Supplier<ConsumeStrategy> defaultConsumeStrategy = () -> ConsumeStrategy.Limited;
        transient Supplier<Integer> defaultMaxTasks = () -> 4096;
        transient Supplier<Integer> defaultTickDurantion = () -> 3;

        TaskConsumerConfig() {
            this.maxTasks = this.defaultMaxTasks.get();
            this.consumeMethod = this.defaultConsumeStrategy.get();
            this.tickDurantion = this.defaultTickDurantion.get();
        }
    }

    public static class JedisConfig {
        @SerdeDefault(provider = "defaultHost")
        @Getter
        private final String host;

        @SerdeDefault(provider = "defaultPort")
        @Getter
        private final int port;

        @SerdeDefault(provider = "defaultMaxConnections")
        @Getter
        private final int maxConnections;

        transient Supplier<String> defaultHost = () -> "localhost";
        transient Supplier<Integer> defaultPort = () -> 6379;
        transient Supplier<Integer> defaultMaxConnections = () -> 1024;

        JedisConfig() {
            this.host = this.defaultHost.get();
            this.port = this.defaultPort.get();
            this.maxConnections = this.defaultMaxConnections.get();
        }
    }

    private static final Logger LOGGER = LoggerFactory
            .getLogger("MoFrameCore/" + MoFrameCoreConfig.class.getSimpleName());

    @SerdeDefault(provider = "defaultHikariConfig")
    @Getter
    private HikariPoolConfig hikari;

    @SerdeDefault(provider = "defaultTaskConsumerConfig")
    @Getter
    private final TaskConsumerConfig taskConsumerConfig;

    @SerdeDefault(provider = "defaultJedisConfig")
    @Getter
    private final JedisConfig jedisConfig;

    transient Supplier<HikariPoolConfig> defaultHikariConfig = () -> new HikariPoolConfig();
    transient Supplier<TaskConsumerConfig> defaultTaskConsumerConfig = () -> new TaskConsumerConfig();
    transient Supplier<JedisConfig> defaultJedisConfig = () -> new JedisConfig();

    transient Function0<HikariPoolConfig.Database> database = Function0.of(() -> {
        final var schema = Try.of(() -> this.hikari.url.split(":")[1]).getOrElse("h2");
        final var supported = StreamEx.of(HikariPoolConfig.Database.values()).map(HikariPoolConfig.Database::toString)
                .indexOf(it -> it.equalsIgnoreCase(schema)).orElseGet(() -> {
                    LOGGER.warn("invalid url, using H2 as persitence data backend");
                    LOGGER.warn("!!!YOUR DATA WILL NOT BE SAVED AFTER RESTART!!!");
                    return 0;
                });
        return HikariPoolConfig.Database.values()[(int) supported];
    });

    MoFrameCoreConfig() {
        this.hikari = this.defaultHikariConfig.get();
        this.taskConsumerConfig = this.defaultTaskConsumerConfig.get();
        this.jedisConfig = this.defaultJedisConfig.get();
    }

    public HikariPoolConfig.Database getDatabase() {
        return this.database.apply();
    }
}
