package com.pyxis.matchers.persistence;

import org.hamcrest.*;
import org.hamcrest.core.IsEqual;

import javax.persistence.Embeddable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import static com.pyxis.helpers.Reflection.hasAnnotation;
import static com.pyxis.helpers.Reflection.readField;
import static com.pyxis.matchers.persistence.IsComponentEqual.componentEqualTo;
import static com.pyxis.matchers.persistence.PersistentFieldPredicate.persistentFieldsOf;


public class SamePersistentFieldsAs<T> extends TypeSafeDiagnosingMatcher<T> {

    private final T expectedEntity;

    public SamePersistentFieldsAs(T expectedEntity) {
        this.expectedEntity = expectedEntity;
    }

    @Override
    protected boolean matchesSafely(T argument, Description mismatchDescription) {
        return isCompatibleType(argument, mismatchDescription)
                && hasMatchingPersistentFields(argument, mismatchDescription);
    }

    private boolean isCompatibleType(T argument, Description mismatchDescription) {
        if (!expectedEntity.getClass().isAssignableFrom(argument.getClass())) {
            mismatchDescription.appendText("is incompatible type: " + argument.getClass().getSimpleName());
            return false;
        }
        return true;
    }

    private boolean hasMatchingPersistentFields(T argument, Description mismatchDescription) {
        for (Matcher<? super T> matcher : persistentFieldsMatchersFor(expectedEntity)) {
            if (!matcher.matches(argument)) {
                matcher.describeMismatch(argument, mismatchDescription);
                return false;
            }
        }
        return true;
    }

    private static <T> Iterable<Matcher<? super T>> persistentFieldsMatchersFor(T entity) {
        Collection<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        for (Field field : persistentFieldsOf(entity)) {
            matchers.add(new FieldMatcher(field, valueMatcherFor(entity, field)));
        }
        return matchers;
    }

    private static Matcher<Object> valueMatcherFor(Object entity, Field field) {
        return !isEmbedded(field) ?
            new IsEqual<Object>(readField(entity, field)) :
            componentEqualTo(readField(entity, field));
    }

    private static boolean isEmbedded(Field field) {
        return hasAnnotation(field.getType(), Embeddable.class);
    }

    public void describeTo(Description description) {
        description.appendText("with fields [");
        boolean addSeparator = false;
        for (Field field : persistentFieldsOf(expectedEntity)) {
            if (addSeparator) description.appendText(", ");
            description.appendText(field.getName() + ": ");
            valueMatcherFor(expectedEntity, field).describeTo(description);
            addSeparator = true;
        }
        description.appendText("]");
    }

    public static class FieldMatcher extends DiagnosingMatcher<Object> {

        private final Field field;
        private final Matcher<Object> valueMatcher;

        public FieldMatcher(Field field, Matcher<Object> valueMatcher) {
            this.field = field;
            this.valueMatcher = valueMatcher;
        }

        @Override protected boolean matches(Object argument, Description mismatchDescription) {
            Object actualValue = readField(argument, field);
            boolean valueMatches = valueMatcher.matches(actualValue);
            if (!valueMatches) {
                mismatchDescription.appendText(field.getName() + " ");
                valueMatcher.describeMismatch(actualValue, mismatchDescription);
            }
            return valueMatches;
        }

        public void describeTo(Description description) {
            description.appendText(field.getName() + ": ").appendDescriptionOf(valueMatcher);
        }
    }


    @Factory
    public static <T> Matcher<T> samePersistentFieldsAs(T entity) {
        return new SamePersistentFieldsAs<T>(entity);
    }
}