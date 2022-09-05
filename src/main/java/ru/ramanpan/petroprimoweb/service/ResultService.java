package ru.ramanpan.petroprimoweb.service;

import ru.ramanpan.petroprimoweb.dto.ResultDTO;
import ru.ramanpan.petroprimoweb.model.Result;

import java.util.List;

public interface ResultService {
    List<Result> findAll();

    Result findById(Long id);

    List<Result> findAllByTestId(Long testId);

    void deleteById(Long id);

    Result findByDescription(String description);

    Result findByHeader(String header);

    Result save(ResultDTO resultDTO);

    Result update(ResultDTO resultDTO);
}
