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
 * The exception of parsers being executed in the wrong order.
 *
 */
public class ParsingSequenceException extends RuntimeException {

	private static final long serialVersionUID = -6748130575288765451L;

	private Parser neededFirst;

	private Parser neededSecond;

	public ParsingSequenceException(Parser neededFirst, Parser neededSecond) {
		super("Parser \"" + neededFirst.getId() == null ? "" : neededFirst
				.getId()
				+ "\" of type "
				+ neededFirst.getClass().getSimpleName()
				+ " needs to be exeuted before parser \""
				+ neededSecond.getId() == null ? "" : neededSecond.getId()
				+ "\" of type " + neededSecond.getClass().getSimpleName() + ".");

		this.neededFirst = neededFirst;
		this.neededSecond = neededSecond;
	}

	public Parser getNeededFirst() {
		return neededFirst;
	}

	public Parser getNeededSecond() {
		return neededSecond;
	}

}
