package com.alexkasko.springjdbc.querybuilder;

import org.junit.Test;

import static com.alexkasko.springjdbc.querybuilder.Expressions.*;
import static org.junit.Assert.assertEquals;

/**
 * User: alexkasko
 * Date: 11/8/12
 */
public class QueryBuilderTest {
    private static final String TEMPLATE = "select emp.*" +
                    " from employee emp" +
                    " join departments dep on emp.id_department = dep.id" +
                    " ${where}" +
                    " ${order}" +
                    " limit :limit offset :offset";

    @Test
    public void test() {
        Expression where = Expressions.where()
                .and("emp.surname = :surname")
                .and("emp.name like :name")
                .and(or(expr("emp.salary > :salary").and("emp.position in (:positionList)"),
                        not("emp.age > :ageThreshold")))
                .and("status != 'ARCHIVED'");

        ExpressionList order = Expressions.orderBy().add("dep.id desc").add("cust.salary");

        String sql = QueryBuilder.query(TEMPLATE).set("where", where).set("order", order).build();

        assertEquals("Query build fail",
                "select emp.*" +
                " from employee emp" +
                " join departments dep on emp.id_department = dep.id" +
                " where emp.surname = :surname" +
                " and emp.name like :name" +
                " and ((emp.salary > :salary and emp.position in (:positionList))" +
                        " or (not (emp.age > :ageThreshold)))" +
                " and status != 'ARCHIVED'" +
                " order by dep.id desc, cust.salary" +
                " limit :limit offset :offset", sql);

        String emptyClauses = QueryBuilder.query(TEMPLATE).set("where", Expressions.where())
                .set("order", Expressions.orderBy()).build();

        assertEquals("Empty clauses fail",
                "select emp.*" +
                " from employee emp" +
                " join departments dep on emp.id_department = dep.id" +
                "   limit :limit offset :offset", emptyClauses);
    }
}
