package org.example

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val input = "σ(ID=10)(π(ID,Name)(table))"
    val lexer = Lexer(input)
    val parser = Parser(lexer)
    val result = parser.parse()
    println("Resultado da análise: $result")
}