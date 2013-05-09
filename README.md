Small library for building SQL query strings
============================================

This library allows to build SQL query string programmatically using API calls. It supports conjunctions, disjunctions,
negations and comma-separated lists.

Library depends on [commons-lang](http://repo1.maven.org/maven2/commons-lang/commons-lang/).

Library is available in [Maven cental](http://repo1.maven.org/maven2/com/alexkasko/springjdbc/).

Javadocs for the latest release are available [here](http://alexkasko.github.com/query-string-builder/javadocs).

SQL queries building problem
----------------------------

Imagine you are developing Java project over relational database and for [some reason](http://www.google.com/search?q=ORM+antipattern)
are not using Hibernate/JPA (which are great tools for appropriate tasks). Then some day you'll want to build some
SQL queries programmatically depending on user input.

Concatenating commas and counting parentheses is not much interesting task so there are [many libraries](http://www.h2database.com/html/jaqu.html#similar_projects)
that can do it for you. Most of such libraries (I don't pretend to do comprehensive analysis) tend to be the bridge between
application code and JDBC - kind of lightweight ORM's. They can build SQL queries using API, execute queries and map results for you.
But if you are not using JPA you are probably already have fine tuned JDBC wrapper for query execution with proper transactions/resources
handling, named parameters support etc (e.g. [spring-jdbc](http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/jdbc.html)).

You can find SQL builder library that doesn't pretend to be an ORM and just building query string for you (e.g.
[this](http://code.google.com/p/squiggle-sql/) or [this](http://openhms.sourceforge.net/sqlbuilder/)). But then you face
another problem: `select` queries have complex structure ([postges](http://www.postgresql.org/docs/current/static/sql-select.html#AEN77017),
[oracle](http://docs.oracle.com/cd/B13789_01/server.101/b10759/statements_10002.htm#i2065706)) and are not portable between RDBMSes.
So libraries trying to build query in type-safe manner need to know all possible query elements and support different dialects
for different RDBMSes.

But if complex query construction is not the big part of you business logic, then you probably want just to concatenate
query parts together with some API (more clever then `StringBuilder`) and execute result SQL string using your usual tools.
This library (`query-string-builder`) created for such cases.

Otherwise, if you really need ORM-like library, [this one](http://static.springsource.org/spring-data/data-jdbc/docs/1.0.0.RC1/reference/html/#core.querydsl)
may be interesting.

Library usage
-------------

Maven dependency (available in central repository):

    <dependency>
        <groupId>com.alexkasko.springjdbc</groupId>
        <artifactId>query-string-builder</artifactId>
        <version>1.2</version>
    </dependency>

_Note1: this library should **NEVER** be used for concatenating user defined **VALUES**. [Bad things](http://xkcd.com/327/) may happen.
You should concatenate prepared static string parts containing parameters placeholders (`?` or `:placeholder`) and execute result query
providing user defined values as parameters to `PreparedStatement`_

_Note2: this library only builds query strings, it knows nothing about SQL semantic, tables, results columns etc,
so it doesn't restrict you with any "known" subset of SQL_

_Note3: this library does not pretend to be a replacement for static SQL queries, it just handles a case,
where you need to build query string in runtime_

`QueryBuilder` is the entry point class. It is created using query template string. Then you should create
expressions (or expression lists) and provide them to builder to fill template placeholders (clauses):

    // query template, probably loaded from external file
    String template = "select emp.* from employee emp" +
                        " join departments dep on emp.id_department = dep.id" +
                        " ${where}" +
                        " ${order}" +
                        " limit :limit offset :offset";
    // create "where" clause
    Expression where = Expressions.where()
            .and("emp.surname = :surname")
            .and("emp.name like :name")
            .and(or(expr("emp.salary > :salary").and("emp.position in (:positionList)"),
                    not("emp.age > :ageThreshold")))
            .and("status != 'ARCHIVED'");
    // create "order" clause
    ExpressionList order = Expressions.orderBy().add("dep.id desc").add("cust.salary");
    // create builder from template and fill clauses
    String sql = QueryBuilder.query(template)
            .set("where", where)
            .set("order", order)
            .build();

Result SQL string will be like this (just unformatted):

    select emp.* from employee emp
        join departments dep on emp.id_department = dep.id
        where emp.surname = :surname
            and emp.name like :name
            and ((emp.salary > :salary and emp.position in (:positionList)) or (not (emp.age > :ageThreshold)))
            and status != 'ARCHIVED
        order by dep.id desc, cust.salary
        limit :limit offset :offset

####query template string

Query template string is a SQL string with some parts replaced with placeholders with syntax: `${placeholder}`
(may be escaped as `$${not_a_placeholder}`). Placeholder names must conform this regex: `[a-zA-Z_0-9]+` and
cannot be duplicated in template.

####expressions

Expressions are designed to be used in `where` clause. All `Expression`s implement `and` method
to allow easy method chaining. Expressions are printed to SQL using `toString` method.

Built-in expressions may be created using instance method `and` and static methods of `Expressions` class:

 * `expr` - creates new expression from string literal, used to start building, prints to provided literal
 * `and` - creates new conjunction expression, prints to `this_expr and arg_expr`
 * `or` - creates new disjunction expression, prints to `((arg_expr_1) or (arg_expr2) or ... or (arg_expr_N))`
 * `not` - creates new negation expression, prints ti `not (arg_expr)`
 * `prefix` (and included prefix types `where` and `and`) - creates new prefix expression,
   prefix will be printed only if this expression will be conjuncted with other expressions,
   empty string will be printed otherwise

All builtin expressions are immutable and serializable.

####expression lists

Expression list is designed to be used in `from`, `group by`, `having` contains multiple expressions. `ExpressionList` implements `comma` method, that allows
to add new expressions to list and returns list itself.

`ExpressionList` is printed to `expr_1, expr_2, ... expr_N`.

Expression lists also may have prefixes (`listWithPrefix` and `orderBy` methods), prefix will be printed
before non empty conditions list and will be omitted if conditions list is empty.

####extending library with new expression

`QueryBuilder` uses only `Expression` and `ExpressionList` interfaces and knows nothing about implementations.
It also doesn't do any operations on expressions (or lists) besides printing them using `toString` method.
Methods `and` and `add` was added directly to interfaces to simplify expressions building -
you may ignore them in your implementations.

License information
-------------------

This project is released under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0)

Changelog
---------

**1.2** (2013-05-09)

 * prefix support for expressions and lists
 * list methods renamed from `comma` to `and`

**1.1** (2013-03-21)

 * vararg disjunctions
 * null input validation

**1.0** (2012-11-09)

 * initial version