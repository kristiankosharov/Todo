package demo.todosample.ui

import android.content.Intent
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.intercepting.SingleActivityFactory
import androidx.test.uiautomator.UiDevice
import demo.todosample.R
import demo.todosample.mock
import demo.todosample.repository.TodoRepository
import demo.todosample.utils.*
import junit.framework.AssertionFailedError
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    val executorRule = TaskExecutorWithIdlingResourceRule()
    @Rule
    @JvmField
    val countingAppExecutors = CountingAppExecutorsRule()

    private val repository: TodoRepository = mock()
    private val repoViewModel = MainViewModel(repository)

    private val injectedFactory = object : SingleActivityFactory<MainActivity>(MainActivity::class.java) {
        override fun create(intent: Intent): MainActivity {
            val activity = MainActivity()
            activity.appExecutors = countingAppExecutors.appExecutors
            activity.mainViewModelFactory = ViewModelUtil.createFor(repoViewModel, repository)
            return activity
        }
    }

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(injectedFactory, true, true)

    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityRule)

    private val device = UiDevice.getInstance(getInstrumentation())

    private companion object {
        const val testDescription: String = "Test Todo"
        const val testStrikeItem = "To strike"
    }

    @Before
    fun init() {
        activityRule.activity.clearList()
    }

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
        onView(withId(R.id.todo_description)).perform(typeText(testDescription))
        onView(withId(R.id.btn_add)).perform(click())

        onView(withId(R.id.todo_description)).check(doesNotExist())
        onView(allOf(withText(testDescription), withParent(withId(R.id.todo_list))))
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
        onView(withId(R.id.todo_description)).perform(typeText(testDescription))
        device.setOrientationRight()
        onView(withId(R.id.todo_description)).check(matches(isDisplayed()))
        onView(withId(R.id.todo_description)).check(matches(withText(testDescription)))
    }

    @Test
    fun checkEmptyView() {
        try {
            onView(withId(R.id.todo_list)).check(RecyclerViewItemCountAssertion(0))
            onView(withId(R.id.empty_container)).check(matches(isDisplayed()))
        } catch (ex: AssertionFailedError) {
            onView(withId(R.id.empty_container)).check(matches(not(isDisplayed())))
        }
    }

    @Test
    fun strikeThroughItem() {
        /** Add new item with description **/
        onView(withId(R.id.add_todo)).perform(click())
        onView(withId(R.id.todo_description)).perform(typeText(testStrikeItem))
        onView(withId(R.id.btn_add)).perform(click())
        onView(withId(R.id.todo_list)).check(RecyclerViewItemCountAssertion(1))

        strikeAndCheck(true)
    }

    @Test
    fun releaseItem() {
        strikeThroughItem()
        strikeAndCheck(false)
    }

    private fun strikeAndCheck(expectation: Boolean) {
        onView(withId(R.id.todo_list))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(
                allOf(
                        withId(R.id.todo_item),
                        withText(testStrikeItem),
                        withEffectiveVisibility(Visibility.VISIBLE)
                )
        )
                .check(StrikeTextViewAssertion(expectation))
    }

    fun androidHomeMatcher(): Matcher<View> {
        return allOf(
                withParent(withClassName(`is`(Toolbar::class.java.name))),
                withClassName(anyOf(
                        `is`(ImageButton::class.java.name),
                        `is`(AppCompatImageButton::class.java.name)
                )))
    }
}