Some notes on the implementation
---

The backend/ module is shared between photo-service and exif-service. In reality, both would have their own backend and
perhaps a shared "common" module.

Queue implementation is not exactly production grade.

API, JSON request/response (de)serialization incomplete at this point.

Photos get buffered in memory during uploads/downloads.

File extensions are disregarded.

Maybe a bit heavy on the use of optionals.

A lot of "final" keywords.

