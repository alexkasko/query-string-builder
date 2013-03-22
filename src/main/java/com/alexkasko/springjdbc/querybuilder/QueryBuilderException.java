package com.alexkasko.springjdbc.querybuilder;


/**
 * Exception for query-building related errors.
 *
 * @author alexkasko
 * Date: 3/22/13
 */
public class QueryBuilderException extends RuntimeException {
    private static final long serialVersionUID = 6684578317996404101L;

    /**
     * Constructor
     *
     * @param message error message
     */
    public QueryBuilderException(String message) {
        super(message);
    }
}
