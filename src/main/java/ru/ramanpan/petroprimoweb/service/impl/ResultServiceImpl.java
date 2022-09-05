package ru.ramanpan.petroprimoweb.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ramanpan.petroprimoweb.dto.ResultDTO;
import ru.ramanpan.petroprimoweb.exceptions.NotFoundException;
import ru.ramanpan.petroprimoweb.model.Result;
import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.repository.ResultRepo;
import ru.ramanpan.petroprimoweb.service.ResultService;
import ru.ramanpan.petroprimoweb.service.TestService;
import ru.ramanpan.petroprimoweb.util.Constants;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ResultServiceImpl implements ResultService {
    private final ResultRepo resultRepo;
    private final TestService testService;


    @Override
    public List<Result> findAll() {
        return resultRepo.findAll();
    }

    @Override
    public Result findById(Long id) {
        return resultRepo.findById(id).orElseThrow(() -> new NotFoundException(Constants.RESULT_NOT_FOUND));
    }

    @Override
    public List<Result> findAllByTestId(Long testId) {
        Test test = testService.findById(testId);
        return resultRepo.findAllByTest(test);
    }

    @Override
    public void deleteById(Long id) {
        resultRepo.deleteById(id);
    }

    @Override
    public Result findByDescription(String description) {
        return resultRepo.findByDescription(description).orElseThrow(() -> new NotFoundException(Constants.RESULT_NOT_FOUND));
    }

    @Override
    public Result findByHeader(String header) {
        return resultRepo.findByHeader(header).orElseThrow(() -> new NotFoundException(Constants.RESULT_NOT_FOUND));
    }

    @Override
    public Result save(ResultDTO resultDTO) {
        Result result = new Result();
        result.setDescription(resultDTO.getDescription());
        if (" ".equals(resultDTO.getPicture())) result.setPicture("plug.png");
        else result.setPicture(resultDTO.getPicture());
        result.setHeader(resultDTO.getHeader());
        result.setStartCondition(Double.valueOf(resultDTO.getStartCondition()));
        result.setEndCondition(Double.valueOf(resultDTO.getEndCondition()));
        result.setTest(testService.findById(resultDTO.getTestLong()));
        result.setCorrectness(resultDTO.getCorrectness());
        result.setCreated(new Date());
        return resultRepo.save(result);
    }

    @Override
    public Result update(ResultDTO resultDTO) {
        Result result = findById(resultDTO.getId());
        result.setDescription(resultDTO.getDescription());
        result.setPicture(resultDTO.getPicture());
        result.setStartCondition(Double.valueOf(resultDTO.getStartCondition()));
        result.setEndCondition(Double.valueOf(resultDTO.getEndCondition()));
        result.setTest(testService.findById(resultDTO.getTestLong()));
        result.setCorrectness(resultDTO.getCorrectness());
        result.setHeader(resultDTO.getHeader());
        return resultRepo.save(result);
    }
}
