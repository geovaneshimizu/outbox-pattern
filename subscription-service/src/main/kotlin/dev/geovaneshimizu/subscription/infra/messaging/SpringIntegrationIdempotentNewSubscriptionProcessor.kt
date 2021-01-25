package dev.geovaneshimizu.subscription.infra.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import dev.geovaneshimizu.subscription.domain.subscription.IdempotencyNewSubscriptionKey
import dev.geovaneshimizu.subscription.domain.subscription.IdempotentNewSubscriptionProcessor
import dev.geovaneshimizu.subscription.domain.subscription.NewSubscription
import dev.geovaneshimizu.subscription.infra.spring.getPayloadMessageAs
import mu.KotlinLogging
import org.springframework.integration.handler.MessageProcessor
import org.springframework.integration.metadata.ConcurrentMetadataStore
import org.springframework.integration.selector.MetadataStoreSelector
import org.springframework.messaging.Message
import org.springframework.stereotype.Component

@Component
class SpringIntegrationIdempotentNewSubscriptionProcessor(private val objectMapper: ObjectMapper,
                                                          idempotencyKeyStore: ConcurrentMetadataStore) :
        IdempotentNewSubscriptionProcessor<Message<String>>, MessageProcessor<String> {

    companion object {
        val logger = KotlinLogging.logger { }
    }

    private val idempotentReceiver by lazy { MetadataStoreSelector(this, idempotencyKeyStore) }

    override fun acceptIfNotExists(message: Message<String>, consumer: (NewSubscription) -> Unit): NewSubscription {
        val newSubscription = message.getPayloadMessageAs<NewSubscription>(this.objectMapper)
        if (this.idempotentReceiver.accept(message)) {
            consumer.invoke(newSubscription)
        } else {
            logger.info { "Duplicated $newSubscription; Discarding..." }
        }

        return newSubscription
    }

    override fun processMessage(message: Message<*>?): String? {
        @Suppress("UNCHECKED_CAST")
        return (message as? Message<String>)
                ?.getPayloadMessageAs<NewSubscription>(this.objectMapper)
                ?.let { IdempotencyNewSubscriptionKey(it) }
    }
}
