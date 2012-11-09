package com.alexkasko.springjdbc.querybuilder;

/**
 * Interface for expressions.
 * Expression is printed to output using {@link #toString()} method.
 * All built-in expressions are immutable.
 *
 * @author alexkasko
 * Date: 11/7/12
 */
public interface Expression {
    /**
     * Adds another expression to this expression as a conjunction
     *
     * @param expr expression
     * @return conjunction
     */
    Expression and(Expression expr);

    /**
     * Adds another expression to this expression as a conjunction
     *
     * @param expr expression literal
     * @return conjunction
     */
    Expression and(String expr);
}
