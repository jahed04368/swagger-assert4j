package com.smartbear.readyapi4j.samples.java;

import com.smartbear.readyapi4j.result.RecipeExecutionResult;
import org.junit.Test;

import static com.smartbear.readyapi4j.assertions.Assertions.json;
import static com.smartbear.readyapi4j.facade.execution.RecipeExecutionFacade.executeRecipe;
import static com.smartbear.readyapi4j.support.AssertionUtils.assertExecutionResult;
import static com.smartbear.readyapi4j.teststeps.TestSteps.GET;
import static com.smartbear.readyapi4j.teststeps.restrequest.ParameterBuilder.query;

public class SimpleRestTest extends ApiTestBase {

    @Test
    public void simpleCountTest() throws Exception {
        RecipeExecutionResult result = executeRecipe(
            GET("https://api.swaggerhub.com/specs")
                .withParameters(
                    query("specType", "API"),
                    query("query", "testserver")
                )
                .withAssertions(
                    json("$.totalCount", "4")
                )
        );

        assertExecutionResult(result);
    }

    @Test
    public void simpleTest() throws Exception {
        RecipeExecutionResult result = executeRecipe(
            GET("https://api.swaggerhub.com/apis")
                .assertValidStatusCodes(303)
        );

        assertExecutionResult(result);
    }
}
