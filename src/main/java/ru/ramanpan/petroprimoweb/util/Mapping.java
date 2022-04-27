package ru.ramanpan.petroprimoweb.util;

import ru.ramanpan.petroprimoweb.DTO.UserResultDTO;
import ru.ramanpan.petroprimoweb.model.UsersResults;

public class Mapping {
    public static UserResultDTO toUserResultDTO(UsersResults usersResults) {
        UserResultDTO u = new UserResultDTO();
        u.setId(usersResults.getId());
        u.setResult(usersResults.getResultNum());
        u.setCorrectness(usersResults.getResult().getCorrectness());
        u.setHeader(usersResults.getResult().getHeader());
        u.setDescription(usersResults.getResult().getDescription());
        u.setPicture(usersResults.getResult().getPicture());
        u.setName(usersResults.getTest().getTest().getName());
        u.setCreated(usersResults.getCreated());
        return u;
    }


}
