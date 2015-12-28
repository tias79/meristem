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

import static com.github.meristem.ParseTreeUtils.*;
import static org.junit.Assert.*;
import mockit.integration.junit4.JMockit;
import mockit.Expectations;
import mockit.Mocked;

import org.junit.runner.RunWith;

import com.github.meristem.Iterations;
import com.github.meristem.IteratorParser;
import com.github.meristem.ParseResult;
import com.github.meristem.ParseTreeLeaf;
import com.github.meristem.Parser;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link IteratorParser}.
 *
 */
@RunWith(JMockit.class)
public class IteratorParserTest {

	@Mocked
	private Parser innerParser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.IteratorParser#doParse(java.lang.String)}.
	 */
	@Test
	public void testParse1() {
		new Expectations() {
			{
				innerParser.parse("test");
				result = new ParseResult(new ParseTreeLeaf("t"), "est");

				innerParser.parse("est");
				result = new ParseResult(new ParseTreeLeaf("e"), "st");

				innerParser.parse("st");
				result = new ParseResult(new ParseTreeLeaf("s"), "t");

			}
		};

		IteratorParser parser = new IteratorParser(innerParser, 3);

		ParseResult result = parser.parse("test");
		assertEquals(node(leaf("t"), node(leaf("e"), leaf("s"))), result.getParseTree());
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.IteratorParser#doParse(java.lang.String)}.
	 */
	@Test
	public void testParse2() {
		new Expectations() {
			{
				innerParser.parse("test");
				result = new ParseResult(null, "test");
			}
		};

		IteratorParser parser = new IteratorParser(innerParser, Iterations.ONE_OR_MORE);

		ParseResult result = parser.parse("test");
		assertEquals(null, result.getParseTree());
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.IteratorParser#doParse(java.lang.String)}.
	 */
	@Test
	public void testParse3() {
		new Expectations() {
			{
				innerParser.parse("test");
				result = new ParseResult(null, "test");
			}
		};

		IteratorParser parser = new IteratorParser(innerParser, Iterations.ZERO_OR_MORE);

		ParseResult result = parser.parse("test");
		assertEquals(new ParseTreeLeaf(""), result.getParseTree());
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.IteratorParser#doParse(java.lang.String)}.
	 */
	@Test
	public void testParse4() {
		new Expectations() {
			{
				innerParser.parse("test");
				result = new ParseResult(new ParseTreeLeaf("t"), "est");

				innerParser.parse("est");
				result = new NilParseResult("est");
			}
		};

		IteratorParser parser = new IteratorParser(innerParser, Iterations.ONE_OR_MORE);

		ParseResult result = parser.parse("test");
		assertEquals(leaf("t"), result.getParseTree());
	}

}
