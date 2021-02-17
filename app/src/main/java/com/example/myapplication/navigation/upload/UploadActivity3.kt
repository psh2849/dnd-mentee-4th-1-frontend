package com.example.myapplication.navigation.upload

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import kotlinx.android.synthetic.main.activity_upload3.*

class UploadActivity3 : AppCompatActivity() {

    companion object {
        private const val REQUEST_GET_IMAGE = 105
        private const val PERMISSION_CODE = 100
    }

    private var select_cut: Int = 0
    private var recipeTitle: String? = null
    private var recipeList = ArrayList<RecipeDTO.Recipe>()
    private var saveFilterList = ArrayList<String>()
    private var thumbnail: Uri? = null
    private var mainFoodTagList = ArrayList<String>()
    private var subFoodTagList = ArrayList<String>()
    private var positionMain = 0

    private lateinit var adapter: UploadRecipeAdapter
    private lateinit var itemMain: RecipeDTO.Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload3)

        callRecycler()

        getItems()

        makeRecyclerView()

        btn_upload_recipe_prev2.setOnClickListener {
            clickPrevButton()
        }
        btn_preview.setOnClickListener {
            clickPreviewButton()
        }
    }

    private fun clickPrevButton() {
        val intent = Intent(this, UploadActivity2::class.java)
        intent.putExtra("number", select_cut)
        intent.putExtra("filter", saveFilterList)
        intent.putExtra("title", recipeTitle)
        startActivity(intent)
    }

    private fun clickPreviewButton() {
        val intent = Intent(App.instance, UploadActivity4::class.java)
        intent.putExtra("number", select_cut)
        intent.putExtra("filter", saveFilterList)
        intent.putExtra("thumbnail", thumbnail)
        intent.putExtra("mainfood", mainFoodTagList)
        intent.putExtra("subfood", subFoodTagList)
        intent.putExtra("recipeList", recipeList)
        intent.putExtra("recipeTitle", recipeTitle)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }
    private fun makeRecyclerView() {
        when (select_cut) {
            3 -> for (i in 1..3) {
                recipeList.add(RecipeDTO.Recipe(i.toString(), null, null))
            }
            6 -> for (i in 1..6) {
                recipeList.add(RecipeDTO.Recipe(i.toString(), null, null))
            }
            9 -> for (i in 1..9) {
                recipeList.add(RecipeDTO.Recipe(i.toString(), null, null))
            }
            else -> null
        }
    }

    private fun getItems() {
        if (intent.hasExtra("number")) {
            select_cut = intent.getIntExtra("number", 1)
            Log.d("number", select_cut.toString())
        }
        if (intent.hasExtra("filter")) {
            saveFilterList = intent.getStringArrayListExtra("filter")!!
            Log.d("savefilterList", saveFilterList.toString())
        }
        if (intent.hasExtra("thumbnail")) {
            thumbnail = intent.getParcelableExtra("thumbnail")
            Log.d("thumbnail", thumbnail.toString())
        }
        if (intent.hasExtra("mainfood")) {
            mainFoodTagList = intent.getStringArrayListExtra("mainfood")!!
            Log.d("mainfood", mainFoodTagList.toString())
        }
        if (intent.hasExtra("subfood")) {
            subFoodTagList = intent.getStringArrayListExtra("subfood")!!
            Log.d("subfood", subFoodTagList.toString())
        }
        if (intent.hasExtra("recipeTitle")) {
            recipeTitle = intent.getStringExtra("recipeTitle")
            Log.d("title", recipeTitle.toString())
        }
    }

    private fun callRecycler() {
        var rv_recipe_list = findViewById(R.id.rv_upload_recipe) as RecyclerView
        adapter = UploadRecipeAdapter(recipeList) { position, item ->
            positionMain = position
            itemMain = item

            checkPermissions()
        }

        rv_recipe_list.adapter = adapter
        rv_recipe_list.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_recipe_list.setHasFixedSize(true)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GET_IMAGE) {
            when (requestCode) {
                REQUEST_GET_IMAGE ->
                    try {
                        val uri = data?.data
                        recipeList[positionMain].image = uri.toString()

                        adapter.notifyDataSetChanged()
                        showItem()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
            }
        }
    }

    private fun pickUpGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GET_IMAGE)
    }

    private fun checkPermissions() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, PERMISSION_CODE)
        } else {
            pickUpGallery()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickUpGallery()
                } else {
                    Toast.makeText(App.instance, "권한이 없습니다.", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun showItem() {
        for (i in 0..select_cut - 1) {
            Log.d(
                "log",
                "${i} 번째 ,number : " + recipeList.get(i).number + "comment :" + recipeList.get(i).comment.toString() + "image : " + recipeList.get(
                    i
                ).image.toString()
            )
        }
    }
}