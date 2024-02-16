package org.example

import java.util.regex.Pattern

class Lexer(val input: String) {
    private var currentPosition = 0
    private var lastTokenWasProjection = false
    private var isInsideParentheses = false


    companion object {
        val ID_PATTERN = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*")
        val NUMBER_PATTERN = Pattern.compile("\\d+")
    }


    fun getNextToken(): Token {
        while (currentPosition < input.length) {
            val char = input[currentPosition]

            when {
                char.isWhitespace() -> {
                    currentPosition++
                    continue
                }
                char == '=' -> {
                    currentPosition++
                    return Token(TokenType.EQUALS, char.toString())
                }
                char == '<' -> {
                    currentPosition++
                    if (currentPosition < input.length && input[currentPosition] == '=') {
                        currentPosition++
                        return Token(TokenType.LESS_THAN_EQUALS, "<=")
                    }
                    return Token(TokenType.LESS_THAN, char.toString())
                }
                char == '>' -> {
                    currentPosition++
                    if (currentPosition < input.length && input[currentPosition] == '=') {
                        currentPosition++
                        return Token(TokenType.GREATER_THAN_EQUALS, ">=")
                    }
                    return Token(TokenType.GREATER_THAN, char.toString())
                }
                char == '(' -> {
                    currentPosition++
                    return Token(TokenType.LPAREN, char.toString())
                }
                char == ',' -> {
                    currentPosition++
                    return Token(TokenType.COMMA, char.toString())
                }
                char == ')' -> {
                    currentPosition++
                    return Token(TokenType.RPAREN, char.toString())
                }
                char == 'σ' -> {
                    currentPosition++
                    return Token(TokenType.SIGMA, char.toString())
                }
                char == 'π' -> {
                    currentPosition++
                    return Token(TokenType.PI, char.toString())
                }
                Character.isLetter(char) -> {
                    val matcher = ID_PATTERN.matcher(input.substring(currentPosition))
                    if (matcher.find()) {
                        val id = matcher.group()
                        currentPosition += id.length
                        return Token(TokenType.ID, id)
                    } else {
                        return Token(TokenType.INVALID, char.toString())
                    }
                }
                Character.isDigit(char) -> {
                    val matcher = NUMBER_PATTERN.matcher(input.substring(currentPosition))
                    if (matcher.find()) {
                        val number = matcher.group()
                        currentPosition += number.length
                        return Token(TokenType.NUMBER, number)
                    } else {
                        return Token(TokenType.INVALID, char.toString())
                    }
                }
                else -> {
                    return Token(TokenType.INVALID, char.toString())
                }
            }
        }

        return Token(TokenType.INVALID, "")
    }
}