Este trabalho apresenta o desenvolvimento de um compilador de AR para SQL. O objetivo principal é fornecer um módulo robusto e eficiente que seja capaz de identificar e classificar os tokens presentes em uma expressão da AR, verificar a correção sintática da expressão e gerar a consulta SQL equivalente.

Como o tempo é pequeno pensei em simplificar um pouco, em vez de fazer para toda a linguagem irei fazer apenas para as funções de seleção e projeção que são equivalente ao `SELECT` e `WHERE` do SQL.

Primeiro eu defini como seria a gramatica da algebra relacional, como eu já fiz bancos de dados não foi um problema, a gramatica gerada e simplificada é a seguinte:

![image](https://github.com/danielsouzza/CompilerAR/assets/100795273/128a3601-4fd1-43e0-b6d2-03e2091e2dc1)

Caso queria testar o código, acesse o <a href="https://pl.kotl.in/iUiMAMMtL">LINK</a>, lemprando que só o Lexer e o Parser foram desenvovidos ainda.

