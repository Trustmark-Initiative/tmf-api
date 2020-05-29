package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStepResult;
import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStepResultType;

/**
 * Created by brad on 4/28/16.
 */
public class AssessmentStepResultImpl implements AssessmentStepResult {

    private String assessmentStepId;
    private Integer assessmentStepNumber;
    private AssessmentStepResultType result;

    @Override
    public String getAssessmentStepId() {
        return assessmentStepId;
    }

    public void setAssessmentStepId(String assessmentStepId) {
        this.assessmentStepId = assessmentStepId;
    }

    @Override
    public Integer getAssessmentStepNumber() {
        return assessmentStepNumber;
    }

    public void setAssessmentStepNumber(Integer assessmentStepNumber) {
        this.assessmentStepNumber = assessmentStepNumber;
    }

    @Override
    public AssessmentStepResultType getResult() {
        return result;
    }

    public void setResult(AssessmentStepResultType result) {
        this.result = result;
    }
}
