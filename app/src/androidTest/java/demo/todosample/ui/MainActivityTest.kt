package demo.todosample.ui

import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
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

    @Test
    fun clickAddTodo() {
        onView(withId(R.id.add_todo)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText("Add clicked")))
    }

    @Test
    fun removeTodo() {
        onView(androidHomeMatcher()).perform(click());
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText("Remove clicked")))
    }

    private fun androidHomeMatcher(): Matcher<View> {
        return allOf(
                withParent(withClassName(`is`(Toolbar::class.java!!.getName()))),
                withClassName(anyOf(
                        `is`(ImageButton::class.java.name),
                        `is`(AppCompatImageButton::class.java.name)
                )))
    }
}