package pl.jewusiak.mwotodomvc.models.apiaccess;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ToggleItemRequest {
    private Boolean status;
}
