package com.pyxis.matchers.dom;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static com.pyxis.matchers.dom.WithRootElement.withRootElement;
import static com.pyxis.matchers.dom.WithTag.withTag;
import static com.threelevers.css.DocumentBuilder.doc;
import static org.hamcrest.core.IsEqual.equalTo;

public class WithRootElementTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return withRootElement(withTag("html"));
    }

    public void testMatchesRootDocumentElement() {
        assertMatches("correct element", withRootElement(withTag("html")), doc("<html></html>"));
        assertDoesNotMatch("incorrect element", withRootElement(withTag("xml")), doc("<html></html>"));
    }

    public void testHasAReadableDescription() {
        assertDescription("a document element with tag \"html\"", withRootElement(withTag(equalTo("html"))));
    }

    public void testHasAReadableMismatchDescription() {
        assertMismatchDescription("document element tag was \"HTML\"", withRootElement(withTag(equalTo("xml"))), doc("<HTML></HTML>"));
    }

}