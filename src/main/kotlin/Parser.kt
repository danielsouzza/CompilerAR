package org.example

class Parser(private val lexer: Lexer) {
    private var currentToken: Token = lexer.getNextToken()

    fun parse(): String {
        return expression()
    }
    private fun selection(): String {
        val sigmaToken = consume(TokenType.SIGMA) // Consumir o token SIGMA
        consume(TokenType.LPAREN)
        val condition = condition()
        consume(TokenType.RPAREN)
        consume(TokenType.LPAREN)
        val result = expression()
        consume(TokenType.RPAREN)
        return "$sigmaToken($condition)($result)"
    }
    private fun projection(): String {
        val piToken = consume(TokenType.PI)
        consume(TokenType.LPAREN)
        val attributes = consume(TokenType.ID)
        consume(TokenType.RPAREN)
        consume(TokenType.LPAREN)
        val result = expression()
        consume(TokenType.RPAREN)
        return "$piToken($attributes)($result)"
    }

    private fun condition(): String {
        val attribute = consume(TokenType.ID)
        val operator = consume(listOf(
            TokenType.EQUALS,
            TokenType.LESS_THAN,
            TokenType.GREATER_THAN,
            TokenType.LESS_THAN_EQUALS,
            TokenType.GREATER_THAN_EQUALS
        ))
        val value = if (currentToken.type == TokenType.NUMBER || currentToken.type == TokenType.ID) {
            consume(currentToken.type)
        } else {
            throw IllegalArgumentException("Erro de sintaxe! Valor inesperado: ${currentToken.type}")
        }

        return "$attribute $operator $value"
    }

    private fun expression(): String {
        val token = currentToken
        return when (token.type) {
            TokenType.SIGMA -> selection()
            TokenType.PI -> projection()
            TokenType.LPAREN -> {
                consume(TokenType.LPAREN)
                val result = expression()
                consume(TokenType.RPAREN)
                result // Retornar o resultado da expressão
            }
            TokenType.RPAREN -> "" // Retornar uma string vazia quando encontrar um RPAREN
            TokenType.ID, TokenType.NUMBER -> {
                consume(token.type)
                val nextToken = currentToken.type
                if (nextToken == TokenType.LPAREN || nextToken == TokenType.ID) {
                    // Se o próximo token for LPAREN ou ID, trata-se da parte da tabela
                    val tablePart = expression()
                    "$token $tablePart"
                } else {
                    token.value // Caso contrário, retorna apenas o valor do token
                }
            }
            else -> throw IllegalArgumentException("Erro de sintaxe! Token inesperado: $token na posição")
        }
    }


    private fun consume(expectedType: TokenType): String? {
        val token = currentToken
        var lastComma = ""
        if (token.type == expectedType) {
            currentToken = lexer.getNextToken()
            if(currentToken.type == TokenType.COMMA){
                consume(TokenType.COMMA)
                val result = consume(TokenType.ID)
                lastComma = ", $result"
            }
            return "${token.value}$lastComma"
        } else {
            throw IllegalArgumentException("Erro de sintaxe! Token inesperado: $token")
        }
    }


    private fun consume(expectedTypes: List<TokenType>): String {
        val token = currentToken
        if (token.type in expectedTypes) {
            currentToken = lexer.getNextToken()
            return token.value
        } else {
            throw IllegalArgumentException("Erro de sintaxe! Token inesperado4: $token")
        }
    }

}