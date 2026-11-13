# Pembaruan dataset Seasonal Calendar

## Perubahan
- Dataset prakiraan musim Indonesia bertambah dari 15 menjadi 22 catatan.
- Tambahan: Palembang 2020 (kemarau dan hujan), 2021 (kemarau dan hujan), 2022 (kemarau), 2026 (kemarau), serta Jakarta 2022 (kemarau).
- Referensi sumber dan penjelasan kualitas data tersedia pada `src/main/resources/data/SUMBER-DATA.md`.
- README diperbarui dengan cakupan dan batas interpretasi data.
- Struktur JSON dan field yang dibaca aplikasi dipertahankan agar kompatibel dengan kode Java saat ini.
- Tidak ada perubahan pada antarmuka, model Java, atau aturan kalender.

## Catatan
Data merupakan prakiraan BMKG pada tahun publikasi, bukan observasi aktual harian. Waktu awal musim dapat berbeda antar-ZOM dalam satu kota. Bulan yang tidak memiliki catatan resmi tetap menggunakan estimasi aplikasi.

## Validasi
- JSON berhasil diparse.
- Tidak ada duplikasi pasangan `locationId` + `referenceMonth`.
- Kompilasi Maven tidak dijalankan di lingkungan penyusunan: Maven tidak tersedia dan JDK yang tersedia adalah Java 21, sedangkan `pom.xml` mensyaratkan Java 25.
