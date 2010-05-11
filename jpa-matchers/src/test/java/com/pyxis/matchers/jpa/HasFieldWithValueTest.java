package com.pyxis.matchers.jpa;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static com.pyxis.matchers.core.HasFieldWithValue.hasField;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.IsEqual.equalTo;

public class HasFieldWithValueTest extends AbstractMatcherTest {

    public AnObject shouldMatch = new AnObject("is expected");
    public AnObject shouldNotMatch = new AnObject("not expected");

    @Override protected Matcher<?> createMatcher() {
        return hasField("irrelevant", anything());
    }

    public void testMatchesAnObjectWithMatchedNamedField() {
        assertMatches("with field", hasField("field", equalTo("is expected")), shouldMatch);
        assertMismatchDescription("\"field\" was \"not expected\"",
                                  hasField("field", equalTo("is expected")), shouldNotMatch);
    }

    public void testDoesNotMatchWhenFieldIsMissing() {
        assertMismatchDescription("no field \"doh\"",
                                  hasField("doh", anything()), shouldNotMatch);
    }

    public void testCanMatchFieldPresence() {
        assertMatches("field", hasField("field"), shouldMatch);
    }

    public void testHasHumanReadableDescription() {
        assertDescription("has field \"field\": \"value\"", hasField("field", equalTo("value")));
    }

    public static class AnObject {
        private String field;

        public AnObject(String field) {
            this.field = field;
        }
    }
}
