package dev.geovaneshimizu.subscription.api

import dev.geovaneshimizu.subscription.domain.subscription.IdempotentNewSubscriptionReceiver
import dev.geovaneshimizu.subscription.domain.subscription.NewSubscriptionListener
import dev.geovaneshimizu.subscription.domain.subscription.SubscriptionRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApiConfig {

    @Bean
    fun <T> newSubscriptionListener(newSubscriptionReceiver: IdempotentNewSubscriptionReceiver<T>,
                                    subscriptionRepository: SubscriptionRepository): NewSubscriptionListener<T> {
        return NewSubscriptionListener(newSubscriptionReceiver, subscriptionRepository)
    }
}
