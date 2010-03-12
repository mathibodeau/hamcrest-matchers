package com.pyxis.matchers.validation;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import javax.validation.ConstraintViolation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.pyxis.matchers.validation.FakeConstraintViolation.aViolation;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;

public class ViolationMatchersTest extends AbstractMatcherTest {

    ConstraintViolation<Object> shouldMatch = aViolation().describedAs("should match");
    ConstraintViolation<Object> wontMatch = aViolation().describedAs("won't match");

    @Override protected Matcher<?> createMatcher() {
        return ViolationMatchers.violates(anything());
    }

    public void testProvidesConvenientShortcutForMatchingAViolationInACollection() {
        assertMatches("a match", ViolationMatchers.violates(equalTo(shouldMatch)), asSet(shouldMatch, wontMatch));
    }

    public void testDoesNotMatchViolationSetWhenNoViolationIsMatched() {
        assertDoesNotMatch("no match", ViolationMatchers.violates(equalTo(shouldMatch)), asSet(wontMatch));
    }

    public void testDoesNotMatchEmptyViolationSet() {
        assertDoesNotMatch("no violation", ViolationMatchers.violates(), asSet());
    }

    public void testProvidesConvenientShortcutToMatchAViolationAgainstSeveralMatchers() {
        assertMatches("multiple matchers", ViolationMatchers.violation(equalTo(shouldMatch), hasToString("should match")), shouldMatch);
    }

    // TODO providesConvenientShortcutToMatchOnPathProperty
    // TODO providesConvenientShortcutToMatchPartOfMessageTemplate

    private <T> Set<ConstraintViolation<T>> asSet(ConstraintViolation<T>... violations) {
        Set<ConstraintViolation<T>> set = new HashSet<ConstraintViolation<T>>();
        set.addAll(Arrays.asList(violations));
        return set;
    }
}
