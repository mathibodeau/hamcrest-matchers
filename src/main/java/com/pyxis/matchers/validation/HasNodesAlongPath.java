package com.pyxis.matchers.validation;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.collection.IsIterableContainingInOrder;

import javax.validation.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.DescribedAs.describedAs;

public class HasNodesAlongPath extends TypeSafeMatcher<Path> {
    List<Matcher<? super Path.Node>> nodeMatchers = new ArrayList<Matcher<? super Path.Node>>();

    public HasNodesAlongPath(Matcher<? super Path.Node>... nodeMatchers) {
        this(Arrays.asList(nodeMatchers));
    }

    public HasNodesAlongPath(List<Matcher<? super Path.Node>> nodeMatchers) {
        this.nodeMatchers = nodeMatchers;
    }

    @Override protected boolean matchesSafely(Path path) {
        return IsIterableContainingInOrder.<Path.Node>contains(nodeMatchers).matches(path);
    }

    public void describeTo(Description description) {
        description.appendList("", "->", "", nodeMatchers);
    }

    public static HasNodesAlongPath path(String expression) {
        List<Matcher<? super Path.Node>> nodeMatchers = new ArrayList<Matcher<? super Path.Node>>();
        for (String component : expression.split("\\.")) {
            nodeMatchers.add(nodeWithName(component));
        }
        return new HasNodesAlongPath(nodeMatchers);
    }

    public static Matcher<? super Path.Node> nodeWithName(String name) {
        return nodeWithName(equalTo(name));
    }

    public static Matcher<? super Path.Node> nodeWithName(Matcher<? super String> nameMatcher) {
        StringDescription description = new StringDescription();
        nameMatcher.describeTo(description);
        return describedAs(description.toString(), hasProperty("name", nameMatcher));
    }
}
