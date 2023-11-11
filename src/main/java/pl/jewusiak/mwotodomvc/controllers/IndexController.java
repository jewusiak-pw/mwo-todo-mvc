package pl.jewusiak.mwotodomvc.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jewusiak.mwotodomvc.client.ApiClient;
import pl.jewusiak.mwotodomvc.models.CreateItemModel;
import pl.jewusiak.mwotodomvc.models.CreateListModel;
import pl.jewusiak.mwotodomvc.models.DeleteFormModel;
import pl.jewusiak.mwotodomvc.models.ToggleFormModel;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final ApiClient apiClient;

    @RequestMapping("/")
    public String index(Model model) {
        var lists = apiClient.getTodoLists();
        model.addAttribute(new ToggleFormModel());
        model.addAttribute(new DeleteFormModel());
        model.addAttribute(new CreateItemModel());
        model.addAttribute(new CreateListModel());
        model.addAttribute("todolists", lists);
        return "index";
    }

    @PostMapping("/toggleitem")
    public String toggleItem(ToggleFormModel toggleFormModel) {
        apiClient.toggleItem(toggleFormModel.getUuid(), Boolean.parseBoolean(toggleFormModel.getNext()));
        return "redirect:/";
    }
    @PostMapping("/deleteitem")
    public String deleteItem(DeleteFormModel deleteFormModel) {
        apiClient.deleteItem(deleteFormModel.getUuid());
        return "redirect:/";
    }
    @PostMapping("/deletelist")
    public String deleteList(DeleteFormModel deleteFormModel) {
        apiClient.deleteList(deleteFormModel.getUuid());
        return "redirect:/";
    }
    @PostMapping("/createlist")
    public String createList(CreateListModel createListModel) {
        apiClient.createList(createListModel.getName());
        return "redirect:/";
    }
    @PostMapping("/createitem")
    public String createItem(CreateItemModel createItemModel) {
        apiClient.createItem(createItemModel.getUuid(), createItemModel.getName());
        return "redirect:/";
    }
}
