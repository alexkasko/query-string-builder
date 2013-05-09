package com.alexkasko.springjdbc.querybuilder;

import org.apache.commons.lang.text.StrSubstitutor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * Builder class for string queries. Query is build from template - string with placeholder sections (clauses),
 * and expressions (lists) set to to builder to fill clauses.
 * Placeholder syntax: {@code ${placeholder_42}
 * Placeholder escaping syntax: {@code $${placeholder_42} (will NOT be parsed)
 * Placeholder name must conform this regex: {@code [a-zA-Z_0-9]+}
 *
 * @author alexkasko
 * Date: 11/7/12
 */
public class QueryBuilder {
    private static final Pattern CLAUSE_PATTERN = Pattern.compile("[^\\$]\\$\\{(\\w+)\\}");

    private final String template;
    private final Map<String, ExpressionList> clauses = new HashMap<String, ExpressionList>();

    /**
     * Constructor
     *
     * @param template query template
     */
    public QueryBuilder(String template) {
        if(isBlank(template)) throw new QueryBuilderException("Provided template is blank");
        this.template = template;
        Matcher m = CLAUSE_PATTERN.matcher(template);
        while (m.find()) {
            String name = m.group(1);
            if(clauses.containsKey(name)) throw new QueryBuilderException(
                    "Duplicate clause: [" + name +"] found in template: [" + template +"]");
            clauses.put(name, null);
        }
    }

    /**
     * Static method, invokes constructor
     *
     * @param template query template
     * @return query vuilder instance
     */
    public static QueryBuilder query(String template) {
        return new QueryBuilder(template);
    }

    /**
     * Registers provided string for clause
     *
     * @param clauseName clause name
     * @param value clause value
     * @return builder itself
     */
    public QueryBuilder set(String clauseName, String value) {
        return set(clauseName, new LiteralExpr(value));
    }

    /**
     * Registers provided expression list for clause
     *
     * @param clauseName clause name
     * @param expr clause expression
     * @return builder itself
     */
    public QueryBuilder set(String clauseName, Expression expr) {
        return set(clauseName, new ExprList().add(expr));
    }

    /**
     * Registers provided expression for clause
     *
     * @param clauseName clause name
     * @param expr clause expression list
     * @return builder itself
     * @throws QueryBuilderException on blank input, on missed clause, on duplicate clause
     */
    public QueryBuilder set(String clauseName, ExpressionList expr) {
        if(isBlank(clauseName)) throw new QueryBuilderException("Provided clauseName is blank");
        if(!clauses.containsKey(clauseName)) throw new QueryBuilderException(
                "Provided clauseName: [" + clauseName + "] is not found in the template: [" +  template + "]" +
                " registered clauses: [" + clauses.keySet() + "]" +
                " (clause name must conform this regex: '[a-zA-Z_0-9]+')");
        if(null != clauses.get(clauseName)) throw new QueryBuilderException(
                "Provided clauseName: [" + clauseName + "] was already set to: [" + clauses.get(clauseName) + "]");
        if(null == expr) throw new QueryBuilderException("Provided expr is null");
        clauses.put(clauseName, expr);
        return this;
    }

    /**
     * Builds query string from template and accumulated values
     *
     * @return query string
     * @throws IllegalStateException on not filled clause
     */
    public String build() {
        Map<String, String> map = new HashMap<String, String>();
        for(Map.Entry<String, ExpressionList> en : clauses.entrySet()) {
            if(null == en.getValue()) throw new QueryBuilderException(
                    "Clause: [" + en.getKey() + "] wasn't filled, template: [" + template + "]");
            map.put(en.getKey(), en.getValue().toString());
        }
        return StrSubstitutor.replace(template, map);
    }
}