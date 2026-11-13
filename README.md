# Seasonal Calendar

Aplikasi kalender desktop berbasis **Java Swing** yang menampilkan informasi musim untuk dua mode: **musim Indonesia** (hujan dan kemarau, berdasarkan arsip prakiraan BMKG untuk Palembang dan Jakarta) serta **empat musim** (semi, panas, gugur, dingin) untuk belahan bumi utara dan selatan.

> **Penting:** data yang ditampilkan adalah **prakiraan awal musim** yang dipublikasikan, bukan observasi cuaca harian dan bukan prediksi hujan harian. Baca bagian [Batasan data](#batasan-data) sebelum menafsirkan hasilnya.

## Daftar isi

- [Fitur](#fitur)
- [Teknologi](#teknologi)
- [Prasyarat](#prasyarat)
- [Menjalankan aplikasi](#menjalankan-aplikasi)
- [Cara menggunakan](#cara-menggunakan)
- [Cara kerja](#cara-kerja)
- [Struktur proyek](#struktur-proyek)
- [Dataset](#dataset)
- [Batasan data](#batasan-data)
- [Menambah atau memperbarui data](#menambah-atau-memperbarui-data)
- [Pemecahan masalah](#pemecahan-masalah)
- [Status proyek dan rencana pengembangan](#status-proyek-dan-rencana-pengembangan)
- [Sumber data](#sumber-data)

## Fitur

- **Kalender bulanan** dengan rentang **Januari 2001 sampai Desember 2050**. Tombol navigasi dinonaktifkan di batas rentang.
- **Dua mode tampilan**, dipilih lewat dropdown di bagian atas:
  - **Musim Indonesia**: menampilkan musim hujan atau kemarau untuk lokasi terpilih.
  - **Empat Musim**: menampilkan musim semi, panas, gugur, atau dingin sesuai belahan bumi terpilih.
- **Dua lokasi**: Palembang dan Jakarta.
- **Arsip prakiraan awal musim BMKG** (22 catatan, 2020–2026) lengkap dengan periode, ringkasan, detail zona musim (ZOM), nama sumber, dan tautan sumber.
- **Tombol "Buka sumber BMKG"** untuk membuka tautan sumber catatan terpilih di browser bawaan sistem.
- **Estimasi bulanan berlabel jelas** untuk bulan yang tidak memiliki catatan BMKG, ditandai sebagai bukan data resmi.
- **Panel informasi dengan teks yang membungkus otomatis**, sehingga keterangan panjang tidak terpotong.
- **Tampilan mengikuti tema sistem** (system look and feel) bila tersedia.

## Teknologi

| Komponen | Keterangan |
|---|---|
| Bahasa | Java 25 (`maven.compiler.release` = 25) |
| Build tool | Maven |
| Antarmuka | Java Swing |
| Pembaca JSON | [Gson](https://github.com/google/gson) 2.13.2 |
| Encoding | UTF-8 |

## Prasyarat

- **JDK 25** (sesuai `pom.xml`). JDK yang lebih lama tidak akan bisa mengompilasi proyek ini kecuali nilai `maven.compiler.release` diturunkan.
- **Maven 3.x** (sudah terbundel di IntelliJ IDEA, atau pasang sendiri untuk memakai terminal).
- Lingkungan desktop dengan layar grafis. Aplikasi Swing tidak bisa berjalan di server tanpa display.
- Koneksi internet hanya diperlukan untuk mengunduh dependensi Maven dan untuk membuka tautan sumber dari dalam aplikasi.

## Menjalankan aplikasi

### Lewat IntelliJ IDEA (disarankan)

1. Clone repositori atau ekstrak ZIP-nya.
2. Buka folder `calendar` melalui IntelliJ IDEA.
3. Jalankan **Reload Maven Project** bila diminta, lalu pastikan Project SDK diarahkan ke JDK 25.
4. Jalankan `src/main/java/org/example/Main.java`.

### Lewat terminal

```bash
git clone https://github.com/gillandghaz/seasonal-calendar.git
cd seasonal-calendar

# kompilasi
mvn compile

# jalankan (memakai exec-maven-plugin yang di-resolve otomatis oleh Maven)
mvn exec:java -Dexec.mainClass=org.example.Main
```

`pom.xml` belum memuat konfigurasi pembuatan JAR yang bisa dijalankan langsung, jadi perintah di atas adalah cara termudah dari terminal. Lihat [rencana pengembangan](#status-proyek-dan-rencana-pengembangan).

## Cara menggunakan

1. **Pilih mode** di dropdown paling kiri: `Musim Indonesia` atau `Empat Musim`.
2. **Pilih lokasi** (mode Indonesia: Palembang atau Jakarta) atau **belahan bumi** (mode empat musim: utara atau selatan). Dropdown yang tidak relevan otomatis disembunyikan.
3. **Pindah bulan** dengan tombol `‹` dan `›` di atas kalender.
4. **Klik sebuah tanggal** untuk memilihnya. Hari ini diberi garis tepi, tanggal terpilih diberi warna biru.
5. **Baca panel "Informasi Musim"** di sisi kanan: nama musim, periode, penjelasan, dan sumber.
6. **Klik "Buka sumber BMKG"** bila tersedia untuk membuka dokumen atau halaman acuan.

Saat berpindah bulan, hari yang dipilih dipertahankan sejauh masih valid (misalnya 31 Januari menjadi 28 atau 29 Februari).

## Cara kerja

### Mode Musim Indonesia

Aplikasi mencari catatan di `musim-indonesia.json` dengan kunci pasangan **`locationId` + `referenceMonth`** (format `yyyy-MM`) dari tanggal yang dipilih.

- **Catatan ditemukan**: panel menampilkan musim (hujan atau kemarau), periode awal musim menurut BMKG, ringkasan, detail, dan sumber. Tombol sumber aktif.
- **Catatan tidak ditemukan**: panel menampilkan **estimasi** dengan label *"Estimasi berdasarkan pola bulanan"* dan *"Sumber: Estimasi kalender (bukan data BMKG)"*. Tombol sumber nonaktif.

Aturan estimasi (bukan data resmi):

| Lokasi | Musim Hujan | Musim Kemarau | Peralihan |
|---|---|---|---|
| Palembang | Oktober – April | Juni – September | Mei |
| Jakarta | November – Maret | Juni – September | April, Mei, Oktober |

### Mode Empat Musim

Memakai pembagian **musim meteorologis** berdasarkan bulan (bukan tanggal ekuinoks atau solstis astronomis), dari `musim-empat.json`:

| Musim | Belahan Utara | Belahan Selatan |
|---|---|---|
| Musim Semi | Maret – Mei | September – November |
| Musim Panas | Juni – Agustus | Desember – Februari |
| Musim Gugur | September – November | Maret – Mei |
| Musim Dingin | Desember – Februari | Juni – Agustus |

Rentang yang melewati pergantian tahun (misalnya Desember – Februari) ditangani secara otomatis.

## Struktur proyek

```text
calendar/
├── pom.xml
├── README.md
├── PEMBARUAN-DATASET.md              # catatan perubahan dataset terakhir
└── src/main/
    ├── java/org/example/
    │   ├── Main.java                 # titik masuk, mengatur look and feel lalu membuka jendela
    │   ├── model/
    │   │   └── SeasonForecast.java   # model satu catatan prakiraan (dipetakan Gson dari JSON)
    │   ├── service/
    │   │   ├── SeasonDataService.java  # memuat musim-indonesia.json dan mencari catatan per lokasi + bulan
    │   │   └── FourSeasonService.java  # memuat musim-empat.json dan menentukan musim dari tanggal + belahan bumi
    │   └── ui/
    │       └── CalendarFrame.java    # seluruh antarmuka Swing: kalender, selector, panel informasi
    └── resources/data/
        ├── musim-indonesia.json      # arsip prakiraan awal musim BMKG
        ├── musim-empat.json          # definisi empat musim meteorologis
        └── SUMBER-DATA.md            # sumber, metodologi, dan batasan dataset
```

Pemisahan tanggung jawab: **model** hanya menampung data, **service** membaca dan menjawab kueri data dari `resources`, **ui** hanya menampilkan dan menangani interaksi.

## Dataset

### `musim-indonesia.json`

Objek dengan field `datasetName`, `lastUpdated`, `notes`, dan larik `forecasts`. Saat ini berisi **22 catatan** (14 Palembang, 8 Jakarta; 12 musim hujan, 10 musim kemarau).

Skema satu catatan:

| Field | Tipe | Keterangan |
|---|---|---|
| `locationId` | string | `palembang` atau `jakarta` |
| `locationName` | string | Nama tampilan lokasi |
| `season` | string | `HUJAN` atau `KEMARAU` |
| `referenceMonth` | string | Bulan acuan awal musim, format `yyyy-MM` |
| `periodLabel` | string | Rentang atau dasarian sebagaimana tertulis di sumber (contoh: "Dasarian II Juni 2025 (ZOM 171)") |
| `summary` | string | Ringkasan satu kalimat |
| `detail` | string | Rincian cakupan ZOM atau wilayah dan catatan interpretasi |
| `sourceName` | string | Nama publikasi sumber |
| `sourceUrl` | string | Tautan sumber |
| `sourceDate` | string | Tanggal atau bulan publikasi sumber |
| `exactDatesAvailable` | boolean | Selalu `false`: sumber tidak memberi tanggal harian seragam untuk seluruh kota |

Contoh:

```json
{
  "locationId": "jakarta",
  "locationName": "Jakarta",
  "season": "KEMARAU",
  "referenceMonth": "2022-04",
  "periodLabel": "April 2022 (rata-rata wilayah DKI Jakarta)",
  "summary": "Ringkasan prakiraan BMKG yang diberitakan pada Mei 2022 ...",
  "detail": "Awal musim tidak seragam: Jakarta Timur dan Jakarta Selatan ...",
  "sourceName": "Prakiraan Musim Kemarau 2022 BMKG - laporan Kompas.com yang dimuat GridOto",
  "sourceUrl": "https://www.gridoto.com/read/223275638/...",
  "sourceDate": "2022-05",
  "exactDatesAvailable": false
}
```

### Cakupan saat ini

| Lokasi | Catatan (`referenceMonth`, K = kemarau, H = hujan) |
|---|---|
| Palembang (14) | 2020-06 K, 2020-10 H, 2021-06 K, 2021-10 H, 2022-06 K, 2022-09 H, 2023-05 K, 2023-11 H, 2024-06 K, 2024-10 H, 2025-06 K, 2025-09 H, 2026-06 K, 2026-11 H |
| Jakarta (8) | 2022-04 K, 2023-11 H, 2024-01 H, 2024-10 H, 2024-11 H, 2025-04 K, 2025-06 K, 2026-11 H |

Jakarta sengaja memiliki lebih sedikit catatan karena hanya entri yang berhasil diverifikasi ke sumber yang dimasukkan.

### `musim-empat.json`

Daftar delapan aturan (empat musim x dua belahan bumi) berisi `hemisphere` (`NORTH` atau `SOUTH`), `name`, `startMonth`, dan `endMonth`.

## Batasan data

Harap dibaca sebelum memakai data ini sebagai acuan.

1. **Prakiraan, bukan observasi.** Dataset memuat prakiraan awal musim yang diterbitkan BMKG. Ini bukan rekaman curah hujan aktual dan tidak boleh dibaca sebagai verifikasi kejadian cuaca.
2. **`referenceMonth` adalah bulan awal musim**, bukan status musim harian untuk setiap tanggal di bulan itu. Aplikasi menampilkan catatan yang sama untuk semua tanggal dalam bulan acuan tersebut.
3. **Satu kota bisa punya beberapa zona musim (ZOM).** Palembang sebagian besar berada di ZOM 127 (arsip lama memakai kode lain, mis. ZOM 32 dan 35), sedangkan Jakarta memiliki beberapa ZOM dengan awal musim berbeda. Periksa `detail` pada tiap catatan.
4. **Rentang tidak diubah menjadi tanggal pasti.** Banyak sumber hanya memberi dasarian (rentang sekitar 10 hari) atau ringkasan tingkat provinsi, sehingga ditampilkan apa adanya.
5. **Kode ZOM berubah antar tahun.** Kode dan cakupan di `detail` mengikuti publikasi pada tahun yang bersangkutan.
6. **Estimasi bukan data resmi.** Bulan tanpa catatan memakai pola bulanan sederhana yang diberi label estimasi.
7. **Satu catatan Jakarta (2022-04) bersumber dari media** yang mengutip prakiraan BMKG, bukan dari situs BMKG langsung. Hal ini dinyatakan pada `sourceName` dan `detail`-nya.
8. **Empat musim bersifat meteorologis**, bukan astronomis, sehingga tidak mengikuti tanggal ekuinoks atau solstis. Untuk lokasi tropis seperti Indonesia, mode ini hanya informatif.

Rincian metodologi dan daftar tautan sumber lengkap ada di [`src/main/resources/data/SUMBER-DATA.md`](src/main/resources/data/SUMBER-DATA.md).

## Menambah atau memperbarui data

### Menambah catatan prakiraan

1. Buka `src/main/resources/data/musim-indonesia.json`.
2. Tambahkan objek baru ke larik `forecasts` dengan **semua field** pada [skema](#musim-indonesiajson) di atas.
3. Pastikan:
   - `locationId` hanya `palembang` atau `jakarta` (sesuai pilihan di UI).
   - `referenceMonth` berformat `yyyy-MM`.
   - Tidak ada duplikat pasangan `locationId` + `referenceMonth`. Aplikasi hanya memakai catatan pertama yang cocok.
   - `sourceUrl` mengarah ke publikasi yang benar-benar memuat informasinya, dan `detail` menyebut cakupan ZOM atau wilayah bila sumber menyediakannya.
   - `exactDatesAvailable` tetap `false` kecuali sumber memang memberi tanggal harian yang berlaku seragam.
4. Perbarui `lastUpdated` dan tambahkan sumber baru ke `SUMBER-DATA.md`.
5. Catat perubahan di `PEMBARUAN-DATASET.md`.

Struktur JSON dan nama field **harus dipertahankan** karena dibaca langsung oleh `SeasonForecast` melalui Gson.

### Memvalidasi JSON dan duplikasi

```bash
python3 - <<'EOF'
import json, collections
data = json.load(open("src/main/resources/data/musim-indonesia.json", encoding="utf-8"))
keys = [(f["locationId"], f["referenceMonth"]) for f in data["forecasts"]]
dupes = [k for k, n in collections.Counter(keys).items() if n > 1]
print("Jumlah catatan:", len(keys))
print("Duplikat:", dupes or "tidak ada")
EOF
```

### Menambah lokasi baru

Perlu perubahan kode, bukan hanya data:

1. Tambahkan nama lokasi di `locationCombo` pada `CalendarFrame`.
2. Petakan indeks combo ke `locationId` (saat ini logikanya `index == 0 ? "palembang" : "jakarta"`, dipakai di `updateSeasonInformation` dan `openSource`).
3. Tambahkan aturan estimasi di `estimateIndonesianSeason`.
4. Tambahkan catatan lokasi itu ke `musim-indonesia.json`.

## Pemecahan masalah

| Gejala | Penyebab umum | Solusi |
|---|---|---|
| `release version 25 not supported` atau galat kompilasi sejenis | JDK yang dipakai lebih lama dari 25 | Pasang JDK 25 dan arahkan Maven atau IntelliJ ke sana |
| `File data/musim-indonesia.json tidak ditemukan.` | Resource belum tersalin ke `target/classes` | Jalankan `mvn clean compile` atau Reload Maven lalu Rebuild Project |
| `Gagal membaca data musim Indonesia.` | JSON rusak (koma berlebih, tanda kutip, encoding) | Validasi JSON dengan skrip di atas dan simpan file sebagai UTF-8 |
| Jendela tidak muncul / `HeadlessException` | Dijalankan tanpa layar grafis | Jalankan di lingkungan desktop |
| Tombol "Buka sumber BMKG" gagal | Sistem tidak mendukung `Desktop.browse` atau tidak ada browser bawaan | Salin `sourceUrl` dari JSON dan buka manual |
| Simbol `‹` `›` tampil sebagai kotak | Font `Segoe UI Symbol` tidak ada (non-Windows) | Cosmetic saja; navigasi tetap berfungsi. Ganti font tombol di `navButton` bila perlu |

## Status proyek dan rencana pengembangan

Proyek ini masih berupa aplikasi sederhana (`1.0-SNAPSHOT`). Hal yang saat ini belum tersedia dan bisa menjadi pengembangan lanjutan:

- Pengujian otomatis (folder `src/test` belum berisi tes) untuk `SeasonDataService` dan `FourSeasonService`.
- Konfigurasi pembuatan JAR yang bisa dijalankan (`maven-jar-plugin` atau `maven-shade-plugin`).
- Penambahan lokasi lain dan perluasan arsip BMKG, terutama untuk Jakarta.
- Pemisahan lokasi dari kode (daftar lokasi dibaca dari dataset, bukan di-*hardcode* di UI).
- Lisensi proyek belum ditentukan.

## Sumber data

- Prakiraan awal musim hujan dan kemarau dari [BMKG](https://www.bmkg.go.id/) dan Stasiun Klimatologi Sumatera Selatan, serta publikasi Pemprov DKI Jakarta yang mengutip BMKG.
- Daftar tautan sumber per catatan ada di field `sourceUrl` pada dataset dan dirangkum di [`SUMBER-DATA.md`](src/main/resources/data/SUMBER-DATA.md).

Proyek ini tidak berafiliasi dengan BMKG. Untuk keputusan yang bergantung pada cuaca atau iklim, rujuk selalu publikasi resmi BMKG terbaru.
