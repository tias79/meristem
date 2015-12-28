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
 * Operator parsers which reduces the parse tree in the parse result from a
 * given parser to a single leaf, by concatinating all string tokens in the
 * tree.
 *
 */
public class PruneParser extends AbstractParser {

	private Parser parser;

	public PruneParser(Parser parser) {
		if (parser == null) {
			throw new IllegalArgumentException("Input can not be null!");
		}

		this.parser = parser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.AbstractParser#doParse(java.lang.String)
	 */
	@Override
	protected ParseResult doParse(String input) {
		ParseResult parseResult = parser.parse(input);

		String value = prune(parseResult.getParseTree());

		return new ParseResult(new ParseTreeLeaf(value),
				parseResult.getRemainder());
	}

	private String prune(ParseTree parseTree) {
		if (parseTree.isLeaf()) {
			return parseTree.getLeafValue() == null ? "" : parseTree
					.getLeafValue();
		} else {
			return prune(parseTree.getLeftBranch())
					+ prune(parseTree.getRightBranch());
		}
	}

}
