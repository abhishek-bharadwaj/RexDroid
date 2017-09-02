package com.rexdroid

import android.support.v7.app.AppCompatActivity
import android.util.Log

/**
 * Created by abhishek on 21/8/17.
 */

abstract class BaseActivity : AppCompatActivity() {

    fun log(tag: String, any: Any) {
        Log.w(tag, any.toString())
    }
}