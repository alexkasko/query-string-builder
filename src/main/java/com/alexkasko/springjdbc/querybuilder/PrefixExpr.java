package com.alexkasko.springjdbc.querybuilder;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * Prefix expression, prefix will be printed
 * only if this expression will be conjuncted with other expressions,
 * empty string will be printed otherwise
 *
 * @author alexkasko
 * Date: 5/9/13
 */
class PrefixExpr extends AbstractExpr implements Serializable {
    private static final long serialVersionUID = 6368601841542179200L;

    private final String prefix;
    private final Expression body;

    /**
     * Constructor
     *
     * @param prefix prefix literal
     */
    PrefixExpr(String prefix) {
        this(prefix, null);
    }

    private PrefixExpr(String prefix, Expression expr) {
        if(isBlank(prefix)) throw new QueryBuilderException("Provided prefix is blank");
        this.prefix = prefix;
        this.body = expr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression and(Expression expr) {
        if(null == body) return new PrefixExpr(prefix, expr);
        return super.and(expr);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression and(String expr) {
        return and(new LiteralExpr(expr));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if(null == body) return "";
        return prefix + " " + body;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrefixExpr that = (PrefixExpr) o;
        EqualsBuilder eqb = new EqualsBuilder()
                .append(prefix, that.prefix);
        if (null != body) eqb.append(body, that.body);
        return eqb.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder()
                .append(prefix);
        if (null != body) hcb.append(body);
        return hcb.toHashCode();
    }
}
