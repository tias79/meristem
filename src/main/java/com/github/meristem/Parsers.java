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
 * Static methods for creating parsers.
 *
 */
public class Parsers {

	public static Parser letter() {
		return new LetterParser();
	}

	public static Parser digit() {
		return new DigitParser();
	}

	public static Parser str(String string) {
		return new StringParser(string);
	}

	public static Parser chr(Character c) {
		return new CharacterParser(c);
	}

	public static Parser anyOf(String anyOf) {
		return new AnyOfParser(anyOf);
	}

	public static Parser not(Parser parser) {
		return new NotParser(parser);
	}

	public static Parser and(Parser... parsers) {
		return new AndParser(parsers);
	}

	public static Parser or(Parser... parsers) {
		return new OrParser(parsers);
	}

	public static Parser xor(Parser... parsers) {
		return new ExclusiveOrParser(parsers);
	}

	public static Parser it(Parser parser, int iterations) {
		return new IteratorParser(parser, iterations);
	}

	public static Parser it(Parser parser, int iterations, boolean prune) {
		Parser it = new IteratorParser(parser, iterations);
		return prune ? new PruneParser(it) : it;
	}

	public static Parser it(Parser parser, Iterations iterations) {
		return new IteratorParser(parser, iterations);
	}

	public static Parser it(Parser parser, Iterations iterations, boolean prune) {
		Parser it = new IteratorParser(parser, iterations);
		return prune ? new PruneParser(it) : it;
	}

	public static Parser cat(Parser... parsers) {
		return new ConcatParser(parsers);
	}

	public static Parser prune(Parser parser) {
		return new PruneParser(parser);
	}

	public static Parser nil(Parser parser) {
		return new NilParser(parser);
	}

	public static Parser sync(Parser parser) {
		return new SynchronizedParser(parser);
	}

	public static Parser def(Parser parser, String defaultLeafValue) {
		return new DefaultParser(parser, defaultLeafValue);
	}
	
}
