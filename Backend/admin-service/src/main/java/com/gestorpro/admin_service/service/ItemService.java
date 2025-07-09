package com.gestorpro.admin_service.service;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestorpro.admin_service.dto.CreateItemDTO;
import com.gestorpro.admin_service.model.Item;
import com.gestorpro.admin_service.model.DisposableItem;
import com.gestorpro.admin_service.model.PatrimonialItems;
import com.gestorpro.admin_service.repository.ItemDescartavelRepository;
import com.gestorpro.admin_service.repository.ItemPatrimonioRepository;
import com.gestorpro.admin_service.repository.ItemRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemPatrimonioRepository itemPatrimonioRepository;

    @Autowired
    private ItemDescartavelRepository itemDescartavelRepository;

    public Item save(CreateItemDTO item) {
        Item saveItem = null;
        if(item.tipo().equalsIgnoreCase("descartavel")){
            saveItem = new DisposableItem(item.nome(), item.descricao(), item.quantidade());
        }
        else if(item.tipo().equalsIgnoreCase("patrimonio")){
            saveItem = new PatrimonialItems(item.nome(), item.descricao(), item.ultimaManutencao());
        }

        if(saveItem == null){
            throw new IllegalArgumentException("Item inválido");
        }
        return itemRepository.save(saveItem);
    }

    public List<Item> listAll() {
        return itemRepository.findAll();
    }

    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }
}