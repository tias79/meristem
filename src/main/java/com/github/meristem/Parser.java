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
 * A parser is something that consumes a string and produces a parse reslt.
 * 
 * For convenience, the parser interface includes a number of methods for
 * creating parse operator (parsers which in turn take other parsers as input).
 *
 * @see ParseResult
 */
public interface Parser extends Cloneable {

	/**
	 * Parse a string.
	 * 
	 * @param input
	 *            Input string.
	 * @return A {@link ParseResult}.
	 */
	ParseResult parse(String input);

	/**
	 * Do a negated parse of a string. A call to this method is expected to
	 * return what would be the negation of the result returned from
	 * parse(String) when it's being called with an equivalent input string.
	 * What this actually means is up to the {@link Parser} implementation.
	 * 
	 * @param input
	 *            Input string.
	 * @return A {@link ParseResult}.
	 */
	ParseResult negatedParse(String input);

	/**
	 * Set the parser's identifier. This identifier isn't used internally by
	 * Meristem and thus needn't be unique (or set at all).
	 * 
	 * @param id
	 *            An identifier or null.
	 */
	void setId(String id);

	/**
	 * Get the parser's identifier. This identifier isn't used internally by
	 * Meristem and thus needn't be unique (or set at all).
	 * 
	 * @return The identifier or null.
	 */
	String getId();

	/**
	 * Add an {@link ParserListener} event listener to the parser, to pick up on
	 * events.
	 * 
	 * @param listener
	 *            {@link ParserListener} object.
	 */
	void addEventListener(ParserListener listener);

	/**
	 * This is a convenience method for creating an {@link AndParser} instance
	 * containing the current parser and an additional parser. The current
	 * parser is expected to take precedence over the parser passed as a
	 * parameter to the method. Meaning the current parser should be executed
	 * first when the new {@link AndParser} is executed.
	 * 
	 * The {@link AbstractParser} implementation of this method does not create
	 * a new {@link AndParser} instance when the current parser already is an
	 * instance of {@link AndParser}. In this case the parser, passed as a
	 * parameter to the method, is merely appended to the end of the list of
	 * parsers contained by the current {@link AndParser} instance. Meaning the
	 * other parsers should take precedence over the parser passed as an
	 * argument to the method.
	 * 
	 * @param otherParser
	 *            {@link Parser} instance to use together with the current
	 *            parser.
	 * @return An {@link AndParser} instance.
	 */
	Parser and(Parser otherParser);

	/**
	 * This is a convenience method for creating an {@link OrParser} instance
	 * containing the current parser and an additional parser. The current
	 * parser is expected to take precedence over the parser passed as a
	 * parameter to the method. Meaning the current parser should be executed
	 * first when the new {@link OrParser} is executed.
	 * 
	 * The {@link AbstractParser} implementation of this method does not create
	 * a new {@link OrParser} instance when the current parser already is an
	 * instance of {@link OrParser}. In this case the parser, passed as a
	 * parameter to the method, is merely appended to the end of the list of
	 * parsers contained by the current {@link OrParser} instance. Meaning the
	 * other parsers should take precedence over the parser passed as an
	 * argument to the method.
	 * 
	 * @param otherParser
	 *            {@link Parser} instance to use together with the current
	 *            parser.
	 * @return An {@link OrParser} instance.
	 */
	Parser or(Parser otherParser);

	/**
	 * This is a convenience method for creating an {@link ExclusiveOrParser} instance
	 * containing the current parser and an additional parser. The current
	 * parser is expected to take precedence over the parser passed as a
	 * parameter to the method. Meaning the current parser should be executed
	 * first when the new {@link ExclusiveOrParser} is executed.
	 * 
	 * The {@link AbstractParser} implementation of this method does not create
	 * a new {@link ExclusiveOrParser} instance when the current parser already is an
	 * instance of {@link ExclusiveOrParser}. In this case the parser, passed as a
	 * parameter to the method, is merely appended to the end of the list of
	 * parsers contained by the current {@link ExclusiveOrParser} instance. Meaning the
	 * other parsers should take precedence over the parser passed as an
	 * argument to the method.
	 * 
	 * @param otherParser
	 *            {@link Parser} instance to use together with the current
	 *            parser.
	 * @return An {@link ExclusiveOrParser} instance.
	 */
	Parser xor(Parser otherParser);

	/**
	 * This is a convenience method for creating an {@link ConcatParser} instance
	 * containing the current parser and an additional parser. The current
	 * parser is expected to take precedence over the parser passed as a
	 * parameter to the method. Meaning the current parser should be executed
	 * first when the new {@link ConcatParser} is executed.
	 * 
	 * The {@link AbstractParser} implementation of this method does not create
	 * a new {@link ConcatParser} instance when the current parser already is an
	 * instance of {@link ConcatParser}. In this case the parser, passed as a
	 * parameter to the method, is merely appended to the end of the list of
	 * parsers contained by the current {@link ConcatParser} instance. Meaning the
	 * other parsers should take precedence over the parser passed as an
	 * argument to the method.
	 * 
	 * @param otherParser
	 *            {@link Parser} instance to use together with the current
	 *            parser.
	 * @return An {@link ConcatParser} instance.
	 */
	Parser cat(Parser otherParser);

	/**
	 * This overrides the default cloning behavior, causing the ID and the set
	 * of registered event listeners to be reset upon clone.
	 * 
	 * @return A {@link Parser} object clone.
	 * @throws CloneNotSupportedException
	 */
	Parser clone() throws CloneNotSupportedException;

}
