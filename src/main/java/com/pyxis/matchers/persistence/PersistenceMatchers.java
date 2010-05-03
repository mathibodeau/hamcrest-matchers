package com.pyxis.matchers.persistence;

import org.hamcrest.Matcher;

/**
 * A collection of hamcrest matchers to be used in assertions validating 
 * field values of persistent objects. 
 */
public class PersistenceMatchers {

    private PersistenceMatchers() {}

    /**
     * Checks that a component (aka value object) is equal to another
     * given component.  A null component is considered to be equal to
     * a component with only null persistent field values. Persistent fields
     * of a component are fields that are neither static or transient. 
     * @param component a component with expected state.
     */
    public static <T> Matcher<T> componentEqualTo(T component) {
        return IsComponentEqual.componentEqualTo(component);
    }

    /**
     * Checks that an entity has the same persistent field values
     * than another given entity. Persistent fields of an entity are
     * fields that are neither static or transient.
     * @param entity an entity with expected state. 
     */
    public static <T> Matcher<T> samePersistentFieldsAs(T entity) {
        return SamePersistentFieldsAs.samePersistentFieldsAs(entity);
    }
}
