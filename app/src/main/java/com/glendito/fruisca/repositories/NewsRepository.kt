package com.glendito.fruisca.repositories

import com.glendito.fruisca.database.daos.NewsDao
import com.glendito.fruisca.database.entities.NewsEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface NewsRepository {
    suspend fun fetchData(): List<NewsEntity>
    suspend fun fetchDetail(id: Int): NewsEntity?
}

class NewsRepositoryImpl(
    private val newsDao: NewsDao,
    private val dispatcher: CoroutineDispatcher
): NewsRepository {

    override suspend fun fetchData(): List<NewsEntity> {
        return withContext(dispatcher) {
            if (!newsDao.isExists()) {
                newsData.forEach { news ->
                    newsDao.insert(news)
                }
            }
            newsDao.getAll()
        }
    }

    override suspend fun fetchDetail(id: Int): NewsEntity? {
        return withContext(dispatcher) {
            newsDao.getDetail(id)
        }
    }

    private val newsData = listOf(
        NewsEntity(
            title = "Hidup Sehat dan Bugar dengan Konsumsi Buah-Buahan",
            content = """
                Setiap  orang tentunya mendambakan memiliki tubuh yang sehat dan bugar, Namun untuk sehat dan bugar ini tidak bisa kita dapatkan dengan instan. Perlu adanya asupan nutrisi yang cukup , salah satunya dengan rajin mengkonsumsi buah- buahan.

                Melalui siaran radio kesehatan kemenkes RI, disampaikan oleh narasumber Tantri Warachesti Prihandini, AMG dari RSUP Persahabatan, menyampaikan bahwa: berdasarkan hasil riset kesehtan dasar ( Rikesda) tahun 2018, presentase sekurangnya konsumsi buah dan sayur di Indonesia adalah 95,5%, sedangkan pada kelompok anak usia sekolah persentasenya lebih tinggi yaitu sekitar 96%. Padahal, konsumsibuah-buahan merupakan salah satu bagian penting dalam mewujudkan Gizi Seimbang.
            """.trimIndent(),
            image = "https://rsuppersahabatan.co.id/assets/cms/uploads/images/ARTIKEL/3899749277.jpg"
        ),
        NewsEntity(
            title = "Manfaat Buah dan Sayuran untuk Kesehatan",
            content = """
                Berbagai  kajian  menunjukkan  bahwa  konsumsi  sayuran dan buah-buahan yang cukup turut berperan dalam menjaga kenormalan tekanan darah, kadar gula dan kolesterol darah. Konsumsi sayur dan buah  yang  cukup  juga  menurunkan  risiko  sulit  buang  air  besar  (BAB/sembelit)   dan   kegemukan. Hal   ini   menunjukkan   bahwa   konsumsi  sayuran dan  buah-buahan yang  cukup  turut  berperan  dalam pencegahan penyakit tidak menular kronik. Konsumsi sayuran dan  buah-buahan yang  cukup  merupakan  salah  satu  indikatorsederhana gizi seimbang.

                Berikut adalah beberpa manfaat buah buhan dan sayuran bagi kesehatan :
                
                1. Meningkatkan Daya Ingat.
                Zat antoksidan yang terkandung dalam buah buhan dan sayuran dapat melindungi sel sel otak dan membantu meningkatkan daya ingat.
                
                2. Membuat Tubuh Lebih Bugar.
                Buah buahan dan sayuran memilki kandungan vitamin dan mineral yang tinggi. Vitamin bersama dengan enzim bereaksi memproduksi energi; sehingga membuat tubuh menjadi lebih bugar.
                
                3. Melancarkan Buang Air Besar.
                Buah buahan dan sayuran mengandung serat yang tinggi yang dapat meningkatkan kelancaran metabolisme tubuh, serta melancarkan buang air besar. Lancarnya buang air besar menghindari penyerapan kembali sisa metabolisme oleh usus.
                
                4. Membantu Mengatasi Obesitas.
                Tingginya kandungan serat dalam buah buahan dan sayuran memberikan rasa kenyang yang lebih lama bahkan dapat mengurangi porsi makan yang berlebih.
                
                5. Mencegah dan Mengobati Kanker.
                Buah buahan dan sayurab kaya kan mineral, vitamin, serat, dan antioksidan. Nutrisi jenis ini dapat memperkuat tubuh serta meningkatkan imun untuk melawan berbagai jenis penyalit secara alami, termasuk penyakit kanker.
                
                6. Membuat Perasaan Lebih Bahagia.
                Aktioksidan dalam buah buhan dan sayuran terbukti mengurangi p[eradangan yang terjadi pada tubuh dan mampu melindungi seseorang dari depresi (antidepresan).
                
                Begitu besar manfaat mengkonsumsi buah buahan dan sayuran bagi kesehatan tubuh, maka mulai saat ini gantikanlan menu cemilan dan kudapan seperti kue, cokelat, gorengan dengan mengkonsumsi buah buahan dan sayuran; mungkin juice buah dan sayuran menjadi menu pilihan yang menarik.
            """.trimIndent(),
            image = "https://krakataumedika.com/images/2020/02/26/fruits-vegetables.jpg"
        ),
    )

}