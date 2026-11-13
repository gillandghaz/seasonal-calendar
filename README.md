# Seasonal Calendar

Aplikasi kalender desktop Java Swing untuk menampilkan informasi musim Indonesia dan empat musim.

## Teknologi
- Java 25
- Maven
- Java Swing
- Gson untuk membaca dataset JSON

## Menjalankan di IntelliJ IDEA
1. Ekstrak ZIP.
2. Buka folder `calendar` melalui IntelliJ IDEA.
3. Reload Maven jika diminta.
4. Jalankan `src/main/java/org/example/Main.java`.

## Fitur
- Kalender dibatasi dari Januari 2001 sampai Desember 2050.
- Lokasi: Palembang dan Jakarta.
- Arsip prakiraan awal musim BMKG untuk Palembang dan Jakarta diperluas hingga 2020–2026 (21 catatan yang bersumber dan memiliki keterangan cakupan wilayah).
- Data awal musim BMKG ditampilkan saat tersedia pada bulan referensinya.
- Jika catatan BMKG untuk bulan tersebut tidak tersedia, aplikasi menampilkan estimasi bulanan dengan label yang jelas.
- Mode empat musim dengan pilihan belahan bumi.
- Teks panel informasi membungkus otomatis agar tidak terpotong.

## Dataset BMKG
Buka `src/main/resources/data/musim-indonesia.json`. Rincian asal data, tautan sumber, dan batas interpretasi dicatat di `src/main/resources/data/SUMBER-DATA.md`. Data yang dimasukkan adalah catatan awal musim yang ditemukan pada publikasi resmi BMKG atau publikasi Pemprov DKI yang mengutip BMKG.

**Penting:** `referenceMonth` menunjukkan bulan awal musim yang diprakirakan. Catatan tersebut bukan status musim harian untuk semua tanggal pada bulan itu. Palembang sebagian besar berada di ZOM 127, sedangkan Jakarta memiliki beberapa ZOM dengan awal musim berbeda. Periksa catatan `detail` sebelum menafsirkan data.

Estimasi untuk bulan tanpa catatan BMKG memakai pola bulan sederhana dan bukan data resmi.


## Catatan kualitas dan cakupan data
- Dataset memuat prakiraan awal musim yang diterbitkan BMKG, bukan observasi cuaca harian atau rekaman aktual curah hujan.
- Catatan historis tahun 2020–2026 tersedia untuk Palembang; Jakarta memiliki catatan yang berhasil diverifikasi pada sebagian tahun.
- Awal musim dapat berbeda antar-ZOM di dalam satu kota. Detail catatan menyebut cakupan ZOM/wilayah jika sumber menyediakannya.
- Bulan yang tidak memiliki catatan BMKG tetap menggunakan estimasi aplikasi yang diberi label estimasi. Jangan menganggap estimasi tersebut sebagai data resmi.
- Pembaruan data berikutnya sebaiknya dilakukan dengan menambahkan entri bersumber dan mempertahankan format field yang sudah ada.
