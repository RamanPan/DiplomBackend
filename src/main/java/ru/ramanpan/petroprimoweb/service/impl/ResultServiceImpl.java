package ru.ramanpan.petroprimoweb.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ramanpan.petroprimoweb.DTO.ResultDTO;
import ru.ramanpan.petroprimoweb.exceptions.NotFoundException;
import ru.ramanpan.petroprimoweb.model.Result;
import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.repository.ResultRepo;
import ru.ramanpan.petroprimoweb.repository.TestRepo;
import ru.ramanpan.petroprimoweb.service.ResultService;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ResultServiceImpl implements ResultService {
    private final ResultRepo resultRepo;
    private final TestRepo testRepo;


    @Override
    public List<Result> findAll() {
        return resultRepo.findAll();
    }

    @Override
    public Result findById(Long id) {
        return resultRepo.findById(id).orElseThrow(() -> new NotFoundException("Result was not found"));
    }

    @Override
    public List<Result> findAllByTestId(Long test_id) {
        Test test = testRepo.findById(test_id).orElseThrow(() -> new NotFoundException("Test was not found"));
        return resultRepo.findAllByTest(test).orElseThrow(() -> new NotFoundException("Result was not found"));
    }

    @Override
    public void deleteById(Long id) {
        resultRepo.deleteById(id);
    }

    @Override
    public Result findByDescription(String description) {
        return resultRepo.findByDescription(description).orElseThrow(() -> new NotFoundException("Result was not found"));
    }

    @Override
    public Result findByHeader(String header) {
        return resultRepo.findByHeader(header).orElseThrow(() -> new NotFoundException("Result was not found"));
    }

    @Override
    public Long save(ResultDTO resultDTO) {
        Result result = new Result();
        result.setDescription(resultDTO.getDescription());
        if (resultDTO.getPicture().equals(" ")) result.setPicture("plug.png");
        else result.setPicture(resultDTO.getPicture());
        result.setHeader(resultDTO.getHeader());
        result.setStartCondition(Double.valueOf(resultDTO.getStartCondition()));
        result.setEndCondition(Double.valueOf(resultDTO.getEndCondition()));
        result.setTest(testRepo.findById(resultDTO.getTestLong()).orElseThrow(() -> new NotFoundException("Test was not found")));
        result.setCorrectness(resultDTO.getCorrectness());
        result.setCreated(new Date());
        return resultRepo.save(result).getId();
    }

    @Override
    public Long update(ResultDTO resultDTO) {
        Result result = resultRepo.findById(resultDTO.getId()).orElseThrow(() -> new NotFoundException("Result was not found"));
        result.setDescription(resultDTO.getDescription());
        result.setPicture(resultDTO.getPicture());
        result.setStartCondition(Double.valueOf(resultDTO.getStartCondition()));
        result.setEndCondition(Double.valueOf(resultDTO.getEndCondition()));
        result.setTest(testRepo.findById(resultDTO.getTestLong()).orElseThrow(() -> new NotFoundException("Test was not found")));
        result.setCorrectness(resultDTO.getCorrectness());
        result.setHeader(resultDTO.getHeader());
        return resultRepo.save(result).getId();
    }
}
