Tentu saja\! Berikut adalah rancangan file `README.md` untuk tugas Praktikum Minggu 5 kamu. Strukturnya sudah disesuaikan persis dengan gaya README minggu lalu, namun isinya sudah di-update untuk mencerminkan penambahan sistem navigasi (Navigation Compose).

Kamu tinggal menyalin teks di bawah ini dan mengganti *link* gambar *screenshot*-nya nanti setelah aplikasinya dijalankan.

-----

# Tugas Praktikum Minggu 5 - Navigasi Antar Layar (Navigation Compose)

* **Nama : Muhammad Bimastiar**
* **NIM : 123140211**

## Deskripsi Tugas

Mengembangkan proyek aplikasi dari minggu sebelumnya dengan mengimplementasikan Jetpack Navigation Compose untuk membuat perpindahan antar layar (*multi-screen*). Berikut adalah fitur dan ketentuan yang diimplementasikan pada praktikum ini:

1.  **Bottom Navigation:**
    - Menambahkan navigasi bawah dengan 3 tab: `Notes`, `Favorites`, dan `Profile`.
    - Mengintegrasikan `ProfileScreen` dari tugas minggu lalu ke dalam tab Profile.
2.  **Navigasi List ke Detail (Passing Arguments):**
    - Menerapkan navigasi dari layar *Note List* ke *Note Detail* dengan mengirimkan argument `noteId` bertipe Integer.
3.  **Floating Action Button (FAB):**
    - Menambahkan FAB pada layar utama Notes untuk melakukan navigasi ke layar *Add Note* (`AddNoteScreen`).
4.  **Navigasi Kembali (Back Navigation):**
    - Mengimplementasikan fungsi `popBackStack()` agar pengguna dapat kembali ke layar sebelumnya dengan benar (proper) tanpa penumpukan *stack* layar yang berlebihan.
5.  **Navigasi Edit Note:**
    - Menambahkan navigasi dari *Note Detail* ke *Edit Note screen* yang juga mengimplementasikan *passing argument* `noteId`.

## Struktur Folder

Proyek ini memperluas struktur MVVM sebelumnya dengan menambahkan *package* khusus untuk komponen UI tambahan dan pengaturan rute navigasi. Berikut adalah susunan *package* utamanya:

```text
composeApp/src/commonMain/kotlin/org/example/project/
├── App.kt                 # Entry point, inisialisasi NavHost, NavController, & Scaffold (BottomBar)
├── components/
│   └── BottomNav.kt       # Komponen UI untuk Bottom Navigation
├── data/
│   └── ProfileUiState.kt  # (Dari Praktikum 4) Data class penampung state
├── navigation/
│   └── Routes.kt          # Definisi Sealed Class untuk rute layar dan argument-nya
├── ui/
│   ├── NotesScreens.kt    # Kumpulan layar baru: NoteList, NoteDetail, AddNote, EditNote, Favorites
│   └── ProfileScreen.kt   # (Dari Praktikum 4) Layar profil pengguna
└── viewmodel/
    └── ProfileViewModel.kt# (Dari Praktikum 4) State holder untuk layar profil
```

## Cara Menjalankan Aplikasi (Langkah-langkah)

Proyek ini menggunakan basis **Jetpack Compose Multiplatform**. Berikut adalah panduan langkah demi langkah untuk menjalankannya:

1.  **Persiapan IDE:** Pastikan Anda menggunakan **Android Studio** versi terbaru.
2.  **Buka Proyek:** Pilih menu `File > Open...` dan arahkan ke folder proyek ini.
3.  **Tunggu Gradle Sync:** Pastikan *dependency* `navigation-compose` sudah terunduh dengan melihat indikator sinkronisasi Gradle di pojok kanan bawah IDE.
4.  **Jalankan Aplikasi:** - Untuk **Android**: Pilih emulator atau perangkat fisik Android Anda, lalu klik tombol **Run** (segitiga hijau) atau tekan `Shift + F10`.
    - Untuk **Desktop**: Pilih konfigurasi Desktop (contoh: `jvmRun`), lalu tekan tombol **Run**.
5.  **Uji Coba Fitur:** Setelah jendela aplikasi terbuka:
    - Gunakan **Bottom Navigation** di bagian bawah layar untuk berpindah antara tab *Notes*, *Favorites*, dan *Profile*.
    - Pada tab *Notes*, klik **Floating Action Button (+)** untuk berpindah ke layar Tambah Catatan.
    - Pada tab *Notes*, klik salah satu **Item Catatan** untuk masuk ke layar Detail (perhatikan `noteId` yang dikirim).
    - Di layar Detail, klik tombol **Edit** untuk masuk ke form pengubahan data.
    - Gunakan tombol **Kembali (Back)** untuk menguji implementasi `popBackStack()`.

## Hasil

### 1\. Tampilan Bottom Navigation (Tab Notes / Layar Utama)

*(Aplikasi menampilkan daftar catatan dan Floating Action Button)*

\<img width="802" height="724" alt="Screenshot Notes List" src="MASUKKAN\_LINK\_GAMBAR\_GITHUB\_DI\_SINI" /\>

### 2\. Tampilan Navigasi Detail & Passing Argument

*(Layar NoteDetailScreen yang berhasil menangkap `noteId`)*

\<img width="802" height="724" alt="Screenshot Note Detail" src="MASUKKAN\_LINK\_GAMBAR\_GITHUB\_DI\_SINI" /\>

### 3\. Tampilan Layar Tambah / Edit Catatan

*(Layar saat navigasi form edit/add terbuka)*

\<img width="802" height="724" alt="Screenshot Add/Edit Note" src="MASUKKAN\_LINK\_GAMBAR\_GITHUB\_DI\_SINI" /\>

### 4\. Tampilan Tab Profile Terintegrasi

*(Layar profil dari tugas sebelumnya yang kini masuk ke dalam tab navigasi)*

\<img width="802" height="724" alt="Screenshot Profile Tab" src="MASUKKAN\_LINK\_GAMBAR\_GITHUB\_DI\_SINI" /\>