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
 * A parser which will try to parse a single one of the provided characters. 
 *
 */
public class AnyOfParser extends AbstractCharacterParser {

	private String anyOf;

	public AnyOfParser(String anyOf) {
		if (anyOf == null) {
			throw new IllegalArgumentException("Input can not be null!");
		}
		
		this.anyOf = anyOf;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.CharacterParser#isMatch(java.lang.Character)
	 */
	@Override
	public boolean isMatch(Character c) {
		return anyOf.contains(c.toString());
	}

}
