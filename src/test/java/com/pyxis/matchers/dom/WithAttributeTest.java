package com.pyxis.matchers.dom;

import com.threelevers.css.Selector;
import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.w3c.dom.Element;

import static com.pyxis.matchers.dom.WithAttribute.withAttribute;
import static com.threelevers.css.DocumentBuilder.dom;
import static org.hamcrest.core.IsEqual.equalTo;

public class WithAttributeTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return withAttribute("name", equalTo("Submit"));
    }

    public void testMatchesWhenElementHasAttributeMatchingValue() {
        assertMatches("correct attribute", withAttribute("name", equalTo("submit")), anElementWithAttribute("name", "submit"));
        assertMatches("correct attribute with different case", withAttribute("NAME", equalTo("submit")), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("incorrect attribute", withAttribute("name", equalTo("commit")), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("missing attribute", withAttribute("value", equalTo("submit")), anElementWithAttribute("name", "submit"));
    }

    public void testProvidesConvenientShortcutForAttributeValue() {
        assertMatches("correct attribute", withAttribute("name", "submit"), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("correct attribute with incorrect case", withAttribute("name", "Submit"), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("incorrect attribute", withAttribute("name", "commit"), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("missing attribute", withAttribute("value", "submit"), anElementWithAttribute("name", "submit"));
    }

    public void testHasAReadableDescription() {
        assertDescription("element with attribute \"name\" \"submit\"", withAttribute("name", "submit"));
    }

    public void testHasAReadableMismatchDescription() {
        assertMismatchDescription("element attribute \"name\" was \"Commit\"", withAttribute("name", "submit"), anElementWithAttribute("name", "Commit"));
        assertMismatchDescription("element attribute \"name\" was \"\"", withAttribute("name", "submit"), anElementWithAttribute("value", "submit"));
    }

    private Element anElementWithAttribute(String attributeName, String attributeValue) {
        return element(String.format("<div %s=\"%s\"></div>", attributeName, attributeValue));
    }

    private Element element(String html) {
        return Selector.from(dom(html)).selectUnique("html > body > *");
    }

}