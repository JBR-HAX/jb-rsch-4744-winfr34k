package org.jetbrains.assignment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Traces a request and the corresponding response
 *
 * This object allows for tracing of calculations and their inputs.
 * In the future, this could be useful for monitoring or automated test generation.
 */
@Entity
public class ApiTrace {

    @GeneratedValue
    @Id
    private Long id;

    @Column
    private RequestType type;

    @Column
    private String request;

    @Column
    private String response;

    public ApiTrace() {
    }

    public ApiTrace(final RequestType type, final String request, final String response) {
        this(null, type, request, response);
    }

    public ApiTrace(final Long id, final RequestType type, final String request, final String response) {
        this.id = id;
        this.type = type;
        this.request = request;
        this.response = response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ApiTrace{" +
                "id=" + id +
                ", type=" + type +
                ", request='" + request + '\'' +
                ", response='" + response + '\'' +
                '}';
    }

    public enum RequestType {
        LOCATIONS,
        MOVES
    }
}
