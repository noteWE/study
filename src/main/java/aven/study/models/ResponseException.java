package aven.study.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseException {
    private String message;
}
