// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.core.attribute;

import org.bukkit.persistence.PersistentDataType;

public class ExtraAttributes {
    public static final ExtraAttribute PLAYER_BASE_PHYSIC_DEF = ExtraAttribute.of(
            "player_base_physic_def", PersistentDataType.LONG);
    public static final ExtraAttribute PLAYER_BASE_MAGIC_DEF = ExtraAttribute.of(
            "player_base_magic_def", PersistentDataType.LONG);

    public static final ExtraAttribute PLAYER_PHYSIC_MULTIPLIER = ExtraAttribute.of(
            "player_physic_atk", PersistentDataType.DOUBLE);
    public static final ExtraAttribute PLAYER_MAGIC_ATK_MULTIPLIER = ExtraAttribute.of(
            "player_magic_atk", PersistentDataType.DOUBLE);
    public static final ExtraAttribute PLAYER_CRIT_RATE = ExtraAttribute.of(
            "player_crit_rate", PersistentDataType.DOUBLE);
    public static final ExtraAttribute PLAYER_CRIT_DMG = ExtraAttribute.of(
            "player_crit_dmg", PersistentDataType.DOUBLE);

    public static final ExtraAttribute PLAYER_MAX_HEALTH = ExtraAttribute.of(
            "player_max_health", PersistentDataType.LONG);
    public static final ExtraAttribute PLAYER_HEALTH_REGEN = ExtraAttribute.of(
            "player_health_regen", PersistentDataType.LONG);
    public static final ExtraAttribute PLAYER_DODGE_CHANCE = ExtraAttribute.of(
            "player_dodge_chance", PersistentDataType.DOUBLE);

    public static final ExtraAttribute PLAYER_MAX_MANA = ExtraAttribute.of(
            "player_mana_max", PersistentDataType.LONG);
    public static final ExtraAttribute PLAYER_MANA_REGEN = ExtraAttribute.of(
            "player_mana", PersistentDataType.LONG);

    public static final ExtraAttribute PLAYER_LIFE_STEAL = ExtraAttribute.of(
            "player_life_steal", PersistentDataType.DOUBLE);
    public static final ExtraAttribute PLAYER_SKILL_HASTE = ExtraAttribute.of(
            "player_skill_haste", PersistentDataType.DOUBLE, 1.0);
    public static final ExtraAttribute PLAYER_MOVEMENT_SPEED = ExtraAttribute.of(
            "player_movement_spd", PersistentDataType.DOUBLE, 1.0);
}
