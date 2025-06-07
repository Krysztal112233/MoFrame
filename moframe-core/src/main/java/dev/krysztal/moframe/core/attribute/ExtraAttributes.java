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
    public static final ExtraAttribute ENTITY_PHYSIC_DEF_RATE = ExtraAttribute.of("entity_physic_def_rate",
            PersistentDataType.DOUBLE, 0.1);
    public static final ExtraAttribute ENTITY_MAGIC_DEF_RATE = ExtraAttribute.of("entity_magic_def_rate",
            PersistentDataType.DOUBLE, 0.1);
    public static final ExtraAttribute ENTITY_PHYSIC_MULTIPLIER = ExtraAttribute.of("entity_physic_multiplier",
            PersistentDataType.DOUBLE, 1.0);
    public static final ExtraAttribute ENTITY_MAGIC_ATK_MULTIPLIER = ExtraAttribute.of("entity_magic_multiplier",
            PersistentDataType.DOUBLE, 1.0);
    public static final ExtraAttribute ENTITY_HEAL_MULTIPLIER = ExtraAttribute.of("entity_heal_multiplier",
            PersistentDataType.DOUBLE, 1.0);

    public static final ExtraAttribute PLAYER_CRIT_RATE = ExtraAttribute.of("player_crit_rate",
            PersistentDataType.DOUBLE, 0.01);
    public static final ExtraAttribute PLAYER_CRIT_MULTIPLIER = ExtraAttribute.of("player_crit_multiplier",
            PersistentDataType.DOUBLE, 1.3);

    public static final ExtraAttribute PLAYER_HEALTH_MAX = ExtraAttribute.of("player_health_max",
            PersistentDataType.LONG);
    public static final ExtraAttribute PLAYER_HEALTH_REGEN = ExtraAttribute.of("player_health_regen",
            PersistentDataType.LONG);
    public static final ExtraAttribute PLAYER_HEALTH = ExtraAttribute.of("player_health", PersistentDataType.LONG);

    public static final ExtraAttribute PLAYER_MANA_MAX = ExtraAttribute.of("player_mana_max", PersistentDataType.LONG);
    public static final ExtraAttribute PLAYER_MANA_REGEN = ExtraAttribute.of("player_mana_regen",
            PersistentDataType.LONG);
    public static final ExtraAttribute PLAYER_MANA = ExtraAttribute.of("player_mana", PersistentDataType.LONG);

    public static final ExtraAttribute PLAYER_COOLDOWN_MULTIPLIER = ExtraAttribute.of("player_cooldown_multiplier",
            PersistentDataType.DOUBLE, 1.0);
    public static final ExtraAttribute PLAYER_MOVEMENT_SPEED_MULTIPLIER = ExtraAttribute
            .of("player_movement_speed_multiplier", PersistentDataType.DOUBLE, 1.0);
}
