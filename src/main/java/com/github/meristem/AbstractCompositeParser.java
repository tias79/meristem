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
import java.util.Arrays;
import java.util.List;

/**
 * Abstract implementation of a CompositeParser.
 */
public abstract class AbstractCompositeParser extends AbstractParser implements CompositeParser {

	private List<Parser> parsers;

	public AbstractCompositeParser(Parser... parsers) {
		if (parsers == null || parsers.length == 0) {
			throw new IllegalArgumentException("Must have at least one Parser object as input!");
		}

		this.parsers = new ArrayList<Parser>(Arrays.asList(parsers));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.AbstractParser#doParse(java.lang.String)
	 */
	@Override
	protected final ParseResult doParse(String input) {
		return doParse(input, parsers);
	}

	protected abstract ParseResult doParse(String input, List<Parser> parsers);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.meristem.CompositeParser#appendParser(com.github.meristem.
	 * Parser)
	 */
	@Override
	public void appendParser(Parser parser) {
		parsers.add(parser);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.CompositeParser#getParsers()
	 */
	@Override
	public List<Parser> getParsers() {
		return new ArrayList<Parser>(parsers);
	}
}