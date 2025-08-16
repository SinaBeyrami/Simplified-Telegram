# Simplified Telegram — Java Console Messenger

A clean, dependency-free console application that models a subset of Telegram’s functionality. It showcases clear **object-oriented design**, **regex-based command routing**, and **role-aware messaging** across **Channels**, **Groups**, and **Private Chats**.

<p align="center">
  <em>Java • OOP • Command-line UI • In-memory state</em>
</p>

---

## Highlights

- **Multi-Chat Types**:  
  - **Channel** — broadcast model; only the owner can post  
  - **Group** — collaborative chat; all members can post  
  - **Private Chat** — one-to-one conversation
- **Account Flow**: register → login → create/join chats → enter chat → send/list
- **Command Grammar**: concise, regex-driven commands for a predictable CLI UX
- **Separation of Concerns**: `LoginMenu` / `MessengerMenu` / `ChatMenu` cleanly split flows
- **Lightweight**: pure Java (`java.util`), no external libraries or build tools required

---

## Quick Start

> Requires **Java 8+**.

**Compile & Run (flat source folder):**
```bash
javac *.java
java Main
````

**Compile & Run (sources in `src/`):**

```bash
javac -d out src/*.java
java -cp out Main
```

---

## Command Reference

> Type commands exactly as shown.

### Login Menu

* **Register**

  ```
  /register i <id> u <username> p <password>/gm
  ```
* **Login**

  ```
  /login i <id> p <password>/gm
  ```
* **Exit**

  ```
  exit
  ```

### Messenger Menu (after login)

* **List all channels**

  ```
  show all channels
  ```
* **Create a channel**

  ```
  /create new channel i <channelId> u <name>/gm
  ```
* **Join a channel**

  ```
  /join channel i <channelId>/gm
  ```
* **Create a group**

  ```
  /create new group i <groupId> u <name>/gm
  ```
* **Start a private chat**

  ```
  /start a new private chat with i <userId>/gm
  ```
* **List my chats**

  ```
  show my chats
  ```
* **Enter a chat**

  ```
  /enter <type> i <id>/gm
  ```
* **Logout**

  ```
  logout
  ```

### Chat Menu (inside a chat)

* **Send a message**

  ```
  /send a message c <text>/gm
  ```

  *(In Channels, only the owner can post.)*
* **Show all messages**

  ```
  show all messages
  ```
* **Show all members**

  ```
  show all members
  ```

  *(Not available for Private Chats.)*
* **Add a member (channel/group owner)**

  ```
  /add member i <userId>/gm
  ```
* **Back**

  ```
  back
  ```

---

## Example Session

```
/register i u1 u Alice p Passw0rd!* /gm
User has been created successfully!

/register i u2 u Bob p Passw0rd!* /gm
User has been created successfully!

/login i u1 p Passw0rd!*/gm
User successfully logged in!

/create new channel i ch1 u news/gm
Channel news has been created successfully!

show all channels
All Channels:
1. news, id: ch1, members: 1

/join channel i ch1/gm
You have successfully joined the channel!

/enter channel i ch1/gm
You have successfully entered the chat!

/send a message c Hello from the owner/gm
Message has been sent successfully!

show all messages
Messages:
Alice(u1): "Hello from the owner"

back
logout
```

---

## Architecture Overview

```
Main
└── LoginMenu
    ├── register/login commands → Messenger (user store)
    └── on success → MessengerMenu(currentUser)
        ├── create/join/list/enter → Messenger + User
        └── enter chat → ChatMenu(currentUser, Chat)
            ├── send/show/add → Chat (messages, members)
            └── back → MessengerMenu

Core Models
├── Messenger        # global state: users, channels, groups
├── User             # id, name, password, owned/joined chats
├── Chat (base)      # members, messages, owner, id, name
│   ├── Channel      # owner-only messaging
│   ├── Group        # all members can post
│   └── PrivateChat  # one-to-one
└── Message          # owner + content

Commands
└── Commands enum    # regex definitions + matcher helper
```

**Design Principles**

* **Encapsulation**: Chats own their member/message lists; `User` owns their personal chat registry.
* **Single Responsibility**: Menus focus on interaction flow; models focus on state and behavior.
* **Explicit Permissions**: Channel posting rights enforced at command handling time.

---

## Project Structure

```
.
├─ Main.java
├─ LoginMenu.java
├─ MessengerMenu.java
├─ ChatMenu.java
├─ Commands.java
│
├─ Messenger.java
├─ User.java
├─ Chat.java
├─ Channel.java
├─ Group.java
├─ PrivateChat.java
└─ Message.java
```

---

## Why This Project

This codebase demonstrates how to build a modular CLI messenger with:

* **OOP modeling** of chat systems
* **Regex-driven** command parsing
* **Role-aware** operations and state transitions
* **Readable, maintainable** Java without external dependencies

---


License: MIT © Sina Beyrami
