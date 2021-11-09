/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */

package org.opensearch.tasks;

import java.util.concurrent.atomic.AtomicLong;

/**
 *  Defines resource stats information.
 */
public class ResourceStatsInfo {
    private final long startValue;
    private final AtomicLong endValue;

    public ResourceStatsInfo(long startValue) {
        this.startValue = startValue;
        this.endValue = new AtomicLong(startValue);
    }

    public long getTotalValue() {
        return endValue.get() - startValue;
    }

    public long getEndValue() {
        return endValue.get();
    }

    public boolean compareAndSetEndValue(long initialEndValue, long newEndValue) {
        return this.endValue.compareAndSet(initialEndValue, newEndValue);
    }

    @Override
    public String toString() {
        return String.valueOf(getTotalValue());
    }
}
