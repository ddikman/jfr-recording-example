# Java Flight Recording Example

## Running

First build and run the application:

```shell
javac LoopApplication.java
java LoopApplication
```

It will run until you cancel it with `Ctrl+C`, just looping a thread sleep with a random seconds wait.

Then start the flight recording, making sure the `Uncategorized` events are recorded.

## Inspecting the events

Use the [Azul Missipon Control](https://www.azul.com/products/components/azul-mission-control/). This is a fork of the JDK Mission Control, either applicaton works fine though.

The two custom events will be logged under `Uncategorized`:

![Events screenshot](./events.png)