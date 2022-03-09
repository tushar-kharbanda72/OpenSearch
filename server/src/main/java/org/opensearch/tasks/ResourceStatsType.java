/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */

package org.opensearch.tasks;

/** Defines the different types of resource stats. */
public enum ResourceStatsType {
    // Resource stats of the worker thread that is reported directly from runnable.
    WORKER_STATS("worker_stats", false);

    private final String statsType;
    private final boolean onlyForAnalysis;

    ResourceStatsType(String statsType, boolean onlyForAnalysis) {
        this.statsType = statsType;
        this.onlyForAnalysis = onlyForAnalysis;
    }

    public boolean isOnlyForAnalysis() {
        return onlyForAnalysis;
    }

    @Override
    public String toString() {
        return statsType;
    }
}
