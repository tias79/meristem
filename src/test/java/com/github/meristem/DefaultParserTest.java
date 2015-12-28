/**
 * 
 */
package com.github.meristem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;

/**
 * Tests for {@link DefaultParser}.
 *
 */
@RunWith(JMockit.class)
public class DefaultParserTest {

	private DefaultParser parser;
	
	@Mocked
	private Parser innerParser;
	
	private static final String defaultString = "test";
	
	@Before
	public void setup() {
		parser = new DefaultParser(innerParser, defaultString);
	}
	
	/**
	 * Test method for {@link com.github.meristem.AbstractParser#parse(java.lang.String)}.
	 */
	@Test
	public void testParse1() {
		final String input = "inputstring";
		
		new Expectations() {
			{
				innerParser.parse(input);
				result = new ParseResult(null, input);
			}
		};

		ParseResult result = parser.parse(input);
		assertEquals(input, result.getRemainder());
		assertEquals(ParseTreeUtils.leaf(defaultString), result.getParseTree());
	}

	/**
	 * Test method for {@link com.github.meristem.AbstractParser#parse(java.lang.String)}.
	 */
	@Test
	public void testParse2() {
		final String input = "inputstring";
		
		new Expectations() {
			{
				innerParser.parse(input);
				result = new ParseResult(ParseTreeUtils.leaf(input), "");
			}
		};

		ParseResult result = parser.parse(input);
		assertEquals("", result.getRemainder());
		assertEquals(ParseTreeUtils.leaf(input), result.getParseTree());
	}
}
