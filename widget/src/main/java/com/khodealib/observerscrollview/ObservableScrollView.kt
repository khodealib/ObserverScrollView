/*
 * Copyright 2021 Ali Bagheri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.khodealib.observerscrollview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import java.util.*

/**
 * ScrollView that its scroll position can be observed.
 */
class ObservableScrollView : NestedScrollView, Scrollable {
    // Fields that should be saved onSaveInstanceState
    private var mPrevScrollY = 0
    override var currentScrollY = 0
        private set

    // Fields that don't need to be saved onSaveInstanceState
    private var mCallbacks: ObservableScrollViewCallbacks? = null
    private var mCallbackCollection: MutableList<ObservableScrollViewCallbacks?>? = null
    private var mScrollState: ScrollState? = null
    private var mFirstScroll = false
    private var mDragging = false
    private var mIntercepted = false
    private var mPrevMoveEvent: MotionEvent? = null
    private var mTouchInterceptionViewGroup: ViewGroup? = null

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    )

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!, attrs, defStyle
    )

    public override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as SavedState
        mPrevScrollY = ss.prevScrollY
        currentScrollY = ss.scrollY
        super.onRestoreInstanceState(ss.superState)
    }

    public override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val ss = SavedState(superState)
        ss.prevScrollY = mPrevScrollY
        ss.scrollY = currentScrollY
        return ss
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (hasNoCallbacks()) {
            return
        }
        currentScrollY = t
        dispatchOnScrollChanged(t, mFirstScroll, mDragging)
        if (mFirstScroll) {
            mFirstScroll = false
        }
        if (mPrevScrollY < t) {
            mScrollState = ScrollState.UP
        } else if (t < mPrevScrollY) {
            mScrollState = ScrollState.DOWN
            //} else {
            // Keep previous state while dragging.
            // Never makes it STOP even if scrollY not changed.
            // Before Android 4.4, onTouchEvent calls onScrollChanged directly for ACTION_MOVE,
            // which makes mScrollState always STOP when onUpOrCancelMotionEvent is called.
            // STOP state is now meaningless for ScrollView.
        }
        mPrevScrollY = t
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (hasNoCallbacks()) {
            return super.onInterceptTouchEvent(ev)
        }
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                run {
                    mDragging = true
                    mFirstScroll = mDragging
                }
                dispatchOnDownMotionEvent()
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (hasNoCallbacks()) {
            return super.onTouchEvent(ev)
        }
        when (ev.actionMasked) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                mIntercepted = false
                mDragging = false
                dispatchOnUpOrCancelMotionEvent(mScrollState)
            }
            MotionEvent.ACTION_MOVE -> {
                if (mPrevMoveEvent == null) {
                    mPrevMoveEvent = ev
                }
                val diffY = ev.y - mPrevMoveEvent!!.y
                mPrevMoveEvent = MotionEvent.obtainNoHistory(ev)
                if (currentScrollY - diffY <= 0) {
                    // Can't scroll anymore.
                    if (mIntercepted) {
                        // Already dispatched ACTION_DOWN event to parents, so stop here.
                        return false
                    }

                    // Apps can set the interception target other than the direct parent.
                    val parent: ViewGroup = mTouchInterceptionViewGroup ?: parent as ViewGroup

                    // Get offset to parents. If the parent is not the direct parent,
                    // we should aggregate offsets from all of the parents.
                    var offsetX = 0f
                    var offsetY = 0f
                    var v: View? = this
                    while (v != null && v !== parent) {
                        offsetX += (v.left - v.scrollX).toFloat()
                        offsetY += (v.top - v.scrollY).toFloat()
                        v = v.parent as View
                    }
                    val event = MotionEvent.obtainNoHistory(ev)
                    event.offsetLocation(offsetX, offsetY)
                    if (parent.onInterceptTouchEvent(event)) {
                        mIntercepted = true

                        // If the parent wants to intercept ACTION_MOVE events,
                        // we pass ACTION_DOWN event to the parent
                        // as if these touch events just have began now.
                        event.action = MotionEvent.ACTION_DOWN

                        // Return this onTouchEvent() first and set ACTION_DOWN event for parent
                        // to the queue, to keep events sequence.
                        post { parent.dispatchTouchEvent(event) }
                        return false
                    }
                    // Even when this can't be scrolled anymore,
                    // simply returning false here may cause subView's click,
                    // so delegate it to super.
                    return super.onTouchEvent(ev)
                }
            }
        }
        return super.onTouchEvent(ev)
    }

    override fun addScrollViewCallbacks(listener: ObservableScrollViewCallbacks?) {
        if (mCallbackCollection == null) {
            mCallbackCollection = ArrayList()
        }
        mCallbackCollection!!.add(listener)
    }

    override fun removeScrollViewCallbacks(listener: ObservableScrollViewCallbacks?) {
        if (mCallbackCollection != null) {
            mCallbackCollection!!.remove(listener)
        }
    }

    override fun clearScrollViewCallbacks() {
        if (mCallbackCollection != null) {
            mCallbackCollection!!.clear()
        }
    }

    override fun setTouchInterceptionViewGroup(viewGroup: ViewGroup?) {
        mTouchInterceptionViewGroup = viewGroup
    }

    override fun scrollVerticallyTo(y: Int) {
        scrollTo(0, y)
    }

    private fun dispatchOnDownMotionEvent() {
        if (mCallbacks != null) {
            mCallbacks!!.onDownMotionEvent()
        }
        if (mCallbackCollection != null) {
            for (i in mCallbackCollection!!.indices) {
                val callbacks = mCallbackCollection!![i]
                callbacks!!.onDownMotionEvent()
            }
        }
    }

    private fun dispatchOnScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
        if (mCallbacks != null) {
            mCallbacks!!.onScrollChanged(scrollY, firstScroll, dragging)
        }
        if (mCallbackCollection != null) {
            for (i in mCallbackCollection!!.indices) {
                val callbacks = mCallbackCollection!![i]
                callbacks!!.onScrollChanged(scrollY, firstScroll, dragging)
            }
        }
    }

    private fun dispatchOnUpOrCancelMotionEvent(scrollState: ScrollState?) {
        if (mCallbacks != null) {
            mCallbacks!!.onUpOrCancelMotionEvent(scrollState)
        }
        if (mCallbackCollection != null) {
            for (i in mCallbackCollection!!.indices) {
                val callbacks = mCallbackCollection!![i]
                callbacks!!.onUpOrCancelMotionEvent(scrollState)
            }
        }
    }

    private fun hasNoCallbacks(): Boolean {
        return mCallbacks == null && mCallbackCollection == null
    }

    internal class SavedState : BaseSavedState {
        var prevScrollY = 0
        var scrollY = 0

        /**
         * Called by onSaveInstanceState.
         */
        constructor(superState: Parcelable?) : super(superState)

        /**
         * Called by CREATOR.
         */
        private constructor(`in`: Parcel) : super(`in`) {
            prevScrollY = `in`.readInt()
            scrollY = `in`.readInt()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(prevScrollY)
            out.writeInt(scrollY)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState?> = object : Parcelable.Creator<SavedState?> {
                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}