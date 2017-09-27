package com.rexdroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rexdroid.MainActivity
import com.rexdroid.R
import com.rexdroid.base.BaseFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_rx_operators.*
import java.util.*

/**
 * Created by abhishek on 27/9/17.
 */

class RxOperatorsFragment : BaseFragment(), View.OnClickListener {

    lateinit var activity: MainActivity

    companion object {
        fun getInstance() = RxOperatorsFragment()
    }

    val observable1 = Observable.range(1, 10)
    val observable2 = Observable.range(11, 10)

    override fun getDefaultLayout(inflater: LayoutInflater?, container: ViewGroup?)
            = inflater?.inflate(R.layout.fragment_rx_operators, container, false)

    override fun extractArguments(extras: Bundle) {

    }

    override fun init() {
        activity = getActivity() as MainActivity
        activity.supportActionBar?.title = javaClass.simpleName

        btn_flat_map.setOnClickListener(this)
        btn_flat_map.performClick()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_flat_map -> {
                observable1.flatMap { it ->
                    doSomeBigTask().subscribeOn(Schedulers.newThread())
                            .doOnNext { println("processing item on thread ${Thread.currentThread().name}") }
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { activity.log(it) }
            }
        }
    }

    private fun doSomeBigTask(): Observable<Double> {
        return try {
            val rand = Random().nextDouble() * 1000
            Thread.sleep(rand.toLong())
            Observable.just(rand)
        } catch (e: Exception) {
            Observable.just(null)
        }
    }
}