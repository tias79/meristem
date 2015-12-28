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
 * A parse tree node carries two branches, each containing another node or a leaf.
 *
 * @see ParseTreeLeaf
 * @see ParseTree
 */
public class ParseTreeNode implements MutableParseTree {

	private String parserId;
	private ParseTree leftBranch;
	private ParseTree rightBranch;

	public ParseTreeNode(ParseTree leftBranch, ParseTree rightBranch) {
		this(null, leftBranch, rightBranch);
	}

	public ParseTreeNode(String parserId, ParseTree leftBranch,
			ParseTree rightBranch) {
		this.parserId = parserId;
		this.leftBranch = leftBranch;
		this.rightBranch = rightBranch;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.ParseTree#isLeaf()
	 */
	public boolean isLeaf() {
		return false;
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
		result = prime * result
				+ ((leftBranch == null) ? 0 : leftBranch.hashCode());
		result = prime * result
				+ ((parserId == null) ? 0 : parserId.hashCode());
		result = prime * result
				+ ((rightBranch == null) ? 0 : rightBranch.hashCode());
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
		ParseTreeNode other = (ParseTreeNode) obj;
		if (leftBranch == null) {
			if (other.leftBranch != null)
				return false;
		} else if (!leftBranch.equals(other.leftBranch))
			return false;
		if (parserId == null) {
			if (other.parserId != null)
				return false;
		} else if (!parserId.equals(other.parserId))
			return false;
		if (rightBranch == null) {
			if (other.rightBranch != null)
				return false;
		} else if (!rightBranch.equals(other.rightBranch))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.ParseTree#getLeafValue()
	 */
	public String getLeafValue() {
		throw new IllegalStateException("A node can not have a value!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.ParseTree#getMatchBranch()
	 */
	public ParseTree getLeftBranch() {
		return leftBranch;
	}

	public void setLeftBranch(ParseTree matchBranch) {
		this.leftBranch = matchBranch;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.meristem.ParseTree#getRemainderBranch()
	 */
	public ParseTree getRightBranch() {
		return rightBranch;
	}

	public void setRightBranch(ParseTree remainderBranch) {
		this.rightBranch = remainderBranch;
	}

	public String getParserId() {
		return parserId;
	}

	public String toString(int indent) {
		String str = "";
		for (int i = 0; i < indent; i++) {
			str += "\t";
		}
		
		str += "Node" + "\n";

		if (getLeftBranch() != null) {
			str += getLeftBranch().toString(indent+1) + "\n";
		}
		
		if (getRightBranch() != null) {
			str += getRightBranch().toString(indent+1) + "\n";
		}
		return str;
	}
	
	@Override
	public String toString() {
		return toString(0);
	}
	
}
