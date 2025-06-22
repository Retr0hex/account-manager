
# 🗂️ Account Manager – Java Swing

A lightweight offline account manager built with **Java Swing**, featuring animated gradients and a minimalist interface. No databases, no third-party dependencies (other than music playback), and no internet required.

---

## 🎯 Features

- ✅ **Offline** – No internet connection required.
- 🖥️ **Gradient** – Custom styled interface with animated gradient background.
- 🔐 **Secure** – Passwords are saved locally with a log file.
- 🎵 **Music** – Simple soundtrack from a childhood game.
- 📜 **Local storage** – Credentials are written to a `passlog.txt` on your Desktop.

---

## 📸 Preview

First time opening the program.
![image](https://github.com/user-attachments/assets/47af9a43-48a0-4c07-8605-8853a92ed2fc)

Adding an account.
![image](https://github.com/user-attachments/assets/96b0474b-3b05-498d-bcff-9bf6a8882b85)

Checking saved accounts.
![image](https://github.com/user-attachments/assets/3e0e0f96-54b1-459c-9020-ee6c2256efb3)

---

## 🧠 How It Works

The application is built entirely with **Java Swing**, using `JFrame`, `JPanel`.

### 🔄 Gradient Background

The main frame background features a **diagonal animated gradient** with HSB color manipulation. This gives the UI a pulsing visual effect that cycles through red tones every 30ms using `java.util.Timer`.

```java
startColor = Color.getHSBColor(0, 1.0f, 0.4f + 0.2f * sin(hue * 2 * π));
endColor   = Color.getHSBColor(0, 1.0f, 0.2f + 0.2f * cos(hue * 2 * π));
```

### 🎵 Music Integration

Background music is played via [`JLayer`](http://www.javazoom.net/javalayer/javalayer.html) MP3 player.

- Song path: `src/assets/song.mp3`
- Playback: Runs in a separate thread to avoid blocking UI events
- Song is from Syphon Filter: Dark Mirror.

### 📝 Adding an Account

When clicking **“Add account”**, the interface displays a form with the following fields:

- Origin (e.g., website/app name)
- Username
- Email
- Password

After filling in all fields, clicking “Save account” appends the entry to the log file.

> ⚠️ All fields are required. Empty fields will trigger a warning dialog.

### 📂 Saved Log File

Your saved credentials are appended as plain text to:

```
~/Desktop/passlog.txt
```

Each entry is stored like:

```
Origin: example.com
User: test_s
Email: test@gmail.com
password: supertest123
------------------------------
```

> 📁 If the file doesn't exist, it is automatically created.

### 📖 Viewing Saved Accounts

Clicking **“Saved accounts”** opens a `JTextArea` inside a scrollable dialog showing all previously saved log entries from the text file on your Desktop.

---

## 🚀 How to run it

### 1. Clone the Repo

```bash
git clone https://github.com/retr0hex/account-manager.git
cd account-manager
```

### 2. Run the App

Ensure you are using JDK 8+ and have [JLayer](http://www.javazoom.net/javalayer/javalayer.html) in your classpath. (The file is also in the folder if you want it)

```bash
javac -cp .:jlayer.jar src/gerenciador/pass.java
java  -cp .:jlayer.jar src.gerenciador.pass
```

> 🛠 If using an IDE like **VSCode** or **NetBeans**, make sure to add `jlayer.jar` as a library.

---

## 📁 Structure

```
📦 src/
 └── gerenciador/
     └── pass.java          ← Main class
📂 assets/
 └── song.mp3               ← Background music
 └── logo.png               ← Logo displayed in the UI
```

---

## ⚙️ Dependencies

- **Java Swing** (built-in)
- **JLayer** for MP3 playback: [`jlayer-1.0.1.jar`](http://www.javazoom.net/javalayer/javalayer.html)

---


## 👨‍💻 Author

**Vitor Souza**  

You can also find me here:

- [YouTube](https://www.youtube.com/channel/UCShSeONE08BE3c2Vw_F2hlA)
- [LinkedIn](https://www.linkedin.com/in/vitorsouzaretr0/)
- [Instagram](https://instagram.com/bbydlux)

---

You are free to use this project as you wish. Break it, improve it, tweak it, have fun.
