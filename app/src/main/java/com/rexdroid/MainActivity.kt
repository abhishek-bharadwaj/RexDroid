package com.rexdroid

import android.os.Bundle
import android.view.View
import io.reactivex.Maybe
import io.reactivex.MaybeObserver
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import java.util.*

class MainActivity : BaseActivity() {

    val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * No onComplete here as its meant to return one result.
     */
    public fun singleExample(v: View) {
        val methodName = object : Any() {}.javaClass.enclosingMethod.name
        Single.just(Random().nextBoolean())
                .subscribe(object : SingleObserver<Boolean> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onError(e: Throwable) {
                        log(TAG, "$methodName --> onError $e")
                    }

                    override fun onSuccess(t: Boolean) {
                        log(TAG, "$methodName --> onSuccess $t")
                    }
                })

    }

    /**
     * It will be calling onSuccess or onComplete or onError
     */
    public fun mayBeExample(v: View) {
        val methodName = object : Any() {}.javaClass.enclosingMethod.name
        Maybe.just(Random().nextBoolean())
                .filter { it }
                .subscribe(object : MaybeObserver<Boolean> {
                    override fun onSuccess(t: Boolean) {
                        log(TAG, "$methodName --> onSuccess $t")
                    }

                    override fun onError(e: Throwable) {
                        log(TAG, "$methodName --> onError $e")
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onComplete() {
                        log(TAG, "$methodName --> onComplete")
                    }

                })
    }
}
