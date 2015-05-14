Some notes on the implementation
---

The backend/ module is shared between photo-service and exif-service. In reality, both would have their own backend and
perhaps a shared "common" module.

Maybe a bit heavy on the use of optionals.

You will notice a lot of "final" keywords everywhere. Inspections remind me to add them. It's a good bad habit although
it adds to Java's verbosity.

Queue implementation is not exactly production grade.

