package pl.jewusiak.mwotodomvc.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.jewusiak.mwotodomvc.models.apiaccess.GetAllListsResponse;
import pl.jewusiak.mwotodomvc.models.apiaccess.TodoListDto;
import pl.jewusiak.mwotodomvc.models.apiaccess.ToggleItemRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApiClient {
    private final RestTemplate restTemplate;


    public Iterable<TodoListDto> getTodoLists() {
        var result = restTemplate.getForObject("/list", GetAllListsResponse.class);

        return Arrays.asList(result.getContent());
    }

    public void toggleItem(String uuid, boolean next) {
        restTemplate.put("/item/" + uuid, new ToggleItemRequest(next));
    }

    public void deleteItem(String uuid) {
        restTemplate.delete("/item/" + uuid);
    }

    public void deleteList(String uuid) {
        restTemplate.delete("/list/" + uuid);
    }

    public void createList(String name) {
        restTemplate.postForObject("/list", new HashMap<String, String>() {{
            put("name", name);
        }}, Void.class);
    }

    public void createItem(String uuid, String name) {
        var res =  restTemplate.postForObject("/item", new HashMap<String, String>() {{
            put("name", name);
            put("description", "");
        }}, Map.class);
        restTemplate.postForObject("/item/"+res.get("uuid")+"/setlist/"+uuid, null, Void.class);
    }
}
