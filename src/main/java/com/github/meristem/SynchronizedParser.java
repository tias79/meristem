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
 * Operator parser which mimics the parsing behavior of the parser which it
 * wraps. If the parse result of both parsers are identical, the
 * SynchronizedParser will return that result, otherwise it will return a nil
 * parse result. The SynchronizedParser will expect the wrapped parser to be
 * executed first, exactly one time.
 *
 */
public class SynchronizedParser extends AbstractParser implements
		ParserListener {

	private Parser parserOriginal;

	private Parser parserClone;

	private ParseResult otherParseResult;

	public SynchronizedParser(Parser parser) {
		parserOriginal = parser;

		parserOriginal.addEventListener(this);

		try {
			this.parserClone = parser.clone();
		} catch (CloneNotSupportedException e) {
			// As long as Parser implements Cloneable, this shouldn't happen.
			throw new IllegalArgumentException(
					"Only clonable Parser objects are allowed!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.AbstractParser#doParse(java.lang.String)
	 */
	@Override
	protected ParseResult doParse(String input) {
		if (this.otherParseResult == null) {
			throw new ParsingSequenceException(parserOriginal, parserClone);
		}

		ParseResult parseResult = parserClone.parse(input);
		if (parseResult.getParseTree().equals(otherParseResult.getParseTree())) {
			return parseResult;
		}

		return new NilParseResult(input);
	}

	/* (non-Javadoc)
	 * @see com.github.meristem.ParserListener#onParse(java.lang.String, com.github.meristem.ParseResult)
	 */
	@Override
	public void onParse(String parserId, ParseResult parseResult) {
		if (this.otherParseResult != null) {
			throw new ParsingSequenceException(parserOriginal, parserClone);
		}

		this.otherParseResult = parseResult;
	}

}
