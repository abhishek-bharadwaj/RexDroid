package com.rexdroid.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by abhishek on 4/9/17.
 */

abstract class BaseFragment : Fragment() {

    abstract fun getDefaultLayout(inflater: LayoutInflater?, container: ViewGroup?): View?

    abstract fun extractArguments(extras: Bundle)

    abstract fun init()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = getDefaultLayout(inflater, container)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val extras = arguments
        if (extras != null) {
            extractArguments(extras)
        }
        init()
    }
}