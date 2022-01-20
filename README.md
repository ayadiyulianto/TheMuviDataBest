# TheMuviDataBest
 Dicoding - Submission Belajar Android Jetpack Pro


## Skenario Testing
Adapun detail skenario testing aplikasi ini adalah sebagai berikut:

### Unit Testing

#### MovieDetailViewModelTest
- Memuat detail Movie
  - Memastikan data movie tidak null
  - Memastikan data pada detail movie sesuai dengan yang diharapkan

#### MoviesViewModelTest
- Memuat list Movie
  - Memastikan data List movie tidak null
  - Memastikan jumlah data pada list movie sesuai dengan yang diharapkan

#### TvShowDetailViewModelTest
- Memuat detail Tv Show
  - Memastikan data Tv Show tidak null
  - Memastikan jumlah data Tv Show sesuai dengan yang diharapkan

#### TvShowsViewModelTest
- Memuat list Tv Show
  - Memastikan data Tv Show tidak null
  - Memastikan jumlah data pada List Tv Show sesuai dengan yang diharapkan


### Instrumentation Testing

#### MainActivityTest
- Menampilkan data list movies
  - Gulir CollapsingToolbarLayout untuk memunculkan bottom navigation
  - Klik bottom navigation untuk bagian movies
  - Memastikan rv_movies dalam keadaan tampil
  - Gulir rv_movies ke posisi data terakhir
  
- Menampilkan halaman detail movie
  - Gulir CollapsingToolbarLayout untuk memunculkan bottom navigation
  - Klik bottom navigation untuk bagian movies
  - Memberi tindakan klik pada data pertama di rv_movies
  - Memastikan ImageView untuk movie backdrop dapat dilihat dan ditampilkan
  - Memastikan ImageView untuk poster movie dapat dilihat dan ditampilkan
  - Memastikan FAB untuk Movie Favorit dapat ditampilkan
  - Memastikan TextView untuk judul movie tampil sesuai yang diharapkan
  - Memastikan RatingBar untuk movie dapat ditampilkan
  - Memastikan TextView tanggal perilisan movie tampil sesuai yang diharapkan
  - Memastikan TextView synopsis movie tampil sesuai yang diharapkan
  - Memastikan YT Player untuk menampilkan Trailer Movie dapat dilihat dan ditampilkan

- Menampilkan data list tv shows
  - Gulir CollapsingToolbarLayout untuk memunculkan bottom navigation
  - Klik bottom navigation untuk bagian Tv Shows
  - Memastikan rv_tvShows dalam keadaan tampil
  - Gulir rv_tvShows ke posisi data terakhir

- Menampilkan halaman detail tv show
  - Gulir CollapsingToolbarLayout untuk memunculkan bottom navigation
  - Klik bottom navigation untuk bagian Tv Shows
  - Memberi tindakan klik pada data pertama di rv_tvShows
  - Memastikan ImageView untuk tv show backdrop dapat dilihat dan ditampilkan
  - Memastikan ImageView untuk poster tv show dapat dilihat dan ditampilkan
  - Memastikan FAB untuk tv show Favorit dapat ditampilkan
  - Memastikan TextView untuk judul tv show tampil sesuai yang diharapkan
  - Memastikan RatingBar untuk tv show rating dapat ditampilkan
  - Memastikan TextView tanggal perilisan tv show tampil sesuai yang diharapkan
  - Memastikan TextView synopsis tv show tampil sesuai yang diharapkan
  - Memastikan rv_seasons dapat ditampilkan dengan baik
  - Memastikan rv_seasons dapat digulir ke posisi terakhir
  - Memastikan YT Player untuk menampilkan Trailer Movie dapat dilihat dan ditampilkan
