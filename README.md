
# 🗂️ Account Manager – Java Swing

A lightweight offline account manager built with **Java Swing**, featuring animated gradients and a minimalist interface. No databases, no third-party dependencies (other than music playback), and no internet required.

Developed by **Vitor Souza**, this project was designed as a simple way to store and view login credentials locally, with a sleek visual and smooth UX for desktop environments.

---

## 🎯 Features

- ✅ **Offline-First** – No internet connection required.
- 🖥️ **Modern UI** – Custom styled interface with animated gradient background.
- 🔐 **Secure-ish** – Passwords are saved locally, not sent anywhere.
- 🎵 **Background Music** – Retro-inspired soundtrack plays automatically on launch.
- 📜 **Persistent Storage** – Credentials are written to a `passlog.txt` on your Desktop.
- 🧰 **No Database** – Uses plain `.txt` files for simplicity.

---

## 📸 Preview

![preview-placeholder](docs/screenshot.png) <!-- Add a screenshot path if available -->

---

## 🧠 How It Works

The application is built entirely with **Java Swing**, using `JFrame`, `JPanel`, and custom rendering to achieve a visually appealing desktop GUI.

### 🔄 Gradient Background

The main frame background features a **diagonal animated gradient**, powered by HSB (Hue-Saturation-Brightness) color manipulation. This gives the UI a pulsing visual effect that cycles through warm tones every 30ms using `java.util.Timer`.

```java
startColor = Color.getHSBColor(0, 1.0f, 0.4f + 0.2f * sin(hue * 2 * π));
endColor   = Color.getHSBColor(0, 1.0f, 0.2f + 0.2f * cos(hue * 2 * π));
```

### 🎵 Music Integration

Background music is played via [`JLayer`](http://www.javazoom.net/javalayer/javalayer.html) MP3 player.

- Song path: `src/assets/song.mp3`
- Playback: Runs in a separate thread to avoid blocking UI events

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
User: john_doe
Email: john@example.com
password: mySecret123
------------------------------
```

> 📁 If the file doesn't exist, it is automatically created.

### 📖 Viewing Saved Accounts

Clicking **“Saved accounts”** opens a `JTextArea` inside a scrollable dialog showing all previously saved log entries from the text file on your Desktop.

---

## 🚀 Getting Started

### 1. Clone the Repo

```bash
git clone https://github.com/bbydlux/account-manager-swing.git
cd account-manager-swing
```

### 2. Run the App

Ensure you are using JDK 8+ and have [JLayer](http://www.javazoom.net/javalayer/javalayer.html) in your classpath.

```bash
javac -cp .:jlayer.jar src/gerenciador/pass.java
java  -cp .:jlayer.jar src.gerenciador.pass
```

> 🛠 If using an IDE like **VSCode** or **IntelliJ**, make sure to add `jlayer.jar` as a library.

---

## 📁 Project Structure

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

## 📝 Known Limitations

- No encryption (yet): passwords are saved as **plain text**
- No search or filter functionality
- Only supports saving to Desktop
- Single session song playback (does not loop or shuffle)

---

## 📌 Future Improvements

- [ ] AES encryption for stored credentials
- [ ] In-app password search/filter
- [ ] Option to export/import accounts
- [ ] Music toggle & playlist support
- [ ] Dark mode toggle

---

## 👨‍💻 Author

**Vitor Souza**  
A developer who prefers simplicity over complexity.  
Passionate about UI polish, even in Swing 😅

- [YouTube](https://www.youtube.com/channel/UCShSeONE08BE3c2Vw_F2hlA)
- [LinkedIn](https://www.linkedin.com/in/vitor-souza-dev/)
- [Instagram](https://instagram.com/bbydlux)

---

## 📄 License

This project is free to use, modify, break, or improve. No strings attached.  
If you do make something cool out of it — let me know, I’d love to see it.

---
