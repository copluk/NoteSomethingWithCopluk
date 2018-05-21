package com.design.copluk.copluksample.controller

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.*
import android.util.Log
import android.view.ViewTreeObserver
import com.design.copluk.copluksample.R
import com.design.copluk.copluksample.adapter.MainAdapter

import kotlinx.android.synthetic.main.activity_scrollview_height.*
import java.util.ArrayList

/**
 * Created by copluk on 2018/4/28.
 */

public class ScrollViewHeightActivity : AppCompatActivity() {
    var totalViewHeight: Int = 0
    var scrollParams: Int = 0
    var maxHeight: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrollview_height)

        val adapter = MainAdapter(this)
        rcyView.adapter = adapter
        rcyView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val strings = ArrayList<String>()
        for (i in 0..25) {
            strings.add("$i : $i")
        }

        adapter.data = strings
    }

    override fun onStart() {
        super.onStart()

        rcyView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)

        rcyView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                when (newState) {

                    SCROLL_STATE_DRAGGING -> //Start
                        Log.e("onScrollStateChanged", "SCROLL_STATE_DRAGGING")

                    SCROLL_STATE_SETTLING -> { //Touch Over
                        Log.e("onScrollStateChanged", "SCROLL_STATE_SETTLING")
                    }

                    SCROLL_STATE_IDLE -> //finish Animating
                    {
                        Log.e("onScrollStateChanged", "SCROLL_STATE_IDLE")
                        val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                        val firstPosition = layoutManager.findFirstVisibleItemPosition()

                        if (firstPosition == 0) {

                            val firstTop = layoutManager.findViewByPosition(0).top
                            Log.e("firstTop", "$firstTop")
                            if (firstTop == 0) {

                                val params = viewFixed.layoutParams
                                params.height = maxHeight
                                params.width = viewFixed.width
                                viewFixed.layoutParams = params
                            }

                        }
                    }


                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                var resetWhenScroll = false
                val params = viewFixed.layoutParams
                if (dy > 0 && totalViewHeight - params.height > 0) {
                    resetWhenScroll = true
                }

                if (dy < 0 && totalViewHeight - params.height < scrollParams) {
                    resetWhenScroll = true
                }

                if (resetWhenScroll) {

                    params.height = viewFixed.height + dy / 2
                    params.width = viewFixed.width

                    if (dy < 0 && params.height < maxHeight)
                        params.height = maxHeight

                    viewFixed.layoutParams = params
                }

            }
        })

    }

    private val onGlobalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (rcyView.childCount > 0) {
                reSizeFixedView()
                rcyView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        }
    }

    override fun onStop() {
        super.onStop()

        rcyView.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    private fun reSizeFixedView() {

        maxHeight = resources.getDimension(R.dimen.scvChangeMaxHeight).toInt()

        val height = viewFixed.height
        if (height > maxHeight) {
            val params = viewFixed.layoutParams
            params.height = resources.getDimension(R.dimen.scvChangeMaxHeight).toInt()
            params.width = viewFixed.width

            viewFixed.layoutParams = params
        }

        totalViewHeight = viewBase.height
        scrollParams = viewBase.height - viewFixed.layoutParams.height


    }
}