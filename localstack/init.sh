#!/usr/bin/env bash

# SNS topic
awslocal sns create-topic --endpoint-url=http://localhost:4566 --name subscription-created

# SQS queue
awslocal sqs create-queue --endpoint-url=http://localhost:4566 --queue-name new-subscriptions

# Subscribing SQS to SNS
awslocal sns subscribe --endpoint-url=http://localhost:4566 --topic-arn arn:aws:sns:us-east-1:000000000000:subscription-created --protocol sqs --notification-endpoint http://localhost:4566/queue/new-subscriptions

echo "Local AWS stack created"
