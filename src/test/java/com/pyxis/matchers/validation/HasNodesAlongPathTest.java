package com.pyxis.matchers.validation;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class HasNodesAlongPathTest extends AbstractMatcherTest {

    HasNodesAlongPath simplePath = HasNodesAlongPath.path("property");
    FakePath shouldMatchSimplePath = FakePath.pathTo("property");
    HasNodesAlongPath composedPath = HasNodesAlongPath.path("path.to.property");
    FakePath shouldMatchComposedPath = FakePath.pathTo("path", "to", "property");
    FakePath shouldNotMatch = FakePath.pathTo("notMatched");

    @Override protected Matcher<?> createMatcher() {
        return composedPath;
    }

    public void testMatchesPathComposedOfNodesLeadingToProperty() {
        assertMatches("simple path", simplePath, shouldMatchSimplePath);
        assertMatches("composed path", composedPath, shouldMatchComposedPath);
    }

    public void testWillNotMatchIfPathComponentIsNotPresent() {
        assertDoesNotMatch("simple path", simplePath, shouldNotMatch);
    }

    public void testHasHumanReadableDescription() {
        assertDescription("\"property\"", simplePath);        
        assertDescription("\"path\"->\"to\"->\"property\"", composedPath);        
    }
}
