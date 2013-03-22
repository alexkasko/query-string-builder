package com.alexkasko.springjdbc.querybuilder;

/**
 * Static helper methods to work with expressions and expression lists
 *
 * @author alexkasko
 * Date: 11/7/12
 */
public final class Expressions {

    private Expressions() { }

    /**
     * Creates expression list from string literal
     *
     * @param expr expression literal
     * @return expression list
     * @throws QueryBuilderException on empty input
     */
    public static ExpressionList list(String expr) {
        return new ExprList(new LiteralExpr(expr));
    }

    /**
     * Creates expression from string literal
     *
     * @param expr expression literal
     * @return expression
     * @throws QueryBuilderException on empty input
     */
    public static Expression expr(String expr) {
        return new LiteralExpr(expr);
    }

    /**
     * Creates negation expression for given expression
     *
     * @param expr expression
     * @return negation expression
     * @throws QueryBuilderException on null input
     */
    public static Expression not(Expression expr) {
        return new NotExpr(expr);
    }

    /**
     * Creates negation expression for given expression literal
     *
     * @param expr expression literal
     * @return negation expression
     * @throws QueryBuilderException on empty input
     */
    public static Expression not(String expr) {
        return new NotExpr(new LiteralExpr(expr));
    }

    /**
     * Creates disjunction expression for expressions
     *
     * @param exprs expressions for disjunction
     * @return disjunction expression
     * @throws QueryBuilderException on null input
     */
    public static Expression or(Expression... exprs) {
        return new OrExpr(exprs);
    }
}
