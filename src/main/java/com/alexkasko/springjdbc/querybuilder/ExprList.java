package com.alexkasko.springjdbc.querybuilder;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.join;

/**
 * Expression list implementation, may contain additional prefix,
 * that will be printed only if condition list is not empty
 *
 * @author alexkasko
 *         Date: 11/7/12
 */
class ExprList implements ExpressionList, Serializable {
    private static final long serialVersionUID = 7616107916993782428L;
    private static final String DELIMITER = ", ";

    private final String prefix;
    private final List<Expression> conds = new ArrayList<Expression>();

    /**
     * Constructor
     */
    ExprList() {
        this("");
    }

    /**
     * Constructor
     *
     * @param prefix list prefix, will be printed only if
     *               condition list is not empty
     */
    ExprList(String prefix) {
        if(null == prefix) throw new QueryBuilderException("Provided prefix is null");
        this.prefix = prefix;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExpressionList add(Expression expr) {
        if(null == expr) throw new QueryBuilderException("Provided expression is null");
        this.conds.add(expr);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExpressionList add(String expr) {
        return add(new LiteralExpr(expr));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExpressionList add(Collection<Expression> exprs) {
        if(null == exprs) throw new QueryBuilderException("Provided collection is null");
        for(Expression ex : exprs) {
            if(null == ex) throw new QueryBuilderException("Provided expression is null");
            this.conds.add(ex);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if(isEmpty(prefix)) return join(conds, DELIMITER);
        else if(0 == conds.size()) return "";
        else return prefix + " " + join(conds, DELIMITER);
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
                .append(prefix, that.prefix)
                .append(conds, that.conds)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(prefix)
                .append(conds)
                .toHashCode();
    }
}
