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

    private final Expression[] array;

    /**
     * Constructor
     *
     * @param array array of disjunctions
     */
    OrExpr(Expression[] array) {
        if(null == array) throw new QueryBuilderException("Provided expressions array is null");
        this.array = array;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if(0 == array.length) return "";
        StringBuilder sb = new StringBuilder("(");
        for(int i = 0; i < array.length - 1; i++) {
            if(null == array[i]) throw new QueryBuilderException("Provided expression is null, array index: [" + i + "]");
            sb.append("(");
            sb.append(array[i]);
            sb.append(") or ");
        }
        // tail
        int last = array.length-1;
        if(null == array[last]) throw new QueryBuilderException("Provided expression is null, array index: [" + last + "]");
        sb.append("(");
        sb.append(array[last]);
        sb.append("))");
        return sb.toString();
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
                .append(array, that.array)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(array)
                .toHashCode();
    }
}
