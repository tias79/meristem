# Meristem
Meristem is a Java library for parsing text.

Meristem is available under the GNU General Public License, version 2.

## Examples

### A palindrome parser
```
import static com.github.meristem.Parsers.*;

public class Main {

        public static void main(String[] args) throws CloneNotSupportedException {
                Parser letter = letter();
                
                Parser palindrome = or(it(letter, 2), letter);
                
                letter = letter.clone();
                palindrome = and(letter, palindrome, sync(letter));
                                
                ParseResult result = palindrome.parse("abba");
                System.out.println(result.getParseTree().toString());           
        }

}
```

### An IP address parser
```
import static com.github.meristem.Parsers.*;

public class Main {

        public static void main(String[] args) throws CloneNotSupportedException {
                Parser number = it(digit(), 1, 3);
                
                Parser ipnumber = and(number, chr('.'), number, chr('.'), number);
                
                ParseResult result = ipnumber.parse("192.168.0.10");
                System.out.println(result.getParseTree().toString());
        }

}
```
