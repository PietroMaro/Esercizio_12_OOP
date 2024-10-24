package p12.exercise;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.ArrayList;

public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q> {

    private Map<Q, List<T>> queues = new HashMap<>();

    @Override
    public Set<Q> availableQueues() {
        return this.queues.keySet();
    }

    @Override
    public void openNewQueue(Q queue) {
        if (this.queues.containsKey(queue)) {
            throw new IllegalArgumentException("Elemento già contentuo");
        } else {
            this.queues.put(queue, new ArrayList<>());
        }
    }

    @Override
    public boolean isQueueEmpty(Q queue) {
        if (!this.queues.containsKey(queue)) {
            throw new IllegalArgumentException("Fila inesistente");
        } else {
            if (this.queues.get(queue).isEmpty()) {
                return true;
            } else {

                return false;
            }

        }
    }

    @Override
    public void enqueue(T elem, Q queue) {
        if (!this.queues.containsKey(queue)) {
            throw new IllegalArgumentException("Fila inesistente");
        } else {
            this.queues.get(queue).add(elem);
        }
    }

    @Override
    public T dequeue(Q queue) {
        if (!this.queues.containsKey(queue)) {
            throw new IllegalArgumentException("Fila inesistente");
        } else {

            return this.queues.get(queue).isEmpty() ? null : this.queues.get(queue).removeFirst();
        }
    }

    @Override
    public Map<Q, T> dequeueOneFromAllQueues() {
        Map<Q, T> allDequeue = new HashMap<>();

        for (Q i : this.queues.keySet()) {
            if (this.isQueueEmpty(i)) {
                allDequeue.put(i, null);
            } else {
                allDequeue.put(i, this.queues.get(i).getFirst());
            }
        }

        return allDequeue;

    }

    @Override
    public Set<T> allEnqueuedElements() {
        Set<T> allEnqueued = new LinkedHashSet<>();
        for (Q i : this.queues.keySet()) {
            allEnqueued.addAll(this.queues.get(i));
        }
        return allEnqueued;
    }

    @Override
    public List<T> dequeueAllFromQueue(Q queue) {
        ArrayList<T> entireQueue;

        if (!this.queues.containsKey(queue)) {
            throw new IllegalArgumentException("Fila inesistente");
        } else {
            entireQueue = new ArrayList<>(this.queues.get(queue));
            this.queues.put(queue, new ArrayList<>());
            return entireQueue;
        }

    }

    @Override
    public void closeQueueAndReallocate(Q queue) {
        Q newQueue = null;

        if (!this.queues.containsKey(queue)) {
            throw new IllegalArgumentException("Fila inesistente");
        } else if (this.availableQueues().size() == 1) {
            throw new IllegalStateException("Non ci sono file alternative");
        } else {
            for (Q q : this.queues.keySet()) {
                if (q != queue) {
                    newQueue = q;
                }
            }
            for (T i : dequeueAllFromQueue(queue)) {
                enqueue(i, newQueue);

            }
            this.queues.remove(queue);
        }
    }

}
