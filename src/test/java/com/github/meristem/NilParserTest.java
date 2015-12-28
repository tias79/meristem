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

import com.github.meristem.NilParser;
import com.github.meristem.ParseResult;
import com.github.meristem.ParseTreeLeaf;
import com.github.meristem.Parser;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link NilParserTest}.
 * 
 */
@RunWith(JMockit.class)
public class NilParserTest {

	@Mocked
	private Parser innerParser;

	private NilParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		parser = new NilParser(innerParser);
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.NilParser#doParse(java.lang.String)}.
	 */
	@Test
	public void testDoParse() {
		new Expectations() {

			{
				innerParser.parse("test");
				result = new ParseResult(new ParseTreeLeaf("te"), "st");
			}
		};
		
		ParseResult result = parser.doParse("test");
		
		assertNull(result.getParseTree());
		assertEquals("st", result.getRemainder());
	}

}
