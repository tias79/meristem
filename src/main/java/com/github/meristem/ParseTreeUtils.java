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
 * Utility methods for creating and working with parse trees.
 *
 * @see ParseTree
 */
public class ParseTreeUtils {

	public static ParseTree node(String parserId, ParseTree left,
			ParseTree right) {
		return new ParseTreeNode(parserId, left, right);
	}

	public static ParseTree node(ParseTree left, ParseTree right) {
		return node(null, left, right);
	}

	public static ParseTree leaf(String value) {
		return new ParseTreeLeaf(value);
	}

	public static MutableParseTree copy(ParseTree source) {
		if (source.isLeaf()) {
			return new ParseTreeLeaf(source.getLeafValue());
		} else {
			return new ParseTreeNode(source.getParserId(),
					source.getLeftBranch(), source.getRightBranch());
		}
	}
}
