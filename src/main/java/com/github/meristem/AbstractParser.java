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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Abstract parser implementation which implements functionality common to all
 * parsers.
 *
 * @see Parser
 */
public abstract class AbstractParser implements Parser, Cloneable {

	private String id;

	private Collection<ParserListener> listeners = new HashSet<ParserListener>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.Parser#parse(java.lang.String)
	 */
	@Override
	public final ParseResult parse(String input) {
		ParseResult parseResult = doParse(input);

		for (ParserListener listener : listeners) {
			listener.onParse(getId(), parseResult);
		}

		return parseResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.Parser#negatedParse(java.lang.String)
	 */
	@Override
	public final ParseResult negatedParse(String input) {
		ParseResult parseResult = doNegatedParse(input);

		for (ParserListener listener : listeners) {
			listener.onParse(getId(), parseResult);
		}

		return parseResult;
	}

	/**
	 * Parsers extending this class has to implement this method, rather than
	 * the parse(String) method declared in the {@link Parser} interface.
	 * 
	 * @param input
	 *            Input String.
	 * @return {@link ParseResult} instance.
	 */
	protected abstract ParseResult doParse(String input);

	/**
	 * A default implementation for negated parsing is provided, but where it is
	 * insufficient the parser implementation is expected to override this
	 * method.
	 * 
	 * @param input
	 *            Input string.
	 * @return {@link ParseResult} instance.
	 */
	protected ParseResult doNegatedParse(String input) {
		ParseResult result = doParse(input);

		if (result.isNull()) {
			return new ParseResult(ParseTreeUtils.leaf(input), "");
		} else {
			return new NilParseResult(input);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.Parser#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.Parser#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.Parser#addEventListener(com.github.meristem.
	 * ParserListener)
	 */
	@Override
	public void addEventListener(ParserListener listener) {
		listeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.Parser#and(com.github.meristem.Parser)
	 */
	@Override
	public Parser and(Parser otherParser) {
		if (this instanceof AndParser) {
			((AndParser) this).appendParser(otherParser);
			return this;
		} else {
			return new AndParser(this, otherParser);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.Parser#cat(com.github.meristem.Parser)
	 */
	@Override
	public Parser cat(Parser otherParser) {
		if (this instanceof ConcatParser) {
			((ConcatParser) this).appendParser(otherParser);
			return this;
		} else {
			return new ConcatParser(this, otherParser);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.Parser#or(com.github.meristem.Parser)
	 */
	@Override
	public Parser or(Parser otherParser) {
		if (this instanceof OrParser) {
			((OrParser) this).appendParser(otherParser);
			return this;
		} else {
			return new OrParser(this, otherParser);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.Parser#xor(com.github.meristem.Parser)
	 */
	@Override
	public Parser xor(Parser otherParser) {
		if (this instanceof ExclusiveOrParser) {
			((ExclusiveOrParser) this).appendParser(otherParser);
			return this;
		} else {
			return new ExclusiveOrParser(this, otherParser);
		}
	}

	protected ParseResult concatParseResults(List<ParseResult> results) {

		if (results == null || results.isEmpty()) {
			throw new IllegalArgumentException("Can not concatinate 0 ParseResult objects.");
		}

		String newRemainder = null;
		ParseTree newTree = null;

		for (int j = results.size() - 1; j >= 0; j--) {
			if (newTree == null) {
				newTree = results.get(j).getParseTree();
				newRemainder = results.get(j).getRemainder();
			} else {
				newTree = new ParseTreeNode(results.get(j).getParseTree(), newTree);
			}
		}

		return new ParseResult(newTree, newRemainder);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.Parser#clone()
	 */
	@Override
	public Parser clone() throws CloneNotSupportedException {
		AbstractParser newParser = null;

		newParser = (AbstractParser) super.clone();

		newParser.id = null;
		newParser.listeners = new HashSet<ParserListener>();

		return newParser;
	}
}
