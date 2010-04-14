package com.pyxis.matchers.persistence;

import com.google.common.base.Predicate;
import com.pyxis.helpers.Reflection;

import java.lang.reflect.Field;

import static com.pyxis.helpers.Reflection.isStatic;
import static com.pyxis.helpers.Reflection.isTransient;

public class PersistentFieldPredicate implements Predicate<Field> {

    public boolean apply(Field input) {
        return isPersistent(input);
    }

    private static boolean isPersistent(Field each) {
        return !isStatic(each) && !isTransient(each);
    }

    public static Field[] persistentFieldsOf(Object entity) {
        return Reflection.fieldsOf(entity, new PersistentFieldPredicate());
    }

    public static Field[] persistentFieldsOf(Class<?> type) {
        return Reflection.fieldsOf(type, new PersistentFieldPredicate());    
    }
}
