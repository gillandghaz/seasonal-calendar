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
- Data awal musim BMKG ditampilkan saat tersedia pada bulan referensinya.
- Jika catatan BMKG untuk bulan tersebut tidak tersedia, aplikasi menampilkan estimasi bulanan dengan label yang jelas.
- Mode empat musim dengan pilihan belahan bumi.
- Teks panel informasi membungkus otomatis agar tidak terpotong.

## Dataset BMKG
Buka `src/main/resources/data/musim-indonesia.json`. Data yang dimasukkan adalah catatan awal musim yang ditemukan pada publikasi resmi BMKG atau publikasi Pemprov DKI yang mengutip BMKG.

**Penting:** `referenceMonth` menunjukkan bulan awal musim yang diprakirakan. Catatan tersebut bukan status musim harian untuk semua tanggal pada bulan itu. Palembang sebagian besar berada di ZOM 127, sedangkan Jakarta memiliki beberapa ZOM dengan awal musim berbeda. Periksa catatan `detail` sebelum menafsirkan data.

Estimasi untuk bulan tanpa catatan BMKG memakai pola bulan sederhana dan bukan data resmi.
