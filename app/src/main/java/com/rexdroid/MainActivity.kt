package com.rexdroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by abhishek on 21/8/17.
 */

class MainActivity : AppCompatActivity() {

    private val loggerFragment = LoggerFragment.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(ObservableExampleFragment.getInstance(), fl_container.id)
        replaceFragment(loggerFragment, fl_logger.id)
    }

    fun log(tag: String, any: Any) {
        Log.w(tag, any.toString())
        loggerFragment.addLog(":/>$tag : $any")
    }

    private fun replaceFragment(fragment: Fragment, layout: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(layout, fragment, fragment.javaClass.simpleName)
        fragmentTransaction.commit()
    }
}