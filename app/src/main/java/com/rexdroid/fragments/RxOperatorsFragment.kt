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
        btn_concat_map.setOnClickListener(this)
        btn_map.setOnClickListener(this)
        btn_flat_map.performClick()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_flat_map -> {
                observable1.flatMap { it ->
                    println("Flatmap got called")
                    doSomeBigTaskReturnObservable(it)
                            .subscribeOn(Schedulers.newThread())
                            .doOnNext { println("processing item on thread ${Thread.currentThread().name}") }
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { activity.log(it) }

/*                observable1.map {
                    doSomeBigTaskReturnDouble(it)
                }.doOnNext { println("processing item on thread ${Thread.currentThread().name}") }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { activity.log(it) }*/
            }
        }
    }

    private fun doSomeBigTaskReturnObservable(it: Int): Observable<Int> {
        return try {
            val rand = it * 100
            Thread.sleep(rand.toLong())
            Observable.just(rand)
        } catch (e: Exception) {
            Observable.just(null)
        }
    }

    private fun doSomeBigTaskReturnDouble(it: Int): Int {
        return try {
            val rand = it * 100
            Thread.sleep(rand.toLong())
            rand
        } catch (e: Exception) {
            0
        }
    }
}