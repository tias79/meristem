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
 * Operator parser which will return a leaf with a given value if the wrapped
 * parser returns a nil result. If the wrapped parser returns a non-nil result,
 * the DefaultParser instance will return the result of the inner parser.
 *
 * @see NilParseResult
 */
public class DefaultParser extends AbstractParser {

	private Parser parser;

	private String defaultLeafValue;

	/**
	 * @param parser
	 *            The parser to be wrapped.
	 */
	public DefaultParser(Parser parser, String defaultLeafValue) {
		this.parser = parser;
		this.defaultLeafValue = defaultLeafValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.AbstractParser#doParse(java.lang.String)
	 */
	@Override
	protected ParseResult doParse(String input) {
		ParseResult parseResult = parser.parse(input);
		if (parseResult.isNull()) {
			return new ParseResult(ParseTreeUtils.leaf(defaultLeafValue), parseResult.getRemainder());
		}
		return parseResult;
	}
}
