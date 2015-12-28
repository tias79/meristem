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

import com.github.meristem.ExclusiveOrParser;
import com.github.meristem.ParseResult;
import com.github.meristem.ParseTreeLeaf;
import com.github.meristem.Parser;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link ExclusiveOrParser}.
 *
 */
@RunWith(JMockit.class)
public class ExclusiveOrParserTest {

	@Mocked
	private Parser parser1;

	@Mocked
	private Parser parser2;

	@Mocked
	private Parser parser3;

	private ExclusiveOrParser xorParser;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		xorParser = new ExclusiveOrParser(parser1, parser2, parser3);
	}

	/**
	 * Test method for {@link com.github.meristem.ExclusiveOrParser#doParse(java.lang.String)}.
	 */
	@Test
	public void testDoParse1() {
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
		
		ParseResult result = xorParser.parse("test");
		assertEquals(null, result.getParseTree());
		assertEquals("test", result.getRemainder());
	}

	/**
	 * Test method for {@link com.github.meristem.ExclusiveOrParser#doParse(java.lang.String)}.
	 */
	@Test
	public void testDoParse2() {
		new Expectations() {
			{
				parser1.parse("test");
				result = new ParseResult(null, "test");

				parser2.parse("test");
				result = new ParseResult(new ParseTreeLeaf("t"), "est");

				parser3.parse("test");
				result = new ParseResult(null, "test");
			}
		};
		
		ParseResult result = xorParser.parse("test");
		assertEquals(new ParseTreeLeaf("t"), result.getParseTree());
		assertEquals("est", result.getRemainder());
	}

	/**
	 * Test method for {@link com.github.meristem.ExclusiveOrParser#doParse(java.lang.String)}.
	 */
	@Test
	public void testDoParse3() {
		new Expectations() {
			{
				parser1.parse("test");
				result = new ParseResult(null, "test");

				parser2.parse("test");
				result = new ParseResult(new ParseTreeLeaf("t"), "est");

				parser3.parse("test");
				result = new ParseResult(new ParseTreeLeaf("te"), "st");
			}
		};
		
		ParseResult result = xorParser.parse("test");
		assertEquals(null, result.getParseTree());
		assertEquals("test", result.getRemainder());
	}

}
