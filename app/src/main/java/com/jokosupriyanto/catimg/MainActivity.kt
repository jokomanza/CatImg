package com.jokosupriyanto.catimg

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import com.yuyakaido.android.cardstackview.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), CardStackListener {

    lateinit var cardStackView: CardStackView
    private val manager by lazy { CardStackLayoutManager(this, this) }
    private val adapter by lazy { CardStackAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchCat()

        cardStackView = findViewById<CardStackView>(R.id.card_stack_view)
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        manager.setStackFrom(StackFrom.Bottom)
        manager.setVisibleCount(3)
        manager.setSwipeThreshold(0.3f)

        openGithub()
    }

    private fun openGithub() {
        findViewById<ImageView>(R.id.imgOpenGithub)!!.setOnClickListener {
            val uri = Uri.parse("https://github.com/jokomanza")
            startActivity(Intent.createChooser(Intent(Intent.ACTION_VIEW, uri), "Open with"))
        }
    }

    override fun onCardDisappeared(view: View?, position: Int) {

    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {
            paginate()
        if (manager.topPosition == adapter.itemCount - 1) {
        }
    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {

    }

    override fun onCardRewound() {

    }

    private fun paginate() {
        fetchCat()
    }

    private fun fetchCat() {
        val request = ApiService.buildService(CatApi::class.java)

        for (n in 1..5) {
            val call = request.getMovies()
            call.enqueue(object : Callback<Cat> {
                override fun onResponse(call: Call<Cat>, response: Response<Cat>) {
                    if (response.isSuccessful){
                        val old = adapter.getSpots()
                        val new = old.plus(response.body()!!.getCat())
                        val callback = CatDiffCallback(old, new)
                        val result = DiffUtil.calculateDiff(callback)
                        adapter.setSpots(new)
                        result.dispatchUpdatesTo(adapter)
                    }
                }

                override fun onFailure(call: Call<Cat>, t: Throwable) {
                    Toast.makeText( this@MainActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                    t.printStackTrace()
                }
            })
        }

    }
}