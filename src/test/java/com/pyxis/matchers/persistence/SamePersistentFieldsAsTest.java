package com.pyxis.matchers.persistence;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import javax.persistence.Embeddable;

import static com.pyxis.matchers.persistence.SamePersistentFieldsAs.samePersistentFieldsAs;

public class SamePersistentFieldsAsTest extends AbstractMatcherTest {

    private static final Dependent aComponent = new Dependent("expected");
    private static final Dependent aMatchingComponent = new Dependent("expected");
    private static final Value aValue = new Value("expected");
    private static final Value aMatchingValue = new Value("expected");
    private static final ExampleEntity expectedEntity = new ExampleEntity("same", 1, aValue, aComponent);
    private static final ExampleEntity shouldMatch = new ExampleEntity("same", 1, aMatchingValue, aMatchingComponent);

    private static final ExampleEntity differentTransientFields = new ExampleEntity("same", 1, aValue, aComponent) {{
        isStatic = new Object();
        isTransient = new Object();
    }};

    @Override protected Matcher<?> createMatcher() {
        return samePersistentFieldsAs(expectedEntity);
    }

    public void testMatchesWhenAllPersistentFieldsMatch() {
      assertMatches("matched fields", samePersistentFieldsAs(expectedEntity), shouldMatch);
    }

    public void testReportsMismatchWhenActualTypeIsNotAssignableToExpectedType() {
      assertMismatchDescription("is incompatible type: ExampleEntity",
                                samePersistentFieldsAs((Object)aValue), expectedEntity);
    }

    public void testReportsMismatchOnFirstFieldDifference() {
      assertMismatchDescription("string was \"different\"",
          samePersistentFieldsAs(expectedEntity), new ExampleEntity("different", 1, aValue, aComponent));
      assertMismatchDescription("integer was <2>",
          samePersistentFieldsAs(expectedEntity), new ExampleEntity("same", 2, aValue, aComponent));
      assertMismatchDescription("value was <other>",
          samePersistentFieldsAs(expectedEntity), new ExampleEntity("same", 1, new Value("other"), aComponent));
      assertMismatchDescription("component value was \"other\"",
            samePersistentFieldsAs(expectedEntity), new ExampleEntity("same", 1, aValue, new Dependent("other")));
    }

    public void testMatchesIfTransientFieldsAreDifferent() {
      assertMatches("correct fields", samePersistentFieldsAs(expectedEntity), differentTransientFields);
    }

    public void testMatchesDescendantSameFields() {
      assertMatches("sub type with same properties",
          samePersistentFieldsAs(expectedEntity), new DescendantEntity("same", 1, aValue, aComponent));
    }

    public void testMatchesIfSubTypeHasExtraProperties() {
      assertMatches("sub type with extra properties",
          samePersistentFieldsAs(expectedEntity), new DescendantEntityWithExtraProperty("same", 1, aValue, aComponent));
    }

    public void testHasHumanReadableDescription() {
      assertDescription("with fields [string: \"same\", integer: <1>, value: <expected>, component: with fields [value: \"expected\"]]", samePersistentFieldsAs(expectedEntity));
    }


    public static class Value {
        public Value(Object value) {
            this.value = value;
        }

        public final Object value;

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            return value.equals(((Value) o).value);
        }
    }

    @Embeddable
    public static class Dependent {
        private String value;

        public Dependent(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    public static class ExampleEntity {

        private String string;
        private int integer;
        private Value value;
        private Dependent component;

        static Object isStatic;
        transient Object isTransient;

        public ExampleEntity(String string, int integer, Value value, Dependent dependent) {
            this.string = string;
            this.integer = integer;
            this.value = value;
            this.component = dependent;
        }
    }

    public static class DescendantEntity extends ExampleEntity {
        public DescendantEntity(String string, int integer, Value value, Dependent dependent) {
            super(string, integer, value, dependent);
        }
    }

    public static class DescendantEntityWithExtraProperty extends ExampleEntity {
        private String extra = "extra";

        public DescendantEntityWithExtraProperty(String string, int integer, Value value, Dependent dependent) {
            super(string, integer, value, dependent);
        }
    }
}