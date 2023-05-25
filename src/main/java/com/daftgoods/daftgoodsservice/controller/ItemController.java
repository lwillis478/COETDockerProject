package com.daftgoods.daftgoodsservice.controller;

import com.daftgoods.daftgoodsservice.core.item.Item;
import com.daftgoods.daftgoodsservice.core.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping()
    public List<Item> getAllItems()
    {
        return itemRepository.findAll();
    }

    @PostMapping()
    public Item postItem(@RequestBody Item toPost)
    {
        return itemRepository.save(toPost);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable UUID id)
    {
        itemRepository.deleteById(id);
    }
}
