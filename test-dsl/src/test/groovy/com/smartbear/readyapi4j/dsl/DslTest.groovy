package com.smartbear.readyapi4j.dsl

import com.smartbear.readyapi.client.model.GroovyScriptTestStep
import com.smartbear.readyapi.client.model.RestTestRequestStep
import com.smartbear.readyapi.client.model.TestStep
import com.smartbear.readyapi4j.TestRecipe
import org.junit.Test;

import static TestDsl.recipe
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat
import static org.junit.Assert.assertTrue
import static org.junit.Assert.fail

class DslTest {

    private static final String SCRIPT_TEXT = "println 'Peekaboo, little Earth'"
    public static final String URI = "/uri_to_get"

    @Test
    void buildsSimpleRecipe() throws Exception {
        TestRecipe recipe = recipe {
            groovyScriptStep SCRIPT_TEXT
        }

        TestStep singleStep = extractFirstTestStep(recipe)
        assertThat(singleStep, is(GroovyScriptTestStep))
        assertThat(singleStep.script, is(SCRIPT_TEXT))
    }

    @Test
    void buildsRecipeWithGET() throws Exception {
        TestRecipe recipe = recipe {
            GET(URI)
        }

        verifyValuesAndMethod(recipe, 'GET')
    }

    @Test
    void buildsRecipeWithPOST() throws Exception {
        TestRecipe recipe = recipe {
            POST(URI)
        }

        verifyValuesAndMethod(recipe, 'POST')
    }

    @Test
    void buildsRecipeWithPUT() throws Exception {
        TestRecipe recipe = recipe {
            PUT(URI)
        }

        verifyValuesAndMethod(recipe, 'PUT')
    }

    @Test
    void buildsRecipeWithDELETE() throws Exception {
        TestRecipe recipe = recipe {
            DELETE(URI)
        }

        verifyValuesAndMethod(recipe, 'DELETE')
    }

    @Test
    void parameterizesRestRequest() throws Exception {
        String stepName = 'theGET'
        TestRecipe recipe = recipe {
            //Bug in the IntelliJ Groovyc - need parentheses here to make it compile!
            GET ('/some_uri', name: stepName, headers: ['Cache-Control': 'nocache'], followRedirects: true,
                    entitizeParameters: true, postQueryString: true, timeout: 5000)
        }

        RestTestRequestStep restRequest = extractFirstTestStep(recipe) as RestTestRequestStep
        assertThat(restRequest.name, is(stepName))
        assertThat(restRequest.headers['Cache-Control'], is(['nocache']))
        assertTrue('Not respecting followRedirects', restRequest.followRedirects)
        assertTrue('Not respecting entitizeParameters', restRequest.entitizeParameters)
        assertTrue('Not respecting postQueryString', restRequest.postQueryString)
        assertThat(restRequest.timeout, is ('5000'))
    }

    private static TestStep extractFirstTestStep(TestRecipe recipe) {
        List<TestStep> testSteps = recipe?.testCase?.testSteps
        if (!testSteps) {
            fail('No test case or test steps created')
        }
        return testSteps.first()
    }

    private static void verifyValuesAndMethod(TestRecipe recipe, String method) {
        RestTestRequestStep restRequest = extractFirstTestStep(recipe) as RestTestRequestStep
        assertThat(restRequest.URI, is(URI))
        assertThat(restRequest.method, is(method))
    }

}
