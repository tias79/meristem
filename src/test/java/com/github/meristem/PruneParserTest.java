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

import static com.github.meristem.ParseTreeUtils.leaf;
import static com.github.meristem.ParseTreeUtils.node;
import static org.junit.Assert.assertEquals;
import mockit.integration.junit4.JMockit;
import mockit.Expectations;
import mockit.Mocked;

import org.junit.runner.RunWith;

import com.github.meristem.ParseResult;
import com.github.meristem.Parser;
import com.github.meristem.PruneParser;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link PruneParser}.
 *
 */
@RunWith(JMockit.class)
public class PruneParserTest {

	private PruneParser parser;

	@Mocked
	private Parser innerParser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		parser = new PruneParser(innerParser);
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.PruneParser#doParse(java.lang.String)}.
	 */
	@Test
	public void testParse1() {
		new Expectations() {
			{
				innerParser.parse("test");
				result = new ParseResult(node(node(leaf("a"), leaf("b")),
						node(leaf("c"), leaf("d"))), "remainder");
			}
		};

		ParseResult result = parser.parse("test");
		assertEquals(leaf("abcd"), result.getParseTree());
		assertEquals("remainder", result.getRemainder());
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.PruneParser#doParse(java.lang.String)}.
	 */
	@Test
	public void testParse2() {
		new Expectations() {
			{
				innerParser.parse("test");
				result = new ParseResult(leaf("test"), "remainder");
			}
		};

		ParseResult result = parser.parse("test");
		assertEquals(leaf("test"), result.getParseTree());
		assertEquals("remainder", result.getRemainder());
	}

}
