package com.rexdroid

import android.os.Bundle
import android.view.View
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscription
import java.util.*

class ObservableExampleActivity : BaseActivity() {

    val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * No onComplete here as its meant to return one result.
     */
    fun singleExample(v: View) {
        val methodName = object : Any() {}.javaClass.enclosingMethod.name
        Single.just(Random().nextBoolean())
                .subscribe(object : SingleObserver<Boolean> {
                    override fun onSubscribe(d: Disposable) {
                        log(TAG, "$methodName --> onSubscribe")
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
    fun mayBeExample(v: View) {
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
                        log(TAG, "$methodName --> onSubscribe")
                    }

                    override fun onComplete() {
                        log(TAG, "$methodName --> onComplete")
                    }
                })
    }

    fun observableExample(v: View) {
        val methodName = object : Any() {}.javaClass.enclosingMethod.name
        Observable.fromArray(3, 4, 5, 6, 7, 8, 9, 12)
                .subscribe(object : Observer<Int> {

                    override fun onSubscribe(d: Disposable) {
                        log(TAG, "$methodName --> onSubscribe")
                    }

                    override fun onNext(t: Int) {
                        log(TAG, "$methodName --> onNext ---> $t")
                    }

                    override fun onError(e: Throwable) {
                        log(TAG, "$methodName --> onError")
                    }

                    override fun onComplete() {
                        log(TAG, "$methodName --> onComplete")
                    }
                })
    }

    fun flowableExample(v: View) {
        val methodName = object : Any() {}.javaClass.enclosingMethod.name
        Flowable.fromArray(4, 8, 12, 24, 56, 78, 200)
                .subscribe {
                    log(TAG, "$methodName --> onNext ---> $it")
                }
        Flowable.fromArray(4, 8, 12, 24, 56, 78, 200)
                .subscribe(object : FlowableSubscriber<Int> {
                    override fun onNext(t: Int) {
                        log(TAG, "$methodName --> onNext ---> $t")
                    }

                    override fun onError(e: Throwable) {
                        log(TAG, "$methodName --> onError")
                    }

                    override fun onComplete() {
                        log(TAG, "$methodName --> onComplete")
                    }

                    override fun onSubscribe(s: Subscription) {
                        log(TAG, "$methodName --> onSubscribe")
                    }
                })
    }

    fun completableExample(v: View) {
        val methodName = object : Any() {}.javaClass.enclosingMethod.name
        Completable.complete().subscribe(object : CompletableObserver {
            override fun onComplete() {
                log(TAG, "$methodName --> onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                log(TAG, "$methodName --> onSubscribe")
            }

            override fun onError(e: Throwable) {
                log(TAG, "$methodName --> onError")
            }
        })
    }
}
