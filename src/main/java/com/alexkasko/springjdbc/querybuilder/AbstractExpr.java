package com.alexkasko.springjdbc.querybuilder;

import java.io.Serializable;

/**
 * Base class for expressions
 *
 * @author alexkasko
 * Date: 11/7/12
 */
abstract class AbstractExpr implements Expression, Serializable {
    private static final long serialVersionUID = -8223267461679410411L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression and(Expression expr) {
        return new AndExpr(this, expr);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression and(String expr) {
        return new AndExpr(this, new LiteralExpr(expr));
    }
}
