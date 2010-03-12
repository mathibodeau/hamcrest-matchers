package com.pyxis.matchers.validation;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.beans.HasPropertyWithValue;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.core.IsCollectionContaining;

import javax.validation.ConstraintViolation;
import java.util.Arrays;
import java.util.Collection;

import static com.pyxis.matchers.validation.HasNodesAlongPath.path;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;

public class ViolationMatchers {

    public static <T> Matcher<Iterable<ConstraintViolation<T>>> violates(Matcher<? super ConstraintViolation<T>>... matchers) {
        return violates(Arrays.asList(matchers));
    }

    public static <T> Matcher<Iterable<ConstraintViolation<T>>> violates(Collection<Matcher<? super ConstraintViolation<T>>> matchers) {
        return Matchers.<Iterable<ConstraintViolation<T>>>both(ViolationMatchers.<T>fails()).and(IsCollectionContaining.<ConstraintViolation<T>>hasItems(violation(matchers)));
    }

    public static <T> Matcher<Iterable<ConstraintViolation<T>>> fails() {
        return not(ViolationMatchers.<T>succeeds());
    }

    public static <T> Matcher<Iterable<ConstraintViolation<T>>> succeeds() {
        return IsEmptyIterable.emptyIterable();
    }

    public static <T> Matcher<ConstraintViolation<T>> violation(Matcher<? super ConstraintViolation<T>>... matchers) {
        return violation(Arrays.asList(matchers));
    }

    public static <T> Matcher<ConstraintViolation<T>> violation(Collection<Matcher<? super ConstraintViolation<T>>> matchers) {
        return allOf(matchers);
    }

    public static <T> Matcher<ConstraintViolation<T>> on(String pathExpression) {
        return HasPropertyWithValue.hasProperty("propertyPath", path(pathExpression));
    }

    public static <T> Matcher<ConstraintViolation<T>> withError(String messagePart) {
        return HasPropertyWithValue.hasProperty("messageTemplate", containsString(messagePart));
    }
}
