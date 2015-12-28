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
 * A CompositeParser combines the logic of two or more parsers.
 *
 */
public interface CompositeParser {

	/**
	 * Append a parser to the composite parser.
	 * 
	 * @param parser
	 */
	void appendParser(Parser parser);

	/**
	 * Retrieve the list of parsers contained by the composite parser.
	 * 
	 * @return A list of parsers which the current parser contains.
	 */
	List<Parser> getParsers();
}
