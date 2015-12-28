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
 * A parse tree leaf carries a value object - a string.
 * 
 * @see ParseTree
 * @see ParseTreeNode
 */
public class ParseTreeLeaf implements MutableParseTree {

	private String value;

	public ParseTreeLeaf(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.ParseTree#isLeaf()
	 */
	public boolean isLeaf() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.ParseTree#getLeafValue()
	 */
	public String getLeafValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.ParseTree#getMatchBranch()
	 */
	public ParseTree getLeftBranch() {
		throw new IllegalStateException("A leaf can have no left branch!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.meristem.ParseTree#setMatchBranch(com.github.meristem
	 * .ParseTree )
	 */
	public void setLeftBranch(ParseTree matchBranch) {
		throw new IllegalStateException("A leaf can have no left branch!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.ParseTree#getRemainderBranch()
	 */
	public ParseTree getRightBranch() {
		throw new IllegalStateException("A leaf can have no right branch!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.meristem.ParseTree#setRemainderBranch(com.github.meristem
	 * .ParseTree)
	 */
	public void setRightBranch(ParseTree remainderBranch) {
		throw new IllegalStateException("A leaf can have no right branch!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.ParseTree#getParserId()
	 */
	public String getParserId() {
		throw new IllegalStateException("A leaf can have no parser ID!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParseTreeLeaf other = (ParseTreeLeaf) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Leaf (\"" + (value == null ? "null" : value) + "\")";
	}

	public String toString(int indent) {
		String str = "";
		for (int i = 0; i < indent; i++) {
			str += "\t";
		}

		return str + toString();
	}
}
