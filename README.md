# TheMuviDataBest
 Dicoding - Submission Belajar Android Jetpack Pro


## Skenaria Testing
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

#### MovieDetailViewModelTest
- Memuat Movie
  - Memanipulasi data ketika pemanggilan data movie di kelas repository.
  - Memastikan metode di kelas repository terpanggil.
  - Memastikan data movie tidak null
  - Memastikan data movie sesuai dengan yang diharapkan

#### MoviesViewModelTest
- Memuat list movie
  - Memastikan data List movie tidak null
  - Memastikan jumlah data List movie sesuai dengan yang diharapkan

#### TvShowDetailViewModelTest
- Memuat Tv Show
  - Memanipulasi data ketika pemanggilan data tv show di kelas repository.
  - Memastikan metode di kelas repository terpanggil.
  - Memastikan data Tv Show tidak null
  - Memastikan data Tv Show sesuai dengan yang diharapkan

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
  - Gulir rv_movies ke posisi data terakhir
  
- Menampilkan halaman detail movie
  - Gulir CollapsingToolbarLayout untuk memunculkan bottom navigation
  - Klik bottom navigation untuk bagian movies
  - Memberi tindakan klik pada data pertama di rv_movies
  - Memastikan TextView untuk judul movie tampil sesuai yang diharapkan
  - Memastikan RatingBar untuk movie dapat ditampilkan
  - Memastikan TextView tanggal perilisan movie tampil sesuai yang diharapkan
  - Memastikan TextView synopsis movie tampil sesuai yang diharapkan
  - Memastikan FAB untuk Movie Favorit dapat ditampilkan
  - Memastikan YT Player untuk menampilkan Trailer Movie dapat dilihat dan ditampilkan
  - Memastikan ImageView untuk movie backdrop dapat dilihat dan ditampilkan
  - Memastikan ImageView untuk poster movie dapat dilihat dan ditampilkan

- Menampilkan data list tv show
  - Gulir CollapsingToolbarLayout untuk memunculkan bottom navigation
  - Klik bottom navigation untuk bagian Tv Shows
  - Memastikan rv_tvShows dalam keadaan tampil
  - Gulir rv_tvShows ke posisi data terakhir

- Menampilkan halaman detail tv show
  - Gulir CollapsingToolbarLayout untuk memunculkan bottom navigation
  - Klik bottom navigation untuk bagian Tv Shows
  - Memberi tindakan klik pada data pertama di rv_tvShows
  - Memastikan TextView untuk judul tv show tampil sesuai yang diharapkan
  - Memastikan RatingBar untuk tv show rating dapat ditampilkan
  - Memastikan TextView tanggal perilisan tv show tampil sesuai yang diharapkan
  - Memastikan TextView synopsis tv show tampil sesuai yang diharapkan
  - Memastikan FAB untuk tv show Favorit dapat ditampilkan
  - Memastikan YT Player untuk menampilkan Trailer Movie dapat dilihat dan ditampilkan
  - Memastikan ImageView untuk tv show backdrop dapat dilihat dan ditampilkan
  - Memastikan ImageView untuk poster tv show dapat dilihat dan ditampilkan
  - Memastikan rv_seasons dapat ditampilkan dengan baik
  - Memastikan rv_seasons dapat digulir ke posisi terakhir
