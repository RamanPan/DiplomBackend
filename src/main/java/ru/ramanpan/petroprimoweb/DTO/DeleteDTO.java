package ru.ramanpan.petroprimoweb.DTO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DeleteDTO {
    private Long id;

    public DeleteDTO(Long id) {
        this.id = id;
    }
}
