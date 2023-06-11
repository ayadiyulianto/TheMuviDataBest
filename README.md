# TheMuviDataBest

Dicoding - Submission Android Expert

![TheMuviDataBest](https://github.com/ayadiyulianto/TheMuviDataBest/actions/workflows/main.yml/badge.svg)

## Skenario Testing

Adapun detail skenario testing aplikasi ini adalah sebagai berikut:

### Unit Testing

#### TmdbRepositoryTest

- Memuat List Movie
    - Memanipulasi data ketika pemanggilan response list movie dengan data dummy
    - Memastikan metode pemanggilan response list movie terpanggil
    - Memastikan data List movie tidak null
    - Memastikan jumlah data List movie sesuai dengan yang diharapkan

- Memuat List Tv Show
    - Memanipulasi data ketika pemanggilan response list tv show dengan data dummy
    - Memastikan metode pemanggilan response list tv show terpanggil
    - Memastikan data List Tv Show tidak null
    - Memastikan jumlah data List Tv Show sesuai dengan yang diharapkan

- Memuat Detail Movie
    - Memanipulasi data ketika pemanggilan response detail movie dengan data dummy
    - Memastikan metode pemanggilan response detail movie terpanggil
    - Memastikan data response detail movie tidak null
    - Memastikan Id Movie pada response detail movie sesuai dengan yang diharapkan

- Memuat Detail Tv Show
    - Memanipulasi data ketika pemanggilan response detail tv show dengan data dummy
    - Memastikan metode pemanggilan response detail tv show terpanggil
    - Memastikan data response detail tv show tidak null
    - Memastikan Id tv show pada response detail tv show sesuai dengan yang diharapkan

- Memuat Detail Tv Show dengan Season Detail
    - Memanipulasi data ketika pemanggilan Tv Show with Season Detail dengan data dummy
    - Memastikan metode pemanggilan Tv Show with Season Detail terpanggil
    - Memastikan data Tv Show with Season Detail tidak null
    - Memastikan Id Tv Show pada Tv Show with Season Detail sesuai dengan yang diharapkan

- Memuat List Favorite Movie
    - Memanipulasi data ketika pemanggilan list movie favorite dengan data dummy
    - Memastikan metode pemanggilan list movie favorite terpanggil
    - Memastikan data list movie favorite tidak null
    - Memastikan Id movie pada list movie favorite sesuai dengan yang diharapkan

- Memuat List Favorite Tv Show
    - Memanipulasi data ketika pemanggilan list tv show favorite dengan data dummy
    - Memastikan metode pemanggilan list tv show favorite terpanggil
    - Memastikan data list tv show favorite tidak null
    - Memastikan Id tv show pada list tv show favorite sesuai dengan yang diharapkan

- Menambahkan movie ke dalam favorit Movie
    - Mengubah status favorit dalam data movie menjadi true
    - Memastikan data dalam database sesuai dengan yang diharapkan

- Menambahkan tv show ke dalam favorit Tv show
    - Mengubah status favorit dalam data tv show menjadi true
    - Memastikan data dalam database sesuai dengan yang diharapkan

- Menghapus movie dari daftar favorit
    - Mengubah status favorit dalam data movie menjadi false
    - Memastikan data dalam database sesuai dengan yang diharapkan

- Menghapus tv show dari daftar favorit Tv show
    - Mengubah status favorit dalam data tv show menjadi false
    - Memastikan data dalam database sesuai dengan yang diharapkan

#### MovieDetailViewModelTest

- Memuat Movie
    - Memanipulasi data ketika pemanggilan data movie di kelas repository.
    - Memastikan metode di kelas repository terpanggil.
    - Memastikan data movie tidak null
    - Memastikan data movie sesuai dengan yang diharapkan

- Menambahkan movie ke dalam favorit
    - Mengubah status favorit dalam movie ke dalam menjadi true (difavoritkan)
    - Memastikan data dalam database sesuai dengan yang diharapkan

#### MoviesViewModelTest

- Memuat list movie
    - Memastikan data List movie tidak null
    - Memastikan jumlah data List movie sesuai dengan yang diharapkan

#### TvShowDetailViewModelTest

- Memuat Tv Show with Season Detail
    - Memanipulasi data ketika pemanggilan data tv show with season detail di kelas repository.
    - Memastikan metode di kelas repository terpanggil.
    - Memastikan data tv show with season detail apakah null atau tidak.
    - Membandingkan data tv show with season detail sudah sesuai dengan yang diharapkan atau tidak.
  
- Menambahkan tv show ke dalam favorit
    - Mengubah status favorit dalam tv show ke dalam menjadi true (difavoritkan)
    - Memastikan data dalam database sesuai dengan yang diharapkan
  
#### TvShowsViewModelTest

- Memuat list tv show
    - Memanipulasi data ketika pemanggilan data list tv show di kelas repository.
    - Memastikan metode di kelas repository terpanggil.
    - Memastikan data List Tv Show tidak null
    - Memastikan jumlah data List Tv Show sesuai dengan yang diharapkan

### Instrumentation Testing

#### MainActivityTest

- Menampilkan data list movie
    - Gulir CollapsingToolbarLayout untuk memunculkan bottom navigation
    - Klik bottom navigation untuk bagian movies
    - Memastikan rv_movies dalam keadaan tampil
    - Gulir rv_movies ke posisi data ke-10

- Menampilkan halaman detail movie
    - Gulir CollapsingToolbarLayout untuk memunculkan bottom navigation
    - Klik bottom navigation untuk bagian movies
    - Memberi tindakan klik pada data pertama di rv_movies
    - Memastikan ImageView untuk movie backdrop dapat dilihat dan ditampilkan
    - Memastikan ImageView untuk poster movie dapat dilihat dan ditampilkan
    - Memastikan FAB untuk Movie Favorit dapat ditampilkan
    - Memastikan TextView untuk judul movie dapat ditampilkan
    - Memastikan RatingBar untuk movie dapat ditampilkan
    - Memastikan TextView genre movie dapat ditampilkan
    - Memastikan TextView tanggal perilisan movie dapat ditampilkan
    - Memastikan TextView synopsis movie dapat ditampilkan
    - Memastikan YT Player untuk menampilkan Trailer Movie dapat dilihat dan ditampilkan

- Menampilkan data list tv show
    - Gulir CollapsingToolbarLayout untuk memunculkan bottom navigation
    - Klik bottom navigation untuk bagian Tv Shows
    - Memastikan rv_tvShows dalam keadaan tampil
    - Gulir rv_tvShows ke posisi data ke-10

- Menampilkan halaman detail tv show
    - Gulir CollapsingToolbarLayout untuk memunculkan bottom navigation
    - Klik bottom navigation untuk bagian Tv Shows
    - Memberi tindakan klik pada data pertama di rv_tvShows
    - Memastikan ImageView untuk tv show backdrop dapat dilihat dan ditampilkan
    - Memastikan ImageView untuk poster tv show dapat dilihat dan ditampilkan
    - Memastikan FAB untuk tv show Favorit dapat ditampilkan
    - Memastikan TextView untuk judul tv show dapat ditampilkan
    - Memastikan RatingBar untuk tv show rating dapat ditampilkan
    - Memastikan TextView genre tv show dapat ditampilkan
    - Memastikan TextView tanggal perilisan tv show dapat ditampilkan
    - Memastikan TextView synopsis tv show dapat ditampilkan
    - Memastikan rv_seasons dapat ditampilkan dengan baik
    - Memastikan YT Player untuk menampilkan Trailer Movie dapat dilihat dan ditampilkan
