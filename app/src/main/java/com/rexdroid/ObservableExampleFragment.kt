package com.rexdroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rexdroid.base.BaseFragment
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_observable_example.*
import org.reactivestreams.Subscription
import java.util.*

class ObservableExampleFragment : BaseFragment(), View.OnClickListener {

    val TAG = javaClass.simpleName
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
                        activity.log(TAG, "$methodName --> onSubscribe")
                    }

                    override fun onError(e: Throwable) {
                        activity.log(TAG, "$methodName --> onError $e")
                    }

                    override fun onSuccess(t: Boolean) {
                        activity.log(TAG, "$methodName --> onSuccess $t")
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
                        activity.log(TAG, "$methodName --> onSuccess $t")
                    }

                    override fun onError(e: Throwable) {
                        activity.log(TAG, "$methodName --> onError $e")
                    }

                    override fun onSubscribe(d: Disposable) {
                        activity.log(TAG, "$methodName --> onSubscribe")
                    }

                    override fun onComplete() {
                        activity.log(TAG, "$methodName --> onComplete")
                    }
                })
    }

    private fun observableExample() {
        val methodName = object : Any() {}.javaClass.enclosingMethod.name
        Observable.fromArray(3, 4, 5, 6, 7, 8, 9, 12)
                .subscribe(object : Observer<Int> {

                    override fun onSubscribe(d: Disposable) {
                        activity.log(TAG, "$methodName --> onSubscribe")
                    }

                    override fun onNext(t: Int) {
                        activity.log(TAG, "$methodName --> onNext ---> $t")
                    }

                    override fun onError(e: Throwable) {
                        activity.log(TAG, "$methodName --> onError")
                    }

                    override fun onComplete() {
                        activity.log(TAG, "$methodName --> onComplete")
                    }
                })
    }

    private fun flowableExample() {
        val methodName = object : Any() {}.javaClass.enclosingMethod.name
        Flowable.fromArray(4, 8, 12, 24, 56, 78, 200)
                .subscribe {
                    activity.log(TAG, "$methodName --> onNext ---> $it")
                }
        Flowable.fromArray(4, 8, 12, 24, 56, 78, 200)
                .subscribe(object : FlowableSubscriber<Int> {
                    override fun onNext(t: Int) {
                        activity.log(TAG, "$methodName --> onNext ---> $t")
                    }

                    override fun onError(e: Throwable) {
                        activity.log(TAG, "$methodName --> onError")
                    }

                    override fun onComplete() {
                        activity.log(TAG, "$methodName --> onComplete")
                    }

                    override fun onSubscribe(s: Subscription) {
                        activity.log(TAG, "$methodName --> onSubscribe")
                    }
                })
    }

    private fun completableExample() {
        val methodName = object : Any() {}.javaClass.enclosingMethod.name
        Completable.complete().subscribe(object : CompletableObserver {
            override fun onComplete() {
                activity.log(TAG, "$methodName --> onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                activity.log(TAG, "$methodName --> onSubscribe")
            }

            override fun onError(e: Throwable) {
                activity.log(TAG, "$methodName --> onError")
            }
        })
    }
}
