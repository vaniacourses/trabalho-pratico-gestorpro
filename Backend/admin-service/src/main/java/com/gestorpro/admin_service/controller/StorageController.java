package com.gestorpro.admin_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestorpro.admin_service.dto.CreateItemDTO;
import com.gestorpro.admin_service.model.Item;
import com.gestorpro.admin_service.service.ItemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/admin/items")
public class StorageController {
    @Autowired
    private ItemService itemService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> criarItem(@RequestBody CreateItemDTO item) {
        if(!(item.tipo().equalsIgnoreCase("patrimonio") || item.tipo().equalsIgnoreCase("descartavel"))){
            return ResponseEntity.badRequest().body("O tipo deve ser \"patrimônio\" ou \"descartavel\"");
        }

        Item createdItem = itemService.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @GetMapping()
    public ResponseEntity<List<Item>> getItems() {
        return ResponseEntity.ok().body(itemService.listAll());
    }
    
    
}
