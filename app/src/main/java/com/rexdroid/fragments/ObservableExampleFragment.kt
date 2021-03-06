package com.rexdroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rexdroid.MainActivity
import com.rexdroid.R
import com.rexdroid.base.BaseFragment
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subscribers.DisposableSubscriber
import kotlinx.android.synthetic.main.fragment_observable_example.*
import java.util.*

class ObservableExampleFragment : BaseFragment(), View.OnClickListener {

    lateinit var activity: MainActivity

    companion object {
        fun getInstance() = ObservableExampleFragment()
    }

    override fun getDefaultLayout(inflater: LayoutInflater?, container: ViewGroup?)
            = inflater?.inflate(R.layout.fragment_observable_example, container, false)

    override fun extractArguments(extras: Bundle) {
    }

    override fun init() {
        activity = getActivity() as MainActivity
        activity.supportActionBar?.title = javaClass.simpleName

        btn_single.setOnClickListener(this)
        btn_maybe.setOnClickListener(this)
        btn_observable.setOnClickListener(this)
        btn_flowable.setOnClickListener(this)
        btn_completable.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_single -> singleExample()
            R.id.btn_maybe -> mayBeExample()
            R.id.btn_observable -> observableExample()
            R.id.btn_flowable -> flowableExample()
            R.id.btn_completable -> completableExample()
        }
    }

    /**
     * No onComplete here as its meant to return one result.
     */
    private fun singleExample() {
        val methodName = object : Any() {}.javaClass.enclosingMethod.name
        Single.just(Random().nextBoolean())
                .subscribe(object : SingleObserver<Boolean> {
                    override fun onSubscribe(d: Disposable) {
                        activity.log("$methodName --> onSubscribe")
                    }

                    override fun onError(e: Throwable) {
                        activity.log("$methodName --> onError $e")
                    }

                    override fun onSuccess(t: Boolean) {
                        activity.log("$methodName --> onSuccess : value $t")
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
                        activity.log("$methodName --> onSuccess : value $t")
                    }

                    override fun onError(e: Throwable) {
                        activity.log("$methodName --> onError $e")
                    }

                    override fun onSubscribe(d: Disposable) {
                        activity.log("$methodName --> onSubscribe")
                    }

                    override fun onComplete() {
                        activity.log("$methodName --> onComplete")
                    }
                })
    }

    private fun observableExample() {
        val methodName = object : Any() {}.javaClass.enclosingMethod.name
        Observable.fromArray(3, 4, 5, 6, 7, 8, 9, 12)
                .subscribe(object : Observer<Int> {

                    override fun onSubscribe(d: Disposable) {
                        activity.log("$methodName --> onSubscribe")
                    }

                    override fun onNext(t: Int) {
                        activity.log("$methodName --> onNext : value $t")
                    }

                    override fun onError(e: Throwable) {
                        activity.log("$methodName --> onError $e")
                    }

                    override fun onComplete() {
                        activity.log("$methodName --> onComplete")
                    }
                })
    }

    private fun flowableExample() {
        val methodName = object : Any() {}.javaClass.enclosingMethod.name
        /*Flowable.fromArray(4, 8, 12, 24, 56, 78, 200)
                .subscribe {
                    activity.log("$methodName --> onNext ---> $it")
                }*/
        Flowable.fromArray(4, 8, 12, 24, 56, 78, 200)
                .subscribe(object : DisposableSubscriber<Int>() {
                    override fun onNext(t: Int) {
                        activity.log("$methodName --> onNext ---> : value  $t")
                    }

                    override fun onError(e: Throwable) {
                        activity.log("$methodName --> onError")
                    }

                    override fun onComplete() {
                        activity.log("$methodName --> onComplete")
                    }

                    /*override fun onSubscribe(s: Subscription) {
                        activity.log("$methodName --> onSubscribe")
                    }*/
                })
    }

    private fun completableExample() {
        val methodName = object : Any() {}.javaClass.enclosingMethod.name
        Completable.complete().subscribe(object : CompletableObserver {
            override fun onComplete() {
                activity.log("$methodName --> onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                activity.log("$methodName --> onSubscribe")
            }

            override fun onError(e: Throwable) {
                activity.log("$methodName --> onError")
            }
        })
    }
}
