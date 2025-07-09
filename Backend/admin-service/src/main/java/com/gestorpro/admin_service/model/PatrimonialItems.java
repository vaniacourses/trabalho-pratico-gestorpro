package com.gestorpro.admin_service.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "patrimonial_items")
@Entity(name = "PatrimonialItems")
@Getter
@NoArgsConstructor
public class PatrimonialItems extends Item {

    private LocalDate dataUltimaManutencao;

    public PatrimonialItems(String nome, String descricao, LocalDate dataUltimaManutencao){
        super(nome, descricao);
        this.dataUltimaManutencao = dataUltimaManutencao;
    }
}