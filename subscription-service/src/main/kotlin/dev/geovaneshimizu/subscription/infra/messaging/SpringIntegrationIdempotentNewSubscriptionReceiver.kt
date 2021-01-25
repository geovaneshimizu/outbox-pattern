package dev.geovaneshimizu.subscription.infra.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import dev.geovaneshimizu.subscription.domain.subscription.IdempotencyNewSubscriptionKey
import dev.geovaneshimizu.subscription.domain.subscription.IdempotentNewSubscriptionReceiver
import dev.geovaneshimizu.subscription.domain.subscription.NewSubscription
import mu.KotlinLogging
import org.springframework.integration.handler.MessageProcessor
import org.springframework.integration.metadata.ConcurrentMetadataStore
import org.springframework.integration.selector.MetadataStoreSelector
import org.springframework.messaging.Message
import org.springframework.stereotype.Component

@Component
class SpringIntegrationIdempotentNewSubscriptionReceiver(private val objectMapper: ObjectMapper,
                                                         idempotencyKeyStore: ConcurrentMetadataStore) :
        IdempotentNewSubscriptionReceiver<Message<String>>, MessageProcessor<String> {

    companion object {
        val logger = KotlinLogging.logger { }
    }

    private val idempotentReceiverImpl by lazy { MetadataStoreSelector(this, idempotencyKeyStore) }

    override fun acceptNewSubscription(message: Message<String>, consumer: (NewSubscription) -> Unit): NewSubscription? {
        return if (this.idempotentReceiverImpl.accept(message)) {
            val newSubscription = message.getPayloadMessageAs<NewSubscription>(this.objectMapper)
            logger.info { "Accepted $newSubscription" }
            consumer.invoke(newSubscription)
            newSubscription
        } else {
            null
        }
    }

    override fun processMessage(message: Message<*>?): String? {
        @Suppress("UNCHECKED_CAST")
        return (message as? Message<String>)
                ?.getPayloadMessageAs<NewSubscription>(this.objectMapper)
                ?.let { IdempotencyNewSubscriptionKey(it) }
    }
}
