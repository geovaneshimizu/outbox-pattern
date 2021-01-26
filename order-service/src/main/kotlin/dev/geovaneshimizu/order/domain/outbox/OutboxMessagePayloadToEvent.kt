package dev.geovaneshimizu.order.domain.outbox

interface OutboxMessagePayloadToEvent<T> : (Any, Class<*>) -> T
