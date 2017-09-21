package com.rexdroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.rexdroid.fragments.LoggerFragment
import com.rexdroid.fragments.ObservableExampleFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by abhishek on 21/8/17.
 */

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val loggerFragment = LoggerFragment.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_observable_example.setOnClickListener(this)
        replaceFragment(loggerFragment, fl_logger.id)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_observable_example -> {
                ll_container.removeAllViews()
                replaceFragment(ObservableExampleFragment.getInstance(), ll_container.id)
            }
        }
    }

    fun log(any: Any) {
        loggerFragment.addLog(":/> $any")
    }

    private fun replaceFragment(fragment: Fragment, layout: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(layout, fragment, fragment.javaClass.simpleName)
        fragmentTransaction.commit()
    }
}