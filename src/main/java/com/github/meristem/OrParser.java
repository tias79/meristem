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

import java.util.List;

/**
 * Operator parser which executes given parsers on the input, in sequence,
 * expecting at least one of them to return a non-nil parse result. Evaluation
 * is lazy and the result from the first parser returning a non-nil result is
 * returned.
 *
 */
public class OrParser extends AbstractCompositeParser {

	public OrParser(Parser... parsers) {
		super(parsers);
	}

	/* (non-Javadoc)
	 * @see com.github.meristem.AbstractCompositeParser#doParse(java.lang.String, java.util.List)
	 */
	@Override
	protected ParseResult doParse(String input, List<Parser> parsers) {
		for (Parser parser : parsers) {
			ParseResult result = parser.parse(input);
			if (!result.isNull()) {
				return result;
			}
		}

		return new NilParseResult(input);
	}

}
