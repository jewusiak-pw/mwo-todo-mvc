package pl.jewusiak.mwotodomvc.models.apiaccess;


import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TodoListDto {
    private UUID uuid;
    private String name;
    private List<TodoItemDto> items;
}
