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

import org.junit.Before;
import org.junit.Test;

import com.github.meristem.DigitParser;
import com.github.meristem.ParseResult;
import com.github.meristem.ParseTreeLeaf;

/**
 * Tests for {@link DigitParser}.
 *
 */
public class DigitParserTest {

	private DigitParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		parser = new DigitParser();
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.AbstractCharacterParser#doParse(java.lang.String)}
	 * .
	 */
	@Test
	public void testDoParse1() {
		ParseResult result = parser.parse("123test");

		assertEquals(new ParseTreeLeaf("1"), result.getParseTree());
		assertEquals("23test", result.getRemainder());
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.AbstractCharacterParser#doParse(java.lang.String)}
	 * .
	 */
	@Test
	public void testDoParse2() {
		ParseResult result = parser.parse("test");

		assertEquals(null, result.getParseTree());
		assertEquals("test", result.getRemainder());
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.AbstractCharacterParser#doParse(java.lang.String)}
	 * .
	 */
	@Test
	public void testDoParse3() {
		ParseResult result = parser.parse("");

		assertEquals(null, result.getParseTree());
		assertEquals("", result.getRemainder());
	}

}
