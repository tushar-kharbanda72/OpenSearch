/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */

package org.opensearch.tasks;

import org.opensearch.common.ParseField;
import org.opensearch.common.Strings;
import org.opensearch.common.io.stream.StreamInput;
import org.opensearch.common.io.stream.StreamOutput;
import org.opensearch.common.io.stream.Writeable;
import org.opensearch.common.xcontent.ConstructingObjectParser;
import org.opensearch.common.xcontent.ToXContentFragment;
import org.opensearch.common.xcontent.XContentBuilder;
import org.opensearch.common.xcontent.XContentParser;

import java.io.IOException;
import java.util.Objects;

import static org.opensearch.common.xcontent.ConstructingObjectParser.constructorArg;

/**
 * Task resource usage information
 * <p>
 * Writeable TaskResourceUsage objects are used to represent resource usage
 * information of running tasks.
 */
public class TaskResourceUsage implements Writeable, ToXContentFragment {
    private final long cpuTimeInNanos;
    private final long memoryInBytes;

    public TaskResourceUsage(long cpuTimeInNanos, long memoryInBytes) {
        this.cpuTimeInNanos = cpuTimeInNanos;
        this.memoryInBytes = memoryInBytes;
    }

    /**
     * Read from a stream.
     */
    public static TaskResourceUsage readFromStream(StreamInput in) throws IOException {
        return new TaskResourceUsage(in.readVLong(), in.readVLong());
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        out.writeVLong(cpuTimeInNanos);
        out.writeVLong(memoryInBytes);
    }

    public long getCpuTimeInNanos() {
        return cpuTimeInNanos;
    }

    public long getMemoryInBytes() {
        return memoryInBytes;
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        builder.field(ResourceStats.CPU.toString(), cpuTimeInNanos);
        builder.field(ResourceStats.MEMORY.toString(), memoryInBytes);
        return builder;
    }

    public static final ConstructingObjectParser<TaskResourceUsage, Void> PARSER = new ConstructingObjectParser<>(
        "task_resource_usage",
        a -> new TaskResourceUsage((Long) a[0], (Long) a[1])
    );

    static {
        PARSER.declareLong(constructorArg(), new ParseField(ResourceStats.CPU.toString()));
        PARSER.declareLong(constructorArg(), new ParseField(ResourceStats.MEMORY.toString()));
    }

    public static TaskResourceUsage fromXContent(XContentParser parser) {
        return PARSER.apply(parser, null);
    }

    @Override
    public String toString() {
        return Strings.toString(this, true, true);
    }

    // Implements equals and hashcode for testing
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != TaskResourceUsage.class) {
            return false;
        }
        TaskResourceUsage other = (TaskResourceUsage) obj;
        return Objects.equals(cpuTimeInNanos, other.cpuTimeInNanos) && Objects.equals(memoryInBytes, other.memoryInBytes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpuTimeInNanos, memoryInBytes);
    }
}
