package pl.jewusiak.mwotodomvc.models.apiaccess;

import lombok.Data;

@Data
public class GetAllListsResponse {
    private TodoListDto[] content;
}
