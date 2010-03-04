package com.pyxis.matchers.dom;

import static com.pyxis.matchers.dom.IsBlankString.isBlank;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsBlankStringTest extends AbstractMatcherTest {

	@Override
	protected Matcher<?> createMatcher() {
		return isBlank();
	}

	public void testShouldMatchAStringContainingWhitespaceCharacters() {
		assertMatches("a blank string", isBlank(), " ");
	}

	public void testShouldMatchAStringContainingTabOrNewlineCharacters() {
		assertMatches("a blank string with tabs and newlines", isBlank(), "\t\n");
	}
	
	public void testShouldMatchAStringContainingUnicodeBlankCharacters() {
		assertMatches("a blank string with unicode blanks", isBlank(), "" + (char) 160);
	}

	public void testShouldNotMatchAStringContainingNonBlankCharacters() {
		assertDoesNotMatch("a non-blank string", isBlank(), " x ");
	}
	
	public void testDescription() {
		assertDescription("a blank string", isBlank());
	}
	
}
