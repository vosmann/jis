# JIS: Java Image System
---

## About JIS

JIS is a simple prototype of two services exposing REST(-ish) APIs and sharing information asynchronously over 
a queue (RabbitMQ) and Amazon S3.

## Implementation notes

- The `backend/` module is shared between photo-service and exif-service. This could be improved by each service having 
  their own backend module to reduce coupling and enable service independance and easier changes.
- The RabbitMQ code is not production grade, but provides an sample implementation of asynchronous communication.
- Uses the notorious MongoDB for storage.
- Photos get fully buffered in memory during uploads/downloads from S3.
- File extensions are disregarded.
- Heavy on the use of optionals and "finals".

## TODO:

1. Finish API and JSON request/response (de)serialization,
2. Index data in Solr

