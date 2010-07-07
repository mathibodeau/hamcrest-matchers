package com.pyxis.matchers.selenium;

import static com.pyxis.matchers.core.CoreMatchers.being;

import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.lift.match.AttributeMatcher;

/**
 * A collection of hamcrest matchers to validate
 * selenium's HTML elements {@link org.openqa.selenium.WebElement}. 
 */
public class SeleniumMatchers {

    private SeleniumMatchers() {}

    /**
     * Checks that an HTML element has an id attribute with a specific value.
     *
     * @param id the expected value of the id attribute.
     */
    public static Matcher<WebElement> id(String id) {
        return AttributeMatcher.attribute("id", being(id));
    }

    /**
     * Checks that an HTML element has a given class.
     *
     * @param className the expected class of the element.
     */
    public static Matcher<WebElement> className(String className) {
        return AttributeMatcher.attribute("class", being(className));
    }

}
