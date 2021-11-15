package org.openweathermap.api.automation.utilities;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Column {
    private String name;
    private String value;
}
