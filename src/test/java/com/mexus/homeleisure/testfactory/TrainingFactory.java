package com.mexus.homeleisure.testfactory;

import com.mexus.homeleisure.api.training.data.Training;
import com.mexus.homeleisure.api.training.data.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TrainingFactory extends AccountFactory {

    @Autowired
    protected TrainingRepository trainingRepository;

    @Transactional
    public Training generateTraining(int i) {
        return this.trainingRepository.save(
                new Training(
                        null,
                        generateUserAndGetUser(i),
                        "트레이닝" + i,
                        "트레이닝 본문입니다."
                ));
    }
}
