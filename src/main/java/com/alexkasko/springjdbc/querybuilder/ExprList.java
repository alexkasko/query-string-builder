package com.alexkasko.springjdbc.querybuilder;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.StringUtils.join;

/**
 * Expression list implementation
 *
 * @author alexkasko
 *         Date: 11/7/12
 */
class ExprList implements ExpressionList, Serializable {
    private static final long serialVersionUID = 7616107916993782428L;
    private static final String DELIMITER = ", ";

    private final List<Expression> conds = new ArrayList<Expression>();

    /**
     * Constructor
     *
     * @param expr first expression in list
     */
    ExprList(Expression expr) {
        this.conds.add(expr);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExpressionList comma(Expression expr) {
        this.conds.add(expr);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExpressionList comma(String expr) {
        return comma(new LiteralExpr(expr));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return join(conds, DELIMITER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        ExprList that = (ExprList) o;
        return new EqualsBuilder()
                .append(conds, that.conds)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(conds)
                .toHashCode();
    }
}
