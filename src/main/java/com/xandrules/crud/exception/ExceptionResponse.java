package com.xandrules.crud.exception;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ExceptionResponse implements Serializable {

    private Date timestamp;
    private String message;
    private String details;
}
