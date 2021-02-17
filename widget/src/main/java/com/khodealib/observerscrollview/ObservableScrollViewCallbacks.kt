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

/**
 * Callbacks for Scrollable widgets.
 */
interface ObservableScrollViewCallbacks {
    /**
     * Called when the scroll change events occurred.
     *
     * This won't be called just after the view is laid out, so if you'd like to
     * initialize the position of your views with this method, you should call this manually
     * or invoke scroll as appropriate.
     *
     * @param scrollY     Scroll position in Y axis.
     * @param firstScroll True when this is called for the first time in the consecutive motion events.
     * @param dragging    True when the view is dragged and false when the view is scrolled in the inertia.
     */
    fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean)

    /**
     * Called when the down motion event occurred.
     */
    fun onDownMotionEvent()

    /**
     * Called when the dragging ended or canceled.
     *
     * @param scrollState State to indicate the scroll direction.
     */
    fun onUpOrCancelMotionEvent(scrollState: ScrollState?)
}