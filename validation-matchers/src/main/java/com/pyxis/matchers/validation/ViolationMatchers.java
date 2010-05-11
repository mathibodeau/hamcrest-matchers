package com.pyxis.matchers.validation;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.core.IsCollectionContaining;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.Arrays;
import java.util.Collection;

import static com.pyxis.matchers.validation.HasNodesAlongPath.path;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;

/**
 * A collection of hamcrest matchers to be used in assertions validating 
 * {@link javax.validation.ConstraintViolation}s. 
 */
public class ViolationMatchers {

    /**
     * Checks that a collection of violations contains at least one violation 
     * and that each of the given matchers matches at least one of its elements.
     */
    public static <T> Matcher<Iterable<ConstraintViolation<T>>> violates(Matcher<? super ConstraintViolation<T>>... matchers) {
        return violates(Arrays.asList(matchers));
    }

    /**
     * Checks that a collection of violations contains at least one violation 
     * and that each of the given matchers matches at least one of its elements.
     */
    @SuppressWarnings("unchecked")
    public static <T> Matcher<Iterable<ConstraintViolation<T>>> violates(Collection<Matcher<? super ConstraintViolation<T>>> matchers) {
        return Matchers.<Iterable<ConstraintViolation<T>>>both(ViolationMatchers.<T>fails()).and(IsCollectionContaining.<ConstraintViolation<T>>hasItems(violation(matchers)));
    }

    /**
     * Checks that there is at least one violation. 
     */
    public static <T> Matcher<Iterable<ConstraintViolation<T>>> fails() {
        return not(ViolationMatchers.<T>succeeds());
    }

    /**
     * Checks that there is no violation.
     */
    public static <T> Matcher<Iterable<ConstraintViolation<T>>> succeeds() {
        return IsEmptyIterable.emptyIterable();
    }
    
    /**
     * Checks that each of the given matchers matches at least one of the violations.
     */
    public static <T> Matcher<ConstraintViolation<T>> violation(Matcher<? super ConstraintViolation<T>>... matchers) {
        return violation(Arrays.asList(matchers));
    }

    /**
     * Checks that each of the given matchers matches at least one of the violations.
     */
    public static <T> Matcher<ConstraintViolation<T>> violation(Collection<Matcher<? super ConstraintViolation<T>>> matchers) {
        return allOf(matchers);
    }

    /**
     * Checks that a violation applies to a given property. The property can be
     * a nested property expression. For instance, expression foo.bar would
     * check that violation applies to bar property of object stored in foo property. 
     * @param pathExpression
     * @return
     */
    public static <T> Matcher<ConstraintViolation<T>> on(String pathExpression) {
        return new FeatureMatcher<ConstraintViolation<T>, Path>(path(pathExpression), "on path", "path") {
            @Override protected Path featureValueOf(ConstraintViolation<T> actual) {
                return actual.getPropertyPath();
            }
        };
    }

    /**
     * Checks that a violation's error message template contains a given string. 
     */
    public static <T> Matcher<ConstraintViolation<T>> withError(String messagePart) {
        return new FeatureMatcher<ConstraintViolation<T>, String>(containsString(messagePart), "with message", "message") {
            @Override protected String featureValueOf(ConstraintViolation<T> actual) {
                return actual.getMessageTemplate();
            }
        };
    }
}
