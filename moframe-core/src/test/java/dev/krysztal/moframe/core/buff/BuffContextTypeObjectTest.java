// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.ImmutableMap;
import dev.krysztal.moframe.core.buff.*;
import io.vavr.control.Option;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BuffContextTypeObjectTest {

    private BuffContextTypeObject simpleObject;
    private BuffContextTypeObject nestedObject;

    @BeforeEach
    void setUp() {
        simpleObject = BuffContextType
                .of(ImmutableMap.of("name", BuffContextType.of("Krysztal"), "age", BuffContextType.of(25)));

        nestedObject = BuffContextType.of(Map.of("id", BuffContextType.of("user-123"), "profile",
                BuffContextType.of(Map.of("name", BuffContextType.of("Alex"), "preferences",
                        BuffContextType.of(Map.of("theme", BuffContextType.of("dark"), "notifications",
                                BuffContextType.of(true))))),
                "roles", BuffContextType.of(BuffContextType.of("admin"), BuffContextType.of("user"))));
    }

    @Test
    void shouldCreateEmptyObject() {
        BuffContextTypeObject empty = BuffContextType.emptyObject();
        assertTrue(empty.getValue().isEmpty());
    }

    @Test
    void shouldCreateSimpleObject() {
        Map<String, BuffContextType<?>> value = simpleObject.getValue();
        assertEquals(2, value.size());
        assertEquals("Krysztal", ((BuffContextTypeString) value.get("name")).getValue());
        assertEquals(25, ((BuffContextTypeInteger) value.get("age")).getValue());
    }

    @Test
    void shouldGetExistingValue() {
        Option<BuffContextType<?>> name = simpleObject.get("name");
        assertTrue(name.isDefined());
        assertEquals("Krysztal", ((BuffContextTypeString) name.get()).getValue());

        Option<BuffContextType<?>> age = simpleObject.get("age");
        assertTrue(age.isDefined());
        assertEquals(25, ((BuffContextTypeInteger) age.get()).getValue());
    }

    @Test
    void shouldReturnEmptyForMissingKey() {
        Option<BuffContextType<?>> missing = simpleObject.get("missing");
        assertTrue(missing.isEmpty());
    }

    @Test
    void shouldVisitTopLevelProperty() {
        Option<BuffContextType<?>> id = nestedObject.visit("id");
        assertTrue(id.isDefined());
        assertEquals("user-123", ((BuffContextTypeString) id.get()).getValue());
    }

    @Test
    void shouldVisitNestedProperty() {
        Option<BuffContextType<?>> theme = nestedObject.visit("profile.preferences.theme");
        assertTrue(theme.isDefined());
        assertEquals("dark", ((BuffContextTypeString) theme.get()).getValue());
    }

    @Test
    void shouldReturnEmptyForInvalidPath() {

        Option<BuffContextType<?>> invalid1 = nestedObject.visit("profile.invalid.theme");
        assertTrue(invalid1.isEmpty());

        Option<BuffContextType<?>> invalid2 = nestedObject.visit("profile.preferences.theme.extra");
        assertTrue(invalid2.isEmpty());

        Option<BuffContextType<?>> invalid3 = nestedObject.visit("roles.2");
        assertTrue(invalid3.isEmpty());

        Option<BuffContextType<?>> emptyPath = nestedObject.visit("");
        assertTrue(emptyPath.isEmpty());
    }

    @Test
    void shouldRemoveTopLevelProperty() {
        BuffContextTypeObject updated = simpleObject.remove("age");

        Map<String, BuffContextType<?>> value = updated.getValue();
        assertEquals(1, value.size());
        assertTrue(value.containsKey("name"));
        assertFalse(value.containsKey("age"));

        assertEquals(2, simpleObject.getValue().size());
    }

    @Test
    void shouldRemoveNestedProperty() {
        BuffContextTypeObject updated = nestedObject.remove("profile.preferences.theme");

        Option<BuffContextType<?>> preferences = updated.visit("profile.preferences");
        assertTrue(preferences.isDefined());

        BuffContextTypeObject prefsObj = (BuffContextTypeObject) preferences.get();
        assertFalse(prefsObj.getValue().containsKey("theme"));
        assertTrue(prefsObj.getValue().containsKey("notifications"));

        assertTrue(nestedObject.visit("profile.preferences.theme").isDefined());
    }

    @Test
    void shouldRemoveEntireNestedObject() {
        BuffContextTypeObject updated = nestedObject.remove("profile");

        Map<String, BuffContextType<?>> value = updated.getValue();
        assertEquals(2, value.size()); // id 和 roles
        assertTrue(value.containsKey("id"));
        assertTrue(value.containsKey("roles"));
        assertFalse(value.containsKey("profile"));

        assertEquals(3, nestedObject.getValue().size());
    }

    @Test
    void shouldHandleRemoveMissingProperty() {

        BuffContextTypeObject updated = nestedObject.remove("profile.missing.property");

        assertEquals(nestedObject.getValue().size(), updated.getValue().size());
        assertTrue(updated.visit("profile.preferences.theme").isDefined());
    }

    @Test
    void shouldHandleRemoveFromMiddleOfPath() {

        BuffContextTypeObject updated = nestedObject.remove("profile.preferences");

        Option<BuffContextType<?>> preferences = updated.visit("profile.preferences");
        assertTrue(preferences.isEmpty());

        assertTrue(nestedObject.visit("profile.preferences").isDefined());
    }

    @Test
    void shouldHandleComplexRemoveScenario() {
        BuffContextTypeObject complex = BuffContextType.of(Map.of("a",
                BuffContextType.of(Map.of("b", BuffContextType.of(Map.of("c", BuffContextType.of("value")))))));

        BuffContextTypeObject updated = complex.remove("a.b.c");

        Option<BuffContextType<?>> b = updated.visit("a.b");
        assertTrue(b.isDefined());
        BuffContextTypeObject bObj = (BuffContextTypeObject) b.get();
        assertTrue(bObj.getValue().isEmpty());

        assertTrue(complex.visit("a.b.c").isDefined());
    }

    @Test
    void shouldHandleRemoveWithEmptyPath() {
        BuffContextTypeObject updated = simpleObject.remove("");
        assertEquals(simpleObject.getValue().size(), updated.getValue().size());
    }

    @Test
    void shouldHandleRemoveWithSingleDotPath() {
        BuffContextTypeObject updated = simpleObject.remove(".");
        assertEquals(simpleObject.getValue().size(), updated.getValue().size());
    }

    @Test
    void shouldContainCorrectTypes() {
        BuffContextType<?> id = nestedObject.getValue().get("id");
        assertTrue(id instanceof BuffContextTypeString);

        BuffContextType<?> profile = nestedObject.getValue().get("profile");
        assertTrue(profile instanceof BuffContextTypeObject);

        BuffContextType<?> roles = nestedObject.getValue().get("roles");
        assertTrue(roles instanceof BuffContextTypeArray);

        BuffContextType<?> theme = nestedObject.visit("profile.preferences.theme").get();
        assertTrue(theme instanceof BuffContextTypeString);

        BuffContextType<?> notifications = nestedObject.visit("profile.preferences.notifications").get();
        assertTrue(notifications instanceof BuffContextTypeBoolean);
    }

    @Test
    void shouldHandleUUIDType() {
        UUID uuid = UUID.randomUUID();
        BuffContextTypeObject object = BuffContextType.of(Map.of("id", BuffContextType.of(uuid)));

        Option<BuffContextType<?>> id = object.get("id");
        assertTrue(id.isDefined());
        assertTrue(id.get() instanceof BuffContextTypeUUID);
        assertEquals(uuid, ((BuffContextTypeUUID) id.get()).getValue());
    }

    @Test
    void shouldHandleNullValue() {
        BuffContextTypeObject object = BuffContextType.of(Map.of("nullValue", BuffContextType.empty()));

        Option<BuffContextType<?>> nullValue = object.get("nullValue");
        assertTrue(nullValue.isDefined());
        assertTrue(nullValue.get() instanceof BuffContextTypeNull);
    }

    @Test
    void shouldHandleMixedTypes() {
        BuffContextTypeObject mixed = BuffContextType.of(Map.of("string", BuffContextType.of("text"), "int",
                BuffContextType.of(42), "double", BuffContextType.of(3.14), "boolean", BuffContextType.of(true),
                "object", BuffContextType.emptyObject(), "array", BuffContextType.emptyArray(), "null",
                BuffContextType.empty()));

        assertEquals(7, mixed.getValue().size());
        assertTrue(mixed.getValue().get("string") instanceof BuffContextTypeString);
        assertTrue(mixed.getValue().get("int") instanceof BuffContextTypeInteger);
        assertTrue(mixed.getValue().get("double") instanceof BuffContextTypeDouble);
        assertTrue(mixed.getValue().get("boolean") instanceof BuffContextTypeBoolean);
        assertTrue(mixed.getValue().get("object") instanceof BuffContextTypeObject);
        assertTrue(mixed.getValue().get("array") instanceof BuffContextTypeArray);
        assertTrue(mixed.getValue().get("null") instanceof BuffContextTypeNull);
    }
}
