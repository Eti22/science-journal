/*
 *  Copyright 2016 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.google.android.apps.forscience.javalib;

public abstract class Refresher {
    private final Scheduler mScheduler;

    private boolean mRefreshScheduled = false;
    private Runnable mRefreshRunnable = new Runnable() {
        @Override
        public void run() {
            mRefreshScheduled = false;
            refresh();
        }
    };
    private Delay mDelay;

    public Refresher(Scheduler scheduler, Delay delay) {
        mScheduler = scheduler;
        mDelay = delay;
    }

    public void refresh() {
        final boolean rescheduleWouldBeUseful = doRefresh();
        if (rescheduleWouldBeUseful && !mRefreshScheduled) {
            mRefreshScheduled = true;
            mScheduler.schedule(mDelay, mRefreshRunnable);
        }
    }

    /**
     * Does the scheduler-specific work of refreshing
     *
     * @return true iff another refresh should be scheduled.
     */
    protected abstract boolean doRefresh();
}