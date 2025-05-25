package com.insightweb.streaming.usecase

import com.insightweb.streaming.usecase.repository.AggregatedMetricsRepository
import com.insightweb.domain.TrackingEvent
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.concurrent.BlockingDeque
import java.util.concurrent.LinkedBlockingDeque

@Service
class TrackingEventScheduler(
    private val aggregatedMetricsRepository: AggregatedMetricsRepository
) {
    private val deque: BlockingDeque<MutableList<TrackingEvent>> = LinkedBlockingDeque()

    fun addToDeque(event: TrackingEvent) {
        while (true) {
            val lastElement = deque.peekLast()

            // Если последнего элемента нет — добавляем новый список
            if (lastElement == null) {
                deque.putLast(mutableListOf(event))
                return
            }

            // Если размер последнего < SIZE — добавляем в него
            synchronized(lastElement) {
                if (lastElement.size < SIZE) {
                    lastElement.add(event)
                    return
                }
            }

            // Если последний элемент уже заполнен — добавляем новый список
            deque.putLast(mutableListOf(event))
            return
        }
    }

    @Scheduled(fixedDelay = 5000)
    fun schedule() {
        val batch = deque.pollFirst()
        if (batch != null && batch.isNotEmpty()) {
            aggregatedMetricsRepository.insertEvents(batch)
        }
    }

    private companion object {
        const val SIZE = 5;
    }
}