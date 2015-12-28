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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import mockit.integration.junit4.JMockit;
import mockit.Expectations;
import mockit.Mocked;

import org.junit.runner.RunWith;

import com.github.meristem.AndParser;
import com.github.meristem.ParseResult;
import com.github.meristem.ParseTreeLeaf;
import com.github.meristem.ParseTreeNode;
import com.github.meristem.Parser;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link AndParser}.
 *
 */
@RunWith(JMockit.class)
public class AndParserTest {

	@Mocked
	private Parser parser1;

	@Mocked
	private Parser parser2;

	@Mocked
	private Parser parser3;

	private AndParser andParser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		andParser = new AndParser(parser1, parser2, parser3);
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.AndParser#parse(java.lang.String)}.
	 */
	@Test
	public void testParse1() {
		new Expectations() {

			{
				parser1.parse("test");
				result = new ParseResult(null, "test");
			}
		};

		ParseResult result = andParser.parse("test");
		assertNotNull(result);
		assertEquals(true, result.isNull());
		assertEquals("test", result.getRemainder());
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.AndParser#parse(java.lang.String)}.
	 */
	@Test
	public void testParse2() {
		new Expectations() {
			{
				parser1.parse("aaaabbbb");
				result = new ParseResult(new ParseTreeLeaf("aaaa"), "bbbb");
				parser2.parse("bbbb");
				result = new ParseResult(null, "bbbb");
			}
		};

		ParseResult result = andParser.parse("aaaabbbb");
		assertNotNull(result);
		assertNull(result.getParseTree());
		assertEquals("aaaabbbb", result.getRemainder());
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.AndParser#parse(java.lang.String)}.
	 */
	@Test
	public void testParse3() {
		new Expectations() {
			{
				parser1.parse("aaaabbbbcccc");
				result = new ParseResult(new ParseTreeLeaf("aaaa"), "bbbbcccc");
				parser2.parse("bbbbcccc");
				result = new ParseResult(new ParseTreeLeaf("bbbb"), "cccc");
				parser3.parse("cccc");
				result = new ParseResult(null, "cccc");
			}
		};

		ParseResult result = andParser.parse("aaaabbbbcccc");
		assertNotNull(result);
		assertNull(result.getParseTree());
		assertEquals("aaaabbbbcccc", result.getRemainder());
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.AndParser#parse(java.lang.String)}.
	 */
	@Test
	public void testParse4() {
		new Expectations() {
			{
				parser1.parse("aaaabbbbcccc");
				result = new ParseResult(new ParseTreeLeaf("aaaa"), "bbbbcccc");
				parser2.parse("bbbbcccc");
				result = new ParseResult(new ParseTreeLeaf("bbbb"), "cccc");
				parser3.parse("cccc");
				result = new ParseResult(new ParseTreeLeaf("cccc"), "");
			}
		};

		ParseResult result = andParser.parse("aaaabbbbcccc");
		assertNotNull(result);
		assertEquals(new ParseTreeNode(new ParseTreeLeaf("aaaa"),
				new ParseTreeNode(new ParseTreeLeaf("bbbb"), new ParseTreeLeaf(
						"cccc"))), result.getParseTree());
		assertEquals("", result.getRemainder());
	}

	/**
	 * Test method for
	 * {@link com.github.meristem.AndParser#parse(java.lang.String)}.
	 */
	@Test
	public void testParse5() {
		new Expectations() {
			{
				parser1.parse("aaaabbbbccccdddd");
				result = new ParseResult(new ParseTreeLeaf("aaaa"),
						"bbbbccccdddd");
				parser2.parse("bbbbccccdddd");
				result = new ParseResult(new ParseTreeLeaf("bbbb"), "ccccdddd");
				parser3.parse("ccccdddd");
				result = new ParseResult(new ParseTreeLeaf("cccc"), "dddd");
			}
		};

		ParseResult result = andParser.parse("aaaabbbbccccdddd");
		assertNotNull(result);
		assertEquals(new ParseTreeNode(new ParseTreeLeaf("aaaa"),
				new ParseTreeNode(new ParseTreeLeaf("bbbb"), new ParseTreeLeaf(
						"cccc"))), result.getParseTree());
		assertEquals("dddd", result.getRemainder());
	}

}
