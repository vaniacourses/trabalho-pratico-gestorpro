package com.gestorpro.admin_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "disposable_items")
@Entity(name = "DisposableItem")
@Getter
@NoArgsConstructor
public class DisposableItem extends Item {

    private int quantidade;

    public DisposableItem(String nome, String descricao, int quantidade){
        super(nome, descricao);
        this.quantidade = quantidade;
    }
}