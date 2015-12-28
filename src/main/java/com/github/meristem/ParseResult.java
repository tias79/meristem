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
 * A parse result consists of a parse tree and a remainder string. The parse
 * tree is the actual result of the parse, representing one or more string
 * tokens (could be an empty string) in a parse tree. The remainder string is
 * the part of the input string which was not consumed by the parser. The
 * remainder string could be an empty string if all characters from the input
 * string were consumed.
 *
 */
public class ParseResult {

	private ParseTree parseTree;

	private String remainder;

	public ParseResult(ParseTree parseTree, String remainder) {
		this.parseTree = parseTree;
		this.remainder = remainder;
	}

	public ParseTree getParseTree() {
		return parseTree;
	}

	public String getRemainder() {
		return remainder;
	}

	public boolean isNull() {
		return parseTree == null;
	}
}
