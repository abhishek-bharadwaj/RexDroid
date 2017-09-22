package com.rexdroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rexdroid.MainActivity
import com.rexdroid.R
import com.rexdroid.base.BaseFragment
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by abhishek on 21/9/17.
 */

class RxThreadingFragment : BaseFragment(), View.OnClickListener {

    lateinit var activity: MainActivity

    companion object {
        fun getInstance() = RxThreadingFragment()
    }

    override fun getDefaultLayout(inflater: LayoutInflater?, container: ViewGroup?)
            = inflater?.inflate(R.layout.fragment_rx_threading, container, false)

    override fun extractArguments(extras: Bundle) {
    }

    override fun init() {
        activity = getActivity() as MainActivity
        activity.supportActionBar?.title = javaClass.simpleName

        Observable.just("long", "longer", "longest", "most longest...")
                .doOnNext({ c -> activity.log("processing item on thread " + Thread.currentThread().name) })
                .map { it -> it.length }
                .subscribe { length -> activity.log("item length " + length) }
    }

    override fun onClick(view: View?) {
    }
}