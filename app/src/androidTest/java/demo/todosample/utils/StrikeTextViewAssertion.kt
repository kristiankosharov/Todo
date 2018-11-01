package demo.todosample.utils

import android.view.View
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers
import demo.todosample.util.StrikeThroughTextView
import org.hamcrest.CoreMatchers.`is`


class StrikeTextViewAssertion(private val expectedState: Boolean) : ViewAssertion {
    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val textView = view as StrikeThroughTextView
        ViewMatchers.assertThat(textView.addStrike, `is`(expectedState))
    }
}