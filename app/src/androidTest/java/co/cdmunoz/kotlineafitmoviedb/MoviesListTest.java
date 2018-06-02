package co.cdmunoz.kotlineafitmoviedb;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.cdmunoz.kotlineafitmoviedb.ui.list.ListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MoviesListTest {

    @Rule
    public ActivityTestRule<ListActivity> activityTestRule = new ActivityTestRule<>(ListActivity.class);

    @Test
    public void listIsDisplayed(){
        onView(withId(R.id.movies_list)).check(matches(isDisplayed()));
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())));
    }

    @Test
    public void scrollToitem() {
        onView(withId(R.id.movies_list)).perform(RecyclerViewActions.scrollToPosition(4));
    }

    @Test
    public void scrollToItemAndClickOnIt() {
        onView(withId(R.id.movies_list)).perform(RecyclerViewActions.scrollToPosition(4));
        onView(withId(R.id.movies_list)).perform(click());
    }

    @Test
    public void scrollToItemClickOnItAndCheckInfo() {
        String TEXT_TO_MATCH = "Jurassic World: Fallen Kingdom";
        onView(withId(R.id.movies_list)).perform(RecyclerViewActions.scrollToPosition(4));
        onView(withId(R.id.movies_list)).perform(click());
        onView(withId(R.id.detail_title)).check(matches(withText(TEXT_TO_MATCH)));
    }

    @Test
    public void checkLabelOnDetailsScreen(){
        onView(withId(R.id.movies_list)).perform(RecyclerViewActions.actionOnItemAtPosition(7, click()));
        onView(withId(R.id.detail_release_date))
                .check(matches(
                        withText(containsString(activityTestRule.getActivity().getResources().getString(R.string.release_date_lbl)))));
    }

    @Test
    public void backButtonTest(){
        scrollToItemAndClickOnIt();
        Espresso.pressBack();
        listIsDisplayed();
    }
}
