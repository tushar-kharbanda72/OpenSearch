/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */

package org.opensearch.tasks;

import java.util.EnumMap;

/**
 * Resource consumption information about a particular execution of thread.
 * <p>
 * It captures the resource usage information about a particular execution of thread.
 * across different stats type like worker_stats or response_stats etc.,
 */
public class ThreadResourceInfo {
    private final long threadId;
    private final EnumMap<ResourceStatsType, ResourceUsageInfo> resourceUsageInfos = new EnumMap<>(ResourceStatsType.class);
    // WORKER_STATS is the primary resource consumption updater from the runnable and
    // thus only it can update the current status of the thread.
    private volatile boolean isActive = true;

    public ThreadResourceInfo(long threadId, ResourceStatsType statsType, ResourceUsageMetric... resourceUsageMetrics) {
        this.threadId = threadId;
        this.resourceUsageInfos.put(statsType, new ResourceUsageInfo(resourceUsageMetrics));
    }

    public void updateResourceInfo(ResourceStatsType statsType, ResourceUsageMetric... resourceUsageMetrics) {
        ResourceUsageInfo resourceUsageInfo = resourceUsageInfos.get(statsType);
        if (resourceUsageInfo == null) {
            resourceUsageInfos.put(statsType, new ResourceUsageInfo(resourceUsageMetrics));
        } else {
            resourceUsageInfo.recordResourceUsageMetrics(resourceUsageMetrics);
        }
    }

    public long getThreadId() {
        return threadId;
    }

    public EnumMap<ResourceStatsType, ResourceUsageInfo> getResourceUsageInfos() {
        return resourceUsageInfos;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean setActive(boolean active) {
        return isActive = active;
    }

    @Override
    public String toString() {
        return resourceUsageInfos + ", is_active=" + isActive;
    }
}
