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

import org.junit.Before;
import org.junit.Test;

import com.github.meristem.ParseResult;
import com.github.meristem.StringParser;

/**
 * Tests for {@link StringParser}.
 *
 */
public class StringParserTest {

	private StringParser parser;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		parser = new StringParser("xx1");
	}

	/**
	 * Test method for {@link com.github.meristem.StringParser#doParse(java.lang.String)}.
	 */
	@Test
	public void testParse1() {
		ParseResult result = parser.parse("xx1test");
		assertEquals(leaf("xx1"), result.getParseTree());
		assertEquals("test", result.getRemainder());
	}

	/**
	 * Test method for {@link com.github.meristem.StringParser#doParse(java.lang.String)}.
	 */
	@Test
	public void testParse2() {
		ParseResult result = parser.parse("xx1");
		assertEquals(leaf("xx1"), result.getParseTree());
		assertEquals("", result.getRemainder());
	}

	/**
	 * Test method for {@link com.github.meristem.StringParser#doParse(java.lang.String)}.
	 */
	@Test
	public void testParse3() {
		ParseResult result = parser.parse("xxtest");
		assertEquals(true, result.isNull());
		assertEquals("xxtest", result.getRemainder());
	}

	
}
