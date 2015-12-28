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

import java.util.ArrayList;
import java.util.List;

/**
 * Parser operator which takes one or more parsers and run them in sequence,
 * concatinating their output and returning the new output as long as all
 * parsers return a non-nil parse result.
 *
 * @see NilParseResult
 */
public class AndParser extends AbstractCompositeParser {

	public AndParser(Parser... parsers) {
		super(parsers);
	}

	/* (non-Javadoc)
	 * @see com.github.meristem.AbstractCompositeParser#doParse(java.lang.String, java.util.List)
	 */
	@Override
	protected ParseResult doParse(String input, List<Parser> parsers) {
		String originalInput = input;

		List<ParseTree> trees = new ArrayList<ParseTree>();
		for (Parser parser : parsers) {
			ParseResult parseResult = parser.parse(input);

			if (parseResult.isNull()) {
				return new ParseResult(null, originalInput);
			}

			trees.add(parseResult.getParseTree());

			input = parseResult.getRemainder();
		}

		ParseTree root = null;
		for (int i = trees.size() - 1; i >= 0; i--) {
			if (root == null) {
				root = trees.get(i);
			} else {
				root = new ParseTreeNode(trees.get(i), root);
			}
		}

		return new ParseResult(root, input);
	}
}
