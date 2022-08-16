package ru.ramanpan.petroprimoweb.util;

import ru.ramanpan.petroprimoweb.DTO.QuestionDTO;
import ru.ramanpan.petroprimoweb.DTO.ResultDTO;
import ru.ramanpan.petroprimoweb.DTO.TestCardDTO;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.model.enums.DeterministicOption;
import ru.ramanpan.petroprimoweb.model.enums.TestType;

import java.util.List;

public class Switches {
    public static DeterministicOption selectionOption(String option) {
        switch (option) {
            case "Легкие, Средние, Сложные":
                return DeterministicOption.EASY_MEDIUM_HARD;
            case "Легкие, Сложные, Средние":
                return DeterministicOption.EASY_HARD_MEDIUM;
            case "Сложные, Легкие, Средние":
                return DeterministicOption.HARD_EASY_MEDIUM;
            case "Сложные, Средние, Легкие":
                return DeterministicOption.HARD_MEDIUM_EASY;
            case "Средние, Легкие, Сложные":
                return DeterministicOption.MEDIUM_EASY_HARD;
            case "Средние, Сложные, Легкие":
                return DeterministicOption.MEDIUM_HARD_EASY;
            case "Политические, Культурные, Экономические":
                return DeterministicOption.POLITIC_CULTURE_ECONOMIC;
            case "Политические, Экономические, Культурные":
                return DeterministicOption.POLITIC_ECONOMIC_CULTURE;
            case "Экономические, Политические, Культурные":
                return DeterministicOption.ECONOMIC_POLITIC_CULTURE;
            case "Экономические, Культурные, Политические":
                return DeterministicOption.ECONOMIC_CULTURE_POLITIC;
            case "Культурные, Экономические, Политические":
                return DeterministicOption.CULTURE_ECONOMIC_POLITIC;
            case "Культурные, Политические, Экономические":
                return DeterministicOption.CULTURE_POLITIC_ECONOMIC;
            default:
                return DeterministicOption.UNDEFINED;
        }
    }

    public static List<Question> selectionOptionForDeterministic(DeterministicOption option, List<Question> easy, List<Question> medium, List<Question> hard, List<Question> politic, List<Question> culture, List<Question> economic) {
        switch (option.name()) {
            case "EASY_MEDIUM_HARD":
                easy.addAll(medium);
                easy.addAll(hard);
                return easy;
            case "EASY_HARD_MEDIUM":
                easy.addAll(hard);
                easy.addAll(medium);
                return easy;
            case "HARD_EASY_MEDIUM":
                hard.addAll(easy);
                hard.addAll(medium);
                return hard;
            case "HARD_MEDIUM_EASY":
                hard.addAll(medium);
                hard.addAll(easy);
                return hard;
            case "MEDIUM_EASY_HARD":
                medium.addAll(easy);
                medium.addAll(hard);
                return medium;
            case "MEDIUM_HARD_EASY":
                medium.addAll(hard);
                medium.addAll(easy);
                return medium;
            case "POLITIC_CULTURE_ECONOMIC":
                politic.addAll(culture);
                politic.addAll(economic);
                return politic;
            case "POLITIC_ECONOMIC_CULTURE":
                politic.addAll(economic);
                politic.addAll(culture);
                return politic;
            case "ECONOMIC_POLITIC_CULTURE":
                economic.addAll(politic);
                economic.addAll(culture);
                return economic;
            case "ECONOMIC_CULTURE_POLITIC":
                economic.addAll(culture);
                economic.addAll(politic);
                return economic;
            case "CULTURE_ECONOMIC_POLITIC":
                culture.addAll(economic);
                culture.addAll(politic);
                return culture;
            case "CULTURE_POLITIC_ECONOMIC":
                culture.addAll(politic);
                culture.addAll(economic);
                return culture;
            default:
                return null;
        }
    }

    public static TestCardDTO testCardForUpdate(TestCardDTO testCardDTO, Test t) {
        testCardDTO.setIsDeterministic(false);
        switch (t.getTestType().name()) {
            case "STOCHASTIC":
                testCardDTO.setTestTypeNum(0);
                break;
            case "DYNAMIC":
                testCardDTO.setTestTypeNum(2);
                break;
            default:
                testCardDTO.setIsDeterministic(true);
                testCardDTO.setTestTypeNum(1);
        }
        if (t.getOptionForDeterministicType() != null) {
            switch (t.getOptionForDeterministicType().name()) {
                case "EASY_MEDIUM_HARD":
                    testCardDTO.setOptionNum(1);
                    break;
                case "EASY_HARD_MEDIUM":
                    testCardDTO.setOptionNum(2);
                    break;
                case "HARD_EASY_MEDIUM":
                    testCardDTO.setOptionNum(3);
                    break;
                case "HARD_MEDIUM_EASY":
                    testCardDTO.setOptionNum(4);
                    break;
                case "MEDIUM_EASY_HARD":
                    testCardDTO.setOptionNum(5);
                    break;
                case "MEDIUM_HARD_EASY":
                    testCardDTO.setOptionNum(6);
                    break;
                case "POLITIC_CULTURE_ECONOMIC":
                    testCardDTO.setOptionNum(7);
                    break;
                case "POLITIC_ECONOMIC_CULTURE":
                    testCardDTO.setOptionNum(8);
                    break;
                case "ECONOMIC_POLITIC_CULTURE":
                    testCardDTO.setOptionNum(9);
                    break;
                case "ECONOMIC_CULTURE_POLITIC":
                    testCardDTO.setOptionNum(10);
                    break;
                case "CULTURE_ECONOMIC_POLITIC":
                    testCardDTO.setOptionNum(11);
                    break;
                case "CULTURE_POLITIC_ECONOMIC":
                    testCardDTO.setOptionNum(12);
                    break;
                default:
                    testCardDTO.setOptionNum(0);
            }
        }
        return testCardDTO;
    }

    public static QuestionDTO questionForUpdate(QuestionDTO questionDTO, Question q, int i) {
        if (q.getType().name().equals("CLOSED")) questionDTO.setTypeNum(0);
        else {
            questionDTO.setTypeNum(1);
        }
        if (q.getDifficult().name().equals("EASY")) questionDTO.setDifficultNum(0);
        else if (q.getDifficult().name().equals("MEDIUM")) questionDTO.setDifficultNum(1);
        else questionDTO.setDifficultNum(2);
        if (q.getCategory().name().equals("POLITIC")) questionDTO.setCategoryNum(0);
        else if (q.getCategory().name().equals("ECONOMIC")) questionDTO.setCategoryNum(2);
        else questionDTO.setCategoryNum(1);
        questionDTO.setNumber(i);
        return questionDTO;
    }

    public static ResultDTO resultForUpdate(ResultDTO resultDTO, int i) {
        resultDTO.setNumber(i);
        return resultDTO;
    }


    public static TestType selectionTestType(String option) {
        switch (option) {
            case "Стохастический":
                return TestType.STOCHASTIC;
            case "Динамический":
                return TestType.DYNAMIC;
            default:
                return TestType.DETERMINISTIC;
        }
    }
}
