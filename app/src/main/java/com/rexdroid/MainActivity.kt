package com.rexdroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.rexdroid.fragments.LoggerFragment
import com.rexdroid.fragments.ObservableExampleFragment
import com.rexdroid.fragments.RxThreadingFragment
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
        btn_rx_threading.setOnClickListener(this)

        replaceFragment(loggerFragment, fl_logger)
        replaceFragment(RxThreadingFragment.getInstance(), ll_container)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_observable_example -> {
                replaceFragment(ObservableExampleFragment.getInstance(), ll_container)
            }
            R.id.btn_rx_threading -> {
                replaceFragment(RxThreadingFragment.getInstance(), ll_container)
            }
        }
    }

    fun log(any: Any) {
        println(any.toString())
        loggerFragment.addLog(":/> $any")
    }

    private fun replaceFragment(fragment: Fragment, layout: ViewGroup) {
        layout.removeAllViews()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(layout.id, fragment, fragment.javaClass.simpleName)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStack()
    }
}