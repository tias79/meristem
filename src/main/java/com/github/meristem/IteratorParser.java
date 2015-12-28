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
 * Operator parser which uses a given parser to repeatedly execute it on the
 * input.
 *
 * @see Iterations
 */
public class IteratorParser extends AbstractParser {

	private Parser parser;

	private int maxIterations;

	private int minIterations;

	/**
	 * @param parser
	 *            The parser to execute upon every iteration.
	 * @param iterations
	 *            Fixed number of times to execute the parser, no more on less.
	 *            If the given number of executions can not be achieved, a null
	 *            parse result is returned.
	 */
	public IteratorParser(Parser parser, int iterations) {
		this.parser = parser;
		this.minIterations = iterations;
		this.maxIterations = iterations;
	}

	/**
	 * @param parser
	 *            The parser to execute upon every iteration.
	 * @param iterations
	 *            The iterations range. If a number of executions within the
	 *            given range can not be avhieve, a null parse result is
	 *            returned.
	 */
	public IteratorParser(Parser parser, Iterations iterations) {
		this.parser = parser;

		switch (iterations) {
		case ONE_OR_MORE:
			this.minIterations = 1;
			this.maxIterations = -1;
			break;
		case ZERO_OR_MORE:
			this.minIterations = 0;
			this.maxIterations = -1;
			break;
		default:
			throw new UnsupportedOperationException("Not implemented: "
					+ iterations.toString());
		}
	}

	/* (non-Javadoc)
	 * @see com.github.meristem.AbstractParser#doParse(java.lang.String)
	 */
	@Override
	protected ParseResult doParse(String input) {
		List<ParseResult> results = new ArrayList<ParseResult>();

		String remainder = input;
		for (int i = 0; i < maxIterations || maxIterations == -1; i++) {
			ParseResult result = parser.parse(remainder);
			if (result.getParseTree() != null) {
				remainder = result.getRemainder();
				results.add(result);
			} else {
				if (i >= minIterations) {
					if (results.isEmpty()) {
						results.add(new ParseResult(new ParseTreeLeaf(""),
								input));
					}
				} else {
					if (results.isEmpty()) {
						results.add(new NilParseResult(remainder));
					}
				}

				return concatParseResults(results);
			}
		}
		return concatParseResults(results);
	}
}