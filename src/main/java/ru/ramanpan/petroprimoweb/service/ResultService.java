package ru.ramanpan.petroprimoweb.service;

import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.Result;

import java.util.List;

public interface ResultService {
    List<Result> findAll();
    Result findById(Long id);
    List<Result> findAllByTestId(Long test_id);
    void deleteById(Long id);
    Result findByDescription(String description);
    Result findByHeader(String header);
    Long save(Result result);
    Long update(Result result);
}
