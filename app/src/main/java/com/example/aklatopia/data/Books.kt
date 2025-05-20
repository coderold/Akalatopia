package com.example.aklatopia.data

import com.example.aklatopia.R
import kotlinx.serialization.Serializable

@Serializable
data class BookCategory(val displayName: String, val progress: Int) {
    companion object {
        val CULTURES_AND_TRADITIONS = BookCategory("Cultures and Traditions", 5)
        val EDUCATIONAL_LEARNING = BookCategory("Educational Learning", 10)
        val POETRY_AND_RHYMES = BookCategory("Poetry and Rhymes", 0)
        val SCIENCE_FICTION = BookCategory("Science Fiction", 8)
        val FABLES = BookCategory("Fables", 5)
        val FOLKLORE = BookCategory("Folklore/Tales", 10)

        val allCategories = listOf(
            CULTURES_AND_TRADITIONS,
            EDUCATIONAL_LEARNING,
            POETRY_AND_RHYMES,
            SCIENCE_FICTION,
            FABLES,
            FOLKLORE
        ).also {
            require(it.all { category -> category.displayName.isNotBlank() }) {
                "Invalid category found in allCategories list"
            }
        }
    }   
}

data class Book(
    val id: String,
    val title: String,
    val cover: Int,
    val desc: String,
    val year: String,
    val author: String,
    val category: BookCategory,
    val synopsis: String,
    val ratings: Double,
    val totalRatings: Int,
    val totalReviews: Int,
)

val books = listOf(
    Book(
        id = "0",
        title = "Ang Pambihirang Buhok ni Lola",
        cover = R.drawable.ang_pambihirang_buhok_ni_lola_cover,
        desc = "buhok",
        year = "2003",
        author = "Rene O. Villanueva",
        category = BookCategory.FOLKLORE,
        synopsis = "Si Lola ay may mahaba at pambihirang buhok na tila may mahika. " +
                "Sa kabila ng mga tukso at pag-uusisa ng iba, ipinapakita ng kwento ang" +
                " kahalagahan ng pagtanggap at pagmamahal sa isang mahal sa buhay.",
        ratings = 3.23,
        totalReviews = 45,
        totalRatings = 7,
    ),
    Book(
        id = "1",
        title = "Alamat ng Gubat",
        cover = R.drawable.alamat_ng_gubat_cover,
        desc = "gubat",
        year = "2003",
        author = "Bob Ong",
        category = BookCategory.SCIENCE_FICTION,
        synopsis = "Si Tong, isang anak ng alimango, ay naglakbay sa gubat upang humanap ng gamot" +
                " sa kanyang ama. Nakilala niya ang iba’t ibang hayop na kumakatawan sa iba’t " +
                "ibang ugali ng tao. Isang satirikong kwento na puno ng aral at katatawanan.",
        ratings = 4.74,
        totalReviews = 245,
        totalRatings = 35,
    ),
    Book(
        id = "2",
        title = "Bilang! Isang Aklat ng Pagbilang",
        cover = R.drawable.bilang_isang_aklat_ng_pagbilang_cover,
        desc = "gubat",
        year = "2012",
        author = "Adarna House",
        category = BookCategory.EDUCATIONAL_LEARNING,
        synopsis = "Nagpapakilala ng mga numero mula 1–10 gamit ang masaya at makukulay na imahe.",
        ratings = 3.45,
        totalReviews = 68,
        totalRatings = 2,
    ),
    Book(
        id = "3",
        title = "Si Langgam at si Tipaklong",
        cover = R.drawable.si_langgam_at_si_tipaklong_cover,
        desc = "gubat",
        year = "1984",
        author = "Virgilio S. Almario",
        category = BookCategory.FABLES,
        synopsis = "Isang Filipino na bersyon ng klasikong pabula tungkol sa masipag na langgam at " +
                "sa tamad na tipaklong. Ang kwento ay nagtuturo ng kahalagahan ng pagsusumikap at " +
                "paghahanda para sa hinaharap, at ipinapakita ang mga resulta ng pagiging tamad at pabaya.",
        ratings = 3.23,
        totalReviews = 45,
        totalRatings = 7,
    ),
    Book(
        id = "4",
        title = "Ang alamat ng ampalaya",
        cover = R.drawable.alamat_ng_ampalaya_cover,
        desc = "gubat",
        year = "2002",
        author = "Augie D. Rivera, Jr.",
        category = BookCategory.FOLKLORE,
        synopsis = "Isinasalaysay ng librong ito ang alamat ng ampalaya, isang gulay na may " +
                "mapait na lasa. Ayon sa alamat, ang ampalaya ay naging mapait dahil sa kasakiman " +
                "at pagyayabang. Ang kuwento ay nagtuturo ng aral tungkol sa pagpapatawad, " +
                "pagpapakumbaba, at pagtanggap sa ating kalikasan.",
        ratings = 5.0,
        totalReviews = 23,
        totalRatings = 3,
    ),
    Book(
        id = "5",
        title = "Si Janus Sílang at ang Tiyanak ng Tábon",
        cover = R.drawable.si_janus_silang_cover,
        desc = "gubat",
        year = "2002",
        author = "Edgar Calabia Samar",
        category = BookCategory.SCIENCE_FICTION,
        synopsis = "Si Janus ay isang gamer na napasok sa mundo ng alamat, teknolohiya, at " +
                "sinaunang nilalang. Nahalo ang cyber world at mitolohiya sa kanyang " +
                "pakikipagsapalaran.",
        ratings = 2.53,
        totalReviews = 345,
        totalRatings = 2,
    ),
    Book(
        id = "6",
        title = "Ay! May Bukbok ang Ngipin ni Ani!",
        cover = R.drawable.ay_may_bukbok_ang_ngipin_ni_ani_cover,
        desc = "gubat",
        year = "2003",
        author = "Luis P. Gatmaitan",
        category = BookCategory.POETRY_AND_RHYMES,
        synopsis = "Sa pamamagitan ng tugma at tula, ipinaliwanag sa masayang paraan ang " +
                "kahalagahan ng pag-aalaga sa ngipin ni Ani.",
        ratings = 3.53,
        totalReviews = 345,
        totalRatings = 2,
    ),
    Book(
        id = "7",
        title = "Araw sa Palengke",
        cover = R.drawable.araw_sa_palengke_cover,
        desc = "gubat",
        year = "2008",
        author = "May Tobias-Papa",
        category = BookCategory.CULTURES_AND_TRADITIONS,
        synopsis = "Kuwento ng isang batang babae na sumama sa kanyang nanay sa palengke tuwing " +
                "Linggo. Ipinakikita ang buhay palengke at kaugalian ng pamimili sa lokal na komunidad.",
        ratings = 4.67,
        totalReviews = 35,
        totalRatings = 22,
    ),

)

data class BookList(
    val listName:String,
    val books: List<Book>
)

val list1 = listOf(
    books[1],
    books[2],
    books[3],
)

val list2 = listOf(
    books[0],
    books[4],
    books[5],
)

val bookList = listOf(
    BookList(
        listName = "My List 1",
        books = list1
    ),
    BookList(
        listName = "Books to Buy",
        books = list2
    )
)

val favorites = listOf(
    books[1],
    books[0],
    books[3]
)
