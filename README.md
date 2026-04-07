# Tugas Praktikum Minggu 4 - State Management & MVVM

* **Nama : Muhammad Bimastiar**
* **NIM : 123140211**

## Deskripsi Tugas
Mengembangkan "My Profile App" dari minggu lalu dengan menerapkan arsitektur MVVM dan *State Management*. Berikut adalah fitur dan ketentuan yang diimplementasikan:
1. **Implementasi MVVM Pattern:**
    - Membuat `ProfileViewModel` dengan `StateFlow` untuk mengelola logika UI.
    - Membuat Data class `ProfileUiState` untuk menampung seluruh state aplikasi.
2. **Fitur Edit Profile:**
    - Menambahkan form (Dialog) untuk mengedit nama dan bio.
    - Menggunakan *State hoisting* untuk komponen `TextField`.
    - Menambahkan tombol *Save* yang memperbarui data langsung ke `ViewModel`.
3. **Fitur Dark Mode Toggle:**
    - Menambahkan komponen `Switch` untuk mengubah tampilan ke *dark/light mode*.
    - Menyimpan state tema di dalam `ViewModel` agar UI bereaksi secara otomatis.

## Struktur Folder
Proyek ini telah dirombak mengikuti standar arsitektur pemisahan *layer* (UI, Data, dan ViewModel). Berikut adalah susunan *package* utamanya:

```text
composeApp/src/commonMain/kotlin/org/example/project/
├── App.kt                 # Entry point, inisialisasi ViewModel, dan MaterialTheme
├── data/
│   └── ProfileUiState.kt  # Data class yang menampung seluruh state layar (Model)
├── ui/
│   └── ProfileScreen.kt   # Berisi komponen UI utama dan Form Edit (View)
└── viewmodel/
    └── ProfileViewModel.kt# Mengelola logika bisnis dan StateFlow (ViewModel)
````

## Cara Menjalankan Aplikasi (Langkah-langkah)

Proyek ini menggunakan basis **Jetpack Compose Multiplatform** (Android/Desktop). Berikut adalah panduan langkah demi langkah untuk menjalankannya:

1.  **Persiapan IDE:** Pastikan Anda menggunakan **Android Studio** versi terbaru.
2.  **Buka Proyek:** Pilih menu `File > Open...` dan arahkan ke folder proyek ini.
3.  **Tunggu Gradle Sync:** Perhatikan bagian pojok kanan bawah IDE. Tunggu hingga proses sinkronisasi Gradle selesai sepenuhnya.
4.  **Jalankan Aplikasi:** - Untuk **Android**: Pilih emulator atau perangkat fisik Android Anda, lalu klik tombol **Run** (segitiga hijau) atau tekan `Shift + F10`.
    - Untuk **Desktop** (jika dikonfigurasi): Pilih konfigurasi Desktop (contoh: `jvmRun`), lalu tekan tombol **Run**.
5.  **Uji Coba Fitur:** Setelah jendela aplikasi terbuka:
    - Klik tombol **Edit Profil** untuk mencoba fitur *State Hoisting* pada Form Edit.
    - Klik **Switch Dark Mode** di pojok kanan atas untuk melihat perubahan tema aplikasi secara reaktif.

## Hasil

### 1\. Tampilan Profile View (Light Mode)

<img width="802" height="724" alt="image" src="https://github.com/user-attachments/assets/99ee769c-48ff-4e53-ade5-135ce98f3019" />

### 2\. Tampilan Form Edit Profile

<img width="801" height="722" alt="image" src="https://github.com/user-attachments/assets/4bd4451f-3ae0-42c6-a236-a7bdf69c3574" />

### 3\. Tampilan Profile View (Dark Mode)

<img width="802" height="723" alt="image" src="https://github.com/user-attachments/assets/5c4c0ac7-cbd0-4670-8182-7285f54a2703" />