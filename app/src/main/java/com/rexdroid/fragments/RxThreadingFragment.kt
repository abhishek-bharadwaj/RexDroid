package com.rexdroid.fragments

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rexdroid.MainActivity
import com.rexdroid.R
import com.rexdroid.base.BaseFragment
import io.reactivex.Observable

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

//        val intent = Intent(activity, TestService::class.java)
//        activity.startService(intent)
//
//        val intentService = Intent(activity, TestIntentService::class.java)
//        activity.startService(intentService)

//        Handler().post {
//            RxThreadingFragment.Hello.runRxCode()
//        }

//        Runnable {
//            RxThreadingFragment.Hello.runRxCode()
//        }.run()

//        AsyncTask.execute {
//            RxThreadingFragment.Hello.runRxCode()
//        }

//        TestAsyncTask().execute()
    }

    override fun onClick(view: View?) {
    }

    class TestService : Service() {
        private val mBinder = Binder()
        override fun onBind(p0: Intent?) = mBinder

        override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
            RxThreadingFragment.Hello.runRxCode()
            return super.onStartCommand(intent, flags, startId)
        }
    }

    class TestIntentService() : IntentService("") {

        override fun onHandleIntent(p0: Intent?) {
            RxThreadingFragment.Hello.runRxCode()
        }
    }

    class TestAsyncTask : AsyncTask<Any, Any, Any>() {
        override fun doInBackground(vararg p0: Any?): Any {
            RxThreadingFragment.Hello.runRxCode()
            return ""
        }
    }

    object Hello {

        fun runRxCode() {
            Observable.just("long", "longer", "longest", "most longest...")
                    .map { it -> it.length }
                    .doOnNext { c -> println("processing item on thread " + Thread.currentThread().name) }
                    .subscribe { length -> println("item length " + length) }
        }
    }
}