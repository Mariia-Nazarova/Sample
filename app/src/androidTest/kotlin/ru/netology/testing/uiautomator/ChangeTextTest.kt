package ru.netology.testing.uiautomator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


const val SETTINGS_PACKAGE = "com.android.settings"
const val MODEL_PACKAGE = "ru.netology.testing.uiautomator"

const val TIMEOUT = 5000L

@RunWith(AndroidJUnit4::class)
class ChangeTextTest {

    private lateinit var device: UiDevice
    private val textView = "Hello UiAutomator!"
    private val textToSet = "Netology"
    private val textIsNullOrBlank = "  "
    private val textToSet2 = "test"

    @Before
    fun beforeEachTest() {
        // Press home
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
    }

    private fun waitForPackage(packageName: String) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
    }

    @After
    fun afterEachTest() {
        Thread.sleep(5000)
    }

    //ЛЕКЦИОННЫЙ МАТЕРИАЛ
    @Test
    fun testInternetSettings() {
        waitForPackage(SETTINGS_PACKAGE)

        device.findObject(
            UiSelector().resourceId("android:id/title").instance(0)
        ).click()
    }

    @Test
    fun testChangeText() {
        waitForPackage(MODEL_PACKAGE)

        device.findObject(By.res(MODEL_PACKAGE, "userInput")).text = textToSet
        device.findObject(By.res(MODEL_PACKAGE, "buttonChange")).click()

        val result = device.findObject(By.res(MODEL_PACKAGE, "textToBeChanged")).text
        assertEquals(result, textToSet)
    }

    //ДОМАШНЕЕ ЗАДАНИЕ

    @Test
    fun testTextisNullOrBlank() {
        waitForPackage(MODEL_PACKAGE)
        device.findObject(By.res(MODEL_PACKAGE, "userInput")).text = textIsNullOrBlank
        device.findObject(By.res(MODEL_PACKAGE, "buttonChange")).click()

        val result = device.findObject(By.res(MODEL_PACKAGE, "textToBeChanged")).text
        assertEquals(result, textView)

    }

    @Test
    fun testNewActivity() {
        waitForPackage(MODEL_PACKAGE)
        device.findObject(By.res(MODEL_PACKAGE, "userInput")).text = textToSet2
        device.findObject(By.res(MODEL_PACKAGE, "buttonActivity")).click()
        device.wait(Until.hasObject(By.res(MODEL_PACKAGE, "text")), TIMEOUT)

        val result = device.findObject(By.res(MODEL_PACKAGE, "text")).text
        assertEquals(result, textToSet2)

    }

}



