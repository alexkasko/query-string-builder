package com.alexkasko.springjdbc.querybuilder;

import java.util.Collection;

/**
 * Interface for expression lists. List is joined from expressions with commas.
 * List is printed to output using {@link #toString()} method.
 *
 * @author alexkasko
 * Date: 11/8/12
 */
public interface ExpressionList {
    /**
     * Adds expression to list
     *
     * @param expr expression
     * @return list itself
     */
    ExpressionList comma(Expression expr);

    /**
     * Adds expression to list
     *
     * @param expr expression literal
     * @return list itself
     */
    ExpressionList comma(String expr);

    /**
     * Adds expressions collection to list
     *
     * @param exprs expressions collection
     * @return list itself
     */
    ExpressionList comma(Collection<Expression> exprs);
}
