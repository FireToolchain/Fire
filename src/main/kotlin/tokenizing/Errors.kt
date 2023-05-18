package tokenizing

class SyntaxError(str: String, line: Int, col: Int) : Error("$str\n At: line $line, column $col.")