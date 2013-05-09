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
     * Creates expression list
     *
     * @return expression list
     */
    public static ExpressionList list() {
        return new ExprList();
    }

    /**
     * Creates expression list with prefix literal,
     * that will be printed only if condition list is not empty
     *
     * @param prefix list prefix literal
     * @return expression list
     */
    public static ExpressionList listWithPrefix(String prefix) {
        return new ExprList(prefix);
    }

    /**
     * Creates expression list with {@code "order by"} prefix,
     * that will be printed only if condition list is not empty
     *
     * @return expression list
     */
    public static ExpressionList orderBy() {
        return new ExprList("order by");
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

    /**
     * Creates prefix expression, prefix will be printed
     * only if this expression will be conjuncted with other expressions,
     * empty string will be printed otherwise
     *
     * @param prefix prefix literal
     * @return prefix expression
     * @throws QueryBuilderException on empty input
     */
    public static Expression prefix(String prefix) {
        return  new PrefixExpr(prefix);
    }

    /**
     * Creates prefix expression with {@code "where"} prefix, that
     * will be printed only if this expression will be conjuncted with other expressions,
     * empty string will be printed otherwise
     *
     * @return prefix expression with {@code "where"} prefix
     */
    public static Expression where() {
        return new PrefixExpr("where");
    }

    /**
     * Creates prefix expression with {@code "and"} prefix, that
     * will be printed only if this expression will be conjuncted with other expressions,
     * empty string will be printed otherwise
     *
     * @return prefix expression with {@code "and"} prefix
     */
    public static Expression and() {
        return new PrefixExpr("and");
    }
}
