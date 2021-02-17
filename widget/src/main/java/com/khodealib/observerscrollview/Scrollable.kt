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

import android.view.ViewGroup

/**
 * Interface for providing common API for observable and scrollable widgets.
 */
interface Scrollable {
    /**
     * Add a callback listener.
     *
     * @param listener Listener to add.
     * @since 1.1.0
     */
    fun addScrollViewCallbacks(listener: ObservableScrollViewCallbacks?)

    /**
     * Remove a callback listener.
     *
     * @param listener Listener to remove.
     * @since 1.1.0
     */
    fun removeScrollViewCallbacks(listener: ObservableScrollViewCallbacks?)

    /**
     * Clear callback listeners.
     *
     * @since 1.1.0
     */
    fun clearScrollViewCallbacks()

    /**
     * Scroll vertically to the absolute Y.<br></br>
     * Implemented classes are expected to scroll to the exact Y pixels from the top,
     * but it depends on the type of the widget.
     *
     * @param y Vertical position to scroll to.
     */
    fun scrollVerticallyTo(y: Int)

    /**
     * Return the current Y of the scrollable view.
     *
     * @return Current Y pixel.
     */
    val currentScrollY: Int

    /**
     * Set a touch motion event delegation ViewGroup.<br></br>
     * This is used to pass motion events back to parent view.
     * It's up to the implementation classes whether or not it works.
     *
     * @param viewGroup ViewGroup object to dispatch motion events.
     */
    fun setTouchInterceptionViewGroup(viewGroup: ViewGroup?)
}