# ⚡ Concurrent Programming — University Notes & Labs

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![University](https://img.shields.io/badge/Type-University%20Course-4B8BBE?style=for-the-badge)
![Topic](https://img.shields.io/badge/Topic-Concurrent%20Programming-6A0DAD?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-In%20Progress-green?style=for-the-badge)

A structured collection of Java programs, labs, and exercises from my university course on **Concurrent Programming**. Covers foundational to intermediate concepts including threads, synchronization, deadlocks, and race conditions — with working code examples for each topic.

---

## 📚 Topics Covered

| # | Topic | Description |
|---|-------|-------------|
| 1 | **Threads & Synchronization** | Creating and managing threads, `synchronized` blocks, `wait()`/`notify()`, thread lifecycle |
| 2 | **Deadlocks & Race Conditions** | Identifying and preventing deadlocks, detecting race conditions, lock ordering strategies |

---

## 🗂️ Project Structure

```
concurrent-programming/
│
├── threads-and-synchronization/
│   ├── BasicThreadExample.java
│   ├── SynchronizedCounter.java
│   ├── ProducerConsumer.java
│   └── WaitNotifyExample.java
│
├── deadlocks-and-race-conditions/
│   ├── DeadlockDemo.java
│   ├── DeadlockPrevention.java
│   ├── RaceConditionDemo.java
│   └── SafeRaceCondition.java
│
└── README.md
```

> 📝 Structure may evolve as new topics are added throughout the course.

---

## 🚀 Getting Started

### Prerequisites

- Java **17** or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code with Java extension)

### Running an Example

1. **Clone the repository**
   ```bash
   git clone https://github.com/parkash34/concurrent_programming.git
   cd concurrent-programming
   ```

2. **Compile a file**
   ```bash
   javac threads-and-synchronization/BasicThreadExample.java
   ```

3. **Run it**
   ```bash
   java BasicThreadExample
   ```

---

## 🧵 Threads & Synchronization

This section covers the core building blocks of concurrent Java programs.

**Key concepts:**
- Extending `Thread` vs implementing `Runnable`
- The `synchronized` keyword — methods and blocks
- Thread states: New → Runnable → Blocked → Waiting → Terminated
- `wait()`, `notify()`, and `notifyAll()` for inter-thread communication
- The classic **Producer-Consumer** problem

**Example snippet:**
```java
public class SynchronizedCounter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}
```

---

## 💀 Deadlocks & Race Conditions

This section explores the pitfalls of concurrent programming and strategies to avoid them.

**Key concepts:**
- The **four conditions** for deadlock (Coffman conditions)
- Lock ordering to prevent circular waits
- Using `tryLock()` from `java.util.concurrent.locks`
- Identifying race conditions with shared mutable state
- Making classes thread-safe

**Example snippet:**
```java
// Prevent deadlock by always acquiring locks in the same order
synchronized (lockA) {
    synchronized (lockB) {
        // critical section
    }
}
```

---

## 👤 Author

**Om Parkash** — [LinkedIn](https://www.linkedin.com/in/om-parkash-a93a87275) · [GitHub](https://github.com/parkash34)

> 📌 *This repository is for educational purposes as part of a university concurrent programming course.*