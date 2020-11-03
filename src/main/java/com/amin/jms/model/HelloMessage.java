package com.amin.jms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HelloMessage implements Serializable {
    private static final long serialVersionUID = 37275576868111664L;

    private UUID id;
    private String message;
}
