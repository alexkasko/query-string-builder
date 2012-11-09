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
     */
    public static ExpressionList list(String expr) {
        return new ExprList(new LiteralExpr(expr));
    }

    /**
     * Creates expression from string literal
     *
     * @param expr expression literal
     * @return expression
     */
    public static Expression expr(String expr) {
        return new LiteralExpr(expr);
    }

    /**
     * Creates negation expression for given expression
     *
     * @param expr expression
     * @return negation expression
     */
    public static Expression not(Expression expr) {
        return new NotExpr(expr);
    }

    /**
     * Creates negation expression for given expression literal
     *
     * @param expr expression literal
     * @return negation expression
     */
    public static Expression not(String expr) {
        return new NotExpr(new LiteralExpr(expr));
    }

    /**
     * Creates disjunction expression for two given expressions
     *
     * @param left left expression
     * @param right right expression
     * @return disjunction expression
     */
    public static Expression or(Expression left, Expression right) {
        return new OrExpr(left, right);
    }

    /**
     * Creates disjunction expression for two given expressions
     *
     * @param left left expression literal
     * @param right right expression
     * @return disjunction expression
     */
    public static Expression or(String left, Expression right) {
        return new OrExpr(new LiteralExpr(left), right);
    }

    /**
     * Creates disjunction expression for two given expressions
     *
     * @param left left expression
     * @param right right expression literal
     * @return disjunction expression
     */
    public static Expression or(Expression left, String right) {
        return new OrExpr(left, new LiteralExpr(right));
    }

    /**
     * Creates disjunction expression for two given expressions literals
     *
     * @param left left expression
     * @param right right expression literal
     * @return disjunction expression literal
     */
    public static Expression or(String left, String right) {
        return new OrExpr(new LiteralExpr(left), new LiteralExpr(right));
    }
}
