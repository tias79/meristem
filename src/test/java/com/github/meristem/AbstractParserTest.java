/**
 * Meristem - A Java library for parsing text
 * Copyright (C) 2015-2016 Mattias Eklöf <mattias.eklof@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.github.meristem;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;

/**
 * Tests for {@link AbstractParser}.
 *
 */
@RunWith(JMockit.class)
public class AbstractParserTest {

	private final ParseResult doParseParseResult = new ParseResult(null, "doParse");
	private final ParseResult doNegatedParseParseResult = new ParseResult(null, "doNegatedParse");

	private final String parserId = "abc";

	private AbstractParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		parser = new AbstractParser() {

			@Override
			protected ParseResult doParse(String input) {
				assertEquals("doParse", input);
				return doParseParseResult;
			}

			@Override
			protected ParseResult doNegatedParse(String input) {
				assertEquals("doNegatedParse", input);
				return doNegatedParseParseResult;
			}

		};
		parser.setId(parserId);
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.AbstractParser#parse(java.lang.String)}.
	 */
	@Test
	public void testParse(@Mocked final ParserListener listener) {
		final String input = "doParse";

		parser.addEventListener(listener);

		new Expectations() {
			{
				listener.onParse(parserId, doParseParseResult);
			}
		};

		ParseResult result = parser.parse(input);

		assertTrue(result == doParseParseResult);
		assertEquals(input, result.getRemainder());
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.AbstractParser#negatedParse(java.lang.String)}
	 * .
	 */
	@Test
	public void testNegatedParse(@Mocked final ParserListener listener) {
		final String input = "doNegatedParse";

		parser.addEventListener(listener);

		new Expectations() {
			{
				listener.onParse(parserId, doNegatedParseParseResult);
			}
		};

		ParseResult result = parser.negatedParse(input);

		assertTrue(result == doNegatedParseParseResult);
		assertEquals(input, result.getRemainder());
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.AbstractParser#and(com.github.meristem.Parser)}
	 * .
	 */
	@Test
	public void testAnd1(@Mocked final Parser otherParser) {
		Parser newParser = parser.and(otherParser);

		assertTrue(newParser instanceof AndParser);
		assertArrayEquals(new Parser[] { parser, otherParser },
				((AndParser) newParser).getParsers().toArray(new Parser[0]));
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.AbstractParser#and(com.github.meristem.Parser)}
	 * .
	 */
	@Test
	public void testAnd2(@Mocked final Parser otherParser) {	
		AndParser andParser = new AndParser(parser);
		
		Parser newParser = andParser.and(otherParser);

		assertTrue(newParser == andParser);
		assertArrayEquals(new Parser[] { parser, otherParser },
				((AndParser) newParser).getParsers().toArray(new Parser[0]));
	}
	
	/**
	 * Test method for
	 * {@link com.github.meristem.AbstractParser#cat(com.github.meristem.Parser)}
	 * .
	 */
	@Test
	public void testCat1(@Mocked final Parser otherParser) {
		Parser newParser = parser.cat(otherParser);

		assertTrue(newParser instanceof ConcatParser);
		assertArrayEquals(new Parser[] { parser, otherParser },
				((ConcatParser) newParser).getParsers().toArray(new Parser[0]));
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.AbstractParser#cat(com.github.meristem.Parser)}
	 * .
	 */
	@Test
	public void testCat2(@Mocked final Parser otherParser) {	
		ConcatParser catParser = new ConcatParser(parser);
		
		Parser newParser = catParser.cat(otherParser);

		assertTrue(newParser == catParser);
		assertArrayEquals(new Parser[] { parser, otherParser },
				((ConcatParser) newParser).getParsers().toArray(new Parser[0]));
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.AbstractParser#or(com.github.meristem.Parser)}
	 * .
	 */
	@Test
	public void testOr1(@Mocked final Parser otherParser) {
		Parser newParser = parser.or(otherParser);

		assertTrue(newParser instanceof OrParser);
		assertArrayEquals(new Parser[] { parser, otherParser },
				((OrParser) newParser).getParsers().toArray(new Parser[0]));
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.AbstractParser#or(com.github.meristem.Parser)}
	 * .
	 */
	@Test
	public void testOr2(@Mocked final Parser otherParser) {	
		OrParser orParser = new OrParser(parser);
		
		Parser newParser = orParser.or(otherParser);

		assertTrue(newParser == orParser);
		assertArrayEquals(new Parser[] { parser, otherParser },
				((OrParser) newParser).getParsers().toArray(new Parser[0]));
	}
	
	/**
	 * Test method for
	 * {@link com.github.meristem.AbstractParser#xor(com.github.meristem.Parser)}
	 * .
	 */
	@Test
	public void testXor1(@Mocked final Parser otherParser) {
		Parser newParser = parser.xor(otherParser);

		assertTrue(newParser instanceof ExclusiveOrParser);
		assertArrayEquals(new Parser[] { parser, otherParser },
				((ExclusiveOrParser) newParser).getParsers().toArray(new Parser[0]));
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.AbstractParser#xor(com.github.meristem.Parser)}
	 * .
	 */
	@Test
	public void testXor2(@Mocked final Parser otherParser) {	
		ExclusiveOrParser xorParser = new ExclusiveOrParser(parser);
		
		Parser newParser = xorParser.xor(otherParser);

		assertTrue(newParser == xorParser);
		assertArrayEquals(new Parser[] { parser, otherParser },
				((ExclusiveOrParser) newParser).getParsers().toArray(new Parser[0]));
	}

}
