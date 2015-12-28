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

/**
 * Parser parsing an exact string.
 *
 */
public class StringParser extends AbstractParser {

	private String literal;

	public StringParser(String str) {
		if (str == null) {
			throw new IllegalArgumentException("String can not be null!");
		}

		this.literal = str;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.AbstractParser#doParse(java.lang.String)
	 */
	public ParseResult doParse(String input) {
		boolean match = true;

		if (input.length() < literal.length()) {
			match = false;
		} else {
			for (int i = 0; i < literal.length(); i++) {
				if (literal.charAt(i) != input.charAt(i)) {
					match = false;
					break;
				}
			}
		}

		if (!match) {
			return new NilParseResult(input);
		}

		ParseTree parseTree = ParseTreeUtils.leaf(literal);

		return new ParseResult(parseTree, input.substring(literal.length()));
	}

}
