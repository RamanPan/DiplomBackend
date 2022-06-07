package ru.ramanpan.petroprimoweb.service.impl;

import org.springframework.stereotype.Service;
import ru.ramanpan.petroprimoweb.model.Result;
import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.repository.ResultRepo;
import ru.ramanpan.petroprimoweb.repository.TestRepo;
import ru.ramanpan.petroprimoweb.service.ResultService;

import java.util.Date;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {
    private final ResultRepo resultRepo;
    private final TestRepo testRepo;

    public ResultServiceImpl(ResultRepo resultRepo, TestRepo testRepo) {
        this.resultRepo = resultRepo;
        this.testRepo = testRepo;
    }

    @Override
    public List<Result> findAll() {
        return resultRepo.findAll();
    }

    @Override
    public Result findById(Long id) {
        return resultRepo.findById(id).orElse(null);
    }

    @Override
    public List<Result> findAllByTestId(Long test_id) {
        Test test = testRepo.findById(test_id).orElse(null);
        return resultRepo.findAllByTest(test).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        resultRepo.deleteById(id);
    }

    @Override
    public Result findByDescription(String description) {
        return resultRepo.findByDescription(description).orElse(null);
    }

    @Override
    public Result findByHeader(String header) {
        return resultRepo.findByHeader(header).orElse(null);
    }

    @Override
    public Long save(Result result) {
        result.setCreated(new Date());
        return resultRepo.save(result).getId();
    }

    @Override
    public Long update(Result result) {
        return resultRepo.save(result).getId();
    }
}
