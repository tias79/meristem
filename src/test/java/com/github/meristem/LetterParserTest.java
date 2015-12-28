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

import com.github.meristem.LetterParser;
import com.github.meristem.ParseResult;

/**
 * Tests for {@link LetterParser}.
 *
 */
public class LetterParserTest {

	private LetterParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		parser = new LetterParser();
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.LetterParser#isMatch(java.lang.Character)}.
	 */
	@Test
	public void testIsMatch() {
		assertTrue(parser.isMatch('a'));
		assertTrue(parser.isMatch('Z'));
		assertFalse(parser.isMatch('0'));
		assertFalse(parser.isMatch('!'));
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.AbstractCharacterParser#parse(java.lang.String)}
	 * .
	 */
	@Test
	public void testParse_match() {
		ParseResult result = parser.parse("a5gd");
		assertNotNull(result.getParseTree());
		assertTrue(result.getParseTree().isLeaf());
		assertEquals("a", result.getParseTree().getLeafValue());
		assertEquals("5gd", result.getRemainder());
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.AbstractCharacterParser#parse(java.lang.String)}
	 * .
	 */
	@Test
	public void testParse_noMatch1() {
		ParseResult result = parser.parse("5agd");
		assertNull(result.getParseTree());
		assertEquals("5agd", result.getRemainder());
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.AbstractCharacterParser#parse(java.lang.String)}
	 * .
	 */
	@Test
	public void testParse_noMatch2() {
		ParseResult result = parser.parse("");
		assertNull(result.getParseTree());
		assertEquals("", result.getRemainder());
	}

}
