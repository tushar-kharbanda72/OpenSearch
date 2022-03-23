/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */

package org.opensearch.tasks;

import java.util.PriorityQueue;

public class SearchTasksMemoryTracker {

    /*
        - Top n search requests are currently running or might have finished and we can't cancel (These should be removed)
        - In future it could be improved to estimate as well. Today it'll help only in cases where search request is running for
          atleast 5 secs.
        - logic :
            Memory% * (.50) + CPU% (.20) + Phase (.30) (search = 1, phase = 0)

        - memory overhead to be shared across nodes for potential ARS improvements
        - invalidation based on request rate factor that in with current total memory overhead. if request rate remains same consider overhead same.
          It's kind of estimation.
     */

    private TaskManager taskManager;

    class SearchRequestStats {
        public long totalTasks;
        public long currTotalMemoryOverhead;
        public PriorityQueue<Task> topNSearchTasks;
    }

    public SearchTasksMemoryTracker(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void filter() {

    }

    public void taskRegistered() {}

    public void taskUnregistered() {

    }

}
