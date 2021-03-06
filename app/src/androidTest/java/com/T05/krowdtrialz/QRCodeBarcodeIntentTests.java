package com.T05.krowdtrialz;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import android.app.Activity;
import android.widget.EditText;
import android.widget.SearchView;

import com.T05.krowdtrialz.ui.SplashActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class QRCodeBarcodeIntentTests {
    private Solo solo;
    private String description = "Determine what rate of success a coin has";
    private String minTrials = "13";
    private String region = "Antarctica";
    private String passCriteria = "Heads";
    private String failCriteria = "Tails";
    private String searchTerm = "Determine what rate of success a coin";
    private String pass = "10";
    private String fail = "5";

    @Rule
    public ActivityTestRule<MainActivity> ruleMain =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @ClassRule
    public static ActivityTestRule<SplashActivity> ruleSplash =
            new ActivityTestRule<>(SplashActivity.class, true, true);

    @BeforeClass
    public static void beforeClass() {
        Solo solo = new Solo(InstrumentationRegistry.getInstrumentation(), ruleSplash.getActivity());
        Activity activity = ruleSplash.getActivity();
        solo.waitForActivity(SplashActivity.class);
    }

    @Before
    public void before() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), ruleMain.getActivity());
        Activity activity = ruleMain.getActivity();
        solo.waitForActivity(MainActivity.class);
    }

    @Test
    /**
     * @alert Verification for US 03.01.01
     */
    public void testGenerateQRCode(){
        publish();

        search();

        //Make sure the experiment shows up
        assertTrue(solo.waitForText(description));

        //Click on experiment
        solo.clickOnText(description);

        //Click add trials button
        solo.waitForView(solo.getView(R.id.add_trials_experiment));
        solo.clickOnView(solo.getView(R.id.add_trials_experiment));

        //Make sure warning appears
        assertTrue(solo.waitForText("Warning! This experiment requires your location."));

        //Click through warning
        solo.clickOnText("Continue");

        //Click Generate qr button
        solo.clickOnView(solo.getView(R.id.generate_qr_button));

        // Make sure we are still on add trial screen
        assertTrue(solo.waitForView(R.id.generate_qr_button));

        //Click pass EditText
        solo.waitForView(solo.getView(R.id.submit_trial_button));
        EditText passEditText = (EditText) solo.getView(R.id.binomial1_editText);
        solo.clickOnView(passEditText);

        //Type a pass value
        solo.typeText(passEditText, pass);

        //Click fail EditText
        solo.waitForView(solo.getView(R.id.submit_trial_button));
        EditText failEditText = (EditText) solo.getView(R.id.binomial2_editText);
        solo.clickOnView(failEditText);

        //Type a fail value
        solo.typeText(failEditText, fail);


        //Click generate qr button
        solo.clickOnView(solo.getView(R.id.generate_qr_button));

        //Confirm we have generated qrcode
        assertTrue(solo.waitForView(R.id.qr_code_imageView));

        //Go back to subscribed page
        solo.goBack();
        solo.goBack();
        solo.goBack();
        solo.hideSoftKeyboard();
        solo.goBack();

        delete();
    }

    @Test
    /**
     * @alert Verification for US 03.03.01
     */
    public void testAssignBarcode(){
        publish();

        search();

        //Make sure the experiment shows up
        assertTrue(solo.waitForText(description));

        //Click on experiment
        solo.clickOnText(description);

        //Click add trials button
        solo.waitForView(solo.getView(R.id.add_trials_experiment));
        solo.clickOnView(solo.getView(R.id.add_trials_experiment));

        //Make sure warning appears
        assertTrue(solo.waitForText("Warning! This experiment requires your location."));

        //Click through warning
        solo.clickOnText("Continue");

        //Click Barcode button
        solo.clickOnView(solo.getView(R.id.assign_barcode_button));

        // Make sure we are still on add trial screen
        assertTrue(solo.waitForView(R.id.generate_qr_button));

        //Click pass EditText
        solo.waitForView(solo.getView(R.id.submit_trial_button));
        EditText passEditText = (EditText) solo.getView(R.id.binomial1_editText);
        solo.clickOnView(passEditText);

        //Type a pass value
        solo.typeText(passEditText, pass);

        //Click fail EditText
        solo.waitForView(solo.getView(R.id.submit_trial_button));
        EditText failEditText = (EditText) solo.getView(R.id.binomial2_editText);
        solo.clickOnView(failEditText);

        //Type a fail value
        solo.typeText(failEditText, fail);


        //Click barcode button
        solo.clickOnView(solo.getView(R.id.assign_barcode_button));

        //Confirm we have made it to camera
        assertTrue(solo.waitForView(R.id.scanner_codeScannerView));

        //Go back to subscribed page
        solo.goBack();
        solo.goBack();
        solo.goBack();
        solo.hideSoftKeyboard();
        solo.goBack();

        delete();
    }

    @Test
    /**
     * @alert Verification for US 03.02.01
     */
    public void testScanner(){
        // Click on scan button
        solo.waitForView(R.id.scan_button);
        solo.clickOnView(solo.getView(R.id.scan_button));

        //Confirm we have made it to camera
        assertTrue(solo.waitForView(R.id.scanner_codeScannerView));
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

    private void search(){
        //Click on search icon
        solo.waitForView(R.id.search_action_button);
        solo.clickOnView(solo.getView(R.id.search_action_button));

        //Click on search bar
        SearchView searchBar = (SearchView) solo.getView(R.id.search_experiment_query);
        solo.clickOnView(searchBar);
        solo.sleep(500);

        //Type in a word from the description
        solo.typeText(0, searchTerm);
    }

    private void publish(){
        //Click on publish tab
        solo.waitForView(R.id.search_action_button);
        solo.clickOnView(solo.getView(R.id.navigation_publish));

        // Click on "Publish New Experiment" button
        solo.clickOnView(solo.getView(R.id.new_publish_button));
        solo.waitForView(R.id.geo_location_toggle);

        //Click on binomial
        solo.clickOnView(solo.getView(R.id.binomial_experiment_radio));

        //Click on add a description editText
        EditText descriptionBox = (EditText) solo.getView(R.id.experiment_description_input);
        solo.clickOnView(descriptionBox);
        solo.sleep(1000);

        //Type in a description
        solo.typeText(descriptionBox, description);

        //Click on geo location required toggle
        solo.clickOnView(solo.getView(R.id.geo_location_toggle));

        //Click on min trials editText
        EditText minTrialsBox = (EditText) solo.getView(R.id.minimum_trials_input);
        solo.clickOnView(minTrialsBox);
        solo.sleep(500);

        //Type in minTrials
        solo.typeText(minTrialsBox, minTrials);

        //Click on experiment region
        EditText regionBox = (EditText) solo.getView(R.id.experiment_region_input);
        solo.clickOnView(regionBox);
        solo.sleep(500);

        //Type in a region
        solo.typeText(regionBox, region);

        //Click on pass criteria
        EditText passCriteriaBox = (EditText) solo.getView(R.id.binomial_pass_criteria_input);
        solo.clickOnView(passCriteriaBox);
        solo.sleep(500);

        //Type in a pass criteria
        solo.typeText(passCriteriaBox, passCriteria);

        //Click on fail criteria
        EditText failCriteriaBox = (EditText) solo.getView(R.id.binomial_fail_criteria_input);
        solo.clickOnView(failCriteriaBox);
        solo.sleep(500);

        //Type in a fail criteria
        solo.typeText(failCriteriaBox, failCriteria);

        //Click on publish
        solo.clickOnView(solo.getView(R.id.publish_experiment_button));
    }

    private void delete(){
        //Click on search icon
        solo.waitForView(R.id.search_action_button);
        solo.clickOnView(solo.getView(R.id.search_action_button));

        //Click on search bar
        SearchView searchBar = (SearchView) solo.getView(R.id.search_experiment_query);
        solo.clickOnView(searchBar);
        solo.sleep(500);

        //Type in a word from the description
        solo.typeText(0, searchTerm);

        //Make sure the experiment shows up
        assertTrue(solo.waitForText(description));

        //Click on experiment
        solo.clickOnText(description);

        //Click on more tab
        solo.waitForView(R.id.subscribe_button_experiment);
        solo.clickOnText("More");

        //Click on unpublish button
        solo.clickOnView(solo.getView(R.id.delete_experiment_button));
    }
}
