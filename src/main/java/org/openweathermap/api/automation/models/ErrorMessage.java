package org.openweathermap.api.automation.models;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ErrorMessage {
    private Integer code;
    private String message;
}
