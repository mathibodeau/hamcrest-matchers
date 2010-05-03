package com.pyxis.matchers.selenium;

import static com.pyxis.matchers.core.CoreMatchers.being;

import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.lift.match.AttributeMatcher;

/**
 * A collection of hamcrest matchers to be used in assertions validating 
 * selenium's HTML elements {@link org.openqa.selenium.WebElement}. 
 */
public class SeleniumMatchers {

    private SeleniumMatchers() {}

    /**
     * Checks that an HTML element has an id attribute with specific value.
     * @param id expected value of the id attribute.
     */
    public static Matcher<WebElement> id(String id) {
        return AttributeMatcher.attribute("id", being(id));
    }

    /**
     * Checks that an HTML element has a class attribute with specific value.
     * @param className expected value of the className attribute.
     */
    public static Matcher<WebElement> className(String className) {
        return AttributeMatcher.attribute("class", being(className));
    }

}
