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

import static org.junit.Assert.*;
import mockit.integration.junit4.JMockit;
import mockit.Expectations;
import mockit.Mocked;

import org.junit.runner.RunWith;

import com.github.meristem.ConcatParser;
import com.github.meristem.ParseResult;
import com.github.meristem.ParseTreeLeaf;
import com.github.meristem.ParseTreeNode;
import com.github.meristem.Parser;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link ConcatParser}.
 *
 */
@RunWith(JMockit.class)
public class ConcatParserTest {

	@Mocked
	private Parser parser1;

	@Mocked
	private Parser parser2;

	@Mocked
	private Parser parser3;

	private ConcatParser concatParser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		concatParser = new ConcatParser(parser1, parser2, parser3);
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.ConcatParser#doParse(java.lang.String)}.
	 */
	@Test
	public void testParse1() {
		new Expectations() {

			{
				parser1.parse("test");
				result = new ParseResult(null, "test");

				parser2.parse("test");
				result = new ParseResult(null, "test");

				parser3.parse("test");
				result = new ParseResult(null, "test");
			}
		};

		ParseResult result = concatParser.parse("test");
		assertNotNull(result);
		assertNull(result.getParseTree());
		assertEquals("test", result.getRemainder());
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.ConcatParser#doParse(java.lang.String)}.
	 */
	@Test
	public void testParse2() {
		new Expectations() {
			{
				parser1.parse("test");
				result = new ParseResult(null, "test");

				parser2.parse("test");
				result = new ParseResult(new ParseTreeLeaf("te"), "st");

				parser3.parse("st");
				result = new ParseResult(new ParseTreeLeaf("st"), "");
			}
		};

		ParseResult result = concatParser.parse("test");
		assertNotNull(result);
		assertEquals(new ParseTreeNode(new ParseTreeLeaf("te"),
				new ParseTreeLeaf("st")), result.getParseTree());
		assertEquals("", result.getRemainder());
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.ConcatParser#doParse(java.lang.String)}.
	 */
	@Test
	public void testParse3() {
		new Expectations() {
			{
				parser1.parse("test");
				result = new ParseResult(null, "st");

				parser2.parse("st");
				result = new ParseResult(new ParseTreeLeaf("s"), "t");

				parser3.parse("t");
				result = new ParseResult(new ParseTreeLeaf("t"), "");
			}
		};

		ParseResult result = concatParser.parse("test");
		assertNotNull(result);
		assertEquals(new ParseTreeNode(new ParseTreeLeaf("s"),
				new ParseTreeLeaf("t")), result.getParseTree());
		assertEquals("", result.getRemainder());
	}

}
