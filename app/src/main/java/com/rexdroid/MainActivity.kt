package com.rexdroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Maybe
import io.reactivex.MaybeObserver
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import java.util.*

class MainActivity : AppCompatActivity() {

    val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        singlesExample()
        mayBeExample()
    }

    private fun log(any: Any) {
        Log.w(TAG, any.toString())
    }

    /**
     * No onComplete here as its meant to return one result
     */
    private fun singlesExample() {
        val methodName = object : Any() {}.javaClass.enclosingMethod.name
        Single.just(Random().nextBoolean())
                .subscribe(object : SingleObserver<Boolean> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onError(e: Throwable) {
                        log("$methodName --> onError $e")
                    }

                    override fun onSuccess(t: Boolean) {
                        log("$methodName --> onSuccess $t")
                    }
                })

    }

    /**
     * It will be calling onSuccess or onComplete or onError
     */
    private fun mayBeExample() {
        val methodName = object : Any() {}.javaClass.enclosingMethod.name
        Maybe.just(Random().nextBoolean())
                .filter { it }
                .subscribe(object : MaybeObserver<Boolean> {
                    override fun onSuccess(t: Boolean) {
                        log("$methodName --> onSuccess $t")
                    }

                    override fun onError(e: Throwable) {
                        log("$methodName --> onError $e")
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onComplete() {
                        log("$methodName --> onComplete")
                    }

                })
    }
}
