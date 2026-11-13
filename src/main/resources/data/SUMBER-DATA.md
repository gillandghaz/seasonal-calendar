# Sumber dan metodologi dataset musim

Tanggal pemutakhiran paket: 13 November 2026.

File utama: `musim-indonesia.json`. Setiap entri adalah **prakiraan awal musim yang dipublikasikan**, bukan hasil pengamatan cuaca aktual. `referenceMonth` adalah bulan acuan mulai musim. `periodLabel` menyimpan dasarian atau rentang sebagaimana dinyatakan sumber. `exactDatesAvailable` sengaja `false`: sumber tidak menyediakan tanggal harian yang berlaku seragam untuk seluruh kota.

## Sumber yang ditambahkan pada pembaruan ini

| Lokasi | Periode acuan | Informasi | Sumber |
|---|---|---|---|
| Palembang | 2020-06 | Awal kemarau, sebagian besar Sumsel awal Juni; Palembang timur pertengahan Juni | [BMKG Staklim Sumsel – Prakiraan Musim Kemarau 2020](https://staklim-sumsel.bmkg.go.id/prakiraan-musim-kemarau-tahun-2020/) |
| Palembang | 2020-10 | Palembang tercantum dalam kelompok Sumatera yang diprakirakan mulai hujan pada Oktober | [BMKG – Buku PMH 2020/2021](https://content.bmkg.go.id/wp-content/uploads/Buku_PMH20-21.pdf) |
| Palembang | 2021-06 | Awal kemarau Palembang sekitar pertengahan Juni | [BMKG Staklim Sumsel – Prakiraan Musim Kemarau 2021](https://staklim-sumsel.bmkg.go.id/prakiraan-musim-kemarau-tahun-2021/) |
| Palembang | 2021-10 | ZOM 32 (Palembang tengah–barat) dan ZOM 35 (Palembang timur), awal hujan Sep II–Okt I | [BMKG Staklim Sumsel – Buletin PMH 2021/2022](https://staklim-sumsel.bmkg.go.id/wp-content/uploads/2021/09/Buletin-PMH-21-22.pdf) |
| Palembang | 2022-06 | ZOM 32 awal kemarau Mei III–Jun II; Palembang timur (ZOM 35) lebih awal | [BMKG Staklim Sumsel – Buletin PMK 2022](https://staklim-sumsel.bmkg.go.id/wp-content/uploads/2022/03/Buletin-PMK-22-cetak.pdf) |
| Palembang | 2026-06 | Awal kemarau awal–pertengahan Juni pada sebagian besar ZOM barat/timur; Palembang tersebar pada ZOM 125 dan 127 | [BMKG Staklim Sumsel – Prediksi Musim Kemarau 2026](https://staklim-sumsel.bmkg.go.id/prediksi-musim-kemarau-tahun-2026/) |
| Jakarta | 2022-04 | Ringkasan awal kemarau DKI April; Jakarta Timur/Selatan Juni | [Laporan media yang mengutip prakiraan BMKG](https://www.gridoto.com/read/223275638/pengendara-jakarta-waspada-berkendara-mei-september-ini-penjelasan-bmkg?page=all) |

## Sumber historis lain yang sudah tercatat dalam dataset awal

- [BMKG Staklim Sumsel – Prakiraan Musim Hujan 2022/2023](https://staklim-sumsel.bmkg.go.id/prakiraan-musim-hujan-tahun-2022-2023/)
- [BMKG Staklim Sumsel – Prakiraan Musim Kemarau 2023](https://staklim-sumsel.bmkg.go.id/prakiraan-musim-kemarau-tahun-2023/)
- [BMKG Staklim Sumsel – Prakiraan Musim Hujan 2023/2024](https://staklim-sumsel.bmkg.go.id/prakiraan-musim-hujan-tahun-2023-2024/)
- [BMKG Staklim Sumsel – Prakiraan Musim Kemarau 2024](https://staklim-sumsel.bmkg.go.id/prakiraan-musim-kemarau-tahun-2024/)
- [BMKG Staklim Sumsel – Prakiraan Musim Hujan 2024/2025](https://staklim-sumsel.bmkg.go.id/prakiraan-musim-hujan-tahun-2024-2025/)
- [BMKG Staklim Sumsel – Prediksi Musim Kemarau 2026](https://staklim-sumsel.bmkg.go.id/prediksi-musim-kemarau-tahun-2026/)
- [BMKG Staklim Sumsel – Prediksi Musim Hujan 2026/2027](https://staklim-sumsel.bmkg.go.id/wp-content/uploads/2026/11/Press-Release-PMH-2627_opt.pdf)
- [BMKG – Prakiraan Musim Hujan 2023/2024, Banten dan DKI Jakarta](https://content.bmkg.go.id/wp-content/uploads/Buku-PMH2023-24_rev.pdf)
- [BMKG – Prediksi Musim Kemarau 2023, Banten dan DKI Jakarta](https://content.bmkg.go.id/wp-content/uploads/Buku_PMK2023.pdf)
- [BMKG – Prediksi Musim Kemarau 2025](https://content.bmkg.go.id/wp-content/uploads/Buku-PMK25_merged_20250502.pdf)
- [BMKG – Update Informasi Iklim Dasarian III Oktober 2026](https://content.bmkg.go.id/wp-content/uploads/2026.11.01_Update-Informasi-Iklim-Dasarian-III-Oktober.pdf)

## Batasan penting

1. Jakarta dan Palembang terdiri dari lebih dari satu Zona Musim (ZOM). Satu kota tidak selalu mempunyai satu tanggal awal musim.
2. Sejumlah sumber hanya memberikan rentang dasarian atau ringkasan tingkat provinsi. Data ditampilkan sebagai rentang dan tidak diubah menjadi tanggal pasti.
3. Beberapa arsip lama memakai kode ZOM yang berbeda dengan pembagian ZOM terbaru. Kode dan cakupan pada detail entri mengikuti publikasi pada tahun tersebut.
4. Catatan prakiraan tidak boleh dibaca sebagai verifikasi kejadian cuaca aktual.
5. Mode empat musim dalam `musim-empat.json` menggunakan pembagian meteorologis berdasarkan bulan, bukan awal musim astronomis.
6. Estimasi aplikasi untuk bulan tanpa catatan BMKG tetap bukan data resmi.
