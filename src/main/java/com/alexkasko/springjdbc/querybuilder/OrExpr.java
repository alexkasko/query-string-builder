package com.alexkasko.springjdbc.querybuilder;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Disjunction expression implementation
 *
 * @author alexkasko
 *         Date: 11/7/12
 */
class OrExpr extends AbstractExpr {
    private static final long serialVersionUID = -944146038841042727L;

    private final Expression left;
    private final Expression right;

    /**
     * Constructor
     *
     * @param left  left expression in disjunction
     * @param right right expression in disjunction
     */
    OrExpr(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "((" + left + ") or (" + right + "))";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        OrExpr that = (OrExpr) o;
        return new EqualsBuilder()
                .append(left, that.left)
                .append(right, that.right)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(left)
                .append(right)
                .toHashCode();
    }
}
