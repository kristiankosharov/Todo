package demo.todosample.ui

import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import demo.todosample.R
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true, true)


    private val device = UiDevice.getInstance(getInstrumentation())

    @Test
    fun showAddDialog() {
        onView(withId(R.id.add_todo)).perform(click())
        onView(withId(R.id.todo_description))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
        onView(withId(R.id.btn_cancel))
                .inRoot(isDialog())
                .check(matches(withText(android.R.string.cancel)))
                .check(matches(isDisplayed()))
        onView(withId(R.id.btn_add))
                .inRoot(isDialog())
                .check(matches(withText(R.string.dialog_button_add)))
                .check(matches(isDisplayed()))
    }

    @Test
    fun showAndDismissDialog() {
        onView(withId(R.id.add_todo)).perform(click())
        onView(withId(R.id.btn_cancel)).perform(click())
        onView(withId(R.id.todo_description)).check(doesNotExist())
    }

    @Test
    fun showAndSaveDialog() {
        onView(withId(R.id.add_todo)).perform(click())
        onView(withId(R.id.todo_description)).check(matches(isDisplayed()))
        onView(withId(R.id.todo_description)).perform(typeText("Test Todo"))
        onView(withId(R.id.btn_add)).perform(click())

        onView(withId(R.id.todo_description)).check(doesNotExist())
        onView(allOf(withText("Test Todo"), withParent(withId(R.id.todo_list))))
    }

    @Test
    fun showAndRotateDialog() {
        onView(withId(R.id.add_todo)).perform(click())
        onView(withId(R.id.todo_description)).check(matches(isDisplayed()))
        device.setOrientationRight()
        onView(withId(R.id.todo_description)).check(matches(isDisplayed()))
    }

    @Test
    fun showInputRotateDialog() {
        onView(withId(R.id.add_todo)).perform(click())
        onView(withId(R.id.todo_description)).check(matches(isDisplayed()))
        onView(withId(R.id.todo_description)).perform(typeText("Test Todo"))
        device.setOrientationRight()
        onView(withId(R.id.todo_description)).check(matches(isDisplayed()))
        onView(withId(R.id.todo_description)).check(matches(withText("Test Todo")))
    }

    private fun androidHomeMatcher(): Matcher<View> {
        return allOf(
                withParent(withClassName(`is`(Toolbar::class.java.name))),
                withClassName(anyOf(
                        `is`(ImageButton::class.java.name),
                        `is`(AppCompatImageButton::class.java.name)
                )))
    }
}