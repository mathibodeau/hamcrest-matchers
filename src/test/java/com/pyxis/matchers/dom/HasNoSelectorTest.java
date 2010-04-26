package com.pyxis.matchers.dom;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static com.pyxis.matchers.dom.HasNoSelector.hasNoSelector;
import static com.pyxis.matchers.dom.WithAttribute.withAttribute;
import static com.threelevers.css.DocumentBuilder.dom;

public class HasNoSelectorTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasNoSelector("#unknown");
    }
    
    public void testMatchesWhenElementHasNoChildMatchingSelector() {
        assertMatches("correct element", hasNoSelector("#unknown"), dom("<div id='content'>content</div>"));
        assertDoesNotMatch("incorrect element", hasNoSelector("#content"), dom("<div id='content'>content</div>"));
    }

    public void testHasAReadableDescription() {
        assertDescription("has no selector \"#unknown\"", hasNoSelector("#unknown"));
    }

    public void testHasAReadableMismatchDescription() {
        assertMismatchDescription("matched element \"DIV\"", hasNoSelector("#content"), dom("<div id='content'></div>"));
    }
}
